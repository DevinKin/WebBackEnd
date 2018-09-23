package cn.devinkin.controller;

import cn.devinkin.common.pojo.EasyUIDataGridResult;
import cn.devinkin.common.pojo.TaotaoResult;
import cn.devinkin.content.service.ContentService;
import cn.devinkin.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @author devinkin
 * <p>Title: ContentController</p>
 * <p>Description: 内容管理Contrller</p>
 * @version 1.0
 * @see
 * @since 20:26 2018/9/21
 */
@RequestMapping("/content/")
@Controller
public class ContentController {
    @Autowired
    private ContentService contentService;

    @RequestMapping("/list")
    @ResponseBody
    public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows) {
        EasyUIDataGridResult result = contentService.getContentList(categoryId, page,rows);
        return result;
    }

    @RequestMapping("/save")
    @ResponseBody
    public TaotaoResult addContent(TbContent content) {
        // 设置创建时间和修改时间
        TaotaoResult result = contentService.addContent(content);
        return result;
    }


    @RequestMapping("/query/content/{id}")
    @ResponseBody
    public TaotaoResult getContentById(@PathVariable Long id) {
        TbContent result = contentService.getContentById(id);
        return TaotaoResult.ok(result);
    }

    @RequestMapping("/update")
    @ResponseBody
    public TaotaoResult updateContent(TbContent content) {
        // 更新内容
        contentService.updateContent(content);
        // 返回结果
        TaotaoResult result = new TaotaoResult();
        return result;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteContent(Long[] ids) {
        contentService.deleteContent(ids);
        return TaotaoResult.ok();
    }
}
