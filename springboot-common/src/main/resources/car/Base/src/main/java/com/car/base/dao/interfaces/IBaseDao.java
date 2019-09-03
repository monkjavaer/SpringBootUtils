package com.base.springboot.car.Base.src.main.java.com.car.base.dao.interfaces;

import com.base.springboot.car.Base.src.main.java.com.car.base.common.exception.EntityOperateException;
import com.base.springboot.car.Base.src.main.java.com.car.base.common.exception.ValidateException;
import org.hibernate.Criteria;

import java.math.BigInteger;
import java.util.List;

public interface IBaseDao<EntityType> {
	/**
	 * 取主键id
	 * @param id
	 * @return
	 * @throws ValidateException 
	 */
    EntityType get(String id) throws ValidateException;
    EntityType get(BigInteger id) throws ValidateException;
    EntityType get(Integer id) throws ValidateException;

	/**
	 * 是否存在主键为id的记录
	 * @param id
	 * @return
	 */
	boolean exists(String id);
    
	/**
	 * 返回记录总数
	 * @return
	 */
    int countAll();
	
    /**
     * 创建记录
     * @param model
     * @return
     * @throws ValidateException 
     */
    void save(EntityType model) throws EntityOperateException, ValidateException;
    
    void
    batchSave(List<EntityType> list) throws EntityOperateException, ValidateException;

    /**
     * 更新记录
     * @param model
     * @throws ValidateException 
     */
    void update(EntityType model) throws EntityOperateException, ValidateException;

    /**
     * 删除记录
     * @param model
     * @throws ValidateException 
     */
    void delete(EntityType model) throws EntityOperateException, ValidateException;
    
    /**
     * 创建或更新
     * @param model
     */
    void saveOrUpdate(EntityType model) throws EntityOperateException;
    
    /**
     * 
     * @param model
     */
    void merge(EntityType model) throws EntityOperateException;
    
    /**
     * 锋得Criteria（查询条件）
     * @return
     */
    Criteria getCriteria();
    
    /**
     * 查询所有
     * @return
     */
    List<EntityType> listAll();
    
    
     /**
     * 
     * @param orderName
     * @param orderASC
     * @return
     */
     List<EntityType> listAll(String orderName, boolean orderASC);
    
    /**
     * 
     * @param start
     * @param limit
     * @return
     */
    List<EntityType> listPage(int start, int limit);
    
    /**
     *  
     * @param start
     * @param limit
     * @param orderName
     * @param orderASC
     * @return
     */
    List<EntityType> listPage(int start, int limit, String orderName, boolean orderASC);
    
    /**
     *  
     * @param entity
     * @return
     */
    void persist(EntityType entity);
    
    /**
     *     
     * @param model
     */
    void evict(EntityType model);
    
    /**
     * 
     */
    void flush();
    
    /**
     * 
     */
    void clear();
}
