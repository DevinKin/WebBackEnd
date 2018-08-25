package cn.devinkin.jk.action.sysadmin;

import cn.devinkin.jk.action.BaseAction;
import cn.devinkin.jk.domain.Dept;
import cn.devinkin.jk.domain.Module;
import cn.devinkin.jk.domain.Role;
import cn.devinkin.jk.exception.SysException;
import cn.devinkin.jk.service.DeptService;
import cn.devinkin.jk.service.ModuleService;
import cn.devinkin.jk.service.RoleService;
import cn.devinkin.jk.utils.Page;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * 部门管理的Action
 *
 * @author king
 */
public class RoleAction extends BaseAction implements ModelDriven<Role> {

    private Role model = new Role();

    @Override
    public Role getModel() {
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

    // 注入RoleService
    private RoleService roleService;

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    // 注入DeptService
    private DeptService deptService;

    public void setDeptService(DeptService deptService) {
        this.deptService = deptService;
    }

    // 注入ModuleService
    private ModuleService moduleService;

    public void setModuleService(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    /**
     * 分页查询
     */
    public String list() throws Exception {
        roleService.findPage("from Role", page, Role.class, null);

        // 设置分页的url地址
        page.setUrl("roleAction_list");


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
        try {
            Role role = roleService.get(Role.class, model.getId());
            // 放入栈顶
            super.push(role);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SysException("对不起，你有病，请先选中再操作!");
        }
        return "toview";
    }


    /**
     * 进入新增页面
     */
    public String tocreate() throws Exception {
        // 页面跳转
        return "tocreate";
    }


    /**
     * 保存
     */
    public String insert() throws Exception {
        roleService.saveOrUpdate(model);

        // 页面跳转
        return "alist";
    }


    /**
     * 进入修改页面
     */
    public String toupdate() throws Exception {
        // 1. 根据部门id，得到一个对象
        Role role = roleService.get(Role.class, model.getId());

        // 2. 将这个对象放入值栈中
        super.push(role);

        return "toupdate";
    }


    /**
     * 保存修改的结果
     */
    public String update() throws Exception {
        // 调用业务
        Role role = roleService.get(Role.class, model.getId());

        // 2. 设置修改的属性
        role.setName(model.getName());
        role.setRemark(model.getRemark());


        roleService.saveOrUpdate(role);
        return "update";
    }


    /**
     * 删除部门
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
        String ids[] = model.getId().split(", ");

        // 调用业务方法实现批量删除
        roleService.delete(Role.class, ids);
        return "delete";
    }


    /**
     * 进入模块分配页面
     */
    public String tomodule() throws Exception {
        // 1. 根据角色id，得到角色对象
        Role role = roleService.get(Role.class, model.getId());

        // 2. 角色保存到值栈中
        super.push(role);

        // 3. 跳页面
        return "tomodule";
    }


    /**
     * 为了使用zTree树，就要组织好zTree树所使用的json数据
     * json数据结构如下：
     * [{id:"模块id", pId:"父节点(模块)的id", name:"模块名",checked:"true|false"},{id:"模块id", pId:"父节点(模块)的id", name:"模块名",checked:"true|false"}]
     * <p>
     * 茶用的json插件：json-lib fastjson struts-json-plugin-xxx.jar
     * 如何输出：借助于response对象输出数据
     */
    public String roleModuleJsonStr() throws Exception {
        // 1. 根据角色id，获得角色对象
        Role role = roleService.get(Role.class, model.getId());

        // 2. 通过对象导航方式，加载出当前角色的模块列表
        Set<Module> moduleSet = role.getModules();

        // 3. 加载出所有的模块列表
        List<Module> moduleList = moduleService.find("from Module", Module.class, null);

        // 4. 组织json串
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int size = moduleList.size();
        for (Module module : moduleList) {
            size--;
            sb.append("{id:\"").append(module.getId());
            sb.append("\",pId:\"").append(module.getParentId());
            sb.append("\",name:\"").append(module.getName());
            sb.append("\",name:\"").append(module.getName());
            sb.append("\",checked:\"");
            if (moduleSet.contains(module)) {
                sb.append("true");
            } else {
                sb.append("false");
            }
            sb.append("\"}");
            if (size > 0) {
                sb.append(",");
            }
        }
        sb.append("]");

        // 5. 得到response对象
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");

        // 6. 使用response对象输出json串
        response.getWriter().write(sb.toString());

        return NONE;
    }


    /**
     * 保存当前角色的模块列表
     *
     * @return
     */
    public String module() {
        // 1. 通过id获取角色对象
        Role role = roleService.get(Role.class, model.getId());

        // 2. 选中的模块有哪些？
        String[] ids = moduleIds.split(",");

        // 加载出这些模块列表
        Set<Module> modules = new HashSet<Module>();
        if (ids != null && ids.length > 0) {
            for (String id : ids) {
                // 添加选中的模块放入模块列表中
                modules.add(moduleService.get(Module.class, id));
            }
        }

        // 3. 实现角色分配的新模块
        role.setModules(modules);
        // 4. 保存角色对象
        roleService.saveOrUpdate(role);

        // 5. 页面跳转
        return "alist";
    }

    // 接受模块id列表字符串
    private String moduleIds;

    public void setModuleIds(String moduleIds) {
        this.moduleIds = moduleIds;
    }
}
