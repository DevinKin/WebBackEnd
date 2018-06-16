package cn.devinkin.user.web.servlet;

import cn.devinkin.contant.Constant;
import cn.devinkin.utils.*;
import cn.devinkin.user.domain.User;
import cn.devinkin.user.service.UserService;
import cn.devinkin.user.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;

@WebServlet(name = "UserServlet", urlPatterns = "/user")
public class UserServlet extends BaseServlet {
    private UserService userService = (UserService) BeanFactory.getBean("UserService");

    /**
     * regist页面
     *
     * @param request
     * @param response
     * @return 请求转发的路径
     * @throws ServletException
     * @throws IOException
     */
    public String registUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "f:/store/jsp/register.jsp";
    }


    /**
     * 用户注册
     *
     * @param request
     * @param response
     * @return 请求转发的路径
     * @throws ServletException
     * @throws IOException
     */
    public String regist(HttpServletRequest request, HttpServletResponse response) throws Exception {
        /**
         * 1. 封装数据
         *  1. 设置uid
         *  2. 设置激活码
         *  3. 设置
         * 2. 调用service完成注册
         * 3. 页面请求转发，
         */
        User user = new User();

        //注册自定义转换器
        ConvertUtils.register(new DateConvert(), Date.class);
        BeanUtils.populate(user, request.getParameterMap());
        request.setAttribute("reguser", user);

        String vcode = request.getParameter("vcode");
        if (!vcode.equals(request.getSession().getAttribute("vcode"))) {
            request.setAttribute("vcodeError", "验证码错误");
            return "f:/store/jsp/register.jsp";
        }

        //加密密码
        user.setPassword(MD5Utils.md5(user.getPassword()));
        user.setUid(UUIDUtils.getId());
        user.setCode(UUIDUtils.getCode());

        userService.regist(user);

        request.setAttribute("msg", "用户注册成功，请去邮箱激活！");
        return "f:/store/jsp/msg.jsp";
    }

    /**
     * 用户激活
     *
     * @param request
     * @param response
     * @return 请求转发路径
     * @throws Exception
     */
    public String active(HttpServletRequest request, HttpServletResponse response) throws Exception {
        /**
         * 1. 获取激活码
         * 2. 调用service完成激活
         * 3. 页面请求转发到msg.jsp
         */
        String code = request.getParameter("code");
        String msg = userService.active(code);
        request.setAttribute("msg", msg);
        return "f:/store/jsp/msg.jsp";
    }

    /**
     * 登录页面
     *
     * @param request
     * @param response
     * @return 跳转路径
     * @throws Exception
     */
    public String loginUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "f:/store/jsp/login.jsp";
    }


    /**
     * 用户登录
     *
     * @param request
     * @param response
     * @return 跳转路径
     * @throws Exception
     */
    public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        /**
         * 1. 获取用户名和密码
         * 2. 调用service完成登录操作
         * 3. 将用户保存到session域中
         * 3. 判断user是否为空
         *  1. 若为空
         *      1. 返回信息，用户名和密码不匹配
         *  2. 若不为空
         *      1. 判断是否激活
         *          1. 已经激活，将user放入session域中
         *          2. 没有激活，返回信息没有激活
         * 4. 重定向到/store/jsps/index.jsp
         */
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        password = MD5Utils.md5(password);
        User user = userService.login(username, password);
        String vcode = request.getParameter("vcode");
        if (!vcode.equals(request.getSession().getAttribute("vcode"))) {
            request.setAttribute("msg", "验证码错误");
            return "f:/store/jsp/login.jsp";
        }

        if (user == null) {
            request.setAttribute("msg", "用户名和密码不匹配");
            return "f:/store/jsp/login.jsp";
        } else {
            if (Constant.USER_IS_ACTIVE == user.getState()) {
                request.getSession().setAttribute("user", user);
            } else {
                request.setAttribute("msg", "用户没有激活");
                return "f:/store/jsp/login.jsp";
            }
        }


        /**
         * 用户是否勾选自动登录
         */
        String autoLogin = request.getParameter("autoLogin");
        if (Constant.AUTO_LOGIN.equals(autoLogin)) {
            //创建Cookie
            Cookie cookie = new Cookie("autologin", URLEncoder.encode(username,"utf-8") + "-" + password);
            cookie.setMaxAge(3600);
            cookie.setPath(request.getContextPath());
            response.addCookie(cookie);
        }

        /**
         * 用户是否勾选记住用户名
         */
        String saveName = request.getParameter("saveName");
        if (Constant.SAVE_NAME.equals(saveName)) {
            //创建Cookie
            Cookie cookie = new Cookie("savename", URLEncoder.encode(username, "utf-8"));
            cookie.setMaxAge(3600);
            cookie.setPath(request.getContextPath());
            response.addCookie(cookie);
        }


        return "r:/index?method=index";
    }

    /**
     * 用户退出
     *
     * @param request
     * @param response
     * @return 页面重定向到首页
     * @throws Exception
     */
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        /**
         * 1. 使session无效
         * 2. 重定向首页
         * 3. 处理自动登录
         */
        request.getSession().invalidate();
        //处理自动登录功能
        return "r:/store/jsp/index.jsp";
    }

    /**
     * 通过用户名查找用户
     *
     * @param request
     * @param response
     * @return 用户
     * @throws Exception
     */
    public String ajaxFindUserByName(HttpServletRequest request, HttpServletResponse response) throws Exception {
        /**
         * 1. 获取请求参数username
         * 2. 调用sergvice方法，返回user
         *  1. user为空，响应回写0
         *  2. user不为空，响应回写1
         */
        String username = request.getParameter("username");
        User user = userService.findUserByName(username);
        if (user == null)
            response.getWriter().print("0");
        else
            response.getWriter().print("1");
        return null;
    }

    public String ajaxFindUserByEmail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        /**
         * 1. 获取请求参数email
         * 2. 调用sergvice方法，返回user
         *  1. user为空，响应回写0
         *  2. user不为空，响应回写1
         */
        String email = request.getParameter("email");
        User user = userService.findUserByEmail(email);
        if (user == null)
            response.getWriter().print("0");
        else
            response.getWriter().print("1");
        return null;
    }
}
