package cn.devinkin.order.domain;

import cn.devinkin.user.domain.User;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Order implements Serializable{
    /**
     *
     `oid` varchar(32) NOT NULL,
     `ordertime` datetime DEFAULT NULL,
     `total` double DEFAULT NULL,
     `state` int(11) DEFAULT NULL,
     `address` varchar(30) DEFAULT NULL,
     `name` varchar(20) DEFAULT NULL,
     `telephone` varchar(20) DEFAULT NULL,
     `uid` varchar(32) DEFAULT NULL,
     */
    private String oid;
    private Date ordertime;
    private Double total;
    private Integer state = 0;      //订单状态，0：未支付，1：已支付，2：已发货，3：已收货，4：订单完成
    private String address;
    private String name;
    private String telephone;
    private User user;
    private List<OrderItem> orderItemList = new LinkedList<>();

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Date getOrdertime() {
        //java.util.Date-->Mysql datetime格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = sdf.format(ordertime);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
}
