package com.yilin.www.spring.entity;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
 
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@Cacheable
@Table(name = "Student")
public class Student implements Serializable {
 
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
	long id;
	
	@Column(name = "name")
	String name;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return new StringBuilder( "Student [id=" + id + ", name=" + name + " @" + super.toString() + "]").toString();
	}
	 
	
	 
}
