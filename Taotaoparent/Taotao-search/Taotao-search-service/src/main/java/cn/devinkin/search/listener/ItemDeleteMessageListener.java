package cn.devinkin.search.listener;

import cn.devinkin.search.mapper.SearchItemMapper;
import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author devinkin
 * <p>Title: ItemDeleteMessageListener</p>
 * <p>Description: </p>
 * @version 1.0
 * @see
 * @since 12:20 2018/9/27
 */
public class ItemDeleteMessageListener implements MessageListener {
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
            // 根据商品id删除原有的索引
            solrServer.deleteById(id);
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
