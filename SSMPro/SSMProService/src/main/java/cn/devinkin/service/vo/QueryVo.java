package cn.devinkin.service.vo;

import cn.devinkin.pojo.Items;
import lombok.Data;

import java.util.List;

@Data
public class QueryVo {
    // 商品对象
    private Items items;

    // 批量删除使用
    private Integer[] ids;

    // 批量修改使用
    private List<Items> itemsList;

    // 订单对象
    // 用户对象 ...
}
