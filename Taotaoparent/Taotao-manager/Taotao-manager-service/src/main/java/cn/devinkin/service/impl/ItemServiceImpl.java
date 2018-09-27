package cn.devinkin.service.impl;

import cn.devinkin.common.json.JsonUtils;
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
import cn.devinkin.service.jedis.JedisClient;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
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

    @Autowired
    private JmsTemplate jmsTemplate;

    @Resource(name = "addItemtopicDestination")
    private Destination addItemDestination;

    @Resource(name = "editItemtopicDestination")
    private Destination editItemDestination;

    @Resource(name = "deleteItemtopicDestination")
    private Destination deleteItemDestination;

    @Autowired
    private JedisClient jedisClient;

    @Value("${ITEM_INFO}")
    private String ITEM_INFO;

    @Value("${ITEM_EXPIRE}")
    private Integer ITEM_EXPIRE;

    @Override
    public TbItem getItemById(Long itemId) {
        // 查询数据库之前查询缓存
        try {
            String json = jedisClient.get(ITEM_INFO + ":" + itemId + ":BASE");
            if (StringUtils.isNotBlank(json)) {
                // 把json数据转换成pojo
                TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
                return tbItem;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 缓存中没有查询数据库
        TbItem item = tbItemMapper.selectByPrimaryKey(itemId);
        // 把查询结果添加到缓存
        try {
            // 把商品数据添加到缓存
            jedisClient.set(ITEM_INFO + ":" + itemId + ":BASE", JsonUtils.objectToJson(item));
            // 设置过期时间,提高缓存的利用率
            jedisClient.expire(ITEM_INFO + ":" + itemId + ":BASE", ITEM_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        final Long id = IDUtils.genItemId();
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
        // 向ActiveMQ发送商品添加消息
        jmsTemplate.send(addItemDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                // 发送商品id
                TextMessage textMessage = session.createTextMessage(id + "");
                return textMessage;
            }
        });

        // 返回结果
        return TaotaoResult.ok();
    }

    @Override
    public TbItemDesc getItemDescById(Long itemId) {
        // 查询数据库之前查询缓存
        try {
            String json = jedisClient.get(ITEM_INFO + ":" + itemId + ":DESC");
            if (StringUtils.isNotBlank(json)) {
                // 把json数据转换成pojo
                TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return itemDesc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 缓存中没有查询数据库
        TbItemDescExample example = new TbItemDescExample();
        TbItemDescExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemDesc> list = tbItemDescMapper.selectByExampleWithBLOBs(example);
        TbItemDesc itemDesc = null;
        if (list != null && list.size() > 0) {
            itemDesc = list.get(0);
        }

//        TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);

        // 把查询结果添加到缓存
        try {
            // 把商品数据添加到缓存
            jedisClient.set(ITEM_INFO + ":" + itemId + ":DESC", JsonUtils.objectToJson(itemDesc));
            // 设置过期时间,提高缓存的利用率
            jedisClient.expire(ITEM_INFO + ":" + itemId + ":DESC", ITEM_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemDesc;
    }

    @Override
    public void updateItem(final TbItem item, TbItemDesc itemDesc) {
        // 更新商品信息
        tbItemMapper.updateByPrimaryKey(item);
        // 更新商品描述信息
        TbItemDescExample example = new TbItemDescExample();
        // 根据id修改商品描述信息
        TbItemDescExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemDesc.getItemId());
        tbItemDescMapper.updateByExampleWithBLOBs(itemDesc, example);
        // 同步solr索引库
        jmsTemplate.send(editItemDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                // 发送商品id
                TextMessage textMessage = session.createTextMessage(item.getId() + "");
                return textMessage;
            }
        });
    }

    @Override
    public void deleteItem(final Long id) {
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
        // 删除商品,将其状态置为3
        tbItem.setStatus((byte) 3);
        tbItemMapper.updateByPrimaryKey(tbItem);
        // 删除solr索引库中对应的索引
        jmsTemplate.send(deleteItemDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                // 发送商品id
                TextMessage textMessage = session.createTextMessage(id + "");
                return textMessage;
            }
        });
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
