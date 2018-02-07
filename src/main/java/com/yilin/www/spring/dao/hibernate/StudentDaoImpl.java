package com.yilin.www.spring.dao.hibernate;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.yilin.www.spring.dao.HibernateDaoImpl;
import com.yilin.www.spring.dao.StudentDao;
import com.yilin.www.spring.entity.Student;
@Component(value="studentDao_hibernate")
public class StudentDaoImpl extends HibernateDaoImpl<Student, Long> implements StudentDao {

	@Override
	public List<Student> getStudent(Long Id) { 
		 Student s = this.findById(Id);
		 return Arrays.asList(s);
	}

	@Override
	public Long enrollStudent(Student student) {
		return this.save(student); 
	}

	@Override
	public Long getMaxStudentId() {
		 
		return null;
	}

	@Override
	public List<Student> getAllStudents() { 
		return this.loadAll();
	}

	@Override
	public Long removeStudent(Long Id) { 
		this.deleteById(Id);
		return Id;
	}

	@Override
	public Student getStudentByName(String name) {
		Criteria criteria = this.createCriteria();
		criteria.add(Restrictions.eq("name", name));
		return (Student) criteria.list().get(0); 
	}

	@Override
	public long updateSudent(Student student) {
		this.update(student);
		return 1;
	}
 
	  
}
