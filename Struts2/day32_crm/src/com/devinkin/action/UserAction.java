package com.devinkin.action;

import com.devinkin.domain.User;
import com.devinkin.service.UserService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 用户模块的控制器
 * @author king
 */
public class UserAction extends ActionSupport {
    /**
     * 处理登录功能
     * @return
     */
    public String login() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, map);
            // 调用业务层
            User existUser = new UserService().login(user);
            // 判断
            if (existUser == null) {
                // 说明用户名或密码错误
                return LOGIN;
            } else {
                // 存入到session中
                request.getSession().setAttribute("existUser", user);
                return SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("正常执行了...");
        return NONE;
    }
}
