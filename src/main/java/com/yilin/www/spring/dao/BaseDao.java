package com.yilin.www.spring.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;

public interface BaseDao<T extends Serializable, PK extends Serializable> {
 
    public T findById(PK id);
    
    public T load(PK id);
   
    public List<T> loadAll();
  
    // loadAllWithLock() ?  
  
    public void update(T entity);
     
    public void save(T entity);
    
    public void delete(T entity);
     
    public void deleteById(Class<T> clazz, PK id);
     
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
    
  /*
    
    public List<T> findEqualByEntity(T entity, String[] propertyNames) {  
        Criteria criteria = this.createCriteria();  
        Example exam = Example.create(entity);  
        exam.excludeZeroes();  
        String[] defPropertys = getSessionFactory().getClassMetadata(  
                entityClass).getPropertyNames();  
        for (String defProperty : defPropertys) {  
            int ii = 0;  
            for (ii = 0; ii < propertyNames.length; ++ii) {  
                if (defProperty.equals(propertyNames[ii])) {  
                    criteria.addOrder(Order.asc(defProperty));  
                    break;  
                }  
            }  
            if (ii == propertyNames.length) {  
                exam.excludeProperty(defProperty);  
            }  
        }  
        criteria.add(exam);  
        return (List<T>) criteria.list();  
    }  
  
    public List<T> findLikeByEntity(T entity, String[] propertyNames) {  
        Criteria criteria = this.createCriteria();  
        for (String property : propertyNames) {  
            try {  
                Object value = PropertyUtils.getProperty(entity, property);  
                if (value instanceof String) {  
                    criteria.add(Restrictions.like(property, (String) value,  
                            MatchMode.ANYWHERE));  
                    criteria.addOrder(Order.asc(property));  
                } else {  
                    criteria.add(Restrictions.eq(property, value));  
                    criteria.addOrder(Order.asc(property));  
                }  
            } catch (Exception ex) {  
            }  
        }  
        return (List<T>) criteria.list();  
    }  
  
  
  
    public Object getStatValue(DetachedCriteria criteria, String propertyName,  
            String StatName) {  
        if (StatName.toLowerCase().equals("max"))  
            criteria.setProjection(Projections.max(propertyName));  
        else if (StatName.toLowerCase().equals("min"))  
            criteria.setProjection(Projections.min(propertyName));  
        else if (StatName.toLowerCase().equals("avg"))  
            criteria.setProjection(Projections.avg(propertyName));  
        else if (StatName.toLowerCase().equals("sum"))  
            criteria.setProjection(Projections.sum(propertyName));  
        else  
            return null;  
        List list = this.findByCriteria(criteria, 0, 1);  
        return list.get(0);  
    }  
  
    // -------------------------------- Others --------------------------------  
  
   
    public List<T> findByPageParam(final int currPage,final LinkedHashMap args) {  
        final int currPate = currPage*1;  
        final Map map = args;  
        String sql = "from "+ entityClass.getName() +" entity where 1 = 1";  
          
        for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {  
            String key = (String) iter.next();  
            sql = sql+" and "+key+" ? ";  
        }  
  
        final String hql = sql;  
        System.out.println(""+hql);  
        final List<String> paramlist = new ArrayList<String>();  
        for (Iterator iter = map.values().iterator(); iter.hasNext();) {  
            String value = (String) iter.next();  
            paramlist.add(value);  
        }  
          
        List list = this.getHibernateTemplate().executeFind(  
                new HibernateCallback() {  
                    public Object doInHibernate(Session session)  
                    throws HibernateException, SQLException {  
                        Query query = session.createQuery(hql);  
                        for (int i = 0; i < paramlist.size(); i++) {  
                            query.setParameter(i, paramlist.get(i));  
                        }  
                        List result = query.setFirstResult(currPage > 1 ? (currPage - 1) * 15: 0).setMaxResults(15).list();  
                        return result;  
                    }  
                });  
        return list;  
    }  
      
     
  
    
 
  */
   
  
}
