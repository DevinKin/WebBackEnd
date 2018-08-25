

URL权限过滤




shiro安全框架：
+ehcache缓存

1、引入依赖pom.xml

		<!-- Apache Shiro 权限架构 -->
		<dependency>
			<groupId>org.apache.shiro</groupId>
			<artifactId>shiro-all</artifactId>
			<version>1.2.3</version>
		</dependency>

2、核心filter，一个filter相当于10个filter；web.xml

	注意：shiro的filter必须在struts2的filter之前，否则action无法创建

    <!-- Shiro Security filter  filter-name这个名字的值将来还会在spring中用到  -->
    <filter>
        <filter-name>shiroFilter</filter-name>
        <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
        <init-param>
            <param-name>targetFilterLifecycle</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>shiroFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>


3、在spring applicationContext.xml中记载shiro配置文件,放在事务管理器之前配置
<aop:aspectj-autoproxy proxy-target-class="true" />

同时添加专门配置shiro的配置文件
<import resource="spring/applicationContext-shiro.xml"/>

和ehcache支持ehcache-shiro.xml

<ehcache updateCheck="false" name="shiroCache">

    <defaultCache
            maxElementsInMemory="10000"
            eternal="false"
            timeToIdleSeconds="120"
            timeToLiveSeconds="120"
            overflowToDisk="false"
            diskPersistent="false"
            diskExpiryThreadIntervalSeconds="120"
            />
</ehcache>


4、applicationContext-shiro.xml，配置校验的策略，哪些校验，哪些放行

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"  
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"       
	xmlns:p="http://www.springframework.org/schema/p"  
	xmlns:context="http://www.springframework.org/schema/context"   
	xmlns:tx="http://www.springframework.org/schema/tx"  
	xmlns:aop="http://www.springframework.org/schema/aop"  
	xsi:schemaLocation="http://www.springframework.org/schema/beans    
	http://www.springframework.org/schema/beans/spring-beans.xsd    
	http://www.springframework.org/schema/aop    
	http://www.springframework.org/schema/aop/spring-aop.xsd    
	http://www.springframework.org/schema/tx    
	http://www.springframework.org/schema/tx/spring-tx.xsd    
	http://www.springframework.org/schema/context    
	http://www.springframework.org/schema/context/spring-context.xsd">
	
	<description>Shiro的配置</description>
	
	<!-- SecurityManager配置 -->
	<!-- 配置Realm域 -->
	<!-- 密码比较器 -->
	<!-- 代理如何生成？ 用工厂来生成Shiro的相关过滤器-->
	<!-- 配置缓存：ehcache缓存 -->
	<!-- 安全管理 -->
    <bean id="securityManager" class="org.apache.shiro.web.mgt.DefaultWebSecurityManager">
        <!-- Single realm app.  If you have multiple realms, use the 'realms' property instead. -->
        <property name="realm" ref="authRealm"/><!-- 引用自定义的realm -->
        <!-- 缓存 -->
        <property name="cacheManager" ref="shiroEhcacheManager"/>
    </bean>

    <!-- 自定义权限认证 -->
    <bean id="authRealm" class="cn.itcast.jk.shiro.AuthRealm">
		<property name="userService" ref="userService"/>
		<!-- 自定义密码加密算法  -->
		<property name="credentialsMatcher" ref="passwordMatcher"/>
	</bean>
	
	<!-- 设置密码加密策略 md5hash -->
	<bean id="passwordMatcher" class="cn.itcast.jk.shiro.CustomCredentialsMatcher"/>

    <!-- filter-name这个名字的值来自于web.xml中filter的名字 -->
    <bean id="shiroFilter" class="org.apache.shiro.spring.web.ShiroFilterFactoryBean">
        <property name="securityManager" ref="securityManager"/>
        <!--登录页面  -->
        <property name="loginUrl" value="/index.jsp"></property>
        <!-- 登录成功后 -->      
        <property name="successUrl" value="/home.action"></property>
        <property name="filterChainDefinitions">
            <!-- /**代表下面的多级目录也过滤 -->
            <value>
				/index.jsp* = anon
				/home* = anon
				/sysadmin/login/login.jsp* = anon
				/sysadmin/login/logout.jsp* = anon
				/login* = anon
				/logout* = anon
				/components/** = anon
				/css/** = anon
				/images/** = anon
				/js/** = anon
				/make/** = anon
				/skin/** = anon
				/stat/** = anon
				/ufiles/** = anon
				/validator/** = anon
				/resource/** = anon
				/** = authc
				/*.* = authc
            </value>
        </property>
    </bean>

    <!-- 用户授权/认证信息Cache, 采用EhCache  缓存 -->
    <bean id="shiroEhcacheManager" class="org.apache.shiro.cache.ehcache.EhCacheManager">
        <property name="cacheManagerConfigFile" value="classpath:ehcache-shiro.xml"/>
    </bean>

    <!-- 保证实现了Shiro内部lifecycle函数的bean执行 -->
    <bean id="lifecycleBeanPostProcessor" class="org.apache.shiro.spring.LifecycleBeanPostProcessor"/>

    <!-- 生成代理，通过代理进行控制 -->
    <bean class="org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator"
          depends-on="lifecycleBeanPostProcessor">
        <property name="proxyTargetClass" value="true"/>
    </bean>
    
    <!-- 安全管理器 -->
    <bean class="org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor">
        <property name="securityManager" ref="securityManager"/>
    </bean>
	
</beans>


5、自定义realm	AuthRealm

在认证、授权内部实现机制中都有提到，最终处理都将交给Realm进行处理。
因为在Shiro中，最终是通过Realm来获取应用程序中的用户、角色及权限信息的。
通常情况下，在Realm中会直接从我们的数据源中获取Shiro需要的验证信息。可以说，Realm是专用于安全框架的DAO.

public class AuthRealm extends AuthorizingRealm{
	private UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	//授权
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("授权");
		//获取当前用户
		User user = (User)principals.fromRealm(getName()).iterator().next();
		//得到权限字符串
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		Set<Role> roles = user.getRoles();
		List<String> list = new ArrayList();
		for(Role role :roles){
			Set<Module> modules = role.getModules();
			for(Module m:modules){
				if(m.getCtype()==0){
					//说明是主菜单
					list.add(m.getCpermission());
				}
			}
		}

		info.addStringPermissions(list);
		return info;
	}
	//认证  登录
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		System.out.println("认证");
		UsernamePasswordToken upToken = (UsernamePasswordToken)token;
		
		User user = userService.findUserByName(upToken.getUsername());
		if(user==null){
			return null;
		}else{
			AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
			return info;
		}
		
	}

}


6、修改传统登录为shiro登录

package cn.itcast.jk.action;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;

import cn.itcast.common.SysConstant;
import cn.itcast.jk.service.UserService;

/**
 * @Description: 登录和推出类
 * @Author:		传智播客 java学院	宋江
 * @Company:	http://java.itcast.cn
 * @CreateDate:	2014年10月31日
 */
