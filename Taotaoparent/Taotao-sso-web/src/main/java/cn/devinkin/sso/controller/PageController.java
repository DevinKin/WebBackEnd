package cn.devinkin.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author devinkin
 * <p>Title: PageController</p>
 * <p>Description: 展示和注册页面的Controller</p>
 * @version 1.0
 * @see
 * @since 22:54 2018/9/28
 */
@RequestMapping("/user/page")
@Controller
public class PageController {

    @RequestMapping("/register")
    public String showRegister() {
        return "register";
    }

    @RequestMapping("/login")
    public String showLogin(String url, Model model) {
        model.addAttribute("redirect", url);
        return "login";
    }
}
