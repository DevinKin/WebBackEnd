package cn.devinkin.order.service.impl;

import cn.devinkin.common.pojo.TaotaoResult;
import cn.devinkin.mapper.TbOrderItemMapper;
import cn.devinkin.mapper.TbOrderMapper;
import cn.devinkin.mapper.TbOrderShippingMapper;
import cn.devinkin.jedis.JedisClient;
import cn.devinkin.order.pojo.OrderInfo;
import cn.devinkin.order.service.OrderService;
import cn.devinkin.pojo.TbOrderItem;
import cn.devinkin.pojo.TbOrderShipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author devinkin
 * <p>Title: OrderServiceImpl</p>
 * <p>Description: 订单处理Service</p>
 * @version 1.0
 * @see OrderService
 * @since 13:03 2018/10/6
 */

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TbOrderMapper orderMapper;

    @Autowired
    private TbOrderItemMapper orderItemMapper;

    @Autowired
    private TbOrderShippingMapper orderShippingMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${ORDER_ID_GEN_KEY}")
    private String ORDER_ID_GEN_KEY;

    @Value("${ORDER_ITEM_ID_GEN_KEY}")
    private String ORDER_ITEM_ID_GEN_KEY;

    @Value("${ORDER_ID_BEGIN_VALUE}")
    private String ORDER_ID_BEGIN_VALUE;

    @Override
    public TaotaoResult createOrder(OrderInfo orderInfo) {
        // 生成订单号,可以使用redis的incr生成
        if (jedisClient.exists(ORDER_ID_GEN_KEY)) {
            // 设置初始值
            jedisClient.set(ORDER_ID_GEN_KEY, ORDER_ID_BEGIN_VALUE);
        }
        String orderId = jedisClient.incr(ORDER_ID_GEN_KEY).toString();
        // 向订单表插入数据,需要补全pojo的属性
        orderInfo.setOrderId(orderId);
        // 免邮费
        orderInfo.setPostFee("0");
        // 1. 未付款 2. 已付款 3. 未发货 4. 已发货 5. 交易成功 6. 交易关闭
        orderInfo.setStatus(1);
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
        // 向订单表插入数据
        orderMapper.insert(orderInfo);
        // 向订单细明表插入数据
        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        for (TbOrderItem orderItem : orderItems) {
            String oid = jedisClient.incr(ORDER_ITEM_ID_GEN_KEY).toString();
            orderItem.setItemId(oid);
            orderItem.setOrderId(orderId);
            // 插入明细数据
            orderItemMapper.insert(orderItem);
        }
        // 向订单物流表插入数据
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());
        orderShippingMapper.insert(orderShipping);
        // 返回订单号
        return TaotaoResult.ok(orderId);
    }
}
