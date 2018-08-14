package com.devinkin.dao;

import com.devinkin.domain.PageBean;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

/**
 * 以后所有的DAO的接口都需要继承BaseDao接口
 * 自定义泛型接口
 * @author king
 */
public interface BaseDao<T> {
    public void save(T t);

    public void delete(T t);

    public void update(T t);

    public T findById(Long id);

    public List<T> findAll();

    public PageBean<T> findByPage(Integer pageCode, Integer pageSize, DetachedCriteria criteria);
}
