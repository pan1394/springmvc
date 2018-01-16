package com.yilin.www.spring.mvc.controller.test;

import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

 
public class TestControllerTest extends BaseTestFramework {

	/*private MockMvc mockMvc;
	@Before
	public void setUp(){
		TestController controller = new TestController();
		MockHttpServletResponse response = new MockHttpServletResponse();   
		controller.setRes(response);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();  
		MockHttpServletRequest req = new MockHttpServletRequest();
	}*/
	@Test
	public void testHelloWorld() throws Exception  {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/hello/samer"))   
	            .andDo(MockMvcResultHandlers.print())   
	            .andReturn(); 
	}
	
	@Test
	public void testResponse() throws Exception  {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/response/headers"))   
	            .andDo(MockMvcResultHandlers.print())   
	            .andReturn(); ;
	}
}
