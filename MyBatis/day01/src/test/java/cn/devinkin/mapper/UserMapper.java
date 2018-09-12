package cn.devinkin.mapper;

import cn.devinkin.pojo.CustomOrders;
import cn.devinkin.pojo.Orders;
import cn.devinkin.pojo.QueryVo;
import cn.devinkin.pojo.User;

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
