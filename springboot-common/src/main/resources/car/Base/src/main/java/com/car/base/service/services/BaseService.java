package com.base.springboot.car.Base.src.main.java.com.car.base.service.services;

import com.car.base.common.exception.EntityOperateException;
import com.car.base.common.exception.ValidateException;
import com.car.base.common.utilities.PageList;
import com.car.base.common.utilities.PageListUtil;
import com.car.base.dao.interfaces.IBaseDao;
import com.base.springboot.car.Base.src.main.java.com.car.base.service.interfaces.IBaseService;

import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.List;

public abstract class BaseService<EntityType, IDaoType extends IBaseDao<EntityType>>
	implements IBaseService<EntityType, IDaoType> { 
	
	protected final Class<EntityType> entityClass;
	protected final IDaoType entityDao;
	
	@SuppressWarnings("unchecked")
	public BaseService(IDaoType dao) {
		this.entityClass = (Class<EntityType>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		this.entityDao=dao;
	}
	
	@Override
	public EntityType get(String id) throws ValidateException {
		return entityDao.get(id);
	}
	@Override
	public EntityType get(BigInteger id) throws ValidateException {
		return entityDao.get(id);
	}

	@Override
	public boolean exists(String id) {
	    return entityDao.exists(id);
	}
	
	@Override
	public int countAll() {
	    return entityDao.countAll();
	}
	
	@Override
	public void save(EntityType model) throws EntityOperateException, ValidateException {
	    entityDao.save(model);
	}
	
	@Override
	public void batchSave(List<EntityType> list) throws EntityOperateException, ValidateException {
	    entityDao.batchSave(list);
	}
	
	@Override
	public void update(EntityType model) throws EntityOperateException, ValidateException  {
	    entityDao.update(model);
	}
	
	@Override
	public void merge(EntityType model) throws EntityOperateException  {
	    entityDao.merge(model);
	}
	
	@Override
	public void delete(String id) throws EntityOperateException, ValidateException {
		this.delete(this.get(id));
	}
	
	@Override
	public void delete(EntityType model) throws EntityOperateException, ValidateException  {
	    entityDao.delete(model);
	}
	
	@Override
	public void saveOrUpdate(EntityType model) throws EntityOperateException  {
	    entityDao.saveOrUpdate(model);
	}
	
	@Override
	public void evict(EntityType model) {
	    entityDao.evict(model);
	}  
	
	@Override
	public List<EntityType> listAll() {
	    return entityDao.listAll();
	}
	
	@Override
    public List<EntityType> listAll(String orderName, boolean orderASC) {
		return entityDao.listAll(orderName, orderASC);		 
	}
	
	@Override
	public PageList<EntityType> listPage(int pageNo) {
	    return this.listPage(pageNo, PageListUtil.DEFAULT_PAGE_SIZE);
	}   
	
	@Override
	public PageList<EntityType> listPage(int pageNo, int pageSize) {
	    Integer count = countAll();
	    List<EntityType> items = entityDao.listPage((pageNo-1)*pageSize, pageSize);
	    return PageListUtil.getPageList(count, pageNo, items, pageSize);
	}
	
	/*public PageList<EntityType> listAllWithOptimize(int pn) {
	    return this.listAllWithOptimize(pn, Constants.DEFAULT_PAGE_SIZE);
	}
	
	public PageList<EntityType> listAllWithOptimize(int pn, int pageSize) {
	    Integer count = countAll();
	    List<EntityType> items = entityDao.listAll(pn, pageSize);
	    return PageUtil.getPage(count, pn, items, pageSize);
	}
	
	@Override
	public PageList<EntityType> prePage(PKType pk, int pn) {
	    return prePage(PKType, pn, Constants.DEFAULT_PAGE_SIZE);
	}
	
	@Override
	public PageList<EntityType> nextPage(PKType pk, int pn) {
	    return nextPage(PKType, pn, Constants.DEFAULT_PAGE_SIZE);
	}
	
	@Override
	public PageList<EntityType> prePage(PKType pk, int pn, int pageSize) {
	    Integer count = countAll();
	    List<EntityType> items = entityDao.pre(PKType, pn, pageSize);
	    return PageUtil.getPage(count, pn, items, pageSize);
	}
	
	@Override
	public PageList<EntityType> nextPage(PKType pk, int pn, int pageSize) {
	    Integer count = countAll();
	    List<EntityType> items = entityDao.next(PKType, pn, pageSize);
	    return PageUtil.getPage(count, pn, items, pageSize);
	}*/
 
}
