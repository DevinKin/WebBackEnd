package cn.devinkin.sso.service.impl;

import cn.devinkin.common.json.JsonUtils;
import cn.devinkin.common.pojo.TaotaoResult;
import cn.devinkin.jedis.JedisClient;
import cn.devinkin.mapper.TbUserMapper;
import cn.devinkin.pojo.TbUser;
import cn.devinkin.pojo.TbUserExample;
import cn.devinkin.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author devinkin
 * <p>Title: UserServiceImpl</p>
 * <p>Description: 用户处理Servie</p>
 * @version 1.0
 * @see UserService
 * @since 16:09 2018/9/28
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${USER_SESSION}")
    private String USER_SESSION;

    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public TaotaoResult checkUserData(String data, Integer type) {
        // 执行查询
        // 设置查询条件
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        if (type == 1) {
            // 1. 判断用户名是否可用
            criteria.andUsernameEqualTo(data);
        } else if (type == 2) {
            // 2. 判断手机是否可用
            criteria.andPhoneEqualTo(data);
        } else if (type == 3){
            // 3. 判断邮箱是否可用
            criteria.andEmailEqualTo(data);
        } else {
            return TaotaoResult.build(400, "请求参数中非法数据");
        }
        List<TbUser> list = tbUserMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            // 查询到数据,返回false
            return TaotaoResult.ok(false);
        }
        return TaotaoResult.ok(true);
    }

    @Override
    public TaotaoResult regist(TbUser user) {
        // 检查数据的有效性
        if (StringUtils.isBlank(user.getUsername())) {
            return TaotaoResult.build(400, "用户名不能为空");
        }
        // 判断用户名是否重复
        TaotaoResult taotaoResult = checkUserData(user.getUsername(), 1);
        if (!(boolean)taotaoResult.getData()) {
            return TaotaoResult.build(400, "用户名重复");
        }
        // 判断密码是否为空
        if (StringUtils.isBlank(user.getPassword())) {
            return TaotaoResult.build(400, "密码不能为空");
        }
        // 判断手机是否为空,进行重复性校验
        if (StringUtils.isNotBlank(user.getPhone())) {
            taotaoResult = checkUserData(user.getPhone(), 2);
            if (!(boolean)taotaoResult.getData()) {
                return TaotaoResult.build(400, "电话号码重复");
            }
        }

        // 判断email不为空为空,进行重复性校验
        if (StringUtils.isNotBlank(user.getEmail())) {
            // 是否重复校验
            taotaoResult = checkUserData(user.getEmail(), 3);
            if (!(boolean)taotaoResult.getData()) {
                return TaotaoResult.build(400, "Email重复");
            }
        }
        // 补全user的属性
        user.setCreated(new Date());
        user.setUpdated(new Date());
        // 密码进行md5加密,使用Spring的MD5加密工具类,把字节数组加密
        String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Password);
        // 插入数据
        tbUserMapper.insert(user);
        // 返回注册成功
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult login(String username, String password) {
        // 判断用户名和密码是否正确
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> list = tbUserMapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            // 返回登录失败
            return TaotaoResult.build(400, "用户名或密码不正确");
        }
        TbUser user = list.get(0);
        // 密码要进行md5加密后再校验
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
            // 返回登录失败
            return TaotaoResult.build(400, "用户名或密码不正确");
        }
        // 生成token,使用uuid
        String token = UUID.randomUUID().toString();
        // 把用户信息保存到redis,key就是token,value就是用户信息,清空密码,提高安全性
        user.setPassword(null);
        jedisClient.set(USER_SESSION + ":" + token, JsonUtils.objectToJson(user));
        // 设置key的过期时间,30分钟过期
        jedisClient.expire(USER_SESSION + ":" + token, SESSION_EXPIRE);
        // 返回登录成功,其中要把token返回
        return TaotaoResult.ok(token);
    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        String json = jedisClient.get(USER_SESSION + ":" + token);

        if (StringUtils.isBlank(json)) {
            return TaotaoResult.build(400, "用户登录已过期");
        }
        // 把json转换成User对象
        TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
        // 重置Session的过期时间
        jedisClient.expire(USER_SESSION + ":" + token, SESSION_EXPIRE);
        return TaotaoResult.ok(user);
//        return TaotaoResult.ok(json);
    }

    @Override
    public TaotaoResult logout(String token) {
        // 将token对应的有效时间设置为0
        jedisClient.expire(USER_SESSION + ":" + token, 0);
        return TaotaoResult.ok();
    }
}
