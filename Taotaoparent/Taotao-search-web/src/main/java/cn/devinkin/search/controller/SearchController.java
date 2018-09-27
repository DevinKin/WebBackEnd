package cn.devinkin.search.controller;

import cn.devinkin.common.pojo.SearchResult;
import cn.devinkin.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author devinkin
 * <p>Title: SearchController</p>
 * <p>Description: 搜索服务Controller</p>
 * @version 1.0
 * @see
 * @since 18:10 2018/9/25
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Value("${SEARCH_RESULT_ROWS}")
    private Integer SEARCH_RESULT_ROWS;

    @RequestMapping("/search")
    public String search(@RequestParam("q") String queryString,
                         @RequestParam(defaultValue = "1") Long page,
                         Model model) throws Exception{
//        int a = 1/0;
        // 解决查询条件中文乱码
        queryString = new String(queryString.getBytes("ISO-8859-1"), "UTF-8");
        // 调用服务,执行查询
        SearchResult searchResult = searchService.search(queryString, page, SEARCH_RESULT_ROWS);
        // 把结果传递给页面
        model.addAttribute("query", queryString);
        model.addAttribute("totalPages", searchResult.getTotalPages());
        model.addAttribute("itemList", searchResult.getItemList());
        model.addAttribute("page", page);

        // 返回逻辑视图
        return "search";
    }
}
