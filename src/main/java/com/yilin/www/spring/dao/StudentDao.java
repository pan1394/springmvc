package com.yilin.www.spring.dao;

import java.util.List;

import com.yilin.www.spring.entity.Student;


public interface StudentDao{

	public List<Student> getStudent(Long Id);
	public Long enrollStudent(Student student);
	public Long getMaxStudentId();
	public List<Student> getAllStudents();
	public Long removeStudent(Long Id);
	public Student getStudentByName(String name);
	public long updateSudent(Student student);
	
}
