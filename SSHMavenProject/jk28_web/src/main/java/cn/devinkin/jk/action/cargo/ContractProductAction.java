package cn.devinkin.jk.action.cargo;

import cn.devinkin.jk.action.BaseAction;
import cn.devinkin.jk.domain.ContractProduct;
import cn.devinkin.jk.domain.Factory;
import cn.devinkin.jk.service.ContractProductService;
import cn.devinkin.jk.service.FactoryService;
import cn.devinkin.jk.utils.Page;
import com.opensymphony.xwork2.ModelDriven;

import java.util.Arrays;
import java.util.List;


/**
 * 货物管理的Action
 *
 * @author king
 */
public class ContractProductAction extends BaseAction implements ModelDriven<ContractProduct> {

    private ContractProduct model = new ContractProduct();

    @Override
    public ContractProduct getModel() {
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

    // 注入ContractProductService
    private ContractProductService contractProductService;

    public void setContractProductService(ContractProductService contractProductService) {
        this.contractProductService = contractProductService;
    }

    // 注入FactoryProductService
    private FactoryService factoryService;

    public void setFactoryService(FactoryService factoryService) {
        this.factoryService = factoryService;
    }



    /**
     * 进入新增页面
     */
    public String tocreate() throws Exception {
        // 1. 调用业务方法，查询出生产货物的厂家
        String hql = "from Factory where state =1 and ctype='货物'";

        List<Factory> factoryList = (List<Factory>) factoryService.find(hql, Factory.class, null);

        // 2. 放入值栈中
        super.put("factoryList", factoryList);

        // 3. 查询出当前购销合同下的了货物列表
        contractProductService.findPage("from ContractProduct where contract.id = ?", page, ContractProduct.class,
              new String[]{model.getContract().getId()});
//        contractProductService.findPage("from ContractProduct where contract.id='" + model.getContract().getId() + "'", page, ContractProduct.class, null);

        // 4. 设置page的url
        page.setUrl("contractProductAction_tocreate");

        // 5. 将page放入栈顶
        super.push(page);
        // 页面跳转
        return "tocreate";
    }


    /**
     * 保存
     */
    public String insert() throws Exception {
        contractProductService.saveOrUpdate(model);

        // 页面跳转
        return tocreate();
    }


    /**
     * 进入修改页面
     */
    public String toupdate() throws Exception {
        // 1. 根据货物d，得到一个货物对象
        ContractProduct contractProduct = contractProductService.get(ContractProduct.class, model.getId());

        // 2. 将这个对象放入值栈中
        super.push(contractProduct);

        // 3. 加载生产厂家列表
        String hql = "from Factory where state =1 and ctype='货物'";
        List<Factory> factoryList = (List<Factory>) factoryService.find(hql, Factory.class, null);

        // 4. 放入值栈中
        super.put("factoryList", factoryList);


        return "toupdate";
    }


    /**
     * 保存修改的结果
     */
    public String update() throws Exception {
        // 调用业务
        ContractProduct contractProduct = contractProductService.get(ContractProduct.class, model.getId());

        // 2. 设置修改的属性
        contractProduct.setFactory(model.getFactory());
        contractProduct.setFactoryName(model.getFactoryName());
        contractProduct.setProductNo(model.getProductNo());
        contractProduct.setProductImage(model.getProductImage());
        contractProduct.setCnumber(model.getCnumber());
        contractProduct.setAmount(model.getAmount());
        contractProduct.setPackingUnit(model.getPackingUnit());
        contractProduct.setLoadingRate(model.getLoadingRate());
        contractProduct.setBoxNum(model.getBoxNum());
        contractProduct.setPrice(model.getPrice());
        contractProduct.setOrderNo(model.getOrderNo());
        contractProduct.setProductDesc(model.getProductDesc());
        contractProduct.setProductRequest(model.getProductRequest());

        contractProductService.saveOrUpdate(contractProduct);
        return tocreate();
    }


    /**
     * 删除
     */
    public String delete() throws Exception {
        contractProductService.delete(ContractProduct.class, model);
        return tocreate();
    }


}
