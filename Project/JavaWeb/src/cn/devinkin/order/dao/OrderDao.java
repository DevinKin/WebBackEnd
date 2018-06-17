package cn.devinkin.order.dao;

import cn.devinkin.order.domain.Order;
import cn.devinkin.order.domain.OrderItem;
import cn.devinkin.user.domain.User;

import java.util.List;

public interface OrderDao {
    void addOrder(Order order) throws Exception;

    void addOrderItem(OrderItem orderItem) throws Exception;

    List<Order> getOrdersByUidAndCurrentPage(String uid,int currentPage, int pageSize) throws Exception;

    void addOrderItemList(List<OrderItem> orderItemList) throws Exception;

    int getTotalOrderByUid(String uid) throws Exception;

    Order getOrderByOid(String oid) throws Exception;

    void updateOrder(Order order) throws Exception;

    List<Order> findAllByState(String state) throws Exception;

    void updateOrderState(String oid, String state) throws Exception;
}
