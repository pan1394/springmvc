package com.yilin.www.spring.dao;

import java.io.Serializable;
import java.util.List;
 

public interface BasicDao2 {

	public <T extends Serializable> int count(Class<T> type);

	public <T extends Serializable> void persist(T targetObject);

	public <T, PK extends Serializable> PK save(T targetObject);

	public <T extends Serializable> void remove(T targetObject);

	public <T extends Serializable> void removeSet(List<T> targetObjects);

	public <T, PK extends Serializable> void removeByID(Class<T> type, PK id);

	public <T extends Serializable> List<T> findAll(Class<T> type);

	public <T extends Serializable> List<T> findAllActiveTargets(Class<T> type);

	public <T, PK extends Serializable> T findByID(Class<T> type, PK id);

	public <T, PK extends Serializable> T findActiveTargetByID(Class<T> type, PK id);
	  
	public <T, PK extends Serializable> T findLatestHistoricalRecord(Class<T> type, String entityIDName, PK id);
	
	public <T extends Serializable> void update(T targetObject);

	public <T extends Serializable> void saveOrUpdate(T targetObject);

	public void flush();
	
	public void clear();

	public void setMaxResults(int maxResults);
 
 
}
