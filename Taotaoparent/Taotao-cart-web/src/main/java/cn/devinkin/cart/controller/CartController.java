package cn.devinkin.cart.controller;

import cn.devinkin.common.json.JsonUtils;
import cn.devinkin.common.pojo.TaotaoResult;
import cn.devinkin.common.utils.CookieUtils;
import cn.devinkin.manager.service.ItemService;
import cn.devinkin.pojo.TbItem;
import org.apache.commons.lang3.StringUtils;
import org.jboss.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author devinkin
 * <p>Title: CartController</p>
 * <p>Description: 购物车管理Controller</p>
 * @version 1.0
 * @see
 * @since 20:14 2018/10/5
 */
@RequestMapping("/cart")
@Controller
public class CartController {

    @Autowired
    private ItemService itemService;

    @Value("${CART_KEY}")
    private String CART_KEY;

    @Value("${CART_EXPIRE}")
    private Integer CART_EXPIRE;

    @RequestMapping(value = "/add/{itemId}", method = RequestMethod.GET)
    public String addItemCart(@PathVariable Long itemId,
                              @RequestParam(defaultValue = "1") Integer num,
                              HttpServletRequest request, HttpServletResponse response) {
        // 取购物车商品列表
        List<TbItem> cartItemList = getCartItemList(request);
        boolean flag = false;
        // 判断商品在购物车中是否存在
        for (TbItem tbItem : cartItemList) {
            if (tbItem.getId() == itemId.longValue()) {
                // 如果存在,商品数量相加
                tbItem.setNum(tbItem.getNum() + num);
                flag = true;
                break;
            }
        }
        if (!flag) {
            // 如果不存在,添加一个新的商品
            // 需要调用服务取商品信息
            TbItem tbItem = itemService.getItemById(itemId);
            // 设置商品购买的数量
            tbItem.setNum(num);
            // 取一张图片
            String image = tbItem.getImage();
            if (StringUtils.isNotBlank(image)) {
                String[] images = image.split(",");
                tbItem.setImage(images[0]);
            }
            // 把商品添加到购物车
            cartItemList.add(tbItem);
        }

        // 把购物车列表写入cookie
        CookieUtils.setCookie(request,response, CART_KEY, JsonUtils.objectToJson(cartItemList),
                CART_EXPIRE, true);
        // 返回添加成功页面
        return "cartSuccess";
    }

    @RequestMapping("/cart")
    public String showCartList(HttpServletRequest request) {
        // 从cookie取购物车列表
        List<TbItem> cartItemList = getCartItemList(request);
        // 把购物车列表传递给jsp
        request.setAttribute("cartList", cartItemList);
        // 返回逻辑视图
        return "cart";
    }

    @RequestMapping("/update/num/{itemId}/{num}")
    @ResponseBody
    public TaotaoResult updateCartItemNum(@PathVariable Long itemId, @PathVariable Integer num,
                                          HttpServletRequest request, HttpServletResponse response) {
        // 从cookie中取出购物车列表
        List<TbItem> cartItemList = getCartItemList(request);
        // 查询到对应的商品
        for (TbItem tbItem : cartItemList) {
            if (tbItem.getId() == itemId.longValue()) {
                // 更新商品数量
                tbItem.setNum(num);
                break;
            }
        }
        // 把购物车列表写入cookie
        CookieUtils.setCookie(request,response, CART_KEY, JsonUtils.objectToJson(cartItemList),
                CART_EXPIRE, true);
        // 返回成功
        return TaotaoResult.ok();
    }

    @RequestMapping("/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId,
                                 HttpServletRequest request, HttpServletResponse response) {
        // 从cookie中取购物车列表
        List<TbItem> cartItemList = getCartItemList(request);
        // 找到对应的商品
        for (TbItem tbItem : cartItemList) {
            if (tbItem.getId() == itemId.longValue()) {
                // 删除商品
                cartItemList.remove(tbItem);
                break;
            }
        }
        // 把购物车列表回写到Cookie
        CookieUtils.setCookie(request,response, CART_KEY, JsonUtils.objectToJson(cartItemList),
                CART_EXPIRE, true);
        // 重定向购物车列表页面
        return "redirect:/cart/cart.html";
    }

    private List<TbItem> getCartItemList(HttpServletRequest request) {
        // 从cookie中取购物车商品列表
        String json = CookieUtils.getCookieValue(request, CART_KEY, true);
        if (StringUtils.isBlank(json)) {
            // 如果没有内容,返回一个空的列表
            return new ArrayList<>();
        }
        List<TbItem> tbItems = JsonUtils.jsonToList(json, TbItem.class);
        return tbItems;
    }
}
