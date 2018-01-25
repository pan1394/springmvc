package com.yilin.www.spring.mvc.derby;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DerbyDB {

	  Logger logger = LoggerFactory.getLogger(this.getClass());
 
	  /*@Qualifier("simpleJdbcTemplate")
	  @Autowired(required = true)*/
	  private JdbcTemplate jdbcTemplate;
	  @Autowired
	  public DerbyDB(@Qualifier("simpleJdbcTemplate") JdbcTemplate jdbcTemplate) throws IOException {
		  this.jdbcTemplate = jdbcTemplate;
		  Resource resource = new ClassPathResource("sample.sql");
	 	  File f = resource.getFile(); 
	 	  String table = "token_t";
		  this.init(f,table);
	  }
	  public String[] loadSql(File sqlFile) {  
	        List<String> sqlList = new ArrayList<String>();  
	        Scanner s = null;
	        try {     
	        	Reader reader = new FileReader(sqlFile);
	        	s = new Scanner(reader);
	        	while(s.hasNextLine()){
	        		String state = s.nextLine();
	        		sqlList.add(state);
	        		logger.info("added sql entry {}", state);  
	        	}
	        } catch (Exception e) {  
	            logger.error("During loading a exception \'{}\' alerts", e.getMessage());  
	        }  
	        finally {
	        	s.close();
	        	s=null;
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
	        
		  	Set<String> set = this.jdbcTemplate.execute(new ConnectionCallback<Set<String>>(){
				@Override
				public Set<String>  doInConnection(Connection con)
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
	  
	  public boolean init(File sqlFile, String tableName){
		  if(!this.isTableExist(tableName)){
			  this.jdbcTemplate.batchUpdate(this.loadSql(sqlFile));
		  }
		  return true;
	  }
}
