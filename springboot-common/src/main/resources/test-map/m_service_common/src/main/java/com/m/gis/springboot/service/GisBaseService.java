package com.m.gis.springboot.service;

/**
 * @Title: GisBaseService
 * @Package: com.m.gis.springboot.service
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/14
 * @Version: V1.0
 */
public interface GisBaseService<T> {

   /* void save(T model);//持久化

    void save(List<T> models);//批量持久化

    void deleteById(Integer id);//通过主鍵刪除

    void deleteByIds(String ids);//批量刪除 eg：ids -> “1,2,3,4”

    void update(T model);//更新

    T findById(Integer id);//通过ID查找

    T findBy(String property, Object value) throws TooManyResultsException; //通过某个成员属性查找,value需符合unique约束

    List<T> findByIds(String ids);//通过多个ID查找//eg：ids -> “1,2,3,4”

    //List<T> findByCondition(Condition condition);//根据条件查找

    List<T> findAll();//获取所有*/
}
