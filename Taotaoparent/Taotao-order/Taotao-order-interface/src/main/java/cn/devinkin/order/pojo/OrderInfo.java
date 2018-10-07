package cn.devinkin.order.pojo;

import cn.devinkin.pojo.TbOrder;
import cn.devinkin.pojo.TbOrderItem;
import cn.devinkin.pojo.TbOrderShipping;

import java.io.Serializable;
import java.util.List;

/**
 * @author devinkin
 * <p>Title: OrderInfo</p>
 * <p>Description: </p>
 * @version 1.0
 * @see
 * @since 11:38 2018/10/6
 */
public class OrderInfo extends TbOrder implements Serializable {
    private List<TbOrderItem> orderItems;
    private TbOrderShipping orderShipping;

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
