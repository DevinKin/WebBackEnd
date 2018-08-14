package com.devinkin.dao;

import com.devinkin.domain.User;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

/**
 * 持久层
 * @author king
 */
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{

    /**
     * 通过登录名进行验证
     * @param user_code 用户名
     * @return
     */
    @Override
    public User checkCode(String user_code) {
        List<User> list = (List<User>) this.getHibernateTemplate().find("from User where user_code = ?", user_code);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }



    /**
     * 登录功能
     * 通过用户名和密码，用户的状态进行校验
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        // QBC的查询，按条件查询
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        // 拼接查询的条件
        criteria.add(Restrictions.eq("user_code", user.getUser_code()));
        criteria.add(Restrictions.eq("user_password", user.getUser_password()));
        criteria.add(Restrictions.eq("user_name", user.getUser_name()));
        // 查询
        List<User> list = (List<User>) this.getHibernateTemplate().findByCriteria(criteria);
        if (list != null && list.size() > 0)
            return list.get(0);
        return null;
    }
}
