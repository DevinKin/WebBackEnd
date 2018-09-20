package cn.devinkin.service.impl;

import cn.devinkin.common.pojo.EasyUIDataGridResult;
import cn.devinkin.common.pojo.TaotaoResult;
import cn.devinkin.common.utils.IDUtils;
import cn.devinkin.mapper.TbItemDescMapper;
import cn.devinkin.mapper.TbItemMapper;
import cn.devinkin.pojo.TbItem;
import cn.devinkin.pojo.TbItemDesc;
import cn.devinkin.pojo.TbItemExample;
import cn.devinkin.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author devinkin
 * <p>Title: ItemServiceImpl </p>
 * <p>Description: </p>
 * @version 1.0
 * @see cn.devinkin.service.ItemService
 * @since 13:17 2018/9/19
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Override
    public TbItem getItemById(Long itemId) {
        TbItem item = tbItemMapper.selectByPrimaryKey(itemId);
        return item;
    }

    @Override
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        // 设置分页信息
        PageHelper.startPage(page, rows);
        // 执行查询
        TbItemExample example = new TbItemExample();
        List<TbItem> list = tbItemMapper.selectByExample(example);
        //取查询结果
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);
        result.setTotal(pageInfo.getTotal());
        // 返回结果
        return result;
    }

    @Override
    public TaotaoResult addItem(TbItem item, String desc) {
        // 生成商品id
        Long id = IDUtils.genItemId();
        // 补全Item的属性
        item.setId(id);
        // 商品状态,1-正常,2-下架,3-删除
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        // 向商品表插入数据
        tbItemMapper.insert(item);
        // 创建一个商品描述表对应的pojo
        TbItemDesc itemDesc = new TbItemDesc();
        // 补全商品描述pojo属性
        itemDesc.setItemId(id);
        itemDesc.setItemDesc(desc);
        itemDesc.setUpdated(new Date());
        itemDesc.setCreated(new Date());
        // 向商品描述插入数据
        tbItemDescMapper.insert(itemDesc);
        // 返回结果
        return TaotaoResult.ok();
    }

}
