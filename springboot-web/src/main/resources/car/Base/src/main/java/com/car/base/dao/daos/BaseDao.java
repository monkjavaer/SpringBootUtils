package com.car.base.dao.daos;

import com.car.base.common.exception.EntityOperateException;
import com.car.base.common.exception.ValidateException;
import com.car.base.dao.interfaces.IBaseDao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.List;


public abstract class BaseDao<EntityType> 
	implements IBaseDao<EntityType> {
    protected final Class<EntityType> entityClass;
    public static final int JDBC_BATCH_SIZE=30;

    @SuppressWarnings("unchecked")
	public BaseDao() {
    	ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
    	Type[] type2 = type.getActualTypeArguments();
    	this.entityClass = (Class<EntityType>) type2[0];
    	//log = LogFactory.getLog(this.entityClass);
    }
    
    @Autowired
    @Qualifier("sessionFactory")
    protected SessionFactory sessionFactory;
    
	//private static Log log;

    protected Session getSession() {
    	return sessionFactory.getCurrentSession();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
    	this.sessionFactory = sessionFactory;
    }
        
    protected void checkNull(EntityType entity){
    	if(entity==null)
		{throw new NullPointerException("entity is null!");}
    }
    
    
    @Override
    public Criteria getCriteria(){
    	return getSession().createCriteria(this.entityClass);
    }
        
    @SuppressWarnings("unchecked")
	@Override
    public EntityType get(String id) throws ValidateException {
    	EntityType entity=(EntityType) getSession().get(this.entityClass, id);
    	if(entity==null)
		{throw new ValidateException("no entity has found!");}
        return entity;
    }
    @SuppressWarnings("unchecked")
	@Override
    public EntityType get(BigInteger id) throws ValidateException {
    	EntityType entity=(EntityType) getSession().get(this.entityClass, id);
    	if(entity==null)
		{throw new ValidateException("no entity has found!");}
        return entity;
    }
	@SuppressWarnings("unchecked")
	@Override
	public EntityType get(Integer id) throws ValidateException {
		EntityType entity=(EntityType) getSession().get(this.entityClass, id);
		if(entity==null) {
			throw new ValidateException("no entity has found!");
		}
		return entity;
	}
    @Override
    public boolean exists(String id) {
        return getSession().get(this.entityClass, id) != null;
    }
    
    @Override
    public int countAll() {
    	Criteria criteria = getCriteria();
    	criteria.setProjection(Projections.rowCount());      	  	
    	return Integer.parseInt(criteria.uniqueResult().toString());
    }

	@Override
    public void save(EntityType entity) throws EntityOperateException, ValidateException {
    	checkNull(entity);
        getSession().save(entity);
    }
	
	@Override
    public void batchSave(List<EntityType> list) throws EntityOperateException, ValidateException {
		Session newSession=sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = newSession.beginTransaction();
			for ( int i=0; i<list.size(); i++ ) {
				EntityType entity = list.get(i);
				checkNull(entity);
				newSession.save(entity);
			    if ( i % BaseDao.JDBC_BATCH_SIZE == 0 ) { //same as the JDBC batch size
			        //flush a batch of inserts and release memory:
			    	newSession.flush();
			    	newSession.clear();
			    }
			}
			tx.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			newSession.close();
		}
    }
    
    @Override
    public void update(EntityType entity) throws EntityOperateException, ValidateException {
    	checkNull(entity);
        getSession().update(entity);
    }

    @Override
    public void delete(EntityType entity) throws EntityOperateException, ValidateException {
    	checkNull(entity);
        getSession().delete(entity);
    }
    
    @Override
    public void saveOrUpdate(EntityType entity) throws EntityOperateException {
    	checkNull(entity);
        getSession().saveOrUpdate(entity);
    }
    
    @Override
    public void merge(EntityType entity) throws EntityOperateException {
    	checkNull(entity);
        getSession().merge(entity);
    }
    
	@Override
    public List<EntityType> listAll() {
    	return listAll(null, true);
    } 
	
	@Override
	public void persist(EntityType entity) {
			getSession().persist(entity);
	}
	
	@Override
    @SuppressWarnings("unchecked")
	public List<EntityType> listAll(String orderName, boolean orderASC) {
    	Criteria criteria = getCriteria();
    	if(orderName!=null) {
	    	if(orderASC)
			{criteria.addOrder(Order.asc(orderName));}
	    	else
			{criteria.addOrder(Order.desc(orderName));}
    	}
    	return criteria.list();
    }
    
	@Override
	public List<EntityType> listPage(int start, int limit) {  	
    	return listPage(start, limit, null, true);
    }

	@Override
    @SuppressWarnings("unchecked")
	/**
	 * start  -  初始行号，从0开始计算
	 * limit  -  查询的总记录数
	 */
	public List<EntityType> listPage(int start, int limit, String orderName, boolean orderASC) {
    	Criteria criteria = getCriteria();
    	if(orderName!=null) {
	    	if(orderASC)
			{criteria.addOrder(Order.asc(orderName));}
	    	else
			{criteria.addOrder(Order.desc(orderName));}
    	}
    	criteria.setFirstResult(start);  
        criteria.setMaxResults(limit);
    	return criteria.list();
    }
	
	@Override
    public void evict(EntityType entity) {
        getSession().evict(entity);
    }

    @Override
    public void flush() {
        getSession().flush();
    }

    @Override
    public void clear() {
        getSession().clear();
    }
}
