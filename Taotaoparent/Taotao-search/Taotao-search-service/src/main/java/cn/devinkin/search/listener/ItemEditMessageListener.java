package cn.devinkin.search.listener;

import cn.devinkin.common.pojo.SearchItem;
import cn.devinkin.search.mapper.SearchItemMapper;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author devinkin
 * <p>Title: ItemEditMessageListener</p>
 * <p>Description: </p>
 * @version 1.0
 * @see
 * @since 12:20 2018/9/27
 */
public class ItemEditMessageListener implements MessageListener {

    @Autowired
    private SearchItemMapper searchItemMapper;

    @Autowired
    private SolrServer solrServer;

    @Override
    public void onMessage(Message message) {
        try {
            // 获取商品id
            TextMessage textMessage = (TextMessage) message;
            String id = textMessage.getText();
            Long itemId = Long.parseLong(id);
            Thread.sleep(10000);
            // 获得对应的商品信息
            SearchItem item = searchItemMapper.getItemById(itemId);
            // 更改索引库中的内容,由于solr没有直接修改的方法,所以先删除,后添加
            // 根据商品id删除原有的索引
            solrServer.deleteById(id);
            // 根据商品信息添加新的索引

            // 创建文档对象
            SolrInputDocument document = new SolrInputDocument();
            // 向文档对象中添加域
            document.addField("id", item.getId());
            document.addField("item_title", item.getTitle());
            document.addField("item_sell_point", item.getSell_point());
            document.addField("item_price",item.getPrice());
            document.addField("item_image",item.getImage());
            document.addField("item_category_name",item.getCategory_name());
            document.addField("item_desc",item.getItem_desc());
            // 把文档对象写入索引库
            solrServer.add(document);
            // 提交
            solrServer.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
