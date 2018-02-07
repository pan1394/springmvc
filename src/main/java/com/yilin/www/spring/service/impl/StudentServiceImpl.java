package com.yilin.www.spring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yilin.www.spring.dao.StudentDao;
import com.yilin.www.spring.entity.Student;
import com.yilin.www.spring.service.StudentService;
@Transactional
@Service
public class StudentServiceImpl implements StudentService{

	@Autowired
	@Qualifier("studentDao")
	private StudentDao stud;
	
	@Override
	public Student getStudent(Long Id) {
		List<Student> o = stud.getStudent(Id);
		Optional<List<Student>> opt = Optional.ofNullable(o);
		o = opt.orElse(new ArrayList<Student>());
		return o.size() == 1 ? o.get(0) : null ;
	}

	@Override
	public Long enrollStudent(Student student) {
		return stud.enrollStudent(student);
	}

	@Override
	public Long getMaxStudentId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Student> getAllStudents() {
		return stud.getAllStudents();
	}

	@Override
	public Long removeStudent(Long Id) {
		return stud.removeStudent(Id);
	}

	@Override
	public Student getStudentByName(String name) {
		return stud.getStudentByName(name);
	}

	@Override
	public Student updateSudent(Student student) {
		return stud.updateSudent(student);
	}

}
