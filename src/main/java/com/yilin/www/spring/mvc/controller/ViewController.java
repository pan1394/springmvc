package com.yilin.www.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yilin.www.spring.mvc.model.User;
import com.yilin.www.spring.mvc.model.UserList;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ViewController {

	
	@RequestMapping("/hello")
	public String hello() {
		return "/hello";
		
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute("USERS") UserList userlst) {
		List<User> filtered = userlst.getLst().stream().filter(o -> o.getName() != null).collect(Collectors.toList());
		userlst.setLst(filtered);

		filtered.forEach(
				user ->{
					System.out.println("user registed:");
					System.out.println("name: " + user.getName());
					System.out.println("alias: " +user.getAlias());
				}
		);


		
		return "/hello";
	}


}
