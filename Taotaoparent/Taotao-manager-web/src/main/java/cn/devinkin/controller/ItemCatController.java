package cn.devinkin.controller;

import cn.devinkin.common.pojo.EasyUITreeNode;
import cn.devinkin.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.RequestWrapper;
import java.util.List;

/**
 * @author devinkin
 * <p>Title: ItemCatController</p>
 * <p>Description: </p>
 * @version 1.0
 * @see
 * @since 8:58 2018/9/20
 */
@Controller
@RequestMapping("/item")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> getItemCatList(@RequestParam(name ="id", defaultValue = "0") Long parentId) {
        List<EasyUITreeNode> list = itemCatService.getItemCatList(parentId);
        return list;
    }
}
