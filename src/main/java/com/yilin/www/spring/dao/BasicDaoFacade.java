package com.yilin.www.spring.dao;

import java.io.Serializable;
import java.util.List;

public class BasicDaoFacade implements BasicDao2 {

	@Override
	public <T extends Serializable> int count(Class<T> type) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T extends Serializable> void persist(T targetObject) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T, PK extends Serializable> PK save(T targetObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Serializable> void remove(T targetObject) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T extends Serializable> void removeSet(List<T> targetObjects) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T, PK extends Serializable> void removeByID(Class<T> type, PK id) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T extends Serializable> List<T> findAll(Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Serializable> List<T> findAllActiveTargets(Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T, PK extends Serializable> T findByID(Class<T> type, PK id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T, PK extends Serializable> T findActiveTargetByID(Class<T> type,
			PK id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T, PK extends Serializable> T findLatestHistoricalRecord(
			Class<T> type, String entityIDName, PK id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Serializable> void update(T targetObject) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T extends Serializable> void saveOrUpdate(T targetObject) {
		// TODO Auto-generated method stub

	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMaxResults(int maxResults) {
		// TODO Auto-generated method stub

	}

}
