package cn.devinkin.controller;

import cn.devinkin.common.pojo.TaotaoResult;
import cn.devinkin.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author devinkin
 * <p>Title: IndexManagerController</p>
 * <p>Description: 索引库维护Controller</p>
 * @version 1.0
 * @see
 * @since 13:00 2018/9/23
 */
@RequestMapping("/index")
@Controller
public class IndexManagerController {

    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping("/import")
    @ResponseBody
    public TaotaoResult importIndex() {
        TaotaoResult result = searchItemService.importItemToIndex();
        return result;
    }
}
