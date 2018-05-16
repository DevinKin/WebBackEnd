package cn.devinkin.regist.web.servlet;

import cn.devinkin.regist.domain.User;
import cn.devinkin.regist.service.UserService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.handlers.BeanHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet(name = "RegistServlet",urlPatterns = {"/RegistServlet"})
public class RegistServlet extends HttpServlet {
    private UserService userService = new UserService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 设置编码
         * 2. 封装数据
         * 3. 调用UserService#regist(User user)方法
         * 4. 判断是否返回成功
         *  1. 返回成功，请求转发到MsgServlet，成功信息
         *  2. 返回错误，请求转发到sgServlet，错误信息
         */
        request.setCharacterEncoding("utf-8");
        User user = new User();
        try {
            BeanUtils.populate(user, request.getParameterMap());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        String msg = "";
        if (userService.regist(user) == 1) {
            msg = "注册成功";
        } else {
            msg = "注册失败";
        }
        request.setAttribute("msg",msg);
        request.getRequestDispatcher("/MsgServlet").forward(request,response);

    }
}
