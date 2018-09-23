package cn.devinkin.controller;

import cn.devinkin.common.pojo.EasyUIDataGridResult;
import cn.devinkin.common.pojo.TaotaoResult;
import cn.devinkin.pojo.TbItem;
import cn.devinkin.pojo.TbItemDesc;
import cn.devinkin.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Date;

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

    @RequestMapping("/query/desc/{id}")
    @ResponseBody
    public TaotaoResult getItemDescById(@PathVariable Long id) {
        TbItemDesc result = itemService.getItemDescById(id);
        return TaotaoResult.ok(result);
    }

    @RequestMapping("/update")
    @ResponseBody
    public TaotaoResult updateItem(TbItem item, String desc) {
        // 从数据库中取出原商品
        TbItem tbItem = itemService.getItemById(item.getId());
        // 修改商品的属性
        tbItem.setTitle(item.getTitle());
        tbItem.setSellPoint(item.getSellPoint());
        tbItem.setPrice(item.getPrice());
        tbItem.setNum(item.getNum());
        tbItem.setBarcode(item.getBarcode());
        tbItem.setImage(item.getImage());
        tbItem.setCid(item.getCid());
        tbItem.setUpdated(new Date());
        // 修改商品描述信息
        TbItemDesc tbItemDesc = itemService.getItemDescById(item.getId());
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setUpdated(new Date());

        // 将修后的数据保存到数据库中
        itemService.updateItem(tbItem, tbItemDesc);

        return TaotaoResult.ok();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteItem(Long[] ids) {
        for (Long id : ids) {
            itemService.deleteItem(id);
        }
        return TaotaoResult.ok();
    }

    @RequestMapping("/instock")
    @ResponseBody
    public TaotaoResult instockItem(Long[] ids) {
        for (Long id : ids) {
            itemService.instockItem(id);
        }
        return TaotaoResult.ok();
    }

    @RequestMapping("/reshelf")
    @ResponseBody
    public TaotaoResult reshelfItem(Long[] ids) {
        for (Long id : ids) {
            itemService.reshelfItem(id);
        }
        return TaotaoResult.ok();
    }
}
