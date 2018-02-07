package com.yilin.www.spring.service;

import java.util.List;

import com.yilin.www.spring.entity.Student;

public interface StudentService {

	public Student getStudent(Long Id);
	public Long enrollStudent(Student student);
	public Long getMaxStudentId();
	public List<Student> getAllStudents();
	public Long removeStudent(Long Id);
	public Student getStudentByName(String name);
	public Student updateSudent(Student student);
}
