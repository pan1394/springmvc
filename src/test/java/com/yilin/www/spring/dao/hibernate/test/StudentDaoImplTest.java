package com.yilin.www.spring.dao.hibernate.test;

import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.yilin.www.spring.dao.BaseDao;
import com.yilin.www.spring.dao.HibernateDaoImpl.State;
import com.yilin.www.spring.vo.Student;
@Transactional(readOnly=false)
public class StudentDaoImplTest extends BaseDaoFramework{

	@Autowired
	//@Qualifier("studentDao2")
	private BaseDao<Student, Long> dao;
	
	@Before
	public void setup() {
	/*	logger.info("before loaded for StudentDaoImplTest.");
		Student entity = new Student();
	    entity.setName("Peter");
		dao.save(entity);
		Student entity2 = new Student();
	    entity2.setName("Jack");	
	    dao.save(entity2);
	    Student entity3 = new Student();
	    entity3.setName("Lucy");
	    dao.save(entity3); 
	    dao.flush(); */
	}
	
	@Test 
	public void save() {
		Student entity = new Student();
	    entity.setName("Pan Yilin");
		Long id = dao.save(entity);
		logger.info("id: {}", id );
		Assert.assertNotNull(id);
	}
	
	@Test
	public void get(){
		Long id = (Long) dao.getStateValue(dao.createDetachedCriteria(), "id", State.MAX);
		Student s =dao.load(id);
		Student s2 = dao.findById(id);
		Assert.assertSame(s, s2);
		List lst =dao.loadAll();
		Assert.assertTrue(lst.size()>0);
	}

	@Test
	public void deleteAll(){ 
		List lst =dao.loadAll();
		lst.forEach(s -> logger.info(s.toString()));
		dao.deleteAll(lst);
	}
	
	@Test
	public void query(){
		Student s = new Student();
		s.setName("Lucy");
	    List<Student> lst = dao.findEqualByEntity(s, "name");
	    s = lst.get(0);
	    Student s2 = dao.load(s.getId());
	    logger.info("Student s {} \n s2 {}", s,s2);
	    
	}
	
	@Test
	public void queryOut(){ 
		List lst = dao.loadAll(); 
		logger.info("session online resultset: ");
		//lst.forEach(s -> logger.info(s.toString()));
		logger.info("===============================================");
		List lst2 = dao.findByCriteria(dao.createDetachedCriteria());
		logger.info("session offline resultset: ");
		//lst2.forEach(s -> logger.info(s.toString()));
		logger.info("===============================================");
		logger.info("start query cache resultset: ");
		List lst3 =dao.findByHQLCache("from Student");
		//lst3.forEach(s -> logger.info(s.toString()));
		logger.info("===============================================");
		logger.info("start query cache resultset2: ");
		List lst4 =dao.findByHQLCache("from Student");
		//lst4.forEach(s -> logger.info(s.toString()));
		logger.info("===============================================");
	}
	
	@Test
	public void getRowCount(){
		long c = dao.getRowCount(dao.createDetachedCriteria());
		LinkedHashMap<String,String> args = new LinkedHashMap<String,String>();
		long c2 =dao.getCount(null);
		logger.info("Student count 1: {} \n count 2: {}", c,c2);
	}
}
