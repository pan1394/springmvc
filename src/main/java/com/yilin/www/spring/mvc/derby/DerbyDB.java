package com.yilin.www.spring.mvc.derby;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
	
	  public DerbyDB(){
		  File f = new File("C:\\yp15911\\workspace_luna\\SpringMVC\\src\\main\\resources\\samle.sql");
		  String table = "student";
		  this.init(f, table); 
	  }
		@Autowired
		@Qualifier("simpleJdbcTemplate")
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
	  
	            /*InputStream sqlFileIn = new FileInputStream(sqlFile);  
	            StringBuilder sqlSb = new StringBuilder();  
	            byte[] buff = new byte[1024];  
	            int byteRead = 0;  
	            while ((byteRead = sqlFileIn.read(buff)) != -1) {  
	                sqlSb.append(new String(buff, 0, byteRead, "utf-8"));  
	            }  
	            // Windows 下换行是 \r\n, Linux 下是 \n  
	            String[] sqlArr = sqlSb.toString()  
	                    .split("(;\\s*\\r\\n)|(;\\s*\\n)");  
	            for (int i = 0; i < sqlArr.length; i++) {  
	                String sql = sqlArr[i].replaceAll("--.*", "").trim();  
	                if (!sql.equals("")) {  
	                    sqlList.add(sql);  
	                    logger.info("加载 SQL 语句：{}", sql);  
	                }  
	            } */ 
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
	  
	    
	  public boolean isTableExist(final String tableName){  
	        
		  	boolean bool = this.jdbcTemplate.execute(new ConnectionCallback<Boolean>(){
				@Override
				public Boolean doInConnection(Connection con)
						throws SQLException, DataAccessException {
					 ResultSet rs = con.getMetaData().getTables(null, null, tableName, null);  
			         return rs.next();  
				}
	    		
	    	});
	        return bool;  
	  } 
	  
	  public boolean init(File sqlFile, String tableName){
		  if(!this.isTableExist(tableName))
		  this.jdbcTemplate.batchUpdate(this.loadSql(sqlFile));
		  return true;
	  }
}
