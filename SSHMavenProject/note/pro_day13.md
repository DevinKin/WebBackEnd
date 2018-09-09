# FreeMarker
1. 一套模板技术,可以生成html代码,从而实现页面静态化

2.  使用freeMarker制造出代码生成的模板,再结合反射技术,生成代码

3. FreeMarker文件的扩展名是ftl结尾


## 常用用法
1. ${package}
    - package为变量,为map的key,${}后可以直接显示内容
2. ${.now}
    - 当前时间
3. className=dept

4. ${className?cap_first}
    - 首字母大写

5. ${className?uncap_first}
    - 首字母大写
    
6. `<#if (list.title?length > 15)`
    - 字符串长度

7. ${package?replace(".","/")}
    - 替换

8. 特殊字符替换
    - `$`:`${'$'}`
    - `#`:`${'#'}`
    
## FreeMarker不会自动进行类型的转换
1. 常见的转换
    - String to int:`?eval`
    - int to String:`?c`
    
## for循环
1. properties:是准备好的Map对象集合
2. 表结构-元数据(MetaData)
```injectedfreemarker
<#list properties as pro>
    private ${pro.proType} ${proproName};
</#list>
```

## 定义变量，显示变量
1. `<#assign x=pro.proName?index_of("createBy")>`
2. `${x}`

## 布尔类型转换为字符串显示
1. `<#assign x=pro.primary?string('yes', 'no')>`
2. `${x}`

## 布尔判断
```injectedfreemarker
	<#if pro.primary>
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	</#if>
	<#if pro.primary?string('yes', 'no')=="no">
	<#if "createBy,createDept,createTime,updateBy,updateTime"?index_of(pro.proName) == -1>
	public ${pro.proType} get${pro.proName?cap_first}() {
		return this.${pro.proName};
	}
	public void set${pro.proName?cap_first}(${pro.proType} ${pro.proName}) {
		this.${pro.proName} = ${pro.proName};
	}	
	</#if>
	</#if>	
```

## 判断空串
```injectedfreemarker
   <#if pro.proComment != "">//${pro.proComment}</#if>
```

## 判断对象是否存在(null)
1. 经常会用到，如果对象 != null 则xxxx，在freemarker中表达比较奇怪，例如判断 target 是否为null，如果不为 nll 则做xxx动作
```injectedfreemarker
   <#if target??>
       xxxx
   </#if>
   (目标变量后面连续两个??)
```   
    
## 字符串或数字比较
1. java里标准字符串比较需要 .equals() 方法，在freemarkder中进行了简化，字符串的比较方法和数字做到完全一样
```injectedfreemarker
   <#if str == "success">
       xxx
   </#if>
   
   <#if str !== "error">
       xxx
   </#if>
```

## 判断是否包含子串：
1. pro.proName为子串
```injectedfreemarker
   <#if "CreateBy,CreateDept,CreateTime"?index_of(pro.proName)==-1>
```

## 高级应用：宏
1. 业务：实现每个文件引入版本
```injectedfreemarker
   CopyRight.ftl
   <#macro CopyRight> 
   /**
    * @Description:	${ className }Service接口
    * @Author:			传智播客 java学院	传智宋江
    * @Company:		http://java.itcast.cn
    * @CreateDate:		${.now}
    */
    </#macro>
``` 
2. 调用
```injectedfreemarker
   <#import "CopyRight.ftl" as my>
   <@my.CopyRight/>
``` 



