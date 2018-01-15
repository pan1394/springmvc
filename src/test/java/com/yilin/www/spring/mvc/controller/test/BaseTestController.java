package com.yilin.www.spring.mvc.controller.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.yilin.www.spring.mvc.controller.TestController;

@ContextConfiguration(locations = { "classpath:mvc-servlet.xml"  })  
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)  
public class BaseTestController {

	private MockMvc mockMvc;
	@Before
	public void setUp(){
		TestController controller = new TestController();
		MockHttpServletResponse response = new MockHttpServletResponse();   
		controller.setRes(response);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();  
		MockHttpServletRequest req = new MockHttpServletRequest();
	}
	@Test
	public void testHelloWorld() throws Exception  {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/hello/samer"))   
	            .andDo(MockMvcResultHandlers.print())   
	            .andReturn(); ;
	}
	
	@Test
	public void testResponse() throws Exception  {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/response/entity/headers"))   
	            .andDo(MockMvcResultHandlers.print())   
	            .andReturn(); ;
	}
}
