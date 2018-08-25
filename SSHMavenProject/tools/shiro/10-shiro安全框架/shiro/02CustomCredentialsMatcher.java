package cn.itcast.jk.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import cn.itcast.util.Encrypt;

/**
 * @Description:
 * @Author:		传智播客 java学院	传智宋江
 * @Company:	http://java.itcast.cn
 * @CreateDate:	2014年12月30日
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {
	public boolean doCredentialsMatch(AuthenticationToken token,
			AuthenticationInfo info) {
		UsernamePasswordToken usertoken = (UsernamePasswordToken) token; 
		
		//注意token.getPassword()拿到的是一个char[]，不能直接用toString()，它底层实现不是我们想的直接字符串，只能强转
		Object tokenCredentials = Encrypt.md5(String.valueOf(usertoken.getPassword()),usertoken.getUsername());  
		Object accountCredentials = getCredentials(info);  

		//将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false  
		return equals(tokenCredentials, accountCredentials);  
	}
}
