# 用户模块
##功能一：用户注册功能
1. 可以先判断登录名是否已经存在
2. 要给密码使用MD5进行加密操作
	
	
## 功能二：用户登录功能
1. 登录功能要注意需要先给密码加密后，再进行查询
	- 密码加密后再查询
	- 用户的状态必须是1，字符串类型的
	
## 功能三：用户退出功能
1. 把用户信息从HttpSession中清除


# 客户模块 

## 功能一：查询所有客户功能
1. 数据字典表的引入
	- 数据字典表的作用：规范开发中数据的写法
	- 字段表与客户表是一对多的关系
	- 修改客户表，添加外键（使用SQLyog进行修改）
	
2. 创建字典表的实体和映射的配置文件
	- 编写字典表的JavaBean和映射的配置文件
	- 修改Customer的JavaBean，因为是多方，需要把外键字段换成字典对象
	- 修改Customer.hbm.xml的配置文件，配置多对一

3. 分页查询所有的客户功能实现

![](../pictures/01-字典表.bmp)
	
## 功能二：按条件查询所有的客户
1. 使用异步的方式加载客户级别和客户的来源
	- 前端使用JQuery的ajax技术
	- 后端使用fastjson的jar包
		- 导入fastjson的开发jar包fastjson-1.2.8.jar
		    - `String s = JSON.toJSONString(集合)`
			- `String s = JSON.toJSONString(对象)`
		
		* 如果List集合中存入相同引用的对象
			- fastjson默认的情况下是进行循环检测的，去除掉死循环调用的方式
			- 可以使用`JSON.toJSONString(p,SerializerFeature.DisableCircularReferenceDetect)` 去除循环检测，但是就会出现死循环的效果
			- 最后可以使用注解：`@JSONField(serialize=false)`对指定的属性不转换成json
2. 异步获取客户级别
	- ajax的代码
	```js
	var url = "${pageContext.request.contextPath }/dict_findByCode.action";
	var param = {"dict_type_code":"006"};
	$.post(url,param,function(data){
		$(data).each(function(){
			var id = "${model.level.dict_id}";
			if(id == this.dict_id){
				$("#levelId").append("<option value='"+this.dict_id+"' selected>"+this.dict_item_name+"</option>");
			}else{
				$("#levelId").append("<option value='"+this.dict_id+"'>"+this.dict_item_name+"</option>");
			}
		});
	},"json");
    ```
	- Action的代码
	
	```java
	public String findByCode(){
		List<Dict> list = dictService.findByCode(dict.getDict_type_code());
		String jsonString = FastJsonUtil.toJSONString(list);
		HttpServletResponse response = ServletActionContext.getResponse();
		FastJsonUtil.write_json(response, jsonString);
		return NONE;
	}
    ```
		
	- CustomerAction的分页查询的代码
	
	```java
	public String findByPage(){
		// 调用service业务层
		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class);
		// 拼接查询的条件
		String name = customer.getCust_name();
		if(name != null && !name.trim().isEmpty()){
			criteria.add(Restrictions.like("cust_name", "%"+name+"%"));
		}
		
		// System.out.println(customer.getLevel().getDict_type_code());
		Dict level = customer.getLevel();
		if(level != null && !level.getDict_id().trim().isEmpty()){
			criteria.add(Restrictions.eq("level.dict_id", level.getDict_id()));
		}
		
		Dict source = customer.getSource();
		if(source != null && !source.getDict_id().trim().isEmpty()){
			criteria.add(Restrictions.eq("source.dict_id", source.getDict_id()));
		}
		
		// 查询
		PageBean<Customer> page = customerService.findByPage(pageCode,pageSize,criteria);
		// 压栈
		ValueStack vs = ActionContext.getContext().getValueStack();
		// 栈顶是map<"page",page对象>
		vs.set("page", page);
		vs.set("cust_name", name);
		return "page";
	}
    ```
   
