package cn.devinkin.controller;

import cn.devinkin.common.pojo.EasyUIDataGridResult;
import cn.devinkin.common.pojo.TaotaoResult;
import cn.devinkin.pojo.TbItem;
import cn.devinkin.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author devinkin
 * <p>Title: ItemController</p>
 * <p>Description: 商品管理Controller</p>
 * @version 1.0
 * @see ItemController
 * @since 13:54 2018/9/19
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId) {
        TbItem tbItem = itemService.getItemById(itemId);
        return tbItem;
    }


    @RequestMapping("/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        EasyUIDataGridResult result = itemService.getItemList(page, rows);
        return result;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult addItem(TbItem item, String desc) {
        TaotaoResult result = itemService.addItem(item, desc);
        return result;
    }
}
