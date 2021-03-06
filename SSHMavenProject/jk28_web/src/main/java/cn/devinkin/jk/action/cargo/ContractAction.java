package cn.devinkin.jk.action.cargo;

import cn.devinkin.jk.action.BaseAction;
import cn.devinkin.jk.action.print.ContractPrint;
import cn.devinkin.jk.domain.Contract;
import cn.devinkin.jk.domain.User;
import cn.devinkin.jk.service.cargo.ContractService;
import cn.devinkin.jk.utils.Page;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;

import java.util.List;


/**
 * 购销合同管理的Action
 * @author king
 */
public class ContractAction extends BaseAction implements ModelDriven<Contract>{

    private Contract model = new Contract();

    @Override
    public Contract getModel() {
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

    // 注入ContractService
    private ContractService contractService;
    public void setContractService(ContractService contractService) {
        this.contractService = contractService;
    }


    /**
     * 分页查询
     */
    public String list() throws Exception {
        String hql = "from Contract where 1=1";

        // 如何确定出用户的身份,用户的等级
        User user = super.getCurrentUser();
        int degree = user.getUserInfo().getDegree();
        if (degree == 4) {
            // 说明是员工
            hql += " and createBy = '" + user.getId() + "'";
        } else if (degree == 3) {
            // 说明是部门经理,管理本部门
            hql += " and createDept='" + user.getDept().getId() + "'";
        } else if (degree == 2) {
            // 说明是管理本部门及下属部门
        } else if (degree == 1) {
            // 说明是副经理
        } else if (degree == 0) {
            // 说明是总经理
        }
        contractService.findPage(hql, page, Contract.class, null);

        // 设置分页的url地址
        page.setUrl("contractAction_list");


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
        Contract contract = contractService.get(Contract.class, getModel().getId());
        // 放入栈顶
        super.push(contract);
        return "toview";
    }


    /**
     * 进入新增页面
     */
    public String tocreate() throws Exception {
        // 1. 查询所有的模块
        List<Contract> contractList = contractService.find("from Contract WHERE state = 1", Contract.class, null);

        // 2. 将模块列表压入值栈中
        super.put("contractList", contractList);
        // 页面跳转
        return "tocreate";
    }


    /**
     * 保存
     */
    public String insert() throws Exception {
        // 1. 加入细粒度权限控制的数据
        User user = super.getCurrentUser();
        // 设置创建者的id
        model.setCreateBy(user.getId());
        // 设置创建者所在部门的id
        model.setCreateDept(user.getDept().getId());

        // 设置购销合同下的货物数和附件数
        model.setProdAmount(0);
        model.setExtAmount(0);
        contractService.saveOrUpdate(getModel());

        // 页面跳转
        return "alist";
    }


    /**
     * 进入修改页面
     */
    public String toupdate() throws Exception {
        // 1. 根据部门id，得到一个对象
        Contract contract = contractService.get(Contract.class, getModel().getId());

        // 2. 查询所有的模块
        List<Contract> contractList = contractService.find("from Contract WHERE state = 1", Contract.class, null);

        // 3. 在模块列表中去除本模块
        contractList.remove(contract);

        // 4. 将模块列表压入值栈中
        super.put("contractList", contractList);

        // 5. 将这个对象放入值栈中
        super.push(contract);

        return "toupdate";
    }


    /**
     * 保存修改的结果
     */
    public String update() throws Exception {
        // 调用业务
        Contract contract = contractService.get(Contract.class,getModel().getId());

        // 2. 设置修改的属性
        contract.setCustomName(model.getCustomName());
        contract.setPrintStyle(model.getPrintStyle());
        contract.setContractNo(model.getContractNo());
        contract.setOfferor(model.getOfferor());
        contract.setInputBy(model.getInputBy());
        contract.setCheckBy(model.getCheckBy());
        contract.setInspector(model.getInspector());
        contract.setSigningDate(model.getSigningDate());
        contract.setImportNum(model.getImportNum());
        contract.setShipTime(model.getShipTime());
        contract.setTradeTerms(model.getTradeTerms());
        contract.setDeliveryPeriod(model.getDeliveryPeriod());
        contract.setCrequest(model.getCrequest());
        contract.setRemark(model.getRemark());


        contractService.saveOrUpdate(contract);
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
        contractService.delete(Contract.class, ids);
        return "delete";
    }


    /**
     * 提交
     */
    public String submit() throws Exception {
        // 1.获取选中的所有id
        String[] ids = model.getId().split(", ");

        // 2. 遍历ids，并加载出每个购销合同的对象，再修改购销合同的状态
        contractService.changeState(ids, 1);
        return "alist";
    }




    /**
     * 取消
     */
    public String cancel() throws Exception {
        // 1.获取选中的所有id
        String[] ids = model.getId().split(", ");

        // 2. 遍历ids，并加载出每个购销合同的对象，再修改购销合同的状态
        contractService.changeState(ids, 0);
        return "alist";
    }


    /**
     * 打印购销合同
     * @return
     * @throws Exception
     */
    public String print() throws Exception {
        // 根据购销合同的id获得购销合同
        Contract contract = contractService.get(Contract.class,model.getId());
        // 指定path
        String path = ServletActionContext.getServletContext().getRealPath("/");
        ContractPrint cp = new ContractPrint();
        cp.print(contract, path, ServletActionContext.getResponse());
        return NONE;
    }
}
