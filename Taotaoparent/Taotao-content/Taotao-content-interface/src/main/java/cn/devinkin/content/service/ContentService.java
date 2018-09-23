package cn.devinkin.content.service;

import cn.devinkin.common.pojo.EasyUIDataGridResult;
import cn.devinkin.common.pojo.TaotaoResult;
import cn.devinkin.pojo.TbContent;

import java.util.List;

/**
 * @author devinkin
 * <p>Title: ContentService</p>
 * <p>Description: </p>
 * @version 1.0
 * @see
 * @since 20:21 2018/9/21
 */
public interface ContentService {

    /**
     * 添加内容对象
     * @param content 内容
     * @return
     */
    TaotaoResult addContent(TbContent content);

    /**
     * 获取内容列表
     * @param categoryId 内容分类id
     * @param page 当前页
     * @param rows 每页内容列表
     * @return
     */
    EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows);

    /**
     * 通过id查找对应的内容
     * @param id 内容id
     * @return
     */
    TbContent getContentById(Long id);

    /**
     * 更新内容
     * @param tbContent 内容
     */
    void updateContent(TbContent tbContent);

    /**
     * 根据id列表删除内容
     * @param ids
     */
    void deleteContent(Long[] ids);

    /**
     * 通过分类id查询内容列表
     * @param cid
     * @return
     */
    List<TbContent> getContentListByCid(Long cid);
}
