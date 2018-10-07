package cn.devinkin.manager.service.impl;

import cn.devinkin.common.pojo.EasyUITreeNode;
import cn.devinkin.mapper.TbItemCatMapper;
import cn.devinkin.pojo.TbItemCat;
import cn.devinkin.pojo.TbItemCatExample;
import cn.devinkin.manager.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author devinkin
 * <p>Title: ItemCatServiceImpl </p>
 * <p>Description: 商品分类管理Service</p>
 * @version 1.0
 * @see ItemCatService
 * @since 8:43 2018/9/20
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public List<EasyUITreeNode> getItemCatList(Long parentId) {
        // 根据父节点的Id查询子节点的列表
        TbItemCatExample example = new TbItemCatExample();
        // 设置查询条件
        TbItemCatExample.Criteria criteria = example.createCriteria();
        // 设置parentId
        criteria.andParentIdEqualTo(parentId);
        // 转换成EasyUITreeNode列表
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        List<EasyUITreeNode> nodeList = new ArrayList<EasyUITreeNode>();
        for (TbItemCat tbItemCat : list) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbItemCat.getId());
            node.setText(tbItemCat.getName());
            // 如果节点下有子节点,"close",如果没有子节点,"open"
            node.setState(tbItemCat.getIsParent()? "closed" : "open");
            // 添加到节点列表
            nodeList.add(node);
        }
        return nodeList;
    }
}
