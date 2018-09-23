package cn.devinkin.content.service;

import cn.devinkin.common.pojo.EasyUITreeNode;
import cn.devinkin.common.pojo.TaotaoResult;

import java.util.List;

/**
 * @author devinkin
 * <p>Title: ContentCategoryService</p>
 * <p>Description: </p>
 * @version 1.0
 * @see
 * @since 14:04 2018/9/21
 */
public interface ContentCategoryService {
   /**
    * 返回内容分类列表
    * @param parentId 分类父id
    * @return
    */
   List<EasyUITreeNode> getContentCategoryList(Long parentId);

   /**
    * 添加内容分类
    * @param parentId 分类父id
    * @param name 分类名称
    * @return
    */
   TaotaoResult addContentCategory(Long parentId, String name);

   /**
    * 更新内容分类
    * @param id 内容分类id
    * @param name 内容分类名称
    * @return
    */
   TaotaoResult updateContentCategory(Long id, String name);

   /**
    * 根据id删除内容分类
    * @param id 内容分类id
    * @return
    */
   TaotaoResult deleteContentCategory(Long id);
}
