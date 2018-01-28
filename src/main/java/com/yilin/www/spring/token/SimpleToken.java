package com.yilin.www.spring.token;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
 
import com.yilin.www.spring.mvc.utils.UUIDUtils;

@Component
public class SimpleToken extends JdbcDaoSupport implements TokenManager{
 
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired 
	public void setDs(DataSource dataSource) {
	    setDataSource(dataSource);
	}
	
	@Override
	public String generateToken() {
		String uuid = UUIDUtils.get(); 
		return uuid;
	}

	@Override
	public void save(String Token) {
		StringBuffer sb = new StringBuffer("insert into token_t values(?,'A')");
		int l = this.getJdbcTemplate().update(sb.toString(),Token);
		if(l == 1 )
		logger.info("{} has been saved.", Token);
	}

	@Override
	public boolean auth(String Token) {
		if(Token == null) return false;
		boolean pass = false;
		StringBuffer sb = new StringBuffer("select count(1) from token_t where token =? and is_active='A'");
		Long l = this.getJdbcTemplate().queryForObject(sb.toString(),new Object[]{Token}, Long.class);
		pass = l == 1 ? true : false;
		return pass;
	}

	@Override
	public void delete(String Token) {
		StringBuffer sb = new StringBuffer("update token_t set is_active='D' where token =? ");
		int i = this.getJdbcTemplate().update(sb.toString(), Token);
		if(i > 0)
		logger.info("{} has been deleted.", Token);
	}
}
