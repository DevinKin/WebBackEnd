package cn.devinkin.jk.action.cargo;

import cn.devinkin.jk.action.BaseAction;
import cn.devinkin.jk.domain.Contract;
import cn.devinkin.jk.domain.ExportProduct;
import cn.devinkin.jk.service.cargo.ContractService;
import cn.devinkin.jk.service.cargo.ExportProductService;
import cn.devinkin.jk.utils.Page;
import com.opensymphony.xwork2.ModelDriven;

import java.util.List;

public class ExportProductAction extends BaseAction implements ModelDriven<ExportProduct> {
    private ExportProductService exportProductService;

    public void setExportProductService(ExportProductService exportProductService) {
        this.exportProductService = exportProductService;
    }

    private ExportProduct model = new ExportProduct();

    @Override
    public ExportProduct getModel() {
        return model;
    }

    // 分页查询
    private Page page = new Page();

    public void setPage(Page page) {
        this.page = page;
    }

    public Page getPage() {
        return page;
    }

    private ContractService contractService;

    public void setContractService(ContractService contractService) {
        this.contractService = contractService;
    }

    private String id = new String();

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    /**
     * 查询状态为1的购销合同
     * @return
     * @throws Exception
     */
    public String contractList() throws Exception {
        // 查询状态为1的购销合同
        String hql = "from Contract where state = 1";
        // 分页查询
        contractService.findPage(hql, page, Contract.class, null);

        // 放入值栈
        super.push(page);
        return "contractList";
    }

    /**
     * 分页查询
     */
    public String list() throws Exception {
        String hql = "from ExportProduct where 1=1";

        // 如何确定出用户的身份,用户的等级
//        User user = super.getCurrentUser();
//        int degree = user.getUserInfo().getDegree();
//        if (degree == 4) {
//            // 说明是员工
//            hql += " and createBy = '" + user.getId() + "'";
//        } else if (degree == 3) {
//            // 说明是部门经理,管理本部门
//            hql += " and createDept='" + user.getDept().getId() + "'";
//        } else if (degree == 2) {
//            // 说明是管理本部门及下属部门
//        } else if (degree == 1) {
//            // 说明是副经理
//        } else if (degree == 0) {
//            // 说明是总经理
//        }
        exportProductService.findPage(hql, page, ExportProduct.class, null);

        // 设置分页的url地址
        page.setUrl("exportProductAction_list");


        // 将page对象压入栈顶
        super.push(page);

        return "list";
    }


    /**
     * 查看
     * id =
     * model 对象
     *  有id属性：
     */
    public String toview() throws Exception {
        // 1. 调用业务方法，根据id，得到对象
        ExportProduct exportProduct = exportProductService.get(ExportProduct.class, getModel().getId());
        // 放入栈顶
        super.push(exportProduct);
        return "toview";
    }


    /**
     * 进入新增页面
     */
    public String tocreate() throws Exception {

        return "tocreate";
    }


    /**
     * 保存
     */
    public String insert() throws Exception {
        // 1. 加入细粒度权限控制的数据
        // 设置创建者的id
        // 设置创建者所在部门的id
        exportProductService.saveOrUpdate(model);

        // 页面跳转
        return contractList();
    }


    /**
     * 进入修改页面
     */
    public String toupdate() throws Exception {
        // 1. 根据部门id，得到一个对象
        ExportProduct exportProduct = exportProductService.get(ExportProduct.class, getModel().getId());

        // 2. 查询所有的模块
        List<ExportProduct> exportProductList = exportProductService.find("from ExportProduct WHERE state = 1", ExportProduct.class, null);

        // 3. 在模块列表中去除本模块
        exportProductList.remove(exportProduct);

        // 4. 将模块列表压入值栈中
        super.put("exportProductList", exportProductList);

        // 5. 将这个对象放入值栈中
        super.push(exportProduct);

        return "toupdate";
    }


    /**
     * 保存修改的结果
     */
    public String update() throws Exception {
        // 调用业务
        ExportProduct exportProduct = exportProductService.get(ExportProduct.class,getModel().getId());

        // 2. 设置修改的属性



        exportProductService.saveOrUpdate(exportProduct);
        return "update";
    }


    /**
     * 删除
     * model
     *  id：String类型
     *      具有同名框的一组值如何封装数据
     *      如果服务器端是String类型：struts会使用逗号空格分隔id
     *  id：Integer类型
     *      id = 100  id = 200  id = 300
     *      对于Integer，Float，Double，Date只保留最后一个值：300
     *      Integer []id;使用数组来接收参数
     */
    public String delete() throws Exception {
        String ids[] = getModel().getId().split(", ");

        // 调用业务方法实现批量删除
        exportProductService.delete(ExportProduct.class, ids);
        return "delete";
    }


    /**
     * 提交
     */
    public String submit() throws Exception {
        // 1.获取选中的所有id

        // 2. 遍历ids，并加载出每个购销合同的对象，再修改购销合同的状态
        return "alist";
    }




    /**
     * 取消
     */
    public String cancel() throws Exception {
        // 1.获取选中的所有id

        // 2. 遍历ids，并加载出每个购销合同的对象，再修改购销合同的状态
        return "alist";
    }

}
