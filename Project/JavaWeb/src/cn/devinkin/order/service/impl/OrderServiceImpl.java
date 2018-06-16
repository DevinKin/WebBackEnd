package cn.devinkin.order.service.impl;

import cn.devinkin.order.dao.OrderDao;
import cn.devinkin.order.domain.Order;
import cn.devinkin.order.domain.OrderItem;
import cn.devinkin.order.service.OrderService;
import cn.devinkin.product.domain.PageBean;
import cn.devinkin.user.domain.User;
import cn.devinkin.utils.BeanFactory;
import cn.devinkin.utils.DataSourceUtils;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = (OrderDao) BeanFactory.getBean("OrderDao");

    @Override
    public void addOrder(Order order) throws Exception {
        /**
         * 1. 开启事务
         * 2. 向orders表中添加一个数据
         * 3. 向orderItems中添加n条数据
         * 4. 事务处理
         */
        try {

            DataSourceUtils.beginTransaction();

            orderDao.addOrder(order);

            orderDao.addOrderItemList(order.getOrderItemList());

            DataSourceUtils.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            DataSourceUtils.rollbackTransaction();
            throw e;
        }
    }

    /**
     * 通过uid获取所有订单
     * @param uid 用户id
     * @return 订单列表
     * @throws Exception
     */
    @Override
    public PageBean<Order> getOrdersByUid(String uid, int currentPage, int pageSize) throws Exception {
        List<Order> orderList = null;
        try {
//            DataSourceUtils.beginTransaction();

            int totalRecord = orderDao.getTotalOrderByUid(uid);
            orderList = orderDao.getOrdersByUidAndCurrentPage(uid, currentPage, pageSize);

            return new PageBean<Order>(orderList, currentPage, pageSize, totalRecord);
        } catch (Exception e) {
            e.printStackTrace();
//            DataSourceUtils.rollbackTransaction();
            throw e;
        }
    }

    /**
     * 通过oid查找订单
     * @param oid 订单号
     * @return 订单
     * @throws Exception
     */
    @Override
    public Order getOrderByOid(String oid) throws Exception {
        return orderDao.getOrderByOid(oid);
    }


    /**
     * 更新订单状态到1，已付款状态
     * @param order 订单
     * @throws Exception
     */
    @Override
    public void updateOrder(Order order) throws Exception {
        orderDao.updateOrder(order);
    }

    @Override
    public List<Order> findAllByState(String state) throws Exception {
        return orderDao.findAllByState(state);
    }
}
