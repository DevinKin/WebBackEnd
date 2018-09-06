package cn.devinkin.jk.service.cargo;

import cn.devinkin.jk.domain.ExtEproduct;
import cn.devinkin.jk.utils.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface ExtEproductService {
    //查询所有，带条件查询
    public List<ExtEproduct> find(String hql, Class<ExtEproduct> entityClass, Object[] params);
    //获取一条记录
    public  ExtEproduct get(Class<ExtEproduct> entityClass, Serializable id);
    //分页查询，将数据封装到一个page分页工具类对象
    public Page<ExtEproduct> findPage(String hql, Page<ExtEproduct> page, Class<ExtEproduct> entityClass, Object[] params);

    //新增和修改保存
    public  void saveOrUpdate(ExtEproduct entity);
    //批量新增和修改保存
    public  void saveOrUpdateAll(Collection<ExtEproduct> entitys);

    //单条删除，按id
    public  void deleteById(Class<ExtEproduct> entityClass, Serializable id);
    //批量删除
    public  void delete(Class<ExtEproduct> entityClass, Serializable[] ids);

    /**
     * 修改状态
     */
    public void changeState(String[] ids, Integer state);
}
