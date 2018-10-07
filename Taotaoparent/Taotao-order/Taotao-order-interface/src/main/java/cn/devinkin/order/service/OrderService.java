package cn.devinkin.order.service;

import cn.devinkin.common.pojo.TaotaoResult;
import cn.devinkin.order.pojo.OrderInfo;

/**
 * @author devinkin
 * <p>Title: OrderService</p>
 * <p>Description: </p>
 * @version 1.0
 * @see
 * @since 13:01 2018/10/6
 */
public interface OrderService {

    /**
     * 生成订单
     * @param orderInfo 订单pojo类
     * @return
     */
    TaotaoResult createOrder(OrderInfo orderInfo);
}