## 功能三：添加客户功能（含有文件上传功能）
1. 跳转到客户的添加页面，需要通过ajax来显示客户的级别，客户的来源和客户的行业。
2. 添加文件上传的选择项
3. 客户端三个注意事项
	- `method="post"`
	- `enctype="multipart/form-data"`
	- `<input type="file" name="myfile">`
	
4. Struts2框架的使用拦截器完成了文件上传，并且底层使用也是FileUpload开源的组件。
	- 提供 FileUpload 拦截器，用于解析 multipart/form-data 编码格式请求，解析上传文件的内容 
	- fileUpload拦截器 默认在 defaultStack 栈中， 默认会执行的 
		
	- 在Action中编写文件上传，需要定义三个属性
		- 文件类型File ,属性名与表单中file的name属性名一致.
		- 字符串类型String , 属性名：前段是name属性名一致 + ContentType;
		- 字符串类型String , 属性名：前段是name属性名一致+FileName;
			
		- 最后需要为上述的三个属性提供set方法。
		- 可以通过FileUtils提供 copyFile 进行文件复制，将上传文件 保存到服务器端 
	
5. 文件上传中存在的问题
	- 先配置input逻辑视图
	- 在页面中显示错误信息
		
	- 文件上传的总大小默认值是2M，如果超过了2M，程序会报出异常。可以使用<s:actionError>来查看具体信息！
		- 解决总大小的设置，找到常量：
			- `struts.multipart.parser=jakarta`：默认文件上传解析器，就是FileUpload组件
			- `struts.multipart.saveDir=`：文件上传的临时文件存储目录
			- `struts.multipart.maxSize=2097152`：文件上传的最大值（总大小），默认是2M
			
			- 可以在struts.xml中设置常量，修改文件上传的默认总大小！！！
				- `<constant name="struts.multipart.maxSize" value="5000000"></constant>`
	
6. 还可以通过配置拦截器来设置文件上传的一些属性
	- 先在<action>标签中引入文件上传的拦截器
	```xml
	<interceptor-ref name="defaultStack">
		<!-- 设置单个上传文件的大小 -->
		<param name="fileUpload.maximumSize">2097152</param>
		<!-- 设置扩展名 -->
		<param name="fileUpload.allowedExtensions">.txt</param>
	</interceptor-ref>
    ```
	
	
## 功能四：修改客户的功能
1. 先通过客户的主键查询出客户的详细信息，显示到修改的页面上
	- 要把客户的主键和上传文件的路径使用隐藏域保存起来
	
2. 修改客户的信息
	- 如果用户新上传了文件，删除旧的文件，上传新的文件。
	- 如果用户没有上传新文件，正常更新。
	
	
## 功能五：删除客户的功能
1. 删除上传的文件后，再删除客户信息。


# 抽取BaseAction的功能
1. Action需要完成分页的代码，需要接收pageCode和pageSize的请求参数，可以编写BaseAction用来接收分页的请求参数
```java
private Integer pageCode = 1;
public void setPageCode(Integer pageCode) {
	if(pageCode == null){
		pageCode = 1;
	}
	this.pageCode = pageCode;
}
public Integer getPageCode() {
	return pageCode;
}
		
// 每页显示的数据的条数
private Integer pageSize = 2;
public void setPageSize(Integer pageSize) {
	this.pageSize = pageSize;
}
public Integer getPageSize() {
	return pageSize;
}
	
public void setVs(String key,Object obj){
	ActionContext.getContext().getValueStack().set(key, obj);
}
		
public void pushVs(Object obj){
	ActionContext.getContext().getValueStack().push(obj);
}
```
	
	
# 联系人模块 
	
## 功能一：查询联系人功能
1. 分页显示所有的联系人的数据
	
## 功能二：添加联系人功能
1. 自己完成
	
	
## 功能三：修改联系人功能	
1. 自己完成
	
----------
	
## 功能四：删除联系人功能
1. 自己完成

# 客户拜访模块 
	
