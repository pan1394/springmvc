package com.yilin.www.spring.mvc.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.ApiOperation;
import com.yilin.www.spring.entity.Student;
import com.yilin.www.spring.mvc.logmanager.SystemControllerLog;
import com.yilin.www.spring.mvc.model.ResultModel;
import com.yilin.www.spring.service.StudentService;
import com.yilin.www.spring.token2.Authorization;

@RestController 
public class StudentController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	  
	@Autowired 
	private StudentService stud;
	 
	@Authorization
	@PutMapping("/students")
	@SystemControllerLog(description="test_for_logsystem") 
	public ResponseEntity<ResultModel> update(@RequestBody Student student){
		 stud.updateSudent(student);
		 logger.info("Enrolled one student: {}", student.toString());
		 return new ResponseEntity<ResultModel>(ResultModel.ok(student), HttpStatus.OK);  
	}
	
	@Authorization
	@PostMapping("/students")
	@SystemControllerLog(description="test_for_logsystem") 
	public ResponseEntity<ResultModel> enroll(@RequestBody Student student){ 
		 Long id = stud.enrollStudent(student);
	     Student enrolled = stud.getStudent(id);
		 logger.info("Enrolled one student: {}", enrolled.toString());
		 return new ResponseEntity<ResultModel>(ResultModel.ok(enrolled), HttpStatus.OK);  
	}
	
	 @Authorization
	 @GetMapping(value="/students")  
	 @SystemControllerLog(description="test_for_logsystem")
	 @ApiOperation(value = "查询所有学生", httpMethod = "GET", notes = "/students")
	 public ResponseEntity<ResultModel> showAllStudents() {  
		List<Student> all = stud.getAllStudents();
		logger.info("Display all students: {}", all.toString());
        return new ResponseEntity<ResultModel>(ResultModel.ok(all), HttpStatus.OK);  
	 }  
	 
	 @Authorization
	 @DeleteMapping(value="/students/{id}")  
	 @SystemControllerLog(description="test_for_logsystem")
	 public ResponseEntity<ResultModel> delete(@PathVariable Long id) { 
		Student show = stud.getStudent(id);
		if(show != null){
			stud.removeStudent(id);
		}
		logger.info("Display all students: {}", show.toString());
        return new ResponseEntity<ResultModel>(ResultModel.ok(show), HttpStatus.OK);  
	 }  
	 
} 