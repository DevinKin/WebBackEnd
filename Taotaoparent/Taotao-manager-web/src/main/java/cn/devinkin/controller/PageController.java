package cn.devinkin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author devinkin
 * <p>Title: PageController</p>
 * <p>Description: 页面展示Controller</p>
 * @version 1.0
 * @see
 * @since 15:31 2018/9/19
 */
@Controller
public class PageController {
    @RequestMapping("/")
    public String showIndex() {
        return "index";
    }

    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page) {
        return page;
    }
}