## 功能一：搭建客户拜访表的开发环境（导入资料中的拜访客户的SQL语句）
1. 客户关系拜访表是该系统的用户和客户之间的关系建立表
	- 用户可以拜访多个客户
	- 客户也可以被多个用户所拜访
	- 所以：用户和客户之间应该是多对多的关系，那么客户拜访表就是用户和客户的中间表。
	- 正常的情况下，在用户和客户中添加set集合，在映射的配置文件中配置<set>标签即可。
	- 但是现在客户拜访中间表中存在其他的字段，默认的情况下，中间表只能维护外键。而不能维护其他的字段。所以需要把一对多拆开成两个一对多。
	
2. 用户与客户拜访表是一对多的关系
3. 客户与客户拜访表是一对多的关系
4. 创建客户拜访表的实体类和映射配置文件
5. 编写客户拜访的Action等类和完成配置
	- 先开启注解的扫描
		- `<context:component-scan base-package="com.itheima"/>`
		
	- Action编写`（@RestController(value="visitAction") @Scope(value="prototype")）`
	- Service编写`（@Service(value="visitService") @Transactional）`
	- Dao编写（@Repository(value="visitDao")）
		- 重点是dao中注入SessionFactory对象
		```java
		@Resource(name="sessionFactory")
		public void sSessionFactory(SessionFactory sessionFactory){
			// 重点的代码
			super.setSessionFactory(sessionFactory);
		}
        ```
	
## 功能二：客户拜访列表查询功能
1. 先导入客户拜访的页面
	- 在资料中的（visit文件夹和jquery文件夹）
		- visit文件夹复制到jsp的目录下
		- jquery文件夹复制到WebContent目录下
	
2. 查询我的客户拜访记录
	- 登录的用户，点击客户拜访列表，查询该用户下的所有的拜访记录
	- 通过用户的主键查询该用户下的所有的拜访记录
	
	
## 功能三：新增客户拜访记录功能
1. 点击新增客户拜访功能菜单，跳转到新增页面，输入信息，保存数据
	- 从HttpSession中获取到用户的信息，设置到拜访记录中，保存到数据库中
	
	
## 功能四：按条件查询客户信息列表功能
1. 修改list.jsp的页面，添加开始和结束日期的选项
```html
<TD>拜访时间：</TD>
<TD>
	<INPUT class=textbox id="beginDate" style="WIDTH: 80px" maxLength=50 name="beginDate">
	至
	<INPUT class=textbox id="endDate" style="WIDTH: 80px" maxLength=50 name="endDate">
</TD>
```
	
	
# 统计分析模块 
	
## 功能一：客户来源统计
1. 想要统计客户的来源，即该来源下有多少个客户
	- SQL语句：`SELECT d.dict_item_name,COUNT(*) FROM base_dict d,cst_customer c WHERE d.dict_id = c.cust_source GROUP BY d.dict_id;`
	- HQL语句：`String hql = "select c.source.dict_item_name,COUNT(*) from Customer c inner join c.source GROUP BY c.source";`
	
	
## 用户登录的拦截器 
### 用户登录的拦截器功能实现
1. 功能：如果用户没有登录，是不能操作后台的功能的！！
2. 代码如下
```java
public class UserInterceptor extends MethodFilterInterceptor{

	private static final long serialVersionUID = 335018670739692955L;
	
	/**
	 * 进行拦截的方法
	 */
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		// 获取session对象
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("existUser");
		if(user == null){
			// 说明，没有登录，后面就不会执行了
			return "login";
		}
		return invocation.invoke();
	}
}
```
	
3. 配置如下
```xml
<interceptors>
	<interceptor name="UserInterceptor" class="com.itheima.web.interceptor.UserInterceptor"/>
</interceptors>
<interceptor-ref name="UserInterceptor">
	<!-- login方法不拦截 -->
	<param name="excludeMethods">login</param>
</interceptor-ref>
<interceptor-ref name="defaultStack"/>
```
