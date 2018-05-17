# css
    - 层叠样式表
    - 作用
        - 渲染页面
        - 提高工作效率
    - 格式
        - 选择器{属性:值;属性1:值1;}
    - 后缀名
        - .css 独立的css样式文件
    - 与html元素的整合
        - 方式1：内联样式表，通过标签的style属性设置样式
            - style=""
        - 方式2：内部样式表，在当前页面中使用的样式
            - `<style></style>`
        - 方式3：外部样式表，有独立的css文件
            - `<link rel="stylesheet" href="css/1.css" type="text/css"></link>`
    - 选择器
        - id选择器
            - 要求：html元素必须有id属性且有值 `<xxx id="id1"></xxx>`
            - css中通过`#`引入，后面加上id的值`#id1{...}`
        - 类选择器
            - 要求：html元素必须有class属性且有值`<xxx class="cls1"></xxx>`
            - css中通过`.`引入，后面加上class的值`.cls1{...}`
        - 元素选择器
            - 直接用元素名引入即可`xxx{...}`
        - 派生的选择器
            - 属性的选择器
                - 要求：html元素必须有一个属性，不论属性是什么，但是必须有值`<xxx nihao="wohenhao"></xxx>`
                - 在css中通过该方式使用`元素名[属性="属性值"]{...}`
            - 后代选择器
                - 后代选择器:`选择器 后代选择器 {...}`，在满足第一个选择器的条件找后代的选择器，给满足条件的元素添加样式
        - 了解的选择器
            - 锚伪类选择器
                - `a:link {color #FF0000}`  未访问的链接
                - `a:visited {color #00ff00}` 已访问的链接
                - `a:hover {color #ff00ff}`  鼠标移动到链接上
                - `a:active {color #0000ff}` 选定的链接
        - 选择器使用小结
            - id选择器：针对一个元素
            - class选择器：针对一类元素
            - 元素选择器：针对一种元素
            - 属性选择器：元素选择器的特殊用法
            - 若多个样式作用于一个元素的时候
                - 不同的样式，会叠加
                - 相同的样式，就近原则
            - 若多个选择器作用于一个元素的时候
                - 越特殊，优先级越高，id优先级最高
    - 属性
        - 字体
            - font-family
            - font-size
            - font-style
            - color
            - line-height
            - text-decoration:文本添加下划线,none,underline
            - text-align
        - 列表
            - list-style-type
            - list-style-image:url("...")
        - 背景
            - background-color
            - background-image:url
        - 尺寸
            - width
            - height
        - 浮动
            - float
            - clear：设置元素的两边是否有其他的浮动元素
                - both: 两边都不允许
        - 分类
            - display:设置是否及如何显示元素
                - none，此元素不显示
                - block
                - inline

# 框模型
1. margin：外边距，元素最外层的空白
2. padding:内边距，元素和边框的距离
3. border:边框
    - border:宽度 风格 颜色;
        - solid：实线
        - dashed:虚线
        - double:双实线
4. padding-left,right,top,bottom
5. 上面三个属性都有简写
    - padding: 10px 10px 10px 10px ，上右下左，顺时针
        - 只写一个，表示四个边用同一个值
        - 写两个，上下 左右
        - 写三个，10px 20px 10px，左和右相同

# 案例1-用div-css重新布局首页
## 技术分析
1. div:块标签
2. span:行内的块标签

## 步骤分析
1. 创建一个div
2. 在这个div中创建8个div
3. 第1个div，两个logo
    - 嵌套三个div
4. 第2个div，菜单
    - 一个列表: display:inline
5. 第3个div，轮播图
6. 第4个div，热门商品
    - 标题标签 + 图片 display:inline
    - 两个div，左边的div展示一张图片，右边的div展示商品
        - 右边的div嵌套10个div float,clear:both换行
7. 第5个div，广告
8. 第6个div，最新商品
7. 第7个div，广告
7. 第8个div，foot 版权


# JavaScript
1. JavaScript是一种直译式脚本语言，是一种动态类型、弱类型、基于原型的语言。
## 组成部分：
1. ECMASCript：js基础语法
2. BOM(Browser Object Model)：浏览器对象模型
3. DOM(Document Object Model)：文档对象模型
## 作用
1. 修改html页面的内容
2. 修改html页面的样式
3. 完成表单的校验
4. 操作浏览器
## js和html整合
1. 方式1:在页面上直接写，将js代码放在`<script type="text/javascript"></script>`标签中,一般放在head标签中
2. 方式2:独立的js文件，通过`<script src="path"></script>`导入即可

## js中变量声明
1. `var 变量名=初始化值;`
2. `var 变量名;`

## js的数据类型
1. 原始类型(5种)
    - Null
    - String
    - Number
    - Boolean
    - Undefined
    - 通过typeof运算符可以判断一个值或一个变量是否属于原始类型，若属于原始类型，它还可以判断出属于哪个原始类型 `typeof 变量|值`
    - 若变量为null，使用typeof弹出的值为object
2. 引用类型

## js事件驱动
1. 函数定义格式：
    - 方式1:`function 函数名(参数) {函数体;}`
    - 方式2:`var 函数名=function(参数){函数体;}`
2. js的事件
    - 常见事件
        - 单击:onclick
        - 表单提交:onsubmit，加载在form表单上的`onsubmit="return funcName()`，注意函数返回值为boolean类型
        - 双击:ondbclick
        - 页面加载:onload
        - 页面卸载:unload

3. js事件和函数的绑定
    - 方式1:通过标签的事件属性`<xxx onclick="funcname(arg)"></xxx>`
    - 方式2:给元素派发事件`document.getElementById("id值").onclick = function(arg){...}`

## js获取元素:
1. 方式1： `var obj = document.getElementById("id");`
2. 获取元素的value值:`obj.value`
3. 获取元素的标签体中的内容`obj.innerHTML`

## js运算符
1. 运算符：
    - 比较运算符：> >= < <=
        - 若两边都是数字，和java一样
        - 若一遍为数字，另一边为字符串形式的数字，将字符串形式转换为数字再比较
        - 若一边是数字，另一边为字符串，返回一个false
        - 两边都是字符串的时候，比较ascii
    - 等性运算符
        - ==：只判断值是否相同
        - ===：判断值也判断类型是否相同

# 案例2-校验表单
## 需求
1. 表单提交的时候需要校验数据是否完整，若不满足条件，则用弹出框提示一下
## 步骤分析
1. 有一个表单
2. 在form上添加一个事件，onsubmit事件，`onsubmit="return checkForm()`
3. 编写checkForm方法
4. 获取每个表单子标签的内容
5. 判断是否满足要求
    - 若满足，不用管
    - 若不满足，提交表单不能提交，返回false，且提示信息


## 案例3-轮播图片
### 需求
1. 每个3秒更新图片
### 技术分析
1. bom中window对象的定时器方法

### 步骤分析
1. 在首页上面绑定一个onload事件
2. 在事件中绑定的函数里面要编写一个定时器
3. 定时器每隔3秒更换图片

### 定时器
1. id setInterVal(code,毫秒数):每个指定毫秒数执行一次code
2. id setTimeout(code,毫秒数):延迟指定的毫秒数后，只执行一次code
3. 清除定时器：
    - clearInterval(id);
    - clearTimeout(id);

