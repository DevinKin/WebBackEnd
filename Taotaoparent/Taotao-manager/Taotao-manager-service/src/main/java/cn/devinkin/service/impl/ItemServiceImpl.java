package cn.devinkin.service.impl;

import cn.devinkin.common.pojo.EasyUIDataGridResult;
import cn.devinkin.common.pojo.TaotaoResult;
import cn.devinkin.common.utils.IDUtils;
import cn.devinkin.mapper.TbItemDescMapper;
import cn.devinkin.mapper.TbItemMapper;
import cn.devinkin.pojo.TbItem;
import cn.devinkin.pojo.TbItemDesc;
import cn.devinkin.pojo.TbItemDescExample;
import cn.devinkin.pojo.TbItemExample;
import cn.devinkin.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        TbItemExample.Criteria criteria = example.createCriteria();
        List<Byte> status = new ArrayList<>();
        // 查询状态为1和2的商品,即正常和下架的
        status.add((byte) 1);
        status.add((byte) 2);
        criteria.andStatusIn(status);
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

    @Override
    public TbItemDesc getItemDescById(Long id) {
        TbItemDescExample example = new TbItemDescExample();
        TbItemDescExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(id);
        List<TbItemDesc> list = tbItemDescMapper.selectByExampleWithBLOBs(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void updateItem(TbItem item, TbItemDesc itemDesc) {
        // 更新商品信息
        tbItemMapper.updateByPrimaryKey(item);
        // 更新商品描述信息
        TbItemDescExample example = new TbItemDescExample();
        // 根据id修改商品描述信息
        TbItemDescExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemDesc.getItemId());
        tbItemDescMapper.updateByExampleWithBLOBs(itemDesc, example);
    }

    @Override
    public void deleteItem(Long id) {
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
        tbItem.setStatus((byte) 3);
        tbItemMapper.updateByPrimaryKey(tbItem);
    }

    @Override
    public void instockItem(Long id) {
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
        tbItem.setStatus((byte) 2);
        tbItemMapper.updateByPrimaryKey(tbItem);
    }

    @Override
    public void reshelfItem(Long id) {
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
        tbItem.setStatus((byte) 1);
        tbItemMapper.updateByPrimaryKey(tbItem);
    }

}
