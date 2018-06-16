package cn.devinkin.order.service;

import cn.devinkin.order.domain.Order;
import cn.devinkin.order.domain.OrderItem;
import cn.devinkin.product.domain.PageBean;

import java.util.List;

public interface OrderService {
    void addOrder(Order order) throws Exception;

    PageBean<Order> getOrdersByUid(String uid, int currentPage,int pageSize) throws Exception;

    Order getOrderByOid(String oid) throws Exception;

    void updateOrder(Order order) throws Exception;

    List<Order> findAllByState(String state) throws Exception;
}
