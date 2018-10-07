package cn.devinkin.sso.controller;

import cn.devinkin.common.json.JsonUtils;
import cn.devinkin.common.pojo.TaotaoResult;
import cn.devinkin.common.utils.CookieUtils;
import cn.devinkin.pojo.TbUser;
import cn.devinkin.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;

/**
 * @author devinkin
 * <p>Title: UserContrller</p>
 * <p>Description: 用户处理Controller</p>
 * @version 1.0
 * @see
 * @since 16:27 2018/9/28
 */
@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;

    @RequestMapping(value = "/check/{param}/{type}", method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult checkUserData(@PathVariable String param,@PathVariable Integer type) {
        TaotaoResult result = userService.checkUserData(param, type);
        return result;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult register(TbUser user) {
        TaotaoResult result = userService.regist(user);
        return result;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        TaotaoResult result = userService.login(username, password);
        // 当返回TOKEN的时候才将token写入cookie
        if (result.getData() != null) {
            // 把token写入cookie
            CookieUtils.setCookie(request,response, TOKEN_KEY , result.getData().toString());
        }
        return result;
    }

//    @RequestMapping(value = "/token/{token}", method = RequestMethod.GET,
//    // 指定返回响应数据的content-Type
//    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ResponseBody
//    public String getUserByToken(@PathVariable String token,String callback) {
//        TaotaoResult result = userService.getUserByToken(token);
//        // 判断是否为jsonp请求
//        if (StringUtils.isNotBlank(callback)) {
//            return callback + "(" + JsonUtils.objectToJson(result) + ");";
//        }
//        return JsonUtils.objectToJson(result);
//    }

    @RequestMapping(value = "/token/{token}", method = RequestMethod.GET)
    @ResponseBody
    public Object getUserByToken(@PathVariable String token,String callback) {
        TaotaoResult result = userService.getUserByToken(token);
        if (StringUtils.isNotBlank(callback)) {
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            // 设置毁掉方法
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
        return result;
    }
    @RequestMapping(value = "/logout/{token}", method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult logout(@PathVariable String token) {
        TaotaoResult result = userService.logout(token);
        return result;
    }
}
