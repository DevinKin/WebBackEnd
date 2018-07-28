package com.devinkin.service;

import com.devinkin.dao.CustomerDao;
import com.devinkin.dao.LinkmanDao;
import com.devinkin.domain.Customer;
import com.devinkin.domain.Linkman;
import com.devinkin.utils.HibernateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

public class LinkmanService {
    /**
     * 编写业务，保存联系人
     * 先把客户查询出来，设置到联系人中，再保存联系人
     * @param linkman
     * @param cust_id
     */
    public void save(Linkman linkman, Long cust_id) {
        // 先获取Session
        Session session = HibernateUtils.getCurrentSession();

        // 开启事务
        Transaction tr = session.beginTransaction();

        try {
            // 先查客户
            Customer c = new CustomerDao().findById(cust_id);
            // 设置客户
            linkman.setCustomer(c);

            // 保存联系人
            new LinkmanDao().save(linkman);

            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        } finally {

        }
    }


    public List<Linkman> findAll(DetachedCriteria criteria) {
        // 先获取Session
        Session session = HibernateUtils.getCurrentSession();

        // 开启事务
        Transaction tr = session.beginTransaction();
        List<Linkman> linkmans = null;
        try {
            // 保存联系人
            linkmans = new LinkmanDao().findAll(criteria);
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        } finally {

        }
        return linkmans;
    }

    public Linkman findById(Long link_id) {
        return new LinkmanDao().findById(link_id);
    }

    /**
     * 删除联系人
     * @param lkm_id 联系人id
     */
    public void deleteLinkman(Long lkm_id) {
        new LinkmanDao().delete(lkm_id);
    }
}
