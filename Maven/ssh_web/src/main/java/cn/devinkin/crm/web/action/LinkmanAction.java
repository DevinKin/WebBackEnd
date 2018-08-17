package cn.devinkin.crm.web.action;

import cn.devinkin.crm.domain.Customer;
import cn.devinkin.crm.domain.Linkman;
import cn.devinkin.crm.domain.PageBean;
import cn.devinkin.crm.service.CustomerService;
import cn.devinkin.crm.service.LinkmanService;
import cn.devinkin.crm.utils.FastJsonUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LinkmanAction extends BaseAction implements ModelDriven<Linkman>{
    // 模型驱动
    private Linkman linkman = new Linkman();

    @Override
    public Linkman getModel() {
        return linkman;
    }


    // 注入service
    private LinkmanService linkmanService;

    public void setLinkmanService(LinkmanService linkmanService) {
        this.linkmanService = linkmanService;
    }

    private CustomerService customerService;

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * 分页查询
     * @return
     */
    public String findByPage() {
        DetachedCriteria criteria = DetachedCriteria.forClass(Linkman.class);
        // 获取到联系人的名称
        String lkm_name = linkman.getLkm_name();

        if (lkm_name != null && !lkm_name.trim().isEmpty()) {
            criteria.add(Restrictions.like("lkm_name", "%" + lkm_name + "%"));
        }

        // 获取客户
        Customer c = linkman.getCustomer();
        if (c != null && c.getCust_id() != null) {
            // 拼接查询的条件
            criteria.add(Restrictions.eq("customer.cust_id", c.getCust_id()));
        }

        // 调用业务层
        PageBean<Linkman> page = linkmanService.findByPage(this.getPageCode(), this.getPageSize(), criteria);
        this.setVs("page", page);
        return "page";
    }


    /**
     * 更新前的初始化操作
     * @return
     */
    public String initUpdate() {
        linkman = linkmanService.findById(linkman.getLkm_id());
        return "initUpdate";
    }


    /**
     * 更新联系人
     * @return
     */
    public String update() {
        Customer c = customerService.findById(linkman.getCustomer().getCust_id());
        linkman.setCustomer(c);
        System.out.println(linkman);
        linkmanService.update(linkman);
        return "update";
    }

    /**
     * 删除联系人
     */
    public String delete() {
        linkmanService.delete(linkman);
        return "delete";
    }

    /**
     * 新增联系人之前的初始化
     */
    public String initAddUI() {
        return "initAddUI";
    }


    /**
     * 保存联系人
     */
    public String save() {
        Customer c = customerService.findById(linkman.getCustomer().getCust_id());
        linkman.setCustomer(c);
        linkmanService.save(linkman);
        return "save";
    }

    /**
     * 查找所有联系人
     */
    public String findAll() {
        List<Linkman> list = linkmanService.findAll();
        // 转换成json
        String jsonString = FastJsonUtil.toJSONString(list);
        HttpServletResponse response = ServletActionContext.getResponse();
        FastJsonUtil.write_json(response, jsonString);
        return NONE;
    }
}
