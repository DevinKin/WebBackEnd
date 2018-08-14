package com.devinkin.dao;

import com.devinkin.domain.PageBean;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 所有Dao层的实现类，都可以继承BaseDaoImpl，增删改查分页方法不用再编写了
 * @param <T>
 */
public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T>{

    // 定义成员的属性
    private Class clazz;

    public BaseDaoImpl() {
        // this表示的子类，c表示的就是CustomerDaoImpl的Class对象
        Class c = this.getClass();
        // CustomerDaoImpl extends BaseDaoImpl<Customer>
        Type type = c.getGenericSuperclass();
        // 目的：把type接口转换为子接口
        if (type instanceof ParameterizedType) {
            ParameterizedType ptype = (ParameterizedType) type;
            Type[] types = ptype.getActualTypeArguments();
            this.clazz = (Class) types[0];
        }
//        System.out.println("执行BaseDao");
    }


    /**
     * 添加
     * @param t
     */
    @Override
    public void save(T t) {
        this.getHibernateTemplate().save(t);
    }

    /**
     * 删除
     * @param t
     */
    @Override
    public void delete(T t) {
        this.getHibernateTemplate().delete(t);
    }

    /**
     * 修改
     * @param t
     */
    @Override
    public void update(T t) {
        this.getHibernateTemplate().update(t);
    }

    /**
     * 通过主键查询
     * @param id 主键
     * @return
     */
    @Override
    public T findById(Long id) {
        return (T)this.getHibernateTemplate().get(clazz, id);
    }

    @Override
    public List<T> findAll() {
        return (List<T>) this.getHibernateTemplate().find("from " + clazz.getSimpleName());
    }


    /**
     * 分页查询
     * @param pageCode 当前页码
     * @param pageSize 每页显示的数量
     * @param criteria 利息按条件查询
     * @return
     */
    @Override
    public PageBean<T> findByPage(Integer pageCode, Integer pageSize, DetachedCriteria criteria) {
        // 创建分页的对象
        PageBean<T> page = new PageBean<T>();
        page.setPageCode(pageCode);
        page.setPageSize(pageSize);

        // 设置查询聚合函数：SQL已经变成了 select count(*) from
        criteria.setProjection(Projections.rowCount());
        List<Number> list = (List<Number>) this.getHibernateTemplate().findByCriteria(criteria);
        if (list != null && list.size() > 0) {
            // 总记录数
            int totalCount = list.get(0).intValue();
            page.setTotalCount(totalCount);
        }

        // 清除SQL：count
        criteria.setProjection(null);

        List<T> beanList = (List<T>) this.getHibernateTemplate().findByCriteria(criteria, (pageCode - 1)*pageSize, pageSize);

        // 每页显示的数据
        page.setBeanList(beanList);
        return page;
    }
}
