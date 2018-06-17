package cn.devinkin.order.dao.impl;

import cn.devinkin.category.domain.Category;
import cn.devinkin.order.dao.OrderDao;
import cn.devinkin.order.domain.Order;
import cn.devinkin.order.domain.OrderItem;
import cn.devinkin.product.domain.Product;
import cn.devinkin.user.domain.User;
import cn.devinkin.utils.BeanFactory;
import cn.devinkin.utils.CommonUtils;
import cn.devinkin.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDaoImpl implements OrderDao {
    private QueryRunner qr = new QueryRunner();
    private QueryRunner qrn = new QueryRunner(DataSourceUtils.getDataSource());

    /**
     * 添加一条订单
     *
     * @param order 订单
     * @throws Exception
     */
    @Override
    public void addOrder(Order order) throws Exception {
        String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
        Object[] params = {order.getOid(), order.getOrdertime(), order.getTotal(),
                order.getState(), order.getAddress(), order.getName(),
                order.getTelephone(), order.getUser().getUid()};
        qr.update(DataSourceUtils.getConnection(), sql, params);

    }

    /**
     * 添加一条订单项
     *
     * @param orderItem 订单项
     * @throws Exception
     */
    @Override
    public void addOrderItem(OrderItem orderItem) throws Exception {
        String sql = "insert into orderitem values(?,?,?,?,?)";
        Object[] params = {orderItem.getItemid(), orderItem.getCount(), orderItem.getSubtotal(),
                orderItem.getProduct().getPid(), orderItem.getOrder().getOid()};
        qr.update(DataSourceUtils.getConnection(), sql, params);
    }

    /**
     * 添加多条订单条目
     *
     * @param orderItemList 订单条目列表
     */
    @Override
    public void addOrderItemList(List<OrderItem> orderItemList) throws Exception {
        String sql = "insert into orderitem values(?,?,?,?,?)";
        //批处理添加订单
        Object[][] params = new Object[orderItemList.size()][];
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);
            params[i] = new Object[]{orderItem.getItemid(), orderItem.getCount(), orderItem.getSubtotal(),
                    orderItem.getProduct().getPid(), orderItem.getOrder().getOid()};
        }
        qr.batch(DataSourceUtils.getConnection(), sql, params);
    }

    /**
     * 根据用户id获取订单数目
     *
     * @param uid 用户id
     * @return 订单数目
     * @throws Exception
     */
    @Override
    public int getTotalOrderByUid(String uid) throws Exception {
        String sql = "select count(*) from orders where uid=?";
        return ((Long) qrn.query(sql, new ScalarHandler(), uid)).intValue();
    }

    /**
     * 通过oid获取对应订单
     *
     * @param oid 订单号
     * @return 订单
     * @throws Exception
     */
    @Override
    public Order getOrderByOid(String oid) throws Exception {
        String sql = "select * from orders where oid = ?";
        Order order = qrn.query(sql, new BeanHandler<>(Order.class), oid);
        loadOrderItems(order);
        return order;
    }

    /**
     * 更新订单
     *
     * @param order 订单
     * @throws Exception
     */
    @Override
    public void updateOrder(Order order) throws Exception {
        String sql = "update orders set state=?,address=?,name=?,telephone=? where oid=?";
        Object[] params = {order.getState(), order.getAddress(), order.getName(),
                order.getTelephone(), order.getOid()};
        qrn.update(sql, params);
    }


    /**
     * 按照订单状态查询订单
     *
     * @param state 订单状态
     * @return
     * @throws Exception
     */
    @Override
    public List<Order> findAllByState(String state) throws Exception {
        StringBuilder sb = new StringBuilder("select * from orders where 1=1 ");
        if (state != null && !state.trim().isEmpty()) {
            sb.append("and state = ? ");
            sb.append("order by ordertime desc");
            String sql = sb.toString();
            List<Order> orderList = qrn.query(sql, new BeanListHandler<>(Order.class), state);
            for (Order order : orderList) {
                loadOrderItems(order);
            }
            return orderList;
        }
        sb.append("order by ordertime desc");
        String sql = sb.toString();
        List<Order> orderList = qrn.query(sql, new BeanListHandler<>(Order.class));
        for (Order order : orderList) {
            loadOrderItems(order);
        }
        return orderList;

    }


    /**
     * 更新订单状态
     * @param oid
     * @param state
     * @throws Exception
     */
    @Override
    public void updateOrderState(String oid, String state) throws Exception {
        String sql = "update orders set state=? where oid=?";
        Object[] params = {state, oid};
        qrn.update(sql, params);
    }

    /**
     * 通过uid获取所有订单
     *
     * @param uid 用户id
     * @return 订单列表
     * @throws Exception
     */
    @Override
    public List<Order> getOrdersByUidAndCurrentPage(String uid, int currentPage, int pageSize) throws Exception {

        String sql = "select * from orders where uid = ? limit ?,?";
//        List<Order> orderList = qr.query(DataSourceUtils.getConnection(), sql, new BeanListHandler<>(Order.class), uid);
        Object[] params = {uid, (currentPage - 1) * pageSize, pageSize};
        List<Order> orderList = qrn.query(sql, new BeanListHandler<>(Order.class), params);

        //封装OrderItemList
        for (Order order : orderList) {
            loadOrderItems(order);
        }
        return orderList;
    }

    /**
     * 加载订单条目
     *
     * @param order 订单
     */
    private void loadOrderItems(Order order) {
        String sql = "select * from orderitem i, product p where i.pid = p.pid and i.oid = ?";
        try {
            /**
             * 因为一行结果对应不再是一个javabean，而是一个MapListHandler
             * MapList是多个map，每个map对应一行结果集
             * 我们需要使用一个Map生成两个对象，OrderItem和Product
             */
//            List<Map<String, Object>> mapList = qr.query(DataSourceUtils.getConnection(), sql,
//                    new MapListHandler(), order.getOid());
            List<Map<String, Object>> mapList = qrn.query(sql,
                    new MapListHandler(), order.getOid());
            List<OrderItem> orderItemList = toOrderItemList(mapList);
            order.setOrderItemList(orderItemList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将Map集合转换为订单条目集合
     *
     * @param mapList Map集合
     * @return
     */
    private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList) {
        List<OrderItem> orderItemList = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            OrderItem orderItem = toOrderItem(map);
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }

    /**
     * 将map转换为订单条目
     *
     * @param map 参数map
     * @return
     */
    private OrderItem toOrderItem(Map<String, Object> map) {
        OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
        Product product = CommonUtils.toBean(map, Product.class);
        loadCategory(product);
        orderItem.setProduct(product);
        return orderItem;
    }

    /**
     * 为商品加载分类
     *
     * @param product 商品
     */
    private void loadCategory(Product product) {
        String sql = "select * from product p, category c where p.cid = c.cid and p.pid=?";
        try {
//            Map<String,Object> map = qr.query(DataSourceUtils.getConnection(), sql,
//                    new MapHandler(), product.getPid());
            Map<String, Object> map = qrn.query(sql,
                    new MapHandler(), product.getPid());
            Category category = toCategory(map);
            product.setCategory(category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Category toCategory(Map<String, Object> map) {
        return CommonUtils.toBean(map, Category.class);
    }


}