public class LoginAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;

	private UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public String login() throws Exception {	
		/*
		 * shiro登录方式：根据用户名获取密码，密码为null非法用户；有密码检查是否用户填写的密码
		 * 登录成功后无需往httpsession中存放当前用户，这样就跟web容器绑定，关联太紧密；它自己创建
		 * subject对象，实现自己的session。这个跟web容器脱离，实现松耦合。
		 */

		//调用shiro判断当前用户是否是系统用户
		Subject subject = SecurityUtils.getSubject();	//得到当前用户
		//shiro是将用户录入的登录名和密码（未加密）封装到token对象中
		UsernamePasswordToken token = new UsernamePasswordToken(userName,password);
		
		try{
			subject.login(token);	//自动调用AuthRealm.doGetAuthenticationInfo
			
			//写seesion，保存当前user对象
			User curUser = (User)subject.getPrincipal();			//从shiro中获取当前用户
			System.out.println(curUser.getDept().getDeptName());	//让懒加载变成立即加载
			Set<Role> roles = curUser.getRoles();
			for(Role role :roles){
				Set<Module> moduless =  role.getModules();
				for(Module m :moduless)
				   System.out.println(m.getName());
			}
			session.put(SysConstant.CURRENT_USER_INFO, curUser);	//Principal 当前用户对象
		}catch(Exception ex){
			super.put("errorInfo","用户名密码错误，请重新填写!");
			ex.printStackTrace();
			
			return "login";
		}
		return SUCCESS;
	}
	
	public String logout(){
		session.remove(SysConstant.CURRENT_USER_INFO);		//删除session
		return "logout";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}



7、授权(另一种加载数据的思想   可省略因为在登录时已加载)
根据用户查询出角色对应的权限，并返回权限串

-hql，service

	public List<String> getPermission(String userName) {
		List<String> _list = new ArrayList<String>();
		
		//用户，角色，权限，两级多对多，使用left join关联实现
		String hql = "select p from User as u left join u.roles as r left join r.modules as p where u.username='"+userName+"'";
		List<Module> moduleList = baseDao.find(hql, Module.class, null);
		
		for(Module m : moduleList){
			if(m!=null){	//观察hibernate实现的SQL，会多出一条Null记录
				_list.add(m.getName());
			}
		}
		
		return _list;
	}



在realm中进行授权userService.getPermission

	//授权
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		log.info("执行授权...");
		
		//获取登录用户的权限，配合jsp页面中的shiro标签进行控制
		User curUser = (User) principals.fromRealm(getName()).iterator().next();  
		String userName = curUser.getUsername();
		List<String> sList = userService.getPermission(userName );
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		for(String permission : sList){
			//设置当前用户的权限
			authorizationInfo.addStringPermission(permission);
		}

		return authorizationInfo;
	}


8、页面使用shiro标签，/home/title.jsp 主菜单


<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>


			    		<shiro:hasPermission name="sysadmin">
			    		<span id="topmenu" onclick="toModule('sysadmin');">系统管理</span>
			    		</shiro:hasPermission>

