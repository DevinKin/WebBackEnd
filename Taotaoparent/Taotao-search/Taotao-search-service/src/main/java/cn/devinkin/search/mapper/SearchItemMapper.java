package cn.devinkin.search.mapper;

import cn.devinkin.common.pojo.SearchItem;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author devinkin
 * <p>Title: SearchItemMapper</p>
 * <p>Description: 商品搜索Mapper</p>
 * @version 1.0
 * @see
 * @since 10:45 2018/9/23
 */
@Repository
public interface SearchItemMapper {
    List<SearchItem> getItemList();
    SearchItem getItemById(Long itemId);
}
