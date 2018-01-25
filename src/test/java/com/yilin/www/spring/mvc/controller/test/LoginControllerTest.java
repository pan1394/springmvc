package com.yilin.www.spring.mvc.controller.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public class LoginControllerTest extends BaseTestFramework {

	
	@Test
	public void login() throws Exception {		
		// given
		/*Map<String, Object> targetCriteria = new HashMap<>();
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonInString = objectMapper.writeValueAsString(targetCriteria);
		*/
		// when
		MockHttpServletRequestBuilder request = get(
				"/security/login/{name}/{password}", "root","root").
				contentType(MediaType.APPLICATION_JSON);

		//then
		MvcResult response = this.mockMvc.perform(request).andDo(print())
				.andExpect(status().isOk()).andReturn();

		Assert.assertTrue(response.getResponse().getStatus() == 200);

	}
}
