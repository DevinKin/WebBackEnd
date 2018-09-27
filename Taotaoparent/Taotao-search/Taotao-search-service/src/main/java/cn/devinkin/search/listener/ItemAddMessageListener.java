package cn.devinkin.search.listener;

import cn.devinkin.common.pojo.SearchItem;
import cn.devinkin.search.mapper.SearchItemMapper;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author devinkin
 * <p>Title: ItemAddMessageListener</p>
 * <p>Description: 监听商品添加事件</p>
 * @version 1.0
 * @see
 * @since 20:33 2018/9/26
 */
public class ItemAddMessageListener implements MessageListener {

    @Autowired
    private SearchItemMapper searchItemMapper;

    @Autowired
    private SolrServer solrServer;

    @Override
    public void onMessage(Message message) {
        try {
            // 从消息中取出商品id
            TextMessage textMessage = (TextMessage) message;
            String id = textMessage.getText();
            Long itemId = Long.parseLong(id);
            System.out.println(itemId);
            // 根据商品id查询数据库取商品信息
            // 等待1秒,等待事务提交
            Thread.sleep(1000);
            SearchItem item = searchItemMapper.getItemById(itemId);

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
