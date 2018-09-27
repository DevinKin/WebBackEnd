package cn.devinkin.item.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author devinkin
 * <p>Title: HtmlGenContrller</p>
 * <p>Description: 网页静态化处理Controller</p>
 * @version 1.0
 * @see
 * @since 22:25 2018/9/27
 */
@Controller
public class HtmlGenContrller {
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @RequestMapping("/genhtml")
    @ResponseBody
    public String genHtml() throws IOException, TemplateException {
        // 生成静态页面
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Template template = configuration.getTemplate("hello.ftl");
        Map data = new HashMap<>();
        data.put("hello", "spring freemarker test");
        Writer out = new FileWriter(new File("D:\\WebBackEnd\\Taotaoparent\\Taotao-item-web\\src\\main\\webapp\\out\\hello.txt"));
        template.process(data,out);
        // 返回结果
        return "OK";
    }
}
