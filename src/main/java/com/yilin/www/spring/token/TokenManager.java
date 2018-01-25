package com.yilin.www.spring.token;

public interface TokenManager {

	public String generateToken();
	
	public void save(String Token);
	
	public boolean auth(String Token); 
	
	public void delete(String Token);
}
