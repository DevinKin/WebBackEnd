package cn.devinkin.crm.web.action;

import cn.devinkin.crm.domain.Dict;
import cn.devinkin.crm.service.DictService;
import cn.devinkin.crm.utils.FastJsonUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 字典控制器
 * @author king
 */
public class DictAction extends ActionSupport implements ModelDriven<Dict>{

    private Dict dict = new Dict();

    @Override
    public Dict getModel() {
        return dict;
    }

    private DictService dictService;

    public void setDictService(DictService dictService) {
        this.dictService = dictService;
    }


    /**
     * 通过字典的type_code值，查询客户的级别或者是客户的来源
     * @return
     */
    public String findByCode() {
        // 调用业务层
        List<Dict> list = dictService.findByCode(dict.getDict_type_code());

        // 使用fastJson，把list转换为json字符串
        String jsonString = FastJsonUtil.toJSONString(list);

        // 把json字符串写到浏览器
        HttpServletResponse response = ServletActionContext.getResponse();
        FastJsonUtil.write_json(response, jsonString);

        return NONE;
    }
}
