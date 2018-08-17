package cn.devinkin.crm.web.action;

import cn.devinkin.crm.domain.Customer;
import cn.devinkin.crm.domain.Dict;
import cn.devinkin.crm.domain.Linkman;
import cn.devinkin.crm.domain.PageBean;
import cn.devinkin.crm.service.CustomerService;
import cn.devinkin.crm.service.LinkmanService;
import cn.devinkin.crm.utils.FastJsonUtil;
import cn.devinkin.crm.utils.UploadUtils;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 客户的控制层
 *
 * @author king
 */
public class CustomerAction extends BaseAction implements ModelDriven<Customer> {
    // 提供service成员属性，提供service的set方法，供Spring框架注入
    private LinkmanService linkmanService;
    private CustomerService customerService;

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void setLinkmanService(LinkmanService linkmanService) {
        this.linkmanService = linkmanService;
    }

    /**
     * 保存客户的方法
     *
     * @return
     */
    public String save() throws IOException {
        // 做文件的上传，说明用户选择了上传的文件了
        if (uploadFileName != null) {
            // 打印
            System.out.println("文件名称: " + uploadFileName);
            System.out.println("文件类型: " + uploadContentType);
            // 把文件的名称处理一下
            String uuidname = UploadUtils.getUUIDName(uploadFileName);
            // 把文件上传到 upload 目录下
            String path = "/home/king/WebBackEnd/SSH/crm/web/WEB-INF/upload/";
            File file = new File(path + uuidname);
            // 简单的方式
            FileUtils.copyFile(upload, file);

            // 把上传文件的路径，保存到客户表中
            customer.setFilepath(path + uuidname);
        }
        String slkm_id = (String) ServletActionContext.getRequest().getParameter("lkm_id");
        if (slkm_id != null && !slkm_id.trim().isEmpty()) {
            Long lkm_id = Long.parseLong(slkm_id);
            Linkman linkman = linkmanService.findById(lkm_id);

            // 新创建一个对象，对应一条新的联系人记录
            Linkman slinkman = new Linkman(linkman);
            slinkman.setCustomer(customer);
            // 级联保存，外键由Linkman维护
            linkmanService.save(slinkman);
        }
        // 保存客户成功了
//        customerService.save(customer);
        return "save";
    }


//    // 属性驱动的方式;
//    // 当前页，默认值就是1
//    private Integer pageCode = 1;
//
//    public void setPageCode(Integer pageCode) {
//        if (pageCode == null) {
//            pageCode = 1;
//        } else {
//            this.pageCode = pageCode;
//        }
//    }
//
//    // 每页显示的数据的条数
//    private Integer pageSize = 2;
//
//    public void setPageSize(Integer pageSize) {
//        if (pageSize == null) {
//            pageSize = 2;
//        } else {
//            this.pageSize = pageSize;
//        }
//    }

    /**
     * 分页查找客户
     *
     * @return
     */
    public String findByPage() {
        // 调用service业务层
        DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class);

        // 拼接查询的条件
        String cust_name = customer.getCust_name();
        if (cust_name != null && !cust_name.trim().isEmpty()) {
            // 说明客户的名称输入
            criteria.add(Restrictions.like("cust_name", "%" + cust_name + "%"));
        }

        // 拼接客户的级别
        Dict level = customer.getLevel();
        if (level != null && !level.getDict_id().trim().isEmpty()) {
            // 说明客户的级别肯定选择了一个级别
            criteria.add(Restrictions.eq("level.dict_id", level.getDict_id()));
        }

        // 拼接客户的来源
        Dict source = customer.getSource();
        if (source != null && !source.getDict_id().trim().isEmpty()) {
            // 说明客户的级别肯定选择了一个级别
            criteria.add(Restrictions.eq("source.dict_id", source.getDict_id()));
        }

        // 查询
        PageBean<Customer> page = customerService.findByPage(this.getPageCode(), this.getPageSize(), criteria);

