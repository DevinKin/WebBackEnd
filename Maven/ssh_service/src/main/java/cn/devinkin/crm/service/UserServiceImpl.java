package cn.devinkin.crm.service;

import cn.devinkin.crm.dao.UserDao;
import cn.devinkin.crm.domain.User;
import cn.devinkin.crm.utils.MD5Utils;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserServiceImpl implements UserService{

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


    /**
     * 校验客户名称是否被注册
     * @param user_code 客户名称
     * @return
     */
    @Override
    public User checkCode(String user_code) {
        return userDao.checkCode(user_code);
    }


    /**
     * 注册客户，密码需要加密
     * @param user 客户对象
     */
    @Override
    public void save(User user) {
        String pwd = user.getUser_password();
        user.setUser_password(MD5Utils.md5(pwd));
        // 用户的状态默认是1状态
        user.setUser_state("1");
        // 调用持久层
        userDao.save(user);
    }


    /**
     * 用户登录，登录通过登录名和密码做校验
     * 先给密码加密，再查询
     * @param user 用户对象
     * @return
     */
    @Override
    public User login(User user) {
        String pwd = user.getUser_password();
        // 给密码加密
        user.setUser_password(MD5Utils.md5(pwd));
        // 查询
        return userDao.login(user);
    }
}
