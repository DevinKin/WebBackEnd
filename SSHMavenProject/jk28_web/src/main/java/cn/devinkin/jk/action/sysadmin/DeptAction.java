package cn.devinkin.jk.action.sysadmin;

import cn.devinkin.jk.action.BaseAction;
import cn.devinkin.jk.domain.Dept;
import cn.devinkin.jk.service.sysadmin.DeptService;
import cn.devinkin.jk.utils.Page;
import com.opensymphony.xwork2.ModelDriven;

import java.util.List;


/**
 * 部门管理的Action
 * @author king
 */
public class DeptAction extends BaseAction implements ModelDriven<Dept>{

    private Dept model = new Dept();

    @Override
    public Dept getModel() {
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

    // 注入DeptService
    private DeptService deptService;

    public void setDeptService(DeptService deptService) {
        this.deptService = deptService;
    }


    /**
     * 分页查询
     */
    public String list() throws Exception {
        deptService.findPage("from Dept", page, Dept.class, null);

        // 设置分页的url地址
        page.setUrl("deptAction_list");


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
        Dept dept = deptService.get(Dept.class, model.getId());
        // 放入栈顶
        super.push(dept);
        return "toview";
    }


    /**
     * 进入新增页面
     */
    public String tocreate() throws Exception {

        // 调用业务方法，父部们是启动的部门
        List<Dept> deptList = deptService.find("from Dept where state = 1", Dept.class, null);
        // 将查询的结果压入值栈中
        super.put("deptList", deptList);
        // 页面跳转
        return "tocreate";
    }


    /**
     * 保存
     */
    public String insert() throws Exception {
        deptService.saveOrUpdate(model);

        // 页面跳转
        return "alist";
    }


    /**
     * 进入修改页面
     */
    public String toupdate() throws Exception {
        // 1. 根据部门id，得到一个对象
        Dept dept = deptService.get(Dept.class, model.getId());

        // 2. 将这个对象放入值栈中
        super.push(dept);

        // 3. 查询父部们
        List<Dept> deptList = deptService.find("from Dept where state = 1", Dept.class, null);

        // 将当前放在值栈中要修改的对象，从部门列表中清除，目的是为了让下拉列表中没有当前在值栈中的这个对象
        deptList.remove(dept);

        // 查询到的结果放到值栈中，放到context区域中
        super.put("deptList", deptList);
        return "toupdate";
    }


    /**
     * 保存修改的结果
     */
    public String update() throws Exception {
        // 调用业务
        Dept dept = deptService.get(Dept.class,model.getId());

        // 2. 设置修改的属性
        dept.setParent(model.getParent());
        dept.setDeptName(model.getDeptName());

        deptService.saveOrUpdate(dept);
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
        deptService.delete(Dept.class, ids);
        return "delete";
    }
}
