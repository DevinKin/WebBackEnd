package cn.devinkin.jk.action.sysadmin;

import cn.devinkin.jk.action.BaseAction;
import cn.devinkin.jk.domain.Dept;
import cn.devinkin.jk.domain.Module;
import cn.devinkin.jk.service.DeptService;
import cn.devinkin.jk.service.ModuleService;
import cn.devinkin.jk.utils.Page;
import com.opensymphony.xwork2.ModelDriven;

import java.util.List;


/**
 * 部门管理的Action
 * @author king
 */
public class ModuleAction extends BaseAction implements ModelDriven<Module>{

    private Module model = new Module();

    @Override
    public Module getModel() {
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

    // 注入ModuleService
    private ModuleService moduleService;
    public void setModuleService(ModuleService moduleService) {
        this.moduleService = moduleService;
    }
    // 注入DeptService
    private DeptService deptService;
    public void setDeptService(DeptService deptService) {
        this.deptService = deptService;
    }


    /**
     * 分页查询
     */
    public String list() throws Exception {
        moduleService.findPage("from Module", page, Module.class, null);

        // 设置分页的url地址
        page.setUrl("moduleAction_list");


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
        Module module = moduleService.get(Module.class, model.getId());
        // 放入栈顶
        super.push(module);
        return "toview";
    }


    /**
     * 进入新增页面
     */
    public String tocreate() throws Exception {
        // 1. 查询所有的模块
        List<Module> moduleList = moduleService.find("from Module WHERE state = 1", Module.class, null);

        // 2. 将模块列表压入值栈中
        super.put("moduleList", moduleList);
        // 页面跳转
        return "tocreate";
    }


    /**
     * 保存
     */
    public String insert() throws Exception {
        moduleService.saveOrUpdate(model);

        // 页面跳转
        return "alist";
    }


    /**
     * 进入修改页面
     */
    public String toupdate() throws Exception {
        // 1. 根据部门id，得到一个对象
        Module module = moduleService.get(Module.class, model.getId());

        // 2. 查询所有的模块
        List<Module> moduleList = moduleService.find("from Module WHERE state = 1", Module.class, null);

        // 3. 在模块列表中去除本模块
        moduleList.remove(module);

        // 4. 将模块列表压入值栈中
        super.put("moduleList", moduleList);

        // 5. 将这个对象放入值栈中
        super.push(module);

        return "toupdate";
    }


    /**
     * 保存修改的结果
     */
    public String update() throws Exception {
        // 调用业务
        Module module = moduleService.get(Module.class,model.getId());

        // 2. 设置修改的属性
        module.setName(model.getName());
        module.setParentId(model.getParentId());
        module.setParentName(model.getParentName());
        module.setLayerNum(model.getLayerNum());
        module.setCpermission(model.getCpermission());
        module.setCurl(model.getCurl());
        module.setCtype(model.getCtype());
        module.setState(model.getState());
        module.setBelong(model.getBelong());
        module.setCwhich(model.getCwhich());
        module.setRemark(model.getRemark());
        module.setOrderNo(model.getOrderNo());


        moduleService.saveOrUpdate(module);
        return "update";
    }


    /**
     * 删除部门
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
        String ids[] = model.getId().split(", ");

        // 调用业务方法实现批量删除
        moduleService.delete(Module.class, ids);
        return "delete";
    }
}
