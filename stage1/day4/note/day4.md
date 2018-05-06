# jquery
1. jquery就是js的一个类库，对常用的方法和对象进行封装，方便使用

## jquery和html的整合
1. jquery是单独的js文件，通过script标签的src属性导入即可

## 获取一个jquery对象
1. $("选择器")
2. jQuery("选择器")

## dom对象和jquery对象之间的转换
1. dom对象==>jquery对象
    - $(dom对象)

2. jquery对象==>dom对象
    - 方式1：jquery对象[index]
    - 方式2：jquery对象.get(index)

## 页面加载
1. js
    - window.onload=function() {}，这个方式在页面中只能使用一次
2. jquery：在一个页面可以使用多次
    - 方式1： $(function() {...})
    - 方式2： $(document).ready(function(){});

## 派发事件
1. ${"选择器"}.click(function(){...})，统一去掉on
2. 等价于原生js中的 dom对象.onclick

### 掌握的事件
1. focus
2. blur
3. submit
4. change
5. click

## jquery中的效果
1. 隐藏或展示
    - show(毫秒数)
        - slow
        - normal
        - fast 
    - hide(毫秒数)
    - toggle(毫秒数):切换
2. 滑入或滑出
    - slideDown(毫秒数):向下滑入
    - slideUp(毫秒数):向上滑出
    - slideToggle(毫秒数):毫秒数
3. 淡入或淡出
    - fadeIn(毫秒数)
    - fadeOut(毫秒数)
    - fadeToggle(毫秒数):切换

## 选择器
### 基本选择器
1. id选择器
    - $("#id值")
2. 类选择器
    - $(".class值")
3. 标签选择器
    - $("标签名")
4. 所有选择器
    - $("*")
5. 获取多个选择器,用逗号分隔
    - $("#id值, .class值")

### 层级选择器
1. 后代选择器
    - $("a b")：a的所有b后代
    - $("a>b")：a的所有b孩子
    - $("a+b")：a的下一个兄弟
    - $("a~b")：a的所有兄弟

### 基本过滤选择器
1. $("a:first")：第一个a
2. $("a:last")：最后一个a
3. $("a:odd")：索引奇数
4. $("a:even")：索引偶数
5. $("a:eq(index)")：指定索引
6. $("a:gt(index)")：`>`
7. $("a:lt(index)")：`<`

### 内容过滤选择器
1. $("a:has("选择器"))：a包含指定选择器的元素

### 可见过滤选择器
1. $("a:hidden")：在页面不展示的元素，一般指`input type="hidden"`和样式中`diplay:none`
2. $("a:visible")：在页面可见的元素

### 属性过滤选择器
1. $("a[属性名]")
2. $("a[属性名='值']")


### 表单过滤选择器
1. $("#form :input")：所有的表单子标签

### 表单对象属性过滤选择器
1. $("":enabled)：可用的
2. $("":disabled)：不可用的
3. $("":checked)：选中的(针对于单选框和复选框)
4. $("":seleced)：选中的(针对于下拉选择框)



## jquery的文档操作
1. 内部插入
    - append
    - prepend
    - appendTo
    - prependTo
2. 外部插入
    - after
    - before
    - insertAfter
    - insertBefore
3. 删除
    - empty:清空元素
    - remove:删除元素

# 案例1-弹出广告
## 技术
1. 定时器
2. jQuery
## 步骤分析
1. 页面加载成功后，$(funciton() {...})，开启一个定时器，setTimeout()
2. 编写展示广告的方法
    - 获取div，然后编写效果方法 
    - 设置另一个定时器隐藏广告
3. 编写隐藏广告的方法
    - 获取div，然后编写效果方法


# 案例2-隔行换色
## 技术分析
1. 页面加载成功
2. 获取所有的奇数行，添加一个css

## 属性和css操作总结
1. 对属性的操作:    
    - attr()：设置或者获取元素的属性
        - 方式1：attr("属性名称")
        - 方式2：attr("属性名称","值“)
        - 方式3：attr({"属性1":"值1", "属性2":"值2”})
    - removeAttr():移除指定的属性
    - prop()：设置或者获取元素的属性
        - 方式1：prop("属性名称")
        - 方式2：prop("属性名称","值“)
        - 方式3：prop({"属性1":"值1", "属性2":"值2”})
    - removeProp():移除指定的属性
2. 添加删除class属性:
    - addClass("指定样式值")
    - removeClass("指定样式值")

3. 对css操作
    - 方式1：css("属性名")
    - 方式2：css("属性名","值")
    - 方式3：css({"":"", "":""})

4. 获取元素的尺寸
    - width()
    - height()


# 案例3-全选和全部选
1. 将内容中复选框的选择状态与上面内容复选框一致
2. 确定事件，复选框的单击事件
3. 函数中
    - 获取该复选框的选中状态，attr|prop
    - 获取所有的复选框修改他们的状态
    - attr获取不了checked属性
4. 注意：若jquery版本>1.6，统一使用prop操作元素的属性


# 案例4-省市联动
## 步骤分析
1. 确定事件，省份下拉选变化的时候：onchange
2. 编写函数
    - 获取当前省份的value值
    - 然后通过value值索引数组,获取该省份下的所有市区
    - 遍历数组，拼装成option元素，追加到市下拉选择框即可
    - 注意：每次改变的时候初始化市的值

## 技术
1. 遍历数组
    - 数组.each(function(){});
    - $.each(遍历的数组,function(){});
2. 设置或者而获取value属性
    - jquery对象.val()
    - jquery对象.val("...")

3. 设置和获取标签体的内容
    - html()：
        - 设置时：会把字符串解析
        - 获取时：获取html源码
        - `html("<option></option>");`：可设置标签
        - `html(");`：可获取内容及当中的标签
    - text()
        - 设置时：只会设置普通字符串
        - 获取时：获取只是页面展示的内容
    - xxx()
    - xxxxxx("...")
4. 创建一个元素
    - $("").html("<标签>")
    - $("").html("<标签></标签>")

# 案例5-左右选中
## 步骤分析
1. 确定事件：onclick
2. 编写函数：
    - 移动一个
    - 移动多个
    - 移动全部