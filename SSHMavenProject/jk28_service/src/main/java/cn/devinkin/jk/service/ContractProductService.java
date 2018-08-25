package cn.devinkin.jk.service;

import cn.devinkin.jk.domain.ContractProduct;
import cn.devinkin.jk.utils.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface ContractProductService {
    //查询所有，带条件查询
    public List<ContractProduct> find(String hql, Class<ContractProduct> entityClass, Object[] params);
    //获取一条记录
    public  ContractProduct get(Class<ContractProduct> entityClass, Serializable id);
    //分页查询，将数据封装到一个page分页工具类对象
    public Page<ContractProduct> findPage(String hql, Page<ContractProduct> page, Class<ContractProduct> entityClass, Object[] params);

    //新增和修改保存
    public  void saveOrUpdate(ContractProduct entity);
    //批量新增和修改保存
    public  void saveOrUpdateAll(Collection<ContractProduct> entitys);

    //单条删除，按id
    public  void deleteById(Class<ContractProduct> entityClass, Serializable id);
    //批量删除
    public  void delete(Class<ContractProduct> entityClass, Serializable[] ids);

    /**
     * 删除指定的货物对象
     * @param entityClass
     * @param entity
     */
    public  void delete(Class<ContractProduct> entityClass, ContractProduct entity);
}
