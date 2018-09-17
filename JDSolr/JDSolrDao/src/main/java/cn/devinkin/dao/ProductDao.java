package cn.devinkin.dao;

import cn.devinkin.pojo.ResultModel;
import org.apache.solr.client.solrj.SolrQuery;

public interface ProductDao {
    public ResultModel queryProducts(SolrQuery solrQuery) throws Exception;
}
