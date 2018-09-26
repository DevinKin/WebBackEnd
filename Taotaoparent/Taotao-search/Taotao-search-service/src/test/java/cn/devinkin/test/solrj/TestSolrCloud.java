package cn.devinkin.test.solrj;

import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * @author devinkin
 * <p>Title: TestSolrCloud</p>
 * <p>Description: </p>
 * @version 1.0
 * @see
 * @since 23:02 2018/9/25
 */
public class TestSolrCloud {
    @Test
    public void testSolrCloudAdDocument() throws Exception{
        // 创建一个CloudSolrServer对象,构造方法中需要指定参数,zookeeper的地址列表
        CloudSolrServer cloudSolrServer = new CloudSolrServer("192.168.85.137:2181,192.168.85.137:2182,192.168.85.137:2183");
        // 需要设置一个默认Collection
        cloudSolrServer.setDefaultCollection("collection2");
        // 创建一个文档对象
        SolrInputDocument document = new SolrInputDocument();
        // 向文档中添加域
        document.addField("id", "test001");
        document.addField("item_title", "测试商品名称");
        document.addField("item_price", "1200");
        // 把文档写入索引库
        cloudSolrServer.add(document);
        // 提交
        cloudSolrServer.commit();
    }
}
