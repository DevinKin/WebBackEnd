package com.devinkin.dao;


import com.devinkin.domain.Customer;
import com.devinkin.domain.PageBean;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

/**
 * 持久层
 * @author king
 */
public class CustomerDaoImpl extends BaseDaoImpl<Customer> implements CustomerDao{
    /**
     * 统计客户的来源
     * @return
     */
    @Override
    public List<Object[]> findBySource() {
        // 定义HQL
        // select * from cst_customer c, base_dict d where d.dict_id = c.cust_source
        // 分组查询 SELECT * FROM cst_customer c, base_dict d where d.dict_id = c.cust_source group by d.dict_id
        // 查询内容：SELECT d.dict_item_name, count(*) FROM cst_customer c, base_dict d where d.dict_id = c.cust_source group by d.dict_id
        String hql = "select c.source.dict_item_name, count(*) from Customer c inner join c.source group by c.source";
        // 查询
        return (List<Object[]>) this.getHibernateTemplate().find(hql);
    }

    @Override
    public List<Object[]> findByIndustry() {
        String hql = "select c.industry.dict_item_name, count(*) from Customer c inner join c.industry group by c.industry";
        return (List<Object[]>) this.getHibernateTemplate().find(hql);
    }

//    public CustomerDaoImpl() {
//        // 调用父类的构造方法
//        super(Customer.class);
//    }


    /**
     * 保存客户
     * @param customer 客户对象
    @Override
    public void save(Customer customer) {
        System.out.println(customer);

        // 报数据保存到数据库中
        this.getHibernateTemplate().save(customer);
    }

    /**
     * 分页查询客户
     * @param pageCode 当前页码
     * @param pageSize 每页数量
     * @param criteria 离线条件查询对象
     * @return
    @Override
    public PageBean<Customer> findByPage(Integer pageCode, Integer pageSize, DetachedCriteria criteria) {
        PageBean<Customer> page = new PageBean<>();

        page.setPageCode(pageCode);
        page.setPageSize(pageSize);

        // 分页查询数据，每页显示的数据，

        // 先查询记录数 select count(*)
        criteria.setProjection(Projections.rowCount());
        List<Number> list = (List<Number>) this.getHibernateTemplate().findByCriteria(criteria);
        if (list != null && list.size() > 0) {
            int totalCount = list.get(0).intValue();
            page.setTotalCount(totalCount);
        }

        // 强调：把 select count(*) 先清空，变成查询 select *
        criteria.setProjection(null);
        List<Customer> beanList = (List<Customer>) this.getHibernateTemplate().findByCriteria(criteria, (pageCode - 1) * pageSize, pageSize);
        page.setBeanList(beanList);
        return page;
    }


    /**
     * 通过主键，查询客户
     * @param cust_id 主键
     * @return
    @Override
    public Customer findById(Long cust_id) {
        return this.getHibernateTemplate().get(Customer.class, cust_id);
    }

    /**
     * 删除客户
     * @param customer 客户
    @Override
    public void delete(Customer customer) {
        this.getHibernateTemplate().delete(customer);
    }


    /**
     * 更新客户
     * @param customer 客户对象
    @Override
    public void update(Customer customer) {
        this.getHibernateTemplate().update(customer);
    }
     */
}
