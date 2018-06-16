package cn.devinkin.cart.web.servlet;

import cn.devinkin.cart.domain.Cart;
import cn.devinkin.cart.domain.CartItem;
import cn.devinkin.product.domain.Product;
import cn.devinkin.product.service.ProductService;
import cn.devinkin.utils.BaseServlet;
import cn.devinkin.utils.BeanFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CartServlet",urlPatterns = "/cart")
public class CartServlet extends BaseServlet {
    private ProductService productService = (ProductService) BeanFactory.getBean("ProductService");
    public Cart getCart(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        /**
         * 1. 判断cart是否为空
         *  1. 为空，创建一个cart，添加到session中
         *  2. 不为空，直接返回cart
         */
        if (cart == null) {
            cart = new Cart();
            request.getSession().setAttribute("cart", cart);
        }
        return cart;
    }

    /**
     * 购物车条目添加到购物车
     * @param request
     * @param response
     * @return 请求路径
     * @throws ServletException
     * @throws IOException
     */
    public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 获取pid和count
         * 2. 通过pid调用ProductService查找对应的商品
         * 3. 组装成CartItem
         * 4. 添加到购物车
         * 5. 重定向
         */
        String pid = request.getParameter("pid");
        int count = Integer.parseInt(request.getParameter("count"));

        Product product = null;
        try {
            product = productService.getProductByPid(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        CartItem cartItem = new CartItem(product,count);

        Cart cart = getCart(request);
        cart.add2Cart(cartItem);
        return "r:/store/jsp/cart.jsp";
    }

    /**
     * 删除商品条目
     * @param request
     * @param response
     * @return 请求转发路径
     * @throws ServletException
     * @throws IOException
     */
    public String remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 获取商品的pid
         * 2. 调用购物车的remove方法
         * 3. 重定向
         */
        String pid = request.getParameter("pid");
        getCart(request).removeFromCart(pid);

        return "r:/store/jsp/cart.jsp";
    }


    /**
     * 清空购物车
     * @param request
     * @param response
     * @return 请求转发路径
     * @throws ServletException
     * @throws IOException
     */
    public String clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 获取购物车，清空
         */
        getCart(request).clearCart();
        return "r:/store/jsp/cart.jsp";
    }

    /**
     * 修改购物车条目选中状态
     * @param request
     * @param response
     * @return 请求转发路径
     * @throws ServletException
     * @throws IOException
     */
    public String modifyState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 获取购物车
         * 2. 获取请求参数pid
         * 3. 遍历购物车条目，查看是否存在商品id为pid的商品
         * 4. 若有，将状态翻转
         */
        Cart cart = getCart(request);
        String pid = request.getParameter("pid");
        for (CartItem cartItem : cart.getCartItems()) {
            if (pid.equals(cartItem.getProduct().getPid())) {
                cartItem.setSelected(!cartItem.getSelected());
            }
        }
        return null;
    }
}
