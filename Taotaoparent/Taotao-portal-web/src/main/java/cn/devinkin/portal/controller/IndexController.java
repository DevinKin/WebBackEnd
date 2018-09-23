package cn.devinkin.portal.controller;

import cn.devinkin.common.json.JsonUtils;
import cn.devinkin.content.service.ContentService;
import cn.devinkin.pojo.TbContent;
import cn.devinkin.portal.pojo.AD1Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author devinkin
 * <p>Title: IndexController</p>
 * <p>Description: 首页展示Controller</p>
 * @version 1.0
 * @see
 * @since 12:26 2018/9/21
 */
@Controller
public class IndexController {

    @Value("${AD1_CATEGORY_ID}")
    private Long AD1_CATEGORY_ID;

    @Value("${AD1_WIDTH}")
    private Integer AD1_WIDTH;

    @Value("${AD1_WIDTH_B}")
    private Integer AD1_WIDTH_B;

    @Value("${AD1_HEIGHT}")
    private Integer AD1_HEIGHT;

    @Value("${AD1_HEIGHT_B}")
    private Integer AD1_HEIGHT_B;

    @Autowired
    private ContentService contentService;

    @RequestMapping("/index")
    public String showIndex(Model model) {
        // 根据cid查询轮播图内容列表
        List<TbContent> contentList = contentService.getContentListByCid(AD1_CATEGORY_ID);
        // 把列表转换为AD1Node列表
        List<AD1Node> ad1Nodes = new ArrayList<>();
        for (TbContent tbContent : contentList) {
            AD1Node ad1Node = new AD1Node();
            ad1Node.setAlt(tbContent.getTitle());
            ad1Node.setHeight(AD1_HEIGHT);
            ad1Node.setWidth(AD1_WIDTH);
            ad1Node.setHeightB(AD1_HEIGHT_B);
            ad1Node.setWidthB(AD1_WIDTH_B);
            ad1Node.setSrc(tbContent.getPic());
            ad1Node.setSrcB(tbContent.getPic2());
            ad1Node.setHref(tbContent.getUrl());
            // 添加到节点列表
            ad1Nodes.add(ad1Node);
        }
        // 把列表转换为json数据
        String ad1Json = JsonUtils.objectToJson(ad1Nodes);
        // 把json数据传递给页面
        model.addAttribute("ad1", ad1Json);
        return "index";
    }


}
