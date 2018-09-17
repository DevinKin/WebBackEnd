package cn.devinkin.dao;

import cn.devinkin.pojo.ProductModel;
import cn.devinkin.pojo.ResultModel;
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


@Repository
public class ProductDaoImpl implements ProductDao{

    @Autowired
    private SolrServer solrServer;

    @Override
    public ResultModel queryProducts(SolrQuery solrQuery) throws Exception {
        // 查询并获取查询响应
        QueryResponse queryResponse = solrServer.query(solrQuery);

        // 从响应中获取查询结果集,文档的集合
        SolrDocumentList docList = queryResponse.getResults();
        ResultModel resultModel = new ResultModel();
        // 创建返回结果集
        List<ProductModel> productModelList = new ArrayList<ProductModel>();
        if (docList != null) {
            // 获取总记录数
            resultModel.setRecordCount(docList.getNumFound());
            // 遍历结果集
            for (SolrDocument doc : docList) {
                ProductModel productModel = new ProductModel();
                productModel.setPid(String.valueOf(doc.get("id")));
                // =========获取高亮=========
                Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
                if (highlighting != null) {
                    List<String> list = highlighting.get(doc.get("id")).get("product_name");
                    if (list != null && list.size() > 0) {
                        productModel.setName(list.get(0));
                    } else {
                        productModel.setName(String.valueOf(doc.get("product_name")));
                    }
                } else {
                    productModel.setName(String.valueOf(doc.get("product_name")));
                }
                // =========获取高亮=========

                if (doc.get("product_price") != null && !"".equals(doc.get("product_price").toString())) {
                    productModel.setPrice(Float.valueOf(doc.get("product_price").toString()));
                }
                productModel.setCatalog_name(String.valueOf(doc.get("product_catalog_name")));
                productModel.setPicture(String.valueOf(doc.get("product_picture")));
                productModelList.add(productModel);
            }
            resultModel.setProductList(productModelList);
        }
        return resultModel;
    }
}
