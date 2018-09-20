package cn.devinkin.service;

import cn.devinkin.common.pojo.EasyUIDataGridResult;
import cn.devinkin.common.pojo.TaotaoResult;
import cn.devinkin.pojo.TbItem;

public interface ItemService {
    /**
     * 根据商品Id查找商品
     * @param itemId 商品Id
     * @return 商品
     */
    TbItem getItemById(Long itemId);

    /**
     * 分页查找商品
     * @param page 当前页
     * @param rows 每页商品列表
     * @return
     */
    EasyUIDataGridResult getItemList(Integer page, Integer rows);

    /**
     * 添加商品
     * @param item 商品
     * @param desc 商品描述
     * @return
     */
    TaotaoResult addItem(TbItem item, String desc);
}
