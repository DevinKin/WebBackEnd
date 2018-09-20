package cn.devinkin.service;

import cn.devinkin.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * @author devinkin
 * <p>Title: ItemCatService</p>
 * <p>Description: </p>
 * @version 1.0
 * @see
 * @since 8:41 2018/9/20
 */
public interface ItemCatService {
    /**
     * 根据父节点id查询商品分类列表
     * @param parentId 父节点id
     * @return
     */
    List<EasyUITreeNode> getItemCatList(Long parentId);
}
