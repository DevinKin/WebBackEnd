package cn.devinkin.item.controller;

import cn.devinkin.item.pojo.Item;
import cn.devinkin.pojo.TbItem;
import cn.devinkin.pojo.TbItemDesc;
import cn.devinkin.manager.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author devinkin
 * <p>Title: ItemController</p>
 * <p>Description: 商品详情页面展示</p>
 * @version 1.0
 * @see
 * @since 18:48 2018/9/27
 */
@RequestMapping("/item/")
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/{itemId}")
    public String showItem(@PathVariable Long itemId, Model model) {
        // 获取商品基本信息
        TbItem tbItem = itemService.getItemById(itemId);
        Item item = new Item(tbItem);
        // 取商品详情
        TbItemDesc tbItemDesc = itemService.getItemDescById(itemId);
        // 把数据传递给页面
        model.addAttribute("item", item);
        model.addAttribute("itemDesc", tbItemDesc);
        // 返回逻辑视图
        return "item";
    }
}
