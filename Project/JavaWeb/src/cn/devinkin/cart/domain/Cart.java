package cn.devinkin.cart.domain;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    //存放购物车项目的集合，key为商品id，CartItem为购物车项。使用map集合便于删除单个购物项
    private Map<String, CartItem> cartItemMap = new LinkedHashMap<>();
    //总金额
    private Double total = 0.0;

    public Map<String, CartItem> getCartItemMap() {
        return cartItemMap;
    }

    public void setCartItemMap(Map<String, CartItem> cartItemMap) {
        this.cartItemMap = cartItemMap;
    }


    public Double getTotal() {
        return total;
    }


    /**
     * 获取选中购物车条目的总额
     * @return 购物车条目的总额
     */
    public Double getSelectedTotal() {
        Double selectedTotal = 0.0;
        for (CartItem cartItem : getCartItems()) {
            if (cartItem.getSelected())
                selectedTotal += cartItem.getSubtotal();
        }
        return selectedTotal;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * 添加到购物车
     * @param cartItem 购物车项
     */
    public void add2Cart(CartItem cartItem) {
        /**
         * 1. 判断购物车有无该商品
         *  1. 若有
         *      1. 添加到购物车
         *  2. 若没有
         *      1. 该购物项购买数量+1
         *  3. 修改总金额
         */
        String pid = cartItem.getProduct().getPid();
        if (cartItemMap.containsKey(pid)) {
            CartItem cartItem1 = cartItemMap.get(pid);
            cartItem1.setCount(cartItem1.getCount() + 1);
        } else {
            cartItemMap.put(pid, cartItem);
        }
        total += cartItem.getSubtotal();
    }

    /**
     * 移除购物车项
     * @param pid 商品id
     */
    public void removeFromCart(String pid) {
        /**
         * 1. 从集合中删除
         * 2. 修改总金额
         */
        CartItem cartItem = cartItemMap.remove(pid);
        total -= cartItem.getSubtotal();
    }

    public void removeSelectedFromCart() {
        /**
         * 1. 获取被选中的商品pid
         * 2. 通过pid从集合中删除
         * 3. 修改总金额
         */
        for (CartItem cartItem : getSelectedCartItems()) {
            cartItemMap.remove(cartItem.getProduct().getPid());
            total -= cartItem.getSubtotal();
        }
    }


    /**
     * 清空购物车
     */
    public void clearCart() {
        /**
         * 1. 将map集合置空
         * 2. 将总金额设置为0
         */
        cartItemMap.clear();
        total = 0.0;
    }

    /**
     * 获取购物车提哦啊木
     * @return 购物车条目集合
     */
    public Collection<CartItem> getCartItems() {
        return cartItemMap.values();
    }

    public Collection<CartItem> getSelectedCartItems() {
        Map<String, CartItem> subMap = new LinkedHashMap<>();
        for (Map.Entry<String, CartItem> entry : cartItemMap.entrySet()) {
            CartItem cartItem = entry.getValue();
            if (cartItem.getSelected()) {
                subMap.put(entry.getKey(), entry.getValue());
            }
        }
        return subMap.values();
    }
}
