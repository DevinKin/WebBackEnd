package cn.devinkin.search.service;

import cn.devinkin.common.pojo.SearchResult;

/**
 * @author devinkin
 * <p>Title: SearchService</p>
 * <p>Description: </p>
 * @version 1.0
 * @see
 * @since 11:11 2018/9/25
 */
public interface SearchService {
    SearchResult search(String queryString, long page, int rows) throws Exception;
}
