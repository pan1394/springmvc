package com.yilin.www.spring.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.query.Query;
import org.hibernate.query.NativeQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;


public class HibernateDaoImpl<T extends Serializable, PK extends Serializable> extends HibernateDaoSupport implements BaseDao<T, PK> {

  
	private Class<T> clazz;
	
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
	public void save(T entity) {
		getHibernateTemplate().save(entity);
		
	}

	@Override
	public void delete(T entity) {
		getHibernateTemplate().delete(entity);
		
	}

	@Override
	public void deleteById(Class<T> clazz, PK id) {
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
	     List list = this.findByCriteria(criteria);  
	     return (Long) list.get(0);  
	}

	@Override
	public long getCount(LinkedHashMap args) {
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
				return query.setCacheable(true).list();
		});
	}

	@Override
	public List findByNSQL(String SQL) {
		return this.getHibernateTemplate().execute( session -> {  
                NativeQuery query = session.createNativeQuery(SQL);  
                return query.list();  
	    });    
	}

 
  
 /* 
  
  
    // -------------------------------- Criteria ------------------------------  
 
  
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
      
       
  
  
  
    public static Object getBean(String beanName) {  
        String[] str = {"WebRoot/WEB-INF/applicationContext.xml"};  
        ApplicationContext ctx = new FileSystemXmlApplicationContext(str);  
        try {  
            return ctx.getBean(beanName);  
        } catch (Exception e) {  
            System.out.println(e);  
            System.out.println("?...");  
        }  
        return null;  
  
    }  
  
  
   */
}  