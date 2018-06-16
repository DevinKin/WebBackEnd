package cn.devinkin.user.service.impl;

import cn.devinkin.contant.Constant;
import cn.devinkin.user.dao.UserDao;
import cn.devinkin.user.dao.impl.UserDaoImpl;
import cn.devinkin.user.domain.User;
import cn.devinkin.user.service.UserService;
import cn.devinkin.utils.BeanFactory;
import cn.devinkin.utils.MailUtils;

import java.text.MessageFormat;

public class UserServiceImpl implements UserService {
    UserDao userDao = (UserDao) BeanFactory.getBean("UserDao");

    /**
     * 用户注册
     *
     * @param user 用户
     */
    @Override
    public void regist(User user) throws Exception {
        userDao.addUser(user);
        /**
         * 1. 发送邮件
         */
        /**
         * 收件人地址，邮件内容
         */
        String emailContent = MessageFormat.format(Constant.EMAILCONTENT, user.getCode());
        MailUtils.sendMail(user.getEmail(), emailContent);
    }

    /**
     * 用户激活
     *
     * @param code 激活码
     * @return String 返回信息
     */
    @Override
    public String active(String code) throws Exception {
        /**
         * 1. 通过激活码获取用户
         * 2. 判断用户是否为空
         *  1. 若不为空
         *      1. 判断激活状态是否为1
         *          1. 为1，请求转发到msg.jsp，你已经激活了！
         *          2. 为0，修改state为1
         *  2. 请求转发到msg.jsp，激活码错误
         */
        User user = userDao.getUserByCode(code);
        if (user == null) {
            return Constant.CODE_INVALID;
        }

        if (user.getState() == 0) {
            userDao.active(code);
            return Constant.ACTIVE_SUCCESS;
        } else if (user.getState() == 1) {
            return Constant.ACTIVED;
        }
        return null;
    }

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @Override
    public User login(String username, String password) throws Exception {
        return userDao.login(username,password);
    }

    /**
     * 通过用户名查找用户
     * @param username
     * @return 用户
     * @throws Exception
     */
    @Override
    public User findUserByName(String username) throws Exception {
        return userDao.findUserByName(username);
    }

    /**
     * 通过email查找用户
     * @param email
     * @return 用户
     * @throws Exception
     */
    @Override
    public User findUserByEmail(String email) throws Exception {
        return userDao.findUserByEmail(email);
    }
}
