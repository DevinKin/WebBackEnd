package cn.devinkin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {

    // 跳转到登录页面
    @RequestMapping("/login")
    public String login() throws Exception {
        return "login";
    }

    @RequestMapping("/submit")
    public String submit(String username, String pwd, HttpServletRequest request) throws Exception {
        if (username != null && !username.trim().isEmpty()) {
            // 判断用户名和密码的正确性,如果正确则将登录信息放入session
            if (pwd != null && !pwd.trim().isEmpty()) {
                request.getSession().setAttribute("username", username);
            }
        }

        // 跳转到列表页
        return "redirect:/items/list";
    }
}
