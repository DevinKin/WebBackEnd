package cn.devinkin.order.web.servlet;

import cn.devinkin.cart.domain.Cart;
import cn.devinkin.cart.domain.CartItem;
import cn.devinkin.order.domain.Order;
import cn.devinkin.order.domain.OrderItem;
import cn.devinkin.order.service.OrderService;
import cn.devinkin.product.domain.PageBean;
import cn.devinkin.user.domain.User;
import cn.devinkin.utils.BaseServlet;
import cn.devinkin.utils.BeanFactory;
import cn.devinkin.utils.PaymentUtil;
import cn.devinkin.utils.UUIDUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * 订单模块
 */
@WebServlet(name = "OrderServlet", urlPatterns = "/order")
public class OrderServlet extends BaseServlet {
    private OrderService orderService = (OrderService) BeanFactory.getBean("OrderService");

    public String addOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 0. 判断用户是否登录
         * 1. 封装数据
         * 2. 调用service添加订单
         * 3. 将order放入request域中
         * 4. 清空购物车中选中商品
         * 5. 请求转发
         */
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("msg", "你还没有登录，请先登录吧");
            return "f:/store/jsp/msg.jsp";
        }
        /**
         * 封装数据
         * 1. 订单id
         * 2. 订单时间
         * 3. 订单总金额
         * 4. 订单的被选中订单项
         * 5. User用户
         */
        Order order = new Order();
        order.setOid(UUIDUtils.getId());
        order.setOrdertime(new Date());

        //获取session中的cart
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        //获取选中购物车条目总金额
        order.setTotal(cart.getSelectedTotal());

        /**
         * 获取subcart中items
         * 遍历items，组装成Item
         * 将orderItem添加到list中
         */
        for (CartItem cartItem : cart.getSelectedCartItems()) {
            OrderItem orderItem = new OrderItem();
            // 设置id
            orderItem.setItemid(UUIDUtils.getId());
            //设置购买数量
            orderItem.setCount(cartItem.getCount());
            //设置小计
            orderItem.setSubtotal(cartItem.getSubtotal());
            //设置product
            orderItem.setProduct(cartItem.getProduct());
            //设置order
            orderItem.setOrder(order);
            //添加到list中
            order.getOrderItemList().add(orderItem);
        }

        order.setUser(user);

        if (order.getTotal() == 0.0) {
            request.setAttribute("msg", "无效订单");
            return "f:/store/jsp/msg.jsp";
        }

        try {
            orderService.addOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("order", order);
        cart.removeSelectedFromCart();

        return "f:/store/jsp/order_info.jsp";
    }

    /**
     * 通过uid获取订单列表
     *
     * @param request
     * @param response
     * @return 请求转发路径
     * @throws ServletException
     * @throws IOException
     */
    public String getOrdersByUid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 获取用户uid
         * 2. 获取当前页面
         * 3. 固定pageSIze
         * 4. 调用service查找对应uid的订单，返回PageBean
         * 5. 将list保存到request域中
         * 6. 请求转发到order_list.jsp中
         */
        String uid = ((User) request.getSession().getAttribute("user")).getUid();
        int currentPage = Integer.parseInt(request.getParameter("currentPage"));
        final int pageSize = 3;
        PageBean<Order> pageBean = null;
        try {
            pageBean = orderService.getOrdersByUid(uid, currentPage, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("pb", pageBean);
        return "f:/store/jsp/order_list.jsp";
    }

    /**
     * 通过oid查找订单
     *
     * @param request
     * @param response
     * @return 请求转发路径
     * @throws ServletException
     * @throws IOException
     */
    public String getOrderByOid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 获取oid
         * 2. 调用service完成查询操作，返回Order
         * 3. 将Order放入request域中，请求转发到order_info.jsp
         */
        String oid = request.getParameter("oid");
        Order order = null;
        try {
            order = orderService.getOrderByOid(oid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("order", order);
        return "f:/store/jsp/order_info.jsp";
    }


    /**
     * 付款
     *
     * @param request
     * @param response
     * @return 请求转发路径
     * @throws ServletException
     * @throws IOException
     */
    public String pay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接受参数
        String address = request.getParameter("address");
        String name = request.getParameter("name");
        String telephone = request.getParameter("telephone");
        String oid = request.getParameter("oid");


        //通过id获取order
        OrderService s = (OrderService) BeanFactory.getBean("OrderService");
        Order order = null;
        try {
            order = s.getOrderByOid(oid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        order.setAddress(address);
        order.setName(name);
        order.setTelephone(telephone);

        //更新order
        try {
            orderService.updateOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // 组织发送支付公司需要哪些数据
        String pd_FrpId = request.getParameter("pd_FrpId");
        String p0_Cmd = "Buy";
        String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
        String p2_Order = oid;
        String p3_Amt = "0.01";
        String p4_Cur = "CNY";
        String p5_Pid = "";
        String p6_Pcat = "";
        String p7_Pdesc = "";
        // 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
        // 第三方支付可以访问网址
        String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("responseURL");
        String p9_SAF = "";
        String pa_MP = "";
        String pr_NeedResponse = "1";
        // 加密hmac 需要密钥
        String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
        String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
                p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
                pd_FrpId, pr_NeedResponse, keyValue);


        //发送给第三方
        StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
        sb.append("p0_Cmd=").append(p0_Cmd).append("&");
        sb.append("p1_MerId=").append(p1_MerId).append("&");
        sb.append("p2_Order=").append(p2_Order).append("&");
        sb.append("p3_Amt=").append(p3_Amt).append("&");
        sb.append("p4_Cur=").append(p4_Cur).append("&");
        sb.append("p5_Pid=").append(p5_Pid).append("&");
        sb.append("p6_Pcat=").append(p6_Pcat).append("&");
        sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
        sb.append("p8_Url=").append(p8_Url).append("&");
        sb.append("p9_SAF=").append(p9_SAF).append("&");
        sb.append("pa_MP=").append(pa_MP).append("&");
        sb.append("pd_FrpId=").append(pd_FrpId).append("&");
        sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
        sb.append("hmac=").append(hmac);

        response.sendRedirect(sb.toString());

        return null;
    }

    /**
     * 回调函数
     *
     * @param request
     * @param response
     * @return 请求转发路径
     * @throws ServletException
     * @throws IOException
     */
    public String callback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String p1_MerId = request.getParameter("p1_MerId");
        String r0_Cmd = request.getParameter("r0_Cmd");
        String r1_Code = request.getParameter("r1_Code");
        String r2_TrxId = request.getParameter("r2_TrxId");
        String r3_Amt = request.getParameter("r3_Amt");
        String r4_Cur = request.getParameter("r4_Cur");
        String r5_Pid = request.getParameter("r5_Pid");
        String r6_Order = request.getParameter("r6_Order");
        String r7_Uid = request.getParameter("r7_Uid");
        String r8_MP = request.getParameter("r8_MP");
        String r9_BType = request.getParameter("r9_BType");
        String rb_BankId = request.getParameter("rb_BankId");
        String ro_BankOrderId = request.getParameter("ro_BankOrderId");
        String rp_PayDate = request.getParameter("rp_PayDate");
        String rq_CardNo = request.getParameter("rq_CardNo");
        String ru_Trxtime = request.getParameter("ru_Trxtime");
        // 身份校验 --- 判断是不是支付公司通知你
        String hmac = request.getParameter("hmac");
        String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
                "keyValue");

        // 自己对上面数据进行加密 --- 比较支付公司发过来hamc
        boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
                r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
                r8_MP, r9_BType, keyValue);
        if (isValid) {
            // 响应数据有效
            if (r9_BType.equals("1")) {
                // 浏览器重定向
                System.out.println("111");
                request.setAttribute("msg", "您的订单号为:" + r6_Order + ",金额为:" + r3_Amt + "已经支付成功,等待发货~~");

            } else if (r9_BType.equals("2")) {
                // 服务器点对点 --- 支付公司通知你
                System.out.println("付款成功！222");
                // 修改订单状态 为已付款
                // 回复支付公司
                response.getWriter().print("success");
            }

            //修改订单状态
            Order order = null;
            try {
                order = orderService.getOrderByOid(r6_Order);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //修改状态为已支付
            order.setState(1);

            try {
                orderService.updateOrder(order);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            // 数据无效
            System.out.println("数据被篡改！");
        }


        return "/store/jsp/msg.jsp";
    }


    /**
     * 更新订单状态
     *
     * @param request
     * @param response
     * @return 重定向路径
     * @throws ServletException
     * @throws IOException
     */
    public String updateOrderState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 接受oid、state参数
         * 2. 调用service#updateOrderState方法完成修改操作
         * 3. 重定向到我的订单页面
         */
        String oid = request.getParameter("oid");
        String state = request.getParameter("state");
        try {
            orderService.updateOrderState(oid, state);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "r:/order?method=getOrdersByUid&uid=" +
                ((User) request.getSession().getAttribute("user")).getUid() +
                "&currentPage=1";
    }
}
