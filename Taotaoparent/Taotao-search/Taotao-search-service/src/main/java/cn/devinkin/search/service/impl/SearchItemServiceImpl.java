package cn.devinkin.search.service.impl;

import cn.devinkin.common.pojo.SearchItem;
import cn.devinkin.common.pojo.TaotaoResult;
import cn.devinkin.search.mapper.SearchItemMapper;
import cn.devinkin.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author devinkin
 * <p>Title: SearchItemServiceImpl</p>
 * <p>Description: </p>
 * @version 1.0
 * @see
 * @since 12:05 2018/9/23
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private SearchItemMapper searchItemMapper;

    @Autowired
    private SolrServer solrServer;

    @Override
    public TaotaoResult importItemToIndex() {
        try {
            // 1. 先查询所有商品数据
            List<SearchItem> itemList = searchItemMapper.getItemList();
            // 2. 遍历商品数据到索引库
            for (SearchItem item : itemList) {
                // 创建文档对象
                SolrInputDocument document = new SolrInputDocument();
                // 向文档中添加域名
                document.addField("id", item.getId());
                document.addField("item_title", item.getTitle());
                document.addField("item_sell_point", item.getSell_point());
                document.addField("item_price",item.getPrice());
                document.addField("item_image",item.getImage());
                document.addField("item_category_name",item.getCategory_name());
                document.addField("item_desc",item.getItem_desc());
                // 把文档写入索引库
                solrServer.add(document);
            }
            // 3. 提交
            solrServer.commit();
            // 4. 返回添加成功

        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, "数据导入失败");
        }
        return TaotaoResult.ok();
    }
}
