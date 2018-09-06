package cn.devinkin.jk.service.cargo;

import cn.devinkin.jk.domain.Contract;
import cn.devinkin.jk.utils.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface ContractService {
    //查询所有，带条件查询
    public List<Contract> find(String hql, Class<Contract> entityClass, Object[] params);
    //获取一条记录
    public  Contract get(Class<Contract> entityClass, Serializable id);
    //分页查询，将数据封装到一个page分页工具类对象
    public Page<Contract> findPage(String hql, Page<Contract> page, Class<Contract> entityClass, Object[] params);

    //新增和修改保存
    public  void saveOrUpdate(Contract entity);
    //批量新增和修改保存
    public  void saveOrUpdateAll(Collection<Contract> entitys);

    //单条删除，按id
    public  void deleteById(Class<Contract> entityClass, Serializable id);
    //批量删除
    public  void delete(Class<Contract> entityClass, Serializable[] ids);

    /**
     * 修改状态
     */
    public void changeState(String[] ids, Integer state);
}
