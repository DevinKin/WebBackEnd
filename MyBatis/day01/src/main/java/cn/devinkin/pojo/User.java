package cn.devinkin.pojo;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class User {
    private int id;
    // 用户名
    private String username;
    // 性别
    private String sex;
    // 生日
    private Date birthday;
    // 地址
    private String address;

    // 用户与订单,一对多
    private List<Orders> ordersList;

}
