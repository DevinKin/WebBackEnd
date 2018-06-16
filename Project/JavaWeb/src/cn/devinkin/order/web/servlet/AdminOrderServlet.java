package cn.devinkin.order.web.servlet;

import cn.devinkin.order.domain.Order;
import cn.devinkin.order.domain.OrderItem;
import cn.devinkin.order.service.OrderService;
import cn.devinkin.utils.BaseServlet;
import cn.devinkin.utils.BeanFactory;
import cn.devinkin.utils.JsonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "AdminOrderServlet", urlPatterns = "/adminOrder")
public class AdminOrderServlet extends BaseServlet {
    private OrderService orderService = (OrderService) BeanFactory.getBean("OrderService");

    public String findAllByState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 获取state参数
         * 2. 调用orderService查询，得到list
         * 3. 将list放入request域中，请求转发哦到list.jsp
         */
        String state = request.getParameter("state");
        List<Order> orderList = null;
        try {
            orderList = orderService.findAllByState(state);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("orderList", orderList);
        return "f:/store/admin/order/list.jsp";
    }


    /**
     * 通过oid查询订单详情
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String showDetailByOid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        /**
         * 1. 获取oid
         * 2. 调用service查询订单详情，范围值为list<OrderItem>
         * 3. 将list转换成json数据，回写
         */
        String oid = request.getParameter("oid");
        List<OrderItem> orderItemList = null;
        try {
             orderItemList = orderService.getOrderByOid(oid).getOrderItemList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //排除不回写的数据
        /**
         * 1. 获取JsonConfig，给定排除的数据
         * 2. 使用JSONArray.fromObject转换为json
         */
        JsonConfig config = JsonUtil.configJson(new String[]{"class","itemid","order"});
        //必须将java.sql.Date转换为java.util.Date，不然会抛出异常
        for (OrderItem oi : orderItemList) {
            Date date = new Date(oi.getProduct().getPdate().getTime());
            oi.getProduct().setPdate(date);
        }
        JSONArray json = JSONArray.fromObject(orderItemList,config);
        response.getWriter().println(json.toString());
        return null;
    }
}
