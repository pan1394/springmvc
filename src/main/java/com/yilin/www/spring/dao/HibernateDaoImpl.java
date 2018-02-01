package com.yilin.www.spring.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource; 

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.google.common.base.Optional;

@SuppressWarnings({"rawtypes","unchecked"})
@Resource
public class HibernateDaoImpl<T extends Serializable, PK extends Serializable> extends HibernateDaoSupport implements BaseDao<T, PK> {
 
	Logger logger = LoggerFactory.getLogger(this.getClass());
	 
	private Class<T> clazz;
	 
	@Autowired 
	public void setSf( SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
		logger.info("sessionFactory loaded for HibernateDaoImpl.");
	} 
	
	public HibernateDaoImpl() {  
	        this.clazz = null;  
	        Class c = getClass();  
	        Type t = c.getGenericSuperclass();  
	        if (t instanceof ParameterizedType) {  
	            Type[] p = ((ParameterizedType) t).getActualTypeArguments();  
	            this.clazz = (Class<T>) p[0];             
	        }  
	}  
	 
	@Override
	public T findById(PK id) {
		return getHibernateTemplate().get(clazz, id);
	}

	@Override
	public T load(PK id) {
		return getHibernateTemplate().load(clazz, id);
	}

	@Override
	public List<T> loadAll() {
		return getHibernateTemplate().loadAll(clazz);
	}

	@Override
	public void update(T entity) {
		getHibernateTemplate().update(entity);
		
	}

	@Override
	public PK save(T entity) {
		return (PK) getHibernateTemplate().save(entity);
		
	}

	@Override
	public void saveOrUpdate(T entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}
	
	@Override
	public void delete(T entity) {
		getHibernateTemplate().delete(entity);
		
	}

	@Override
	public void deleteById(PK id) {
		getHibernateTemplate().delete(this.findById(id)); 
	}

	@Override
	public void deleteAll(Collection<T> entities) {
		getHibernateTemplate().deleteAll(entities);
		
	}

	@Override
	public void lock(T entity, LockMode lock) {
		getHibernateTemplate().lock(entity, lock);
	}

	@Override
	public void initialize(Object proxy) {
		getHibernateTemplate().initialize(proxy);
		
	}

	@Override
	public void flush() {
		getHibernateTemplate().flush();
	}


	@Override
	public void clear() {
		getHibernateTemplate().clear();
		
	}

	@Override
	public void evict(T entity) {
		getHibernateTemplate().evict(entity);
	}
	
	@Override
	public DetachedCriteria createDetachedCriteria() {  
	    return DetachedCriteria.forClass(clazz);  
	}  
	  
	@Override
    public Criteria createCriteria() {  
        return this.createDetachedCriteria().getExecutableCriteria(this.currentSession());  
    }  
	  
	@Override
	public List findByCriteria(DetachedCriteria criteria) {   
		return getHibernateTemplate().findByCriteria(criteria);  
	    
	}  
	  
	 
	@Override
	public List findByCriteria(DetachedCriteria criteria, int firstResult,int maxResults) {  
	    return getHibernateTemplate().findByCriteria(criteria, firstResult,maxResults);  
	}  
	  

	@Override
	public long getRowCount(DetachedCriteria criteria) {
		 criteria.setProjection(Projections.rowCount());  
	     List list = this.findByCriteria(criteria,0,1);  
	     return (Long) list.get(0);  
	}

	@Override
	public long getCount(LinkedHashMap args) {
		Optional<LinkedHashMap> opt = Optional.fromNullable(args);
		args = opt.or(new LinkedHashMap());
		final Map map = args;  
        StringBuffer sql = new StringBuffer( "select count(entity) from "+ clazz.getName()+" entity where 1 =1 ");
        map.keySet().forEach(key -> sql.append(" and "+key+" ? "));  
        final List paramlist = new ArrayList();  
        map.values().forEach(value -> paramlist.add(value));
        final String q = sql.toString();    
        Object o = this.getHibernateTemplate().find(q, paramlist.toArray()).get(0);  
        return Long.parseLong(o.toString());   
	}

	@Override
	public List findByHQLCache(String HQL) { 
		return this.getHibernateTemplate().execute(session ->{
				Query query = session.createQuery(HQL);  
				return query.setCacheable(true).getResultList();
		});
	}

	@Override
	public List findByNSQL(String SQL) {
		return this.getHibernateTemplate().execute( session -> {  
                NativeQuery query = session.createNativeQuery(SQL);  
                return query.getResultList();
	    });    
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<T> findEqualByEntity(T entity, String... propertyNames) {
		Criteria criteria = this.createCriteria();  
        Example exam = Example.create(entity);  
        exam.excludeZeroes();  
        String[] defPropertys = this.getSessionFactory().getClassMetadata(clazz).getPropertyNames();  
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

	@Override
	public List<T> findLikeByEntity(T entity, String... propertyNames) {
		Criteria criteria = this.createCriteria();  
		Arrays.asList(propertyNames).forEach(property -> {
			try {  
                Object value = PropertyUtils.getProperty(entity, property);  
                if (value instanceof String) {  
                    criteria.add(Restrictions.like(property, (String) value, MatchMode.ANYWHERE));  
                    criteria.addOrder(Order.asc(property));  
                } else {  
                    criteria.add(Restrictions.eq(property, value));  
                    criteria.addOrder(Order.asc(property));  
                }  
            } catch (Exception ex) {  
            	logger.error(ex.getLocalizedMessage(), ex);
            } 
		});  
        return (List<T>) criteria.list();  
	}

	@Override
	public List<T> findByPageParam(int currPage, LinkedHashMap args) { 
		return null;
	}

	@Override
	public Object getStateValue(DetachedCriteria criteria, String propertyName, State s) {
		switch(s) { 
			case MAX:criteria.setProjection(Projections.max(propertyName));
					break;
			case MIN:criteria.setProjection(Projections.min(propertyName));
					break;
			case AVG:criteria.setProjection(Projections.avg(propertyName));
					break;
			case SUM:criteria.setProjection(Projections.sum(propertyName));
					break;
			default:
				break;  
		} 
		return this.findByCriteria(criteria,0,1).get(0);
	}

 
	public static enum State{
		MAX,
		MIN,
		AVG,
		SUM
	}
 /* 
  
  
    // -------------------------------- Criteria ------------------------------  
   
  
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
    }  
      
 
  
   */
}  