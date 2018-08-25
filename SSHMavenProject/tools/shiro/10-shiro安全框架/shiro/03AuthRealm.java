package cn.itcast.jk.shiro;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import cn.itcast.jk.domain.User;
import cn.itcast.jk.service.UserService;


public class AuthRealm extends AuthorizingRealm{
	private static Logger log = Logger.getLogger(AuthRealm.class);
	UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	//授权
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		log.info("执行授权...");
		
		//获取登录用户的权限，配合jsp页面中的shiro标签进行控制
//		User curUser = (User) principals.fromRealm(getName()).iterator().next();  
//		String userName = curUser.getUserName();
//		List<String> sList = userService.getPermission(userName );
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//		for(String permission : sList){
//			//设置当前用户的权限
//			authorizationInfo.addStringPermission(permission);
//		}

		return authorizationInfo;
	}

	//认证
	/*
	 * shiro规则，按用户名查找，如果没找到返回null，如果查找到返回密码，shiro自动和调用subject.login()中的密码值进行比较
	 * 密码一致，登录成功；密码不一致，密码错误。
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		log.info("执行认证...");

		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;
		if(usernamePasswordToken.getPassword()==null){	//从logout退出时
			return null;
		}
		User findUser = new User(usernamePasswordToken.getUsername(), String.valueOf(usernamePasswordToken.getPassword()));
		User user = userService.login(findUser);
		if(user == null){	//该用户不存在
			return null;
		}else{				//用户存在
			//SecurityUtils.getSubject().getSession().setAttribute("authUser", user);
			
			//返回密码，自动比较密码
			AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user,user.getPassword(),getName());
			
			return authenticationInfo;
		}
	}

}