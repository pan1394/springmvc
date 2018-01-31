package com.yilin.www.spring.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;

import com.yilin.www.spring.dao.HibernateDaoImpl.State;

@SuppressWarnings("rawtypes")
public interface BaseDao<T extends Serializable, PK extends Serializable> {
 
    public T findById(PK id);
    
    public T load(PK id);
   
    public List<T> loadAll();
  
    // loadAllWithLock() ?  
  
    public void update(T entity);
     
    public PK save(T entity);
    
    public void saveOrUpdate(T entity);
    
    public void delete(T entity);
     
    public void deleteById(PK id);
     
    public void deleteAll(Collection<T> entities); 
  
    public void lock(T entity, LockMode lock);
     
    public void initialize(Object proxy) ;
  
    public void flush();
    
    public void clear();
    
    public void evict(T entity);
    
    // -------------------------------- Criteria ------------------------------  
  
    public DetachedCriteria createDetachedCriteria(); 
  
    public Criteria createCriteria();
     
	public List findByCriteria(DetachedCriteria criteria);
    /**
     * get pagination data
     * 
     * @param criteria     -- query condition
     * @param firstResult  -- offset, page number
     * @param maxResults   -- page size
     * @return
     */
    public List findByCriteria(DetachedCriteria criteria, int firstResult,int maxResults);
    
    public long getRowCount(DetachedCriteria criteria);
    
    public long getCount(LinkedHashMap args);
    
    public List findByHQLCache(final String HQL);
    
    public List findByNSQL(final String SQL);
    /**
     * query entities based on exact mode with given property names on the entity
     * @param entity
     * @param propertyNames
     * @return
     */
    public List<T> findEqualByEntity(T entity, String... propertyNames);
    
    /**
     * query entities based on like mode with given property name on the entity
     * @param entity
     * @param propertyNames
     * @return
     */
    public List<T> findLikeByEntity(T entity, String... propertyNames);
    
    public List<T> findByPageParam(final int currPage,final LinkedHashMap args);
    
    public Object getStateValue(DetachedCriteria criteria, String propertyName, State s);
 
}
