package com.base.springboot.car.Base.src.main.java.com.car.base.service.interfaces;

import com.car.base.common.exception.EntityOperateException;
import com.car.base.common.exception.ValidateException;
import com.car.base.common.utilities.PageList;
import com.car.base.dao.interfaces.IBaseDao;

import java.math.BigInteger;
import java.util.List;

public interface IBaseService<EntityType, IDaoType extends IBaseDao<EntityType>> {
	EntityType get(String id) throws ValidateException;
	EntityType get(BigInteger id) throws ValidateException;

	boolean exists(String id);
	
	int countAll();
	
    void save(EntityType model) throws EntityOperateException, ValidateException;
    
    void batchSave(List<EntityType> list) throws EntityOperateException, ValidateException;
    
    void update(EntityType model) throws EntityOperateException, ValidateException ;
    
    void merge(EntityType model) throws EntityOperateException ;

    void delete(String id) throws EntityOperateException, ValidateException;

    void delete(EntityType model) throws EntityOperateException, ValidateException ;

    void saveOrUpdate(EntityType model) throws EntityOperateException ;
    
    void evict(EntityType model);
    
    PageList<EntityType> listPage(int pn);
    
    PageList<EntityType> listPage(int pn, int pageSize);

	List<EntityType> listAll();
	
	List<EntityType> listAll(String orderName, boolean orderASC) ;
    
    /*public PageList<EntityType> prePage(PKType pk, int pn);
    
    public PageList<EntityType> nextPage(PKType pk, int pn);
    
    public PageList<EntityType> prePage(PKType pk, int pn, int pageSize);
    
    public PageList<EntityType> nextPage(PKType pk, int pn, int pageSize);*/
    
    /*public void flush();
    
    public void clear();*/ 
}
