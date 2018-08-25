package cn.devinkin.jk.service.impl;

import cn.devinkin.jk.dao.BaseDao;
import cn.devinkin.jk.domain.User;
import cn.devinkin.jk.service.UserService;
import cn.devinkin.jk.utils.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Transactional
public class UserServiceImpl implements UserService {


    private BaseDao baseDao;

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    private SimpleMailMessage mailMessage;

    private JavaMailSender mailSender;

    public void setMailMessage(SimpleMailMessage mailMessage) {
        this.mailMessage = mailMessage;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public List<User> find(String hql, Class<User> entityClass, Object[] params) {
        return baseDao.find(hql, entityClass, params);
    }

    @Override
    public User get(Class<User> entityClass, Serializable id) {
        return baseDao.get(entityClass, id);
    }

    @Override
    public Page<User> findPage(String hql, Page<User> page, Class<User> entityClass, Object[] params) {
        return baseDao.findPage(hql, page, entityClass, params);
    }

    @Override
    public void saveOrUpdate(final User entity) {
        if (UtilFuns.isEmpty(entity.getId())) {
            // 新增
            String id = UUID.randomUUID().toString();
            entity.setId(id);
            entity.getUserInfo().setId(id);

            // 补充Shiro添加后的bug
            entity.setPassword(Encrypt.md5(SysConstant.DEFAULT_PASS, entity.getUserName()));
        }

        // 再开启一个新的线程完成邮件发送功能
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                // 设置邮件简单邮件对象的属性
                mailMessage.setTo(entity.getUserInfo().getEmail());
                mailMessage.setSubject("新员工入职的系统账户通知");
                mailMessage.setText("欢迎你加入本集团，您的用户名：" + entity.getUserName() + "，初始密码：" + SysConstant.DEFAULT_PASS);

                // 发送邮件
                mailSender.send(mailMessage);
            }
        });

        th.start();

        // 记录保存
        baseDao.saveOrUpdate(entity);
    }

    @Override
    public void saveOrUpdateAll(Collection<User> entitys) {
        baseDao.saveOrUpdateAll(entitys);
    }

    @Override
    public void deleteById(Class<User> entityClass, Serializable id) {
        baseDao.deleteById(entityClass, id); // 删除用户
    }

    @Override
    public void delete(Class<User> entityClass, Serializable[] ids) {
        for (Serializable id : ids) {
            this.deleteById(User.class, id);
        }
    }
}
