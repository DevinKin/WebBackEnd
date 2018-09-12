package cn.devinkin.mymapper;

import cn.devinkin.mypojo.CustomOrders;
import cn.devinkin.mypojo.Orders;
import cn.devinkin.mypojo.QueryVo;
import cn.devinkin.mypojo.User;

import java.util.List;

public interface UserMapper {
    public User findUserById(Integer id);

    public List<User> findUserByUserName(String username);

    public void insertUser(User user);

    public void delUser(Integer id);

    public void updateUser(User user);

    public List<User> findUserByVo(QueryVo queryVo);

    public Integer findUserCount();

    public List<User> findUseByUsernameAndSex(User user);

    public List<User> findUserByIds(QueryVo queryVo);

    public List<CustomOrders> findOrdersAndUser1();

    public List<Orders> findOrdersAndUser2();

    public List<User> findUserAndOrders();
}
