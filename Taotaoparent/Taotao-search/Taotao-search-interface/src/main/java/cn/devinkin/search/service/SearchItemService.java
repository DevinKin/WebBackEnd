package cn.devinkin.search.service;

import cn.devinkin.common.pojo.TaotaoResult;

/**
 * @author devinkin
 * <p>Title: SearchItemService</p>
 * <p>Description: </p>
 * @version 1.0
 * @see
 * @since 12:00 2018/9/23
 */
public interface SearchItemService {
    /**
     * 将所有商品导入到索引库中
     * @return
     */
    TaotaoResult importItemToIndex();
}
