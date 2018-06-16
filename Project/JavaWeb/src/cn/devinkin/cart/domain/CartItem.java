package cn.devinkin.cart.domain;

import cn.devinkin.product.domain.Product;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.Serializable;

/**
 * 购物车项
 * @author king
 */
public class CartItem implements Serializable{
    private Product product;    //商品对象
    private Integer count;      //购买对象
    private Double subtotal=0.0;    //小计
    private Boolean selected = false;   //选中状态

    public CartItem() {
    }

    public CartItem(Product product, Integer count) {

        this.product = product;
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getSubtotal() {
        return product.getShop_price()*count;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
