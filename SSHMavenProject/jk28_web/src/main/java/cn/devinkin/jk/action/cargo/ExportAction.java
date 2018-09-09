package cn.devinkin.jk.action.cargo;

import cn.devinkin.jk.action.BaseAction;
import cn.devinkin.jk.domain.Contract;
import cn.devinkin.jk.domain.Export;
import cn.devinkin.jk.domain.ExportProduct;
import cn.devinkin.jk.service.cargo.ContractService;
import cn.devinkin.jk.service.cargo.ExportProductService;
import cn.devinkin.jk.service.cargo.ExportService;
import cn.devinkin.jk.utils.Page;
import cn.devinkin.jk.utils.UtilFuns;
import cn.itcast.export.webservice.EpService;
import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ExportAction extends BaseAction implements ModelDriven<Export> {
    private ExportService exportService;

    public void setExportService(ExportService exportService) {
        this.exportService = exportService;
    }

    private Export model = new Export();

    @Override
    public Export getModel() {
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

    private ExportProductService exportProductService;

    public void setExportProductService(ExportProductService exportProductService) {
        this.exportProductService = exportProductService;
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
     *
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
        exportService.findPage("from Export", page, Export.class, null);

        // 设置分页的url地址
        page.setUrl("exportAction_list");


        // 将page对象压入栈顶
        super.push(page);

        return "list";
    }


    /**
     * 查看
     * id =
     * model 对象
     * 有id属性：
     */
    public String toview() throws Exception {
        // 1. 调用业务方法，根据id，得到对象
        Export export = exportService.get(Export.class, model.getId());
        // 放入栈顶
        super.push(export);
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
        exportService.saveOrUpdate(model);

        // 页面跳转
        return contractList();
    }


    /**
     * 进入修改页面
     */
    public String toupdate() throws Exception {
        // 1. 根据部门id，得到一个对象
        Export export = exportService.get(Export.class, getModel().getId());
        // 2. 将这个对象放入值栈中
        super.push(export);

        StringBuilder sb = new StringBuilder();

        // 关联级别的数据检索
        Set<ExportProduct> epSet = export.getExportProducts();
        // 遍历集合
        for (ExportProduct ep : epSet) {
            sb.append("addTRRecord(\"mRecordTable\",\"").append(ep.getId());
            sb.append("\",\"").append(UtilFuns.convertNull(ep.getProductNo()));
            sb.append("\",\"").append(UtilFuns.convertNull(ep.getCnumber()));
            sb.append("\",\"").append(UtilFuns.convertNull(ep.getGrossWeight()));
            sb.append("\",\"").append(UtilFuns.convertNull(ep.getNetWeight()));
            sb.append("\",\"").append(UtilFuns.convertNull(ep.getSizeLength()));
            sb.append("\",\"").append(UtilFuns.convertNull(ep.getSizeWidth()));
            sb.append("\",\"").append(UtilFuns.convertNull(ep.getSizeHeight()));
            sb.append("\",\"").append(UtilFuns.convertNull(ep.getExPrice()));
            sb.append("\",\"").append(UtilFuns.convertNull(ep.getTax()));
            sb.append("\");");
        }

        // 3. 将拼接好的字符串放入值栈中
        super.put("mRecordData", sb.toString());

        return "toupdate";
    }


    /**
     * 保存修改的结果
     */
    public String update() throws Exception {
        // 调用业务
        Export export = exportService.get(Export.class, getModel().getId());
        // 2. 设置修改的属性
        export.setInputDate(model.getInputDate());
        export.setLcno(model.getLcno());
        export.setConsignee(model.getConsignee());
        export.setShipmentPort(model.getShipmentPort());
        export.setDestinationPort(model.getDestinationPort());
        export.setTransportMode(model.getTransportMode());
        export.setPriceCondition(model.getPriceCondition());
        // 唛头时指居右一定格式的备注信息
        export.setMarks(model.getMarks());
        export.setRemark(model.getRemark());

        Set<ExportProduct> epSet = new HashSet<ExportProduct>();
        for (int i = 0; i < mr_id.length; i++) {
            // 遍历数组,得到每个商品对象
            ExportProduct ep = exportProductService.get(ExportProduct.class, mr_id[i]);
            epSet.add(ep);

            if ("1".equals(mr_changed[i])) {
                ep.setCnumber(mr_cnumber[i]);
                ep.setGrossWeight(mr_grossWeight[i]);
                ep.setNetWeight(mr_netWeight[i]);
                ep.setSizeLength(mr_sizeLength[i]);
                ep.setSizeWidth(mr_sizeWidth[i]);
                ep.setSizeHeight(mr_sizeHeight[i]);
                ep.setExPrice(mr_exPrice[i]);
                ep.setTax(mr_tax[i]);
            }
        }

        // 设置报运单和商品列表的关系
        export.setExportProducts(epSet);

        exportService.saveOrUpdate(export);
        return "update";
    }

    private String mr_changed[];
    private String mr_id[];
    private Integer mr_cnumber[];
    private Double mr_grossWeight[];
    private Double mr_netWeight[];
    private Double mr_sizeLength[];
    private Double mr_sizeWidth[];
    private Double mr_sizeHeight[];
    private Double mr_exPrice[];
    private Double mr_tax[];

    public void setMr_NetWeight(Double[] mr_NetWeight) {
        this.mr_netWeight = mr_NetWeight;
    }

    public void setMr_changed(String[] mr_changed) {
        this.mr_changed = mr_changed;
    }

    public void setMr_id(String[] mr_id) {
        this.mr_id = mr_id;
    }

    public void setMr_cnumber(Integer[] mr_cnumber) {
        this.mr_cnumber = mr_cnumber;
    }

    public void setMr_grossWeight(Double[] mr_grossWeight) {
        this.mr_grossWeight = mr_grossWeight;
    }

    public void setMr_sizeLength(Double[] mr_sizeLength) {
        this.mr_sizeLength = mr_sizeLength;
    }

    public void setMr_sizeWidth(Double[] mr_sizeWidth) {
        this.mr_sizeWidth = mr_sizeWidth;
    }

    public void setMr_sizeHeight(Double[] mr_sizeHeight) {
        this.mr_sizeHeight = mr_sizeHeight;
    }

    public void setMr_exPrice(Double[] mr_exPrice) {
        this.mr_exPrice = mr_exPrice;
    }

    public void setMr_netWeight(Double[] mr_netWeight) {
        this.mr_netWeight = mr_netWeight;
    }

    public void setMr_tax(Double[] mr_tax) {
        this.mr_tax = mr_tax;
    }


    /**
     * 删除
     * model
     * id：String类型
     * 具有同名框的一组值如何封装数据
     * 如果服务器端是String类型：struts会使用逗号空格分隔id
     * id：Integer类型
     * id = 100  id = 200  id = 300
     * 对于Integer，Float，Double，Date只保留最后一个值：300
     * Integer []id;使用数组来接收参数
     */
    public String delete() throws Exception {
        String ids[] = getModel().getId().split(", ");

        // 调用业务方法实现批量删除
        exportService.delete(Export.class, ids);
        return "delete";
    }


    /**
     * 提交
     */
    public String submit() throws Exception {
        // 1.获取选中的所有id
        String[] ids = model.getId().split(", ");

        // 2. 遍历ids，并加载出每个出口报运的对象，再修改出口报运的状态
        exportService.changeState(ids, 1);

        return "alist";
    }


    /**
     * 取消
     */
    public String cancel() throws Exception {
        // 1.获取选中的所有id
        String[] ids = model.getId().split(", ");
        // 2. 遍历ids，并加载出每个出口报运的对象，再修改出口报运的状态
        exportService.changeState(ids, 0);

        return "alist";
    }


    @Resource(name = "exportClient")
    private EpService epService;

    public void setEpService(EpService epService) {
        this.epService = epService;
    }
    //    {
//        JaxWsProxyFactoryBean proxy  = new JaxWsProxyFactoryBean();
//        proxy.setAddress("http://localhost:8080/jk28_export/ws/export?wsdl");
//        proxy.setServiceClass(EpService.class);
//        this.epService = (EpService) proxy.create();
//    }

    /**
     * 电子保运
     * @return
     * @throws Exception
     */
    public String export() throws Exception {
        // 1. 确定出选中的报运单
        Export export = exportService.get(Export.class, model.getId());

        // 2. 将报运单对象及它的商品列表转换为json
        String inputJson = JSON.toJSONString(export);
        System.out.println(inputJson);

        // 3. 调用远程的webservice服务,将json传递给海关报运平台
        String resultJson = epService.exportE(inputJson);


        // 4. 处理海关报运平台响应的结果(json)
        Export resultExport = JSON.parseObject(resultJson,Export.class);

        exportService.updateE(resultExport);

        // 5. 再次查询
        return "alist";
    }
}
