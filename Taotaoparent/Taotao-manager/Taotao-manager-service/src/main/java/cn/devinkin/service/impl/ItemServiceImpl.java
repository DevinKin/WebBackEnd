package cn.devinkin.service.impl;

import cn.devinkin.common.pojo.EasyUIDataGridResult;
import cn.devinkin.mapper.TbItemMapper;
import cn.devinkin.pojo.TbItem;
import cn.devinkin.pojo.TbItemExample;
import cn.devinkin.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
