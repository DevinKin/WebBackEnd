package com.devinkin.dao;

import com.devinkin.domain.Linkman;
import com.devinkin.utils.HibernateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class LinkmanDao {
    /**
     * 保存联系人
     * @param linkman
     */
    public void save(Linkman linkman) {
        Session session = HibernateUtils.getCurrentSession();

        session.save(linkman);
    }

    public void edit(Linkman linkman) {
        Session session = HibernateUtils.getCurrentSession();

        session.update(linkman);
    }


    /**
     * 查找所有联系人
     * @param criteria 筛选条件
     * @return
     */
    public List<Linkman> findAll(DetachedCriteria criteria) {
        Session session = HibernateUtils.getCurrentSession();

        //QBC查询
        return criteria.getExecutableCriteria(session).list();
    }

    /**
     * 通过id查找联系人
     * @param link_id 联系人Id
     * @return
     */
    public Linkman findById(Long link_id) {
        Session session = HibernateUtils.getSession();
        return session.get(Linkman.class, link_id);
    }

    /**
     * 删除联系人
     * @param lkm_id 联系人id
     */
    public void delete(Long lkm_id) {
        Session session = HibernateUtils.getSession();
        Transaction tr = session.beginTransaction();

        Linkman linkman = null;
        try {
            linkman = session.get(Linkman.class, lkm_id);
            if (linkman != null)
                session.delete(linkman);
            tr.commit();

        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

}
