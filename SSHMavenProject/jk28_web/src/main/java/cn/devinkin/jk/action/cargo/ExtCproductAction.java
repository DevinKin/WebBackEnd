package cn.devinkin.jk.action.cargo;

import cn.devinkin.jk.action.BaseAction;
import cn.devinkin.jk.domain.ExtCproduct;
import cn.devinkin.jk.domain.Factory;
import cn.devinkin.jk.service.ExtCproductService;
import cn.devinkin.jk.service.FactoryService;
import cn.devinkin.jk.utils.Page;
import com.opensymphony.xwork2.ModelDriven;

import java.util.List;


/**
 * 货物管理的Action
 *
 * @author king
 */
public class ExtCproductAction extends BaseAction implements ModelDriven<ExtCproduct> {

    private ExtCproduct model = new ExtCproduct();

    @Override
    public ExtCproduct getModel() {
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

    // 注入ExtCproductService
    private ExtCproductService extCproductService;

    public void setExtCproductService(ExtCproductService extCproductService) {
        this.extCproductService = extCproductService;
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
        // 1. 调用业务方法，查询出生产附件的厂家
        String hql = "from Factory where state =1 and ctype='附件'";

        List<Factory> factoryList = (List<Factory>) factoryService.find(hql, Factory.class, null);

        // 2. 放入值栈中
        super.put("factoryList", factoryList);


        // 3. 查询当前货物下的附件列表
        extCproductService.findPage("from ExtCproduct where contractProduct.id=?",page, ExtCproduct.class,
                new Object[] {model.getContractProduct().getId()});

        // 4. 设置page的url
        page.setUrl("extCproductAction_tocreate");

        // 5. 将page放入栈顶
        super.push(page);
        // 页面跳转
        return "tocreate";
    }


    /**
     * 保存
     */
    public String insert() throws Exception {
        extCproductService.saveOrUpdate(model);

        // 页面跳转
        return tocreate();
    }


    /**
     * 进入修改页面
     */
    public String toupdate() throws Exception {
        // 1. 根据货物d，得到一个货物对象
        ExtCproduct extCproduct = extCproductService.get(ExtCproduct.class, model.getId());

        // 2. 将这个对象放入值栈中
        super.push(extCproduct);

        // 3. 加载生产厂家列表
        String hql = "from Factory where state =1 and ctype='附件'";
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
        ExtCproduct extCproduct = extCproductService.get(ExtCproduct.class, model.getId());

        // 2. 设置修改的属性
        extCproduct.setFactory(model.getFactory());
        extCproduct.setFactoryName(model.getFactoryName());
        extCproduct.setProductNo(model.getProductNo());
        extCproduct.setProductImage(model.getProductImage());
        extCproduct.setCnumber(model.getCnumber());
        extCproduct.setAmount(model.getAmount());
        extCproduct.setPackingUnit(model.getPackingUnit());

        extCproduct.setPrice(model.getPrice());
        extCproduct.setOrderNo(model.getOrderNo());
        extCproduct.setProductDesc(model.getProductDesc());
        extCproduct.setProductRequest(model.getProductRequest());


        extCproductService.saveOrUpdate(extCproduct);
        return tocreate();
    }


    /**
     * 删除
     */
    public String delete() throws Exception {
        extCproductService.delete(ExtCproduct.class, model);

        return tocreate();
    }


}
