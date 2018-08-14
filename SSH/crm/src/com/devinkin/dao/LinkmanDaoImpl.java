package com.devinkin.dao;

import com.devinkin.domain.Linkman;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

public class LinkmanDaoImpl extends BaseDaoImpl<Linkman> implements LinkmanDao{
    @Override
    public List<Linkman> findByCustId(Long cust_id) {
        return (List<Linkman>) this.getHibernateTemplate().find("from Linkman where lkm_cust_id = ?", cust_id);
    }

    @Override
    public List<Linkman> findAll() {
        DetachedCriteria criteria = DetachedCriteria.forClass(Linkman.class);
        criteria.setProjection(Projections.distinct(Projections.property("lkm_name")));
        List<String> nameList = (List<String>) this.getHibernateTemplate().findByCriteria(criteria);
        // 清空条件
        criteria.setProjection(null);
        // 获取联系人名字列表
        List<Linkman> list = new ArrayList<Linkman>();
        for (String name : nameList) {
             Linkman linkman = (Linkman) this.getHibernateTemplate().find("From Linkman where lkm_name = ?", name).get(0);
             list.add(linkman);
        }
        return list;
    }
}
