package cn.devinkin.jk.shiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.devinkin.jk.domain.Module;
import cn.devinkin.jk.domain.Role;
import cn.devinkin.jk.domain.User;
import cn.devinkin.jk.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;


public class AuthRealm extends AuthorizingRealm {

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    //授权   当jsp页面出现Shiro标签时，就会执行授权方法
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
        System.out.println("授权");
        User user = (User) pc.fromRealm(this.getName()).iterator().next();//根据realm的名字去找对应的realm

        Set<Role> roles = user.getRoles();//对象导航
        List<String> permissions = new ArrayList<String>();
        for (Role role : roles) {
            //遍历每个角色
            Set<Module> modules = role.getModules();//得到每个角色下的模块列表
            for (Module m : modules) {
                permissions.add(m.getName());
            }
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissions);//添加用户的模块（权限）
        return info;
    }

    //认证   token 代表用户在界面输入的用户名和密码
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("认证");

        //1.向下转型
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;

        //2.调用业务方法，实现根据用户名查询
        String hql = "from User where userName=?";
        List<User> list = userService.find(hql, User.class, new String[]{upToken.getUsername()});
        if (list != null && list.size() > 0) {
            User user = list.get(0);
            AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
            return info;   //此处如果返回，就会立即进入到密码比较器
        }

        return null;//就会出现异常
    }

}
