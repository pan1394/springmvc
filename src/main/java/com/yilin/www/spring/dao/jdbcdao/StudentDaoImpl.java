package com.yilin.www.spring.dao.jdbcdao;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import com.yilin.www.spring.dao.StudentDao;
import com.yilin.www.spring.vo.Student;
@Component
public class StudentDaoImpl extends JdbcDaoSupport implements StudentDao {

	private ReentrantLock lock = new ReentrantLock();

	@Autowired 
	public void setDs(DataSource dataSource) {
	    setDataSource(dataSource);
	}
	
	@Override
	public  List<Student> getStudent(Long id) { 
		StringBuffer sql = new StringBuffer("select * from student where id = ?");
		List<Student> one =  this.getJdbcTemplate().query(sql.toString(), new Object[]{id}, new BeanPropertyRowMapper<Student>(Student.class));
		return one;
	}

	@Override
	public Long enrollStudent(Student student) {
		Long id = -1l;
		StringBuffer sql = new StringBuffer("insert into student values(?,?)");
		try{
			lock.lock();
			id = this.getMaxStudentId() + 1;
			this.getJdbcTemplate().update(sql.toString(), id, student.getName());
		}finally{
			lock.unlock();
		}
		return id;
	}

	@Override
	public Long getMaxStudentId() {
		StringBuffer sql = new StringBuffer("select max(id) from student");
		Long id = this.getJdbcTemplate().queryForObject(sql.toString(), Long.class);
		return id;
	}

	@Override
	public List<Student> getAllStudents() {
		StringBuffer sql = new StringBuffer("select * from student");
		List<Student> one =  this.getJdbcTemplate().query(sql.toString(), new Object[]{}, new BeanPropertyRowMapper<Student>(Student.class));
		return one;
	}

	@Override
	public Long removeStudent(Long Id) {
		StringBuffer sql = new StringBuffer("delete from student where id =?");
		this.getJdbcTemplate().update(sql.toString(),new Object[]{Id});
		return Id;
	}

	@Override
	public Student getStudentByName(String name) {
		StringBuffer sql = new StringBuffer("select * from student where name = ?");
		List<Student> list =  this.getJdbcTemplate().query(sql.toString(), new Object[]{name}, new BeanPropertyRowMapper<Student>(Student.class));
		if(list != null && list.size() == 1)
			return list.get(0);
		return null;
	}

	public Student getStudentById(Long Id) {
		List<Student> list = this.getStudent(Id);
		if(list != null && list.size() == 1)
			return list.get(0);
		return null;
	}

	@Override
	public Student updateSudent(Student student) {
		StringBuffer sql = new StringBuffer("update student set name=? where id=?");
		int i = this.getJdbcTemplate().update(sql.toString(), student.getName(), student.getId());
		if(i == 1)
			return student;
		return null;
	}
	
	
}
