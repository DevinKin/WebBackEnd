# bom(浏览器对象模型)
## 所有的浏览器都有5个对象
### window:窗口
1. 如果文档包含框架(frame或iframe标签)，浏览器会为HTML文档创建一个window对象，并为每个框架创建一个额外的window对象。
2. window常用属性
    - 通过window可以获取其他的四个对象
        - window.location 等价于 location
        - window.history 等价于 history
        - window.navigator 等价于 navigator
3. window常用方法
    1. 消息框
        - alert("...")：警告框
        - confirm("...")：确认框，返回值：boolean
        - prompt("...")：输入框，返回值为你输入的内容
    2. 定时器
        - setInterval(code, 毫秒数);
        - setTimeout(code, 毫秒数);
        - clearInterval(id)
        - clearTimeout(id)
    3. 打开和关闭
        - open(url):打开
        - close():关闭
### location:定位信息(地址栏)
1. 常用属性:
    - href:获取或者设置当前页面的url
    - location.href：获取url
    - location.href=".."：设置url
### history:历史 
1. 常用方法：
    - back():后退
    - forward():向前
    - go(int)
        - 1:forward
        - -1:back
### Navigator:包含有关浏览器信息   
### screen:有关客户端显示屏幕的信息


# dom(文档对象模型)
1. 当浏览器接受到html代码的时候，浏览器会将所有的代码装载到内存中，形成一颗(document树)
## 节点(Node)
1. 文档节点 document
2. 元素节点 element
3. 属性节点 attribute
4. 文本节点 text
### 获取节点
1. getElementById("id值")
2. getElementsByTagName("标签名")
3. getElementsByClassName("class属性的值")
4. getElementsByName("name属性的值")

## 设置或获取节点的value属性
1. dom对象.value
2. dom对象.value=""

## 设置或获取节点的标签体
1. dom对象.innerHT
2. dom对象.innerHTML=""

## 设置或获取style属性
1. dom对象.style.属性=""
2. dom对象.style.属性

## 设置或获取属性
1. dom对象.属性
2. dom对象.属性=""

# 引用类型
1. Array
    - new Array()
    - new Array(size)
    - new Array(e1,e2,...)
2. String
    - new String()
    - String(变量|值)
    - 常用方法
        - substring(start,end)
        - substr(start,size)
        - charAt(index)
        - indexOf("")
        - replace()
        - split()
3. Boolean
4. Number
    - new Number(值|变量)
    - Number(值|变量)
    - null => 0
    - false => 0 true => 1
    - 字符串的数字=>对应的数字
    - 其他的NaN
5. Date
    - toLocalString()
6. RegExp
    - new RegExp(/reg/)
    - new RegExp(/reg/,arg1,...argn)
        - arg
            - i：忽略大小写
            - g：全局
    - 常用方法
        - test()
7. Math
    - Math.常量|方法
    - Math.PI
    - Math.random()
8. 全局
    - decodeURI()
    - encodeURI()
    - parseInt()
    - parseFloat()
    - eval()

# 案例1-定时弹出广告
## 需求
1. 打开页面4秒后，展示广告，2秒之后，该广告隐藏，再停2秒，继续展示

## 技术分析
1. 定时器(window对象下编写)
    - setInterval(code,毫秒数)
    - setTimeout(code,毫秒数)
    - clearInterval(id)
    - clearTimeout(id)

## 步骤分析
1. html页面先把广告隐藏 
2. 页面加载成功事件：onload
4. 编写函数
    - 获取元素
    - 操作css属性
        - document.getElementById("id").style.property="value"
        - 若有属性有"-",只需将"-"删除，后面第一个字母变为大写即可
    - 

4. 只要是window对象的属性和方法，可以把window省略


# 案例2-表单校验plus
## 需求
1. 提示信息不用弹出框，将信息追加在文本框后面

## 技术分析
1. 确定事件：
    - 表单提交的时候 onsubmit
    - 文本框失去焦点的时候 onblur
2. 编写函数
3. 获取元素
4. 操作元素
    - 获取元素的值，操作标签体，操作标签value属性

## 步骤分析
1. 表单提交的时候判断username和password是否为空,onsubmit
2. 校验用户名和密码
3. 获取用户名和密码的对象的值

## 注意：在方法中，this只带的是当前元素(当前dom对象)

# 常见的事件：
1. 焦点事件
    - onfocus
    - onblur
2. 表单事件
    - onsubmit
    - onchange:改变事件
3. 页面加载事件
    - onload
    - onunload

4. 鼠标事件
    - onclick:单击事件
    - ondbclick:双击事件
    - onmousedown:按下
    - onmouseup:弹起
    - onmousemove:移动
    - onmouseover:悬停
    - onmouseout:移出

5. 键盘事件
    - onkeydown:按下
    - onkeyup:松开
    - onkeypress:按住
    

# 绑定事件
1. 方式1：元素的事件属性
2. 方式2：派发事件

# Event方法
1. event.preventDefault():阻止浏览器默认事件的发生
2. event.stopProagation():不再派发事件


# 案例3-隔行换色
## 需求
1. 一个表格，隔一行换一个色

## 技术分析
1. 事件:onload
2. 获取元素
    - document.getElementById("id");
    - document.getElementByTagName("标签名);
3. 步骤分析
    - html表格-加载的时候，确定事件 onload
    - 编写函数
        - 获取所有tr元素
        - 若当前行是偶数行，加样式，奇数行，另一个样式


# 案例4-全选或全部选
## 需求
1. 最上方的复选框选中之后，下面的复选框全选中，上面的复选框取消选中之后，下面的复选框取消选择

## 步骤分析
1. 确认事件：最上面那个复选框的单击事件，onclick
2. 编写函数
    - 获取最上面的这个复选框选中状态
    - 判断上面的复选框的状态，控制下面复选框的状态
    - 获取元素方案
        - document.getElementsByClassName()
        - document.getElementsByName()


# 案例5-左右选中
## 分析
1. 确定事件，按钮的单击事件 onlcick
    - 选中一个，移动一个，添加break
    - 选中多个，移动多个，i++后使用i--
    - 选中全部，移动全部，使用while(i.length > 0) optList[0]进行移动


# 案例6-省市联动
## 需求
1. 选中省的时候，动态查询当前省下的所有市
# 分析
1. 使用数组存储省份
    - new Array()
    - new Array(size)
    - new Array(e1,e2,...)

2. 数组
    - 数组是可变的
    - 数组可以存放任意值
3. 数组常用方法
    - pop
    - push
    - shift:删除第一个
    - unshift:插入到首位
    - join
    - concat
    - sort
    - reverse