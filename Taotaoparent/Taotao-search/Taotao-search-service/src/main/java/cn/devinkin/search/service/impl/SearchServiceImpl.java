package cn.devinkin.search.service.impl;

import cn.devinkin.common.pojo.SearchResult;
import cn.devinkin.search.dao.SearchDao;
import cn.devinkin.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author devinkin
 * <p>Title: SearchServiceImpl</p>
 * <p>Description: 搜索服务功能实现</p>
 * @version 1.0
 * @see
 * @since 11:12 2018/9/25
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao searchDao;


    @Override
    public SearchResult search(String queryString, long page, int rows) throws Exception {
        // 根据查询条件拼装查询对象
        // 创建一个SolrQuery对象
        SolrQuery query = new SolrQuery();
        // 设置查询条件
        query.setQuery(queryString);
        // 设置分页条件
        if (page < 1) {
            page = 1;
        }
        query.setStart((int) ((page - 1) * rows));
        if (rows < 1) {
            rows = 10;
        }
        query.setRows(rows);
        // 设置默认搜索域
        query.set("df", "item_title");
        // 设置高亮显示
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<span style=\"color: red;\">");
        query.setHighlightSimplePost("</span>");
        // 调用dao执行查询
        SearchResult searchResult = searchDao.search(query);
        // 计算查询结果的总页数
        long recordCount = searchResult.getRecordCount();
        long pages = recordCount / rows;
        if ((recordCount % rows) > 0) {
            pages++;
        }
        searchResult.setTotalPages(pages);
        // 返回结果
        return searchResult;
    }
}
