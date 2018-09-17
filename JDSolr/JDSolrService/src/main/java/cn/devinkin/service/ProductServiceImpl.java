package cn.devinkin.service;

import cn.devinkin.dao.ProductDao;
import cn.devinkin.pojo.ResultModel;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{
    private static final Integer PAGE_SIZE = 60;

    @Autowired
    private ProductDao productDao;

    @Override
    public ResultModel query(String queryString, String catalog_name, String price,
                             String sort, Integer page) throws Exception {
        // 创建查询条件对象
        SolrQuery solrQuery = new SolrQuery();

        // 设置默认搜索域
        solrQuery.set("df", "product_keywords");
        // 设置查询关键字
        if (queryString != null && !"".equals(queryString)) {
            solrQuery.setQuery(queryString);
        } else {
            solrQuery.setQuery("*:*");
        }

        // 设置过滤条件,按照分类名称进行过滤
        if (catalog_name != null && !"".equals(catalog_name)) {
            solrQuery.addFilterQuery("product_catalog_name:" + catalog_name);
        }

        // 设置过滤条件,按照价格进行过滤
        if (price != null && !"".equals(price)) {
            String[] split = price.split("-");
            if (split != null && split.length > 1) {
                solrQuery.addFilterQuery("product_price:[" + split[0] + " TO " + split[1] + "]");
            }
        }

        // 设置排序
        if ("1".equals(sort)) {
            solrQuery.addSort("product_price", SolrQuery.ORDER.asc);
        } else {
            solrQuery.addSort("product_price", SolrQuery.ORDER.desc);
        }

        // 设置分页
        if (page == null) {
            page = 1;
        }
        Integer start = (page - 1) * PAGE_SIZE;
        solrQuery.setStart(start);
        solrQuery.setRows(PAGE_SIZE);

        // 设置高亮显示的域,商品名称
        solrQuery.addHighlightField("product_name");
        // 设置高亮显示的前缀
        solrQuery.setHighlightSimplePre("<span style=\"color:red\">");
        // 设置高亮显示的后缀
        solrQuery.setHighlightSimplePost("</span>");

        // 查询返回结果
        ResultModel resultModel = productDao.queryProducts(solrQuery);
        resultModel.setCurPage(Long.valueOf(page));

        // 计算总页数,总记录数/每页的数量
        Long pageCount = resultModel.getRecordCount() / PAGE_SIZE;
        if (resultModel.getRecordCount() % PAGE_SIZE > 0) {
            pageCount++;
        }

        // 设置每页数量
        resultModel.setPageCount(pageCount);
        return resultModel;
    }
}
