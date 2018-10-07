package cn.devinkin.order.controller;

import cn.devinkin.common.json.JsonUtils;
import cn.devinkin.common.pojo.TaotaoResult;
import cn.devinkin.common.utils.CookieUtils;
import cn.devinkin.order.pojo.OrderInfo;
import cn.devinkin.order.service.OrderService;
import cn.devinkin.pojo.TbItem;
import cn.devinkin.pojo.TbUser;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author devinkin
 * <p>Title: OrderCartController</p>
 * <p>Description: 订单确认页面处理Controller</p>
 * @version 1.0
 * @see
 * @since 22:22 2018/10/5
 */
@RequestMapping("/order")
@Controller
public class OrderCartController {

    @Value("${CART_KEY}")
    private String CART_KEY;

    @Autowired
    private OrderService orderService;

    /**
     * 展示订单确认页面
     * @param request 请求
     * @return
     */
    @RequestMapping("/order-cart")
    public String showOrderCart(HttpServletRequest request) {
        // 用户必须是登录状态
        // 取用户id
        TbUser user = (TbUser) request.getAttribute("user");
        System.out.println(user.getUsername());
        // 根据用户信息取收货地址列表,使用静态数据
        // 把收货地址列表取出传递给页面
        // 从cookie中取购物车商品列表展示到页面
        List<TbItem> cartItemList = getCartItemList(request);
        request.setAttribute("cartList", cartItemList);
        // 返回逻辑视图
        return "order-cart";
    }


    /**
     * 生成订单
     * @param orderInfo 订单pojo
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createOrder(OrderInfo orderInfo, Model model) {
        // 生成订单
        TaotaoResult result = orderService.createOrder(orderInfo);
        model.addAttribute("orderId", result.getData().toString());
        model.addAttribute("payment", orderInfo.getPayment());
        // 预计送达时间,预计三天后送达
        DateTime dateTime = new DateTime();
        dateTime = dateTime.plusDays(3);
        model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));
        // 返回逻辑视图
        return "success";
    }

    /**
     * 从cookie中获取购物车列表
     * @param request request请求
     * @return
     */
    private List<TbItem> getCartItemList(HttpServletRequest request) {
        String json = CookieUtils.getCookieValue(request, CART_KEY, true);
        if (StringUtils.isBlank(json)) {
            // 如果没有内容,返回一个空的列表
            return new ArrayList<>();
        }
        List<TbItem> tbItems = JsonUtils.jsonToList(json, TbItem.class);
        return tbItems;
    }
}
