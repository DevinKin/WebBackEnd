package cn.devinkin.pojo;


import lombok.Data;

import java.util.Date;

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

}