        // 压栈
//        ValueStack vs = ActionContext.getContext().getValueStack();
        // 栈顶是一个map集合map<"page",page对象>
//        vs.set("page", page);
        this.setVs("page", page);
        return "page";
    }


    /**
     * 删除客户
     * @return
     */
    public String delete() {
        // 删除客户，先把客户的信息获取到，上传文件的路径
        Customer c = customerService.findById(customer.getCust_id());

        // 获取上传文件的路径
        String filepath = c.getFilepath();

        // 删除客户
        customerService.delete(c);

        if (filepath == null) {
            return "delete";
        }


        // 再删除文件
        File file = new File(filepath);
        if (file != null && file.exists()) {
            file.delete();
        }
        return "delete";
    }


    /**
     *  跳转到出事修改的页面
     * @return
     */
    public String initUpdate() {
        // 默认customer压栈了，Action默认压栈，model是Action类的属性，getModel(返回customer对象)
        customer = customerService.findById(customer.getCust_id());
        // 压栈

        return "initUpdate";
    }


    /**
     * 修改客户的功能
     * @return
     */
    public String update() throws IOException {
        // 判断，说明客户上传了新的文件
        if ( uploadFileName != null) {
            // 先删除旧的图片
            String oldFilepath = customer.getFilepath();
            if (oldFilepath != null && !oldFilepath.trim().isEmpty()) {
                // 说明，旧的路径存在，删除该路径下的文件
                File file = new File(oldFilepath);
                if (file != null && file.exists()) {
                    file.delete();
                }
            }
            // 上传新的文件
            // 先处理文件的名称的问题
            String uuidName = UploadUtils.getUUIDName(uploadFileName);
            String path = "/home/king/WebBackEnd/Maven/ssh_web/src/main/webapp/WEB-INF/upload";

            File file = new File(path + uuidName);
            FileUtils.copyFile(upload, file);

            // 把客户新文件的路径更新到数据库总
            customer.setFilepath(path + uuidName);

        }

        // 客户对象已经放弃外键维护，所以不会导致联系人数据丢失
//        List<Linkman> linkmans = linkmanService.findByCustId(customer.getCust_id());
//        Set<Linkman> linkmanSet = new HashSet<>();
//        for (Linkman linkman : linkmans) {
//        }
//        customer.setLinkmans(linkmanSet);
        // 更新客户的信息
        customerService.update(customer);

        return "update";
    }


    /**
     * 查询所有
     * @return
     */
    public String findAll() {
        List<Customer> list = customerService.findAll();
        // 转换成json
        String jsonString = FastJsonUtil.toJSONString(list);
        HttpServletResponse response = ServletActionContext.getResponse();
        FastJsonUtil.write_json(response, jsonString);
        return NONE;
    }


    /**
     * 统计客户来源的数量
     * @return
     */
    public String findBySource() {
        List<Object[]> list = customerService.findBySource();
        // 压栈，栈顶是map<"page",page>对象
        setVs("list", list);
        return "findBySource";
    }

    /**
     * 统计客户行业的数量
     * @return
     */
    public String findByIndustry() {
        List<Object[]> list = customerService.findByIndustry();
        // 压栈，栈顶是map<"page",page>对象
        setVs("list", list);
        return "findByIndustry";
    }



    /**
     * 添加客户前初始化
     * @return
     */
    public String initAddUI() {
        return "initAddUI";
    }

    /**
     * 文件上传，需要在CustomerAction类中定义成员的属性，命名是有规则的！
     * private File 与表单名称一致;         // 表示要上传的文件
     * private String uploadFileName;   // 表示是上传文件的名称(没有中文乱码)
     * private String uploadContentType;    // 表示上传文件的MIME类型
     * 提供set方法，拦截器就注入值了
     */
    // 要上传的文件
    private File upload;
    // 文件的名称
    private String uploadFileName;
    // 文件的MIME类型
    private String uploadContentType;

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    // 手动创建模型驱动对象
    private Customer customer = new Customer();

    @Override
    public Customer getModel() {
        return customer;
    }
}
