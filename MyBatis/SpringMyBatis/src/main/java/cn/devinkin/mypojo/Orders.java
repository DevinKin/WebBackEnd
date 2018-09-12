package cn.devinkin.mypojo;

import lombok.Data;

import java.util.Date;

@Data
public class Orders {
    private Integer id;
    private Integer userId;
    private String number;
    private Date createtime;
    private String note;

    private User user;
}
