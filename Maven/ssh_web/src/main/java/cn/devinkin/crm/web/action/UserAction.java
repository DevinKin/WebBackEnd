package cn.devinkin.crm.web.action;

import cn.devinkin.crm.domain.User;
import cn.devinkin.crm.service.UserService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用户的控制器
 * @author king
 */
public class UserAction extends ActionSupport implements ModelDriven<User>{

    private User user = new User();

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @Override
    public User getModel() {
        return user;
    }


    /**
     * 通过登录名判断登录名是否存在
     * @return
     */
    public String checkCode() {
        // 调用业务层，查询
        User u = userService.checkCode(user.getUser_code());

        // 进行判断

        // 获取输出流
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        try {
            PrintWriter writer = response.getWriter();
            if (u != null) {
                // 说明登录名查询到用户了，说明登录名已经存在了
                writer.print("no");
            } else {
                // 说明，不存在登录名，可以注册
                writer.print("yes");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }


    /**
     * 注册功能
     * @return path 路径
     */
    public String regist() {
        // 接受请求参数
        userService.save(user);
        return LOGIN;
    }


    /**
     * 登录功能
     * @return
     */
    public String login() {
        User existUser = userService.login(user);

        // 判断，登录名或者密码错误了
        if (existUser == null) {
            return LOGIN;
        } else {
            ServletActionContext.getRequest().getSession().setAttribute("existUser", existUser);
            return "loginOK";
        }
    }


    /**
     * 退出功能
     * @return
     */
    public String exit() {
        ServletActionContext.getRequest().getSession().removeAttribute("existUser");
        return LOGIN;
    }
}
