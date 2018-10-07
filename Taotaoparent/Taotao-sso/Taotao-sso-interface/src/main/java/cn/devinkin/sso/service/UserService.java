package cn.devinkin.sso.service;

import cn.devinkin.common.pojo.TaotaoResult;
import cn.devinkin.pojo.TbUser;

/**
 * @author devinkin
 * <p>Title: cn.devinkin.sso.service.UserService</p>
 * <p>Description: 用户处理服务</p>
 * @version 1.0
 * @see
 * @since 16:07 2018/9/28
 */
public interface UserService {
    /**
     * 校验用户信息
     * @param data 用户数据
     * @param type 数据类型
     * @return
     */
    TaotaoResult checkUserData(String data, Integer type);

    /**
     * @param user 用户信息
     * @return
     */
    TaotaoResult regist(TbUser user);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    TaotaoResult login(String username, String password);

    /**
     * 根据token查找用户
     * @param token token
     * @return
     */
    TaotaoResult getUserByToken(String token);

    /**
     * 根据token删除用户信息缓存,使用户安全退出
     * @param token
     * @return
     */
    TaotaoResult logout(String token);
}
