package com.yilin.www.spring.mvc.derby;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DerbyDB {

	  Logger logger = LoggerFactory.getLogger(this.getClass());
 
	  @Qualifier("simpleJdbcTemplate")
	  @Autowired(required = true)
	  private JdbcTemplate jdbcTemplate;
	  /** 
	     * 读取 SQL 文件，获取 SQL 语句 
	     *  
	     * @param sqlFile 
	     *            SQL 脚本文件 
	     * @return List<sql> 返回所有 SQL 语句的 String[] 
	     * @throws Exception 
	     */  
	    public String[] loadSql(File sqlFile) {  
	        List<String> sqlList = new ArrayList<String>();  
	        try {     
	        	Reader reader = new FileReader(sqlFile);
	        	Scanner s = new Scanner(reader);
	        	while(s.hasNextLine()){
	        		String state = s.nextLine();
	        		sqlList.add(state);
	        		logger.info("加载 SQL 语句：{}", state);  
	        	}
	        } catch (Exception e) {  
	            logger.error("读取 SQL 文件，获取 SQL 语句异常：{}", e.getMessage());  
	        }  
	        if (sqlList.size() > 0) {  
	            String[] sqlArr = new String[sqlList.size()];  
	            sqlList.toArray(sqlArr);  
	            return sqlArr;  
	        } else {  
	            return null;  
	        }  
	    }  
	  
	    
	  public boolean isTableExist(final String schema, final String tableName){  
	        
		  	Set set = this.jdbcTemplate.execute(new ConnectionCallback<Set>(){
				@Override
				public Set doInConnection(Connection con)
						throws SQLException, DataAccessException {
					DatabaseMetaData meta = con.getMetaData();  
					ResultSet res = meta.getTables(null, null, null, new String[]{"TABLE"});  
		            HashSet<String> set=new HashSet<String>();  
		            while (res.next()) {  
		                set.add(res.getString("TABLE_NAME"));  
		            }   
			        return set;  
				}
	    		
	    	});
	        return set.contains(tableName.toUpperCase()) ;  
	  } 
	  
	  public boolean init(File sqlFile, String schema, String tableName){
		  if(!this.isTableExist(schema, tableName)){
			  this.jdbcTemplate.batchUpdate(this.loadSql(sqlFile));
		  }
		  return true;
	  }
}
