package cn.devinkin.jk.action.sysadmin;

import cn.devinkin.jk.action.BaseAction;
import cn.devinkin.jk.domain.Dept;
import cn.devinkin.jk.domain.Role;
import cn.devinkin.jk.domain.User;
import cn.devinkin.jk.service.DeptService;
import cn.devinkin.jk.service.RoleService;
import cn.devinkin.jk.service.UserService;
import cn.devinkin.jk.utils.Page;
import com.opensymphony.xwork2.ModelDriven;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * 部门管理的Action
 * @author king
 */
public class UserAction extends BaseAction implements ModelDriven<User>{

    private User model = new User();

    @Override
    public User getModel() {
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

    // 注入UserService
    private UserService userService;
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    // 注入DeptService
    private DeptService deptService;
    public void setDeptService(DeptService deptService) {
        this.deptService = deptService;
    }
    private RoleService roleService;
    public void setRoleService(RoleService roleService) { this.roleService = roleService; }



    /**
     * 分页查询
     */
    public String list() throws Exception {
        userService.findPage("from User", page, User.class, null);

        // 设置分页的url地址
        page.setUrl("userAction_list");


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
        User user = userService.get(User.class, model.getId());
        // 放入栈顶
        super.push(user);
        return "toview";
    }


    /**
     * 进入新增页面
     */
    public String tocreate() throws Exception {

        // 调用业务方法
        // 查询所有的部门
        List<Dept> deptList = deptService.find("from Dept where state = 1", Dept.class, null);
        // 将查询的结果压入值栈中
        super.put("deptList", deptList);

        List<User> userList = userService.find("from User where state=1", User.class,null);
        super.put("userList", userList);

        // 页面跳转
        return "tocreate";
    }


    /**
     * 保存
     */
    public String insert() throws Exception {
        userService.saveOrUpdate(model);

        // 页面跳转
        return "alist";
    }


    /**
     * 进入修改页面
     */
    public String toupdate() throws Exception {
        // 1. 根据部门id，得到一个对象
        User user = userService.get(User.class, model.getId());

        // 2. 将这个对象放入值栈中
        super.push(user);

        // 3. 查询部们
        List<Dept> deptList = deptService.find("from Dept where state = 1", Dept.class, null);

        // 查询到的结果放到值栈中，放到context区域中
        super.put("deptList", deptList);
        return "toupdate";
    }


    /**
     * 保存修改的结果
     */
    public String update() throws Exception {
        // 调用业务
        User user = userService.get(User.class,model.getId());

        // 2. 设置修改的属性
        user.setDept(model.getDept());
        user.setUserName(model.getUserName());
        user.setState(model.getState());

        userService.saveOrUpdate(user);
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
        userService.delete(User.class, ids);
        return "delete";
    }


    /**
     * 角色分配显示页面
     * @return
     * @throws Exception
     */
    public String torole() throws Exception {
        // 1. 根据id得到user对象
        User user = userService.get(User.class, model.getId());
        // 2. 将对象保存到值栈中
        super.push(user);
        // 3. 调用角色业务方法，得到角色列表
        List<Role> roles = roleService.find("from Role", Role.class, null);
        // 4. 将roles放入值栈中
        super.put("roleList", roles);
        // 5. 得到当前用户的角色列表
        Set<Role> roleSet = user.getRoles();
        StringBuilder sb = new StringBuilder();
        for (Role role : roleSet) {
            sb.append(role.getName()).append(",");
        }
        // 6. 当前用户的角色字符串放入值栈中
        super.put("userRoleStr", sb.toString());
        return "torole";
    }


    /**
     * 实现角色分配
     * @return
     * @throws Exception
     */
    public String role() throws Exception {
        // 1. 根据用户的id，得到用户对象
        User user = userService.get(User.class, model.getId());

        // 2. 有哪些角色，只要遍历roleIds
        // 当前选中的角色列表
        Set<Role> roles = new HashSet<Role>();
        for (String id : roleIds) {
            Role role = roleService.get(Role.class, id);
            roles.add(role);
        }
        // 设置用户与角色列表之间的关系
        user.setRoles(roles);

        // 4. 保存用户与角色关系到数据库中，影响的是用户角色中间表
        userService.saveOrUpdate(user);
        return "role";
    }

    // 模型驱动，保存选中的角色列表
    private String[] roleIds;

    public void setRoleIds(String[] roleIds) {
        this.roleIds = roleIds;
    }
}
