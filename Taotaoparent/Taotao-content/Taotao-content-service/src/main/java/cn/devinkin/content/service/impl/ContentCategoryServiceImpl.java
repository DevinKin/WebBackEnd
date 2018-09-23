package cn.devinkin.content.service.impl;

import cn.devinkin.common.pojo.EasyUITreeNode;
import cn.devinkin.common.pojo.TaotaoResult;
import cn.devinkin.content.service.ContentCategoryService;
import cn.devinkin.mapper.TbContentCategoryMapper;
import cn.devinkin.pojo.TbContentCategory;
import cn.devinkin.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author devinkin
 * <p>Title: ContentCategoryServiceImpl</p>
 * <p>Description: 内容分类管理服务</p>
 * @version 1.0
 * @see
 * @since 14:06 2018/9/21
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCategoryList(Long parentId) {
        // 根据parentId查询子节点列表
        TbContentCategoryExample example = new TbContentCategoryExample();
        // 设置查询条件
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        // 内容分类的状态必须为1
        criteria.andStatusEqualTo(1);
        // 将结果装载到EasyUITreeNode
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        List<EasyUITreeNode> resultList = new ArrayList<>();
        for (TbContentCategory tbContentCategory : list) {
             EasyUITreeNode node = new EasyUITreeNode();
             node.setId(tbContentCategory.getId());
             node.setText(tbContentCategory.getName());
             node.setState(tbContentCategory.getIsParent() ? "closed" : "open");
             // 添加到结果列表
             resultList.add(node);
        }
        return resultList;
    }

    @Override
    public TaotaoResult addContentCategory(Long parentId, String name) {
        // 创建一个pojo对象
        TbContentCategory tbContentCategory = new TbContentCategory();
        // 补全对象的属性
        tbContentCategory.setParentId(parentId);
        tbContentCategory.setName(name);
        // 状态,可选值:1(正常), 2(删除)
        tbContentCategory.setStatus(1);
        // 排序,默认为1
        tbContentCategory.setSortOrder(1);
        tbContentCategory.setIsParent(false);
        tbContentCategory.setCreated(new Date());
        tbContentCategory.setUpdated(new Date());

        // 插入到数据库
        contentCategoryMapper.insert(tbContentCategory);
        // 判断父节点的状态
        TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(tbContentCategory.getParentId());
        if (!parent.getIsParent()) {
            // 如果父子节点为叶子节点,应该改为父节点
            parent.setIsParent(true);
        }
        contentCategoryMapper.updateByPrimaryKey(parent);
        // 返回结果
        return TaotaoResult.ok(tbContentCategory);
    }

    @Override
    public TaotaoResult updateContentCategory(Long id, String name) {
        // 得到内容分类对象
        TbContentCategory tbContentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        // 设置内容分类名称
        tbContentCategory.setName(name);
        // 设置内容分类更新时间
        tbContentCategory.setUpdated(new Date());
        // 将内容分类更新到数据库中
        contentCategoryMapper.updateByPrimaryKey(tbContentCategory);
        // 返回结果
        return TaotaoResult.ok();
    }


    @Override
    public TaotaoResult deleteContentCategory(Long id) {
        // 删除功能,如果为子节点,直接删除,状态置为2,如果是父节点,递归删除子节点
        // 根据id查找内容分类
        TbContentCategory tbContentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        // 判断是否是父节点
        // 根据id将状态置为2
        TbContentCategoryExample example = new TbContentCategoryExample();
        // 设置更新条件
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        if (tbContentCategory.getIsParent()) {
            // 删除所有子节点
            TbContentCategoryExample example2 = new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria2 = example2.createCriteria();
            criteria2.andParentIdEqualTo(id);
            List<TbContentCategory> list = contentCategoryMapper.selectByExample(example2);
            // 循环遍历子节点
            for (TbContentCategory contentCategory : list) {
                // 如果子节点是父节点,递归调用此方法,如果是子节点,则直接删除
                if (contentCategory.getIsParent()) {
                    deleteContentCategory(contentCategory.getId());
                } else {
                    // 删除子节点
                    contentCategory.setStatus(2);
                    contentCategory.setUpdated(new Date());
                    contentCategoryMapper.updateByPrimaryKey(contentCategory);
                }
            }
        }
        // 删除父节点,将父节点状态置为2
        tbContentCategory.setStatus(2);
        // 设置父节点的更新时间
        tbContentCategory.setUpdated(new Date());
        contentCategoryMapper.updateByPrimaryKey(tbContentCategory);
        return TaotaoResult.ok();
    }


}
