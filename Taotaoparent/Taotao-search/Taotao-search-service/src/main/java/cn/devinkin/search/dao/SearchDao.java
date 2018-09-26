package cn.devinkin.search.dao;

import cn.devinkin.common.pojo.SearchItem;
import cn.devinkin.common.pojo.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author devinkin
 * <p>Title: SearchDao</p>
 * <p>Description: 查询索引库商品dao</p>
 * @version 1.0
 * @see
 * @since 15:28 2018/9/23
 */
@Repository
public class SearchDao {

    @Autowired
    private SolrServer solrServer;

    public SearchResult search(SolrQuery solrQuery) throws Exception{
        // 根据query对象进行查询
        QueryResponse response = solrServer.query(solrQuery);
        // 取查询结果
        SolrDocumentList solrDocumentList = response.getResults();
        // 取查询结果总记录数
        long numFound = solrDocumentList.getNumFound();
        SearchResult result = new SearchResult();
        result.setRecordCount(numFound);

        // 把查询结果封装到SearchItem对象中
        List<SearchItem> itemList = new ArrayList<>();
        for (SolrDocument solrDocument : solrDocumentList) {
            SearchItem item = new SearchItem();
            item.setCategory_name((String) solrDocument.get("item_category_name"));
            item.setId((String) solrDocument.get("id"));
            // 取一张图
            String image = (String) solrDocument.get("item_image");
            if (StringUtils.isNotBlank(image)) {
                image = image.split(",")[0];
            }
            item.setImage(image);
            item.setPrice((Long) solrDocument.get("item_price"));
            item.setSell_point((String) solrDocument.get("item_sell_point"));

            // 获取高亮显示
            String itemTitle = (String) solrDocument.get("item_title");
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            if (list != null && list.size() > 0) {
                itemTitle = list.get(0);
            }
            item.setTitle(itemTitle);
            itemList.add(item);
        }
        // 把结果添加到SearchResult中
        result.setItemList(itemList);
        // 返回
        return result;
    }
}
