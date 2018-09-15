package cn.devinkin.controller;

import cn.devinkin.pojo.BaseDict;
import cn.devinkin.pojo.Customer;
import cn.devinkin.pojo.QueryVo;
import cn.devinkin.service.CustomerService;
import cn.itcast.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // 注入客户来源
    @Value("${customer.dict.source}")
    private String source;

    // 注入客户行业
    @Value("${customer.dict.industry}")
    private String industry;

    // 注入客户级别
    @Value("${customer.dict.level}")
    private String level;

    @RequestMapping("/list")
    public String list(Model model, QueryVo queryVo) throws Exception {
        // 客户来源
        List<BaseDict> sourceList = customerService.findDictByCode(source);
        // 所属行业
        List<BaseDict> industryList = customerService.findDictByCode(industry);
        // 客户级别
        List<BaseDict> levelList = customerService.findDictByCode(level);

        if (queryVo.getPage() == null) {
            queryVo.setPage(1);
        }
        // 设置查询的起始记录条数
        queryVo.setStart((queryVo.getPage() - 1) * queryVo.getSize());

        String custName = queryVo.getCustName();
        if (custName != null && custName != "") {
            queryVo.setCustName(new String(custName.getBytes("ISO-8859-1"), "UTF-8"));
        }

        //查询列表和数据总数
        List<Customer> customerList = customerService.findCustomerByVo(queryVo);
        Integer total = customerService.findCustomerByVoCount(queryVo);


        Page<Customer> page = new Page<Customer>();
        // 数据总数
        page.setTotal(total);
        // 每页显示条数
        page.setSize(queryVo.getSize());
        // 当前页数
        page.setPage(queryVo.getPage());
        // 数据列表
        page.setRows(customerList);

        model.addAttribute("page", page);




        // 高级查询下拉列表数据
        model.addAttribute("fromType", sourceList);
        model.addAttribute("industryType", industryList);
        model.addAttribute("levelType", levelList);

        // 高级查询回显数据
        model.addAttribute("custName", queryVo.getCustName());
        model.addAttribute("custSource", queryVo.getCustSource());
        model.addAttribute("custIndustry", queryVo.getCustIndustry());
        model.addAttribute("custLevel", queryVo.getCustLevel());

        return "customer";
    }


    // ResponseBody将返回对象转换为json数据返回到页面
    @RequestMapping("/detail")
    @ResponseBody
    public Customer detail(Long id) throws Exception {
        Customer customer = customerService.findCustomerById(id);
        return customer;
    }

    @RequestMapping("/update")
    public String update(Customer customer) throws Exception{
        customerService.updateCustomerById(customer);
        return "update";
    }

    @RequestMapping("/delete")
    public String delete(Long id) throws Exception {
        customerService.deleteCustomerById(id);
        return "delete";
    }
}
