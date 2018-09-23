package cn.devinkin.test.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author devinkin
 * <p>Title: TestSolrj</p>
 * <p>Description: Solrj测试类</p>
 * @version 1.0
 * @see
 * @since 10:59 2018/9/23
 */
public class TestSolrj {
    @Test
    public void testAddDocument() throws Exception {
        // 创建一个SolrServer对象,创建一个HttpSolrServer对象
        // 需要指定solr服务的url
        SolrServer solrServer = new HttpSolrServer("http://192.168.85.137:8080/solr/collection1");
        // 创建一个文档对象SolrInputDocument
        SolrInputDocument document = new SolrInputDocument();
        // 向文档中添加域,必须有id域,域的名称必须在schema.xml定义
        document.addField("id", "test001");
        document.addField("item_title", "测试商品1");
        document.addField("item_price", 1000);
        // 把文档对象写入索引库
        solrServer.add(document);
        // 提交
        solrServer.commit();
    }

    @Test
    public void testDeleteDocumentById() throws Exception {
        SolrServer solrServer = new HttpSolrServer("http://192.168.85.137:8080/solr/collection1");
        solrServer.deleteById("test001");
        // 根据id删除
        solrServer.commit();
    }

    @Test
    public void testDeleteDocumentByQuery() throws Exception {
        SolrServer solrServer = new HttpSolrServer("http://192.168.85.137:8080/solr/collection1");
        solrServer.deleteByQuery("*:*");
        solrServer.commit();
    }

    @Test
    public void searchDocument() throws Exception {
        // 创建一个SolrServer对象
        SolrServer solrServer = new HttpSolrServer("http://192.168.85.137:8080/solr/collection1");
        // 创建一个SolrQuery对象
        SolrQuery query = new SolrQuery();
        // 设置查询条件
        // 设置过滤条件
//        query.set("q", "*:*");
        query.setQuery("手机");
        // 设置分页条件
        query.setStart(30);
        query.setRows(20);
        // 设置默认搜索域
        query.set("df", "item_keywords");
        // 设置排序条件
        query.setSort("item_price", SolrQuery.ORDER.desc);
        // 设置高亮
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<span style=\"color: red\">");
        query.setHighlightSimplePost("</span>");
        // 执行查询
        QueryResponse response = solrServer.query(query);
        // 取查询结果
        SolrDocumentList solrDocumentList = response.getResults();
        // 取查询结果总记录数
        System.out.println("查询结果总记录数: " + solrDocumentList.getNumFound());
        System.out.println("====================================");
        for (SolrDocument solrDocument : solrDocumentList) {
            System.out.println(solrDocument.get("id"));
            // 取高亮显示
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            String itemTitle = (String) solrDocument.get("item_title");
            if (list != null && list.size() > 0) {
                itemTitle = list.get(0);
                System.out.println(itemTitle);
            }
            System.out.println(solrDocument.get("item_sell_point"));
            System.out.println(solrDocument.get("item_price"));
            System.out.println(solrDocument.get("item_image"));
            System.out.println(solrDocument.get("item_category_name"));
            System.out.println("====================================");
        }
    }
}
