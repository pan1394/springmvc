package com.yilin.www.spring.dao.hibernate.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
/**
 * @author Rest Controller for MOR services
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({
	"classpath:spring-context.xml", 
}) 
public class BaseDaoFramework {

	 
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Before
	public void setup() {
		 	
	}
	
 
 
	
}
