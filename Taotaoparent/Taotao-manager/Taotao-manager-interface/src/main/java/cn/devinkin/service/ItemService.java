package cn.devinkin.service;

import cn.devinkin.common.pojo.EasyUIDataGridResult;
import cn.devinkin.common.pojo.TaotaoResult;
import cn.devinkin.pojo.TbItem;
import cn.devinkin.pojo.TbItemDesc;

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

    /**
     * 通过商品id查询对应的商品描述
     * @param id 商品id
     * @return
     */
    TbItemDesc getItemDescById(Long itemId);

    /**
     * 更新商品信息
     * @param item 商品信息
     * @param itemDesc 商品描述信息
     */
    void updateItem(TbItem item, TbItemDesc itemDesc);

    /**
     * 删除商品,将状态置为3
     * @param id 商品id
     */
    void deleteItem(Long id);

    /**
     * 下架商品,将状态置为2
     * @param id
     */
    void instockItem(Long id);

    /**
     * 上架商品,将状态置为2
     * @param id
     */
    void reshelfItem(Long id);
}
