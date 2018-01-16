package com.yilin.www.spring.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yilin.www.spring.dao.StudentDao;
import com.yilin.www.spring.mvc.logmanager.SystemControllerLog;
import com.yilin.www.spring.vo.Student;

@RestController
@RequestMapping("/student")
public class StudentController {

	 Logger logger = LoggerFactory.getLogger(this.getClass());
	  
	@Autowired(required=false) 
	private HttpServletResponse res;
	
	@Autowired 
	private StudentDao stud;
	
	public void setRes(HttpServletResponse res) {
		this.res = res;
	}


	@GetMapping("/add/{studnetName}")
	@SystemControllerLog(description="test_for_logsystem")
	public ResponseEntity<List<Student>> add(@PathVariable String studnetName){
		 Student s = new Student();
		 s.setName(studnetName);
		 Long id = stud.enrollStudent(s);
		 List<Student> show = stud.getStudent(id);
		 logger.info("Enrolled one student: {}", show.toString());
		 return new ResponseEntity<List<Student>>(show, HttpStatus.OK);  
	}
	
	
	 @GetMapping(value="/show")  
	 @SystemControllerLog(description="test_for_logsystem")
	 public ResponseEntity<List<Student>> showAllStudents() {  
		List<Student> show = stud.getAllStudent();
		logger.info("Display all students: {}", show.toString());
        return new ResponseEntity<List<Student>>(show, HttpStatus.OK);  
	  }  
	 
	 
	 @GetMapping(value="/delete/{id}")  
	 @SystemControllerLog(description="test_for_logsystem")
	 public ResponseEntity<List<Student>> delete(@PathVariable Long id) { 
		List<Student> show = stud.getStudent(id);
		if(show.size() > 0){
			stud.removeStudent(id);
		}
		logger.info("Display all students: {}", show.toString());
        return new ResponseEntity<List<Student>>(show, HttpStatus.OK);  
	  }  
	 
}
