package cn.devinkin.solrj;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class IndexManagerTest {
    @Test
    public void testIndexCreate() throws Exception {
        // 创建和Solr服务端连接
        SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");
        // 创建solr文档对象
        SolrInputDocument doc = new SolrInputDocument();
        // 域要先定义后使用,还要注意要有id主键域
        doc.addField("id", "a001");
        // 会报错,域要先定义后提交
//        doc.addField("", "台灯");
        // solr中没有专用的修改方法,会自动根据id进行查询,如果找到了则删除原来的,加入新值,如果没找到,直接加入新值
        doc.addField("product_name", "台灯111");
        doc.addField("product_price", "12.5");
        // 将文档加入solrServer对象中
        solrServer.add(doc);
        // 提交
        solrServer.commit();
    }

    @Test
    public void testIndexDel() throws Exception {
        // 创建和Solr服务端连接
        SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");

        // 根据主键id进行删除
//        solrServer.deleteById("a001");
        // 根据查询删除,*:*删除所有
        solrServer.deleteByQuery("*:*");
        // 提交
        solrServer.commit();
    }
}
