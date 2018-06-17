# 网上商城体
## 用户(users)
1. id
2. 用户名
3. 密码
4. 真实名称
5. email
6. 性别
7. 电话
8. 生日
9. 用户状态(已注册未激活、已注册已激活、注销)
10. 激活码

### 用户和订单为一对多关系
1. 主键为用户id
## 商品(products)
1. 商品id
2. 商品名称
3. 商城价
4. 参考价(市场价)
5. 库存(商品数量)
6. 是否热门
7. 是否最新
8. 商品图片
9. 商品描述
10. 分类id
### 分类和商品为一对多关系
1. 商品中添加外键，分类id

## 订单(orders)
1. 订单id
2. 总金额
3. 订单状态
4. 收货人
5. 收货人地址
6. 收货人电话
7. 用户id(外键)
### 订单和商品为多对多关系

## 订单条目(orderitems)
1. 订单项id
2. 订单id
3. 商品id
4. 商品购买数量
5. 小计

## 分类(categories)
1. 分类id
2. 分类名称

# 准备工作
1. 数据库和表
2. 包结构
    1. cn.devinkin.user.web.servlet
    2. cn.devinkin.user.web.filter
    3. cn.devinkin.user.service -- service的接口层
    4. cn.devinkin.user.service.impl -- service的实现类
    5. cn.devinkin.user.dao  --dao的接口
    6. cn.devinkin.user.dao.impl  --dao的实现类
    7. cn.devinkin.user.domain
    8. cn.devinkin.user.utils
    9. cn.devinkin.user.constant
3. jar包、工具类和配置文件
    1. mysql驱动
    2. c3p0
    3. dbutils
    4. beanutils
    5. jstl
    6. 邮件相关jar包
    7. 加密jar包
    8. datasourceutils和c3p0配置文件
    9. uploadutils
    10. md5utils
    11. mailutils
    
    
# 通用servlet设计
1. BaseServlet继承HttpServlet
2. 其他模块的Servlet集成BaseServlet
3. 在BaseServlet中重写service方法
    1. 获取请求的方法
    2. 找到相应的子类
    3. 子类调用方法
        1. 反射，通过方法名称获取指定方法`Method m = clazz.getMethod(方法名,HttpServletRequest.class,HttpServletResponse.class)`
        2. 让方法执行`m.invoke(this,HttpServletRequest,HttpServletResponse)`
4. 确定调用方法后请求转发还是重定向还是不做任何事情
    1. 让所有方法返回一个字符串
    2. 字符串代表请求转发或者重定向的路径
    3. 若该方法不转发只需要返回一个null
    
   
# 用户模块
## 注册
### regist方法
1. 封装数据
    1. 设置id
    2. 设置激活状态(state)
    3. 设置激活码(code)
2. 调用service完成注册操作
3. 请求转发到`/jsp/msg.jsp`

### UserService#regist方法
1. 调用dao
2. 发送激活邮件

### UserDao#regist方法
1. 添加一条数据

### 步骤实现
1. 在index.jsp上点击<font color="red">注册页面</font>
2. 点击注册，跳转到`/user?method=registUI`
3. 在UserServlet中编写一个registUI方法
    1. 请求转发到regist.jsp即可
4. 注意：封装数据的时候报错，字符串封装成事件类型的时候出现错误
    1. BeanUtils不支持字符串转换成时间
5. 解决方案
    1. 自定义转换器
    2. 编写一个类，实现Convert接口
    3. 实现方法convert(转换的类型,参数)
    4. 在封装数据之前注册转换器
        1. ConvertUtils.regist(...)
```java
public class DateConvert implements Converter{
    /**
     * 自定义转换器
     * @param aClass 要转换成的类型
     * @param o 传入的值
     * @return 转换后的值
     */
    @Override
    public Object convert(Class aClass, Object o) {
        //将Object转换成Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date =  sdf.parse((String) o);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}

ConvertUtils.register(new DateConvert(), Date.class);
```

### 扩展-使用算法加密密码
1. md5加密，不对称的加密，不可逆
2. 在数据库中可以使用md5()机密

### 发送邮件
1. 电子邮箱：在服务器上开辟的一段空间，
2. 服务器：提供邮件服务
3. 协议：规定数据的格式
    1. 发送邮件的协议
        1. smtp
    2. 接收邮件的协议
        1. pop
        2. pop3
        3. imap

## 激活
### active方法
1. 获取激活码
2. 调用service完成激活
3. 页面跳转，请求转发到`/jsp/msg.jsp`
### UserService#active方法
1. 通过激活码获取一个用户，用户有可能为空
    1. 若不为空，判断用户state是否为1
        1. 如果为1，请求转发到`/jsp/msg.jsp`，你已经激活过了，请不要再激活
        2. 如果为0，将state改为1
## 登录
### 步骤分析
1. 在index.jsp上登录链接，点击跳转到`/loginUI`
2. 修改页面上的for表单，method，每个标签你添加name属性
### loginUI方法
1. 请求转发到`/store/jsp/login.jsp`
### login方法
1. 获取用户名和密码
2. 调用service获取一个user
    1. 判断用户是否为空
        1. 若为空
        2. 若不为空
            1. 继续判断是否激活
                1. 只有激活的时候，将用户放入Session中
3. 页面重定向到首页
    1. 展示用户名，退出，我的订单
## 退出
### 步骤分析
1. 点击用户退出，`/store/user?method=logout`

### logout方法
1. 使session无效
2. 页面重定向到首页
# 分类模块和商品模块
## 案例1-分类展示
1. 点击首页的时候，查询分类信息
### 步骤分析
1. 创建分类表
2. 在IndexServlet查询分类信息
3. 调用CategoryService#findAll()方法，返回`List<Category>`

### 问题
1. 完成之后，只有在访问首页的时候才能把分类列表展示出来，怎么办法？
    1. 想要让所有页面上都有分类，只需要将页面上logo和菜单部分包含进来,怎么去查询分类信息呢？
        1. 只需要在页面加载成功之后，发送一个ajax请求，异步查询所有的分类的信息即可
            1. json
            2. jsp包含
            3. ajax
2. jsp包含
    1. 静态包含`<%@include file=""%>`
    2. 动态包含`<jsp:include page="">`
3. 步骤分析
    1. 编写一个CategoryServlet
    2. findAll方法用来查询所有
    3. 在head.jsp加载成功之后发送一个ajax请求
        1. `$.get(url,params,funciton(){},"json")`
```javascript
    $(function () {
        //发送ajax请求
        $.get("<c:url value="category?method=findall"></c:url>",
            function (data) {
                //获取menu的ul标签
                var $ul = $("#menuid");
                //遍历数组
                $(data).each(function () {
                    $ul.append($("<li><a href='#'>"+ this.cname +"</a></li>"));

                });
            }, "json");
    });
```
4. 上面的操作我们已经可以在每个页面查看到分类信息了，但是只要换一次页面就会查询一下数据库，增加服务器的压力，对于数据不常变化的情况，我们可以使用一种技术，我们可以使用缓存技术
    1. 常见的缓存技术
        1. ehcache：Java分布式缓存，hibernate的底层使用了ehcache
        2. memcache
        3. redis
5. ehcache使用步骤
    1. 导入jar包
        1. ehcache-2.10.1.jar
        2. log4j-1.2.16.jar
        3. slf4j-api-1.7.7.jar
        4. slf4j-jdk14-1.7.7.jar
        5. slf4j-log4j12-1.7.2.jar
    2. 编写配置文件
    3. 使用api
        1. 获取数据先从缓存中获取
            1. 如果获取的值为空，再去查询数据库，将数据放入缓存中
    4. 使用步骤
        1. 获取缓存管理者
        2. 获取指定名称的缓存对象
        3. 通过指定的key获取element
        4. 判断element是否为空
            1. 若为空，查询，将结果封装成Element，通过put方法放入Cache对象
            2. 若不为空，getObjectValue()
```java
    public List<Category> findAll() throws Exception {
        /**
         * 1. 创建缓存管理器
         * 2. 获取指定的缓存
         * 3. 通过指定的缓存获取数据
         * 4. 判断数据是否为空
         */
        //1
        CacheManager cm = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
        //2
        Cache cache = cm.getCache("categoryCache");
        //将Cache看成map集合
        Element element = cache.get("clist");
        List<Category> clist = null;
        //4
        if (element == null) {
            //从数据库中获取
            clist = categoryDao.findAll();

            //将list放入缓存
            cache.put(new Element("clist", clist));
            System.out.println("缓存中没有数据，你去数据库中获取");
        } else {
            //直接返回
            clist = (List<Category>) element.getObjectValue();
            System.out.println("缓存中有数据");
        }
        return clist;
    }

```           

## 案例2-首页上的热门商品和最新商品
### 步骤分析
1. 在页面加载的时候去查询最新商品和热门商品即可
2. 在IndexServlet的index方法中实现就可以了
    1. 查询的结果为两个`List<Product>`，将两个`List<Product>`放入request域中，请求转发到index.jsp即可
    2. 在index.jsp中展示
### 准备工作
1. product表
2. javabean product
3. ProductService ProductDao
## 案例3-查询单个商品详情
1. 在首页上点击每个商品`<a href="product?method=getProductByPId&pid=${product.pid}">...</a>`
2. 编写getProductByPId
    1. 获取商品id
    2. 调用service查找商品，返回商品Product
    3. 将Product放入request域中，请求转发到product_info.jsp
## 案例4-分页展示商品
1. 按类别，分页展示
### 步骤分析
1. 在菜单栏上点击某一个分类`<a href="/product?method=findnByPage&cid=${#}&currentPage=#`
2. findByPage操作
    1. 接受cid，currentPage参数，设定每一页显示的条目，pageSize
    2. 调用productService返回一个PageBean
        1. PageBean
            1. `List<Product>`
            2. currentPage
            3. pageSize
            4. totalPage
            5. totalRecord
    3. 将PageBean放入request域中，请求转发
4. 在productService需要封装成PageBean
5. 在product_list.jsp展示数据
## 扩展：浏览记录
### 技术分析：cookie
### 步骤分析
1. 进入一个商品详情页面的时候需要记录当前商品id`<a href="/store/product?method=getProductById&pid="XXX"></a>`
2. 我们需要在getProductById方法中处理cookie
3. cookie名称：ids，values：2-1-3
4. 获取指定的cookie，CookieUtils.getCookieByName()
    1. 实现是通过request.getCoookies()先获取cookie数组，然后遍历cookie，通过cookie的名称判断
    2. if("ids".equals(cookie.getName)) return cookie;
5. 判断cookie是否为空
    1. 若不为空
        1. 获取cookie的value值
        2. 继续判断value值中有无该商品的id(将字符串切割，转成list集合)
            1. 若有
                1. 先移除，将商品的id放到list的最前面
            2. 若没有
                1. 继续判断list的长度是否大于等于3
                    1. 大于等于3：移除最后一个，将当前商品的id放入list的最前面
                    2. 小于3：直接将当前的id放到list的最前
        3. 将list变为字符串即可
    3. 若为空
        1. 将当前商品的id放入ids中即可

6. 在product_list.jsp需要将cookie里面的商品展示出来
    1. 需要在jsp页面中获取指定cookie
    2. 判断cookie是否为空
        1. 若不为空，获取cookie的value
            1. 切割字符串，获取每一个商品的id
            2. 通过id去数据库中查找，获取商品的所有信息
# 订单模块和订单条目模块
## 案例1-将商品添加到购物车
### 需求
1. 在商品详情页面上，输入购买的数量，点击加入购物车，在购物车页面上展示里面所有的商品
### 涉及的实体
1. 购物车(Cart)
    1. 购物车项的Map集合`Map<pid,CartItem>` 
    2. 总金额
    3. 添加到购物车：add2Cart(CartItem cartItem)
    4. 从购物车中移除：removeFromCart(String pid)
    5. 清空购物车：clearCart()
2. 购物车项(CartItem)
    1. 商品对象
    2. 购买数量
    3. 小计
3. 商品   
### 步骤分析
1. 在product_list.jsp输入购买数量，然后点击加入购物车`/store/cart?method=add&pid=#&count=#`
2. 在CartServlet中的add方法
    1. 获取pid和count参数
    2. 调用ProductService，通过id获取一个商品
    2. 封装CartItem
        1. Product-查询
        2. count-参数传递
        3. subtotal-计算
3. 获取购物车，调用add2Cart(CartItem)
4. 页面重定向到cart.jsp
## 案例2-对购物车进行操作(删除，清空购物车)
### 删除购物车条目
1. 在购物车页面上，点击删除`cart?method=remove&pid=??`
2. 在cartServlet中编写remove
    1. 先获取商品的pid
    2. 获取购物车
    3. 删除购物车条目
3. 页面重定向到cart.jsp

### 清空购物车
1. 在购物车页面上，有一个清空购物车的链接`/store/cart?method=clear`
2. 在CartServlet#clear中
    1. 获取购物车
    2. 调用ClearCart方法
    3. 重定向到cart.jsp上
3. 判断购物车是否为空
    1. 若为空：提示购物车空空如也
    2. 若不为空：展示
## 案例3-生成订单
### 需求
1. 在购物车页面上，有一个提交订单，点击的时候，将用户购物车中的商品添加到数据库中 

### 实体
1. 用户(User)
2. 订单(Order)
3. 商品(Product)
4. 订单项(OrderItem)中间表

### 数据库表
1. 订单表
2. 订单项表
3. 创建bean的时候需要在order实体提供user对象，`list<OrderItem>`成员
4. 在orderItem中提供一个Product对象和一个Order对象

### 步骤分析
1. 点击生成订单(/store/order?method=add)
2. 创建OrderServlet#add方法
3. add方法
    1. 判断用户是否登录，没有登录，重定向到登录页面，登录了，继续执行
    2. 封装数据
        1. oid-随机生成
        2. total-购物车中的总金额
        3. User-session中的当前用户
        4. name
        5. address
        6. telephone
        7. ordertime-当前时间
        8. 订单项集合
            1. 创建OrderItem，添加到list中
                1. 购物车中CartItem
4. 调用orderservice生成订单
    1. 开启事务
    2. 先往订单表中插入一条数据
    3. 往订单项表中插入多条数据
    4. 提交事务

## 扩展-数据库的备份和还原
1. mysql命令备份
```
mysqldump -uroot -p1234 数据库名 >> 保存路径
```
2. mysql数据库通过命令还原
    1. 前提：手动创建数据库
```
mysql -uroot -p1234 数据库名 < 数据库备份文件
```

# 订单模块
## 案例1-所有订单
### 需求
1. 点击页面上的 ”我的订单“，分页展示我所有的订单(将所有订单项都要查出来)

### 技术分析
1. 多表查询
    1. 内连接
        1. `select * from a join b on 连接条件`
        2. `select * from a,b where 连接条件`
    2. 外连接
        1. `select * from a left join b on 连接条件`
    3. 子查询
    
### 步骤分析
1. 修改head.jsp的链接，我的订单`/store/order?method=getOrdersByUid&currentPage=#`
2. 在orderServlet#getOrdersByUid方法中
    1. 获取用户(session)
    2. 获取当前页面
    3. 固定pageSize
    4. 调用orderservice根据用户查询所有订单，返回值为PageBean
    5. 将pageBean放入request域中，请求转发到order_list.jsp中
3. 在orderService#getOrdersByUid方法中
    1. 封装PageBean，`new PageBean(currentPage, pageSize, list, totalCount)`
    2. 调用dao查询list和totalCount，将uid传递过去
4. 在orderDao#getOrdersByUid中查询所有订单
    1.  `select * from orders where uid=? limit m,n`，查询一页的订单
        1. 结果为`List<Order>`，结果可以用BeanListHandler封装
    2. 遍历所有订单，根据订单id，查询订单项和商品表
        1. `select * from orderitem oi,product p where oi.pid = p.pid and oi.oid=?`
        2. 用mapListHandler封装结果集，然后使用BeanUtils封装成指定的bean对象，添加到order的items中即可
        
## 案例2-查询订单的详情
### 需求
1. 只有未付款的订单，点击“付款”，跳转到订单详情页面

### 步骤分析
1. 在订单列表，点击付款，跳转到`/store/order?method=getOrderByOid&oid=#`
2. 在OrderServlet#getOrderByOid方法中
    1. 接收oid
    2. 调用OrderService完成查询操作
    3. 返回Order对象
    4. 将Order放入request域中，请求转发到order_info.jsp
3. OrderDao#getOrderByOid方法中
    1. `select * from orderitem oi,product p where oi.pid = p.pid and oi.oid=?`
    2. 用mapListHandler封装结果集，然后使用BeanUtils封装成指定的bean对象，添加到order的items中即可
    
## 案例3-在线支付
### 在线支付的方式
1. 银行对接
2. 和第三方对接

### 不同支付方式的优缺点
1. 银行对接
    1. 优点
        1. 免费
        2. 资金回笼快
    2. 缺点
        1. 开发成本高
        2. 维护成本高
        
2. 第三方对接
    1. 优点
        1. 开发成本低
        2. 维护成本低
    2. 缺点
        1. 收费的
        2. 资金回笼慢
        
### 注意事项
1. 发送哪些数据?
    1. 第三方规定
2. 如何保证数据安全?
    1. 数字签名
        1. 需要商城将第三方需要的数据，以及加密后的数据发送给第三方支付
        2. 第三方获取所有的数据之后，将商城发送过来的数据，重新按照分配的商城的秘钥和算法重新生成一个数据，然后和商城传递过来的加密后的数据做对比，若一致，则代表数据安全
    2. 商城在第三方开个账户
    3. 第三方给商城的密钥
    4. 第三方给商城的算法

### 步骤分析
1. 在order_info.jsp页面上，填写收货人信息，点击确认订单，请求转发到`/store/order?method=pay`
2. OrderServlet#pay的操作
    1. 获取收货人信息，更新数据库中的orders表
    2. 拼装url，给第三方的数据
    3. 重定向到第三发
    
3. 用户完成支付之后，银行通知第三方支付成功，第三方需要通知用户和商城支付成功了
    1. 要求商城必须有固定的ip
4. 支付结果需要通知商城和用户
    1. url：回调函数`http://localhost:80/store/order?method=callback`
5. 在callback中
    1. 获取第三方发送过来的数据
    2. 判断数据是否被篡改过
        1. 没有被篡改，修改订单状态
        2. 若被篡改，提示信息被篡改
    
## 扩展：权限控制(粗粒度)-通过filter
### 过滤器编写步骤
1. 编写一个类
    1. 实现Filter
    2. 重写方法
2. 编写配置文件
    1. `<filter>`
    2. `<filter-mapping>`

### 步骤分析
1. 编写一个privilegeFilter
    1. 判断用户是否登录(session中获取)
        1. 若为空，请求转发到msg.jsp，并提示用户没有登录

# 后台管理
## 案例1-分类信息的curd
### 步骤分析
1. 左边的dtree
    1. 导入dtree.js
    2. 导入dtree.css
    3. 创建一个div，添加样式class='dtree'
    4. 在div中编写js代码
        1. 创建一颗树`d = new dTree('d')`
        2. 添加根节点，通过d.add(当前节点id,父节点的id,显示的名称,点击时候打开的链接,放上去显示的名称,在哪个地方打开链接)
            1. 根节点写为-1
    5. 最后通过document.write(d)写到页面即可
    
### 查询所有分类步骤
1. 应在左边的dtree上添加连接(展示所有的分类信息)：`d.add(...,'/onlineshop/adminCategory?method=findAll','','mainFrame')`
2. 创建AdminCategoryServlet，编写findAll方法
    1. 查询的结果是`List<Category>`，将list放入request域
    2. 请求转发到`/admin/category/list.jsp`
    
### 添加分类步骤
1. 编写一个连接：`/onlineshop/adminCategory?method=addCategoryUI`
2. 请求转发到`/admin/category/add.jsp`，填写完参数后，请求参数发送到`/onlineshop/adminCategory?method=addCategory`
3. adminCategory#addCategory
    1. 获取cname
    2. 封装成Category，设置cid
    3. 调用categoryService方法即可
    4. 页面重定向到`/onlinshop/adminCategory?method=findAll`
    
### 修改分类步骤
1. 编写一个连接`/onlineshop/adminCategory?method=editCategoryUI`
2. 请求转发到`/admin/category/edit.jsp`，填写完参数后，请求参数发送到`/onlineshop/adminCategory?method=editCategory&cid=#`
3. adminCategory#editCategoryUI
    1. 获取cid
    2. 调用service通过id获取一个分类
    3. 将category放入request域中
    4. 请求转发到edit.jsp
4. edit.jsp
    1. 添加action属性`/adminCategory?method=editCategory`
    2. 添加隐藏域cid
    3. 修改内容，点击提交
5. adminCategory#editCategory
    1. 获取cname 
    2. 获取cid
    3. 封装Category
    4. 调用service#update方法更新分类
    5. 页面重定向到`/onlinshop/adminCategory?method=findAll`
6. Service#update
    1. 清空缓存
    2. 调用dao更新分类
    
    
### 删除分类步骤
1. 在list.jsp编写一个连接`/onlineshop/adminCategory?method=deleteCategory&cid=#`
2. 在deleteCategory方法中
    1. 获取cid
    2. 调用service的deleteCategory方法
    3. 页面重定向到`/onlinshop/adminCategory?method=findAll`
3. service中deleteCategory方法
    1. 添加事务
    2. 通过cid先更新所有商品分类信息
    3. 调用dao删除分类
    4. 事务提交 
    5. 清空缓存
    
   
## 案例2-商品管理
### 技术分析
1. 文件上传(fileupload)
2. 浏览器要求
    1. post请求
    2. input type="file"
    3. 表单的entype="multipart/form-data"
3. 服务器要求
    1. 通过request.getParameterxxx()获取的参数全部为空
    2. 使用步骤
        1. 导入jar包commons-fileupload-1.2.1
        2. 创建一个磁盘文件项工厂
        3. 创建一个核心文件上传对象，ServletUpload
        4. 上传对象调用方法解析请求，获取一个`List<FileItem>`

### 展示所有商品步骤分析
1. left.jsp页面发送ajax请求，获取所有分类信息
1. 修改left.jsp页面的连接`/onlineshop/adminProduct?method=findByPage()`
2. adminProduct#findByPage()方法中
    1. 调用ProductService查询所有商品，返回list集合
    2. 将list放入request域中，请求转发到`/admin/product/list.jsp`
    
### 添加商品(上传图片)步骤分析
1. 修改页面上添加链接`/onlineshop/adminProduct?method=addUI`
2. 在addUI请求转发到添加页面
3. 在表单页面上
    1. 修改action：`/onlineshop/adminProduct?method=add`
    2. 提交方式：`method="post"`
    3. 添加enctype属性：`enctype="multipart/form-data`
    4. 给每个字段添加name属性
4. adminProductServlet#add()    
    1. 通过request.getParameterMap获取的信息全部为空
    2. 我们想使用BeanUtils.populate(bean,map)，我们需要创建一个Map集合，将前台接收过来的信息手动put到map中
        1. 注意：
            1. 商品的图片：
                1. 保存到服务器的磁盘
                2. 在数据库中添加图片的位置
    3. 调用productService完成添加商品操作
    4. 重定向到所有的商品上面
5. 文件下载步骤
    1. 创建磁盘文件项工厂：`DiskFileItemFactory factory = new DiskFileItemFactory();`
    2. 创建核心上传对象：`ServletFileUpload upload = new ServletFileUpload(factory);`
    3. 解析请求：`List<FileItem> list = upload.parseRequest(request);`
    4. 遍历list，判断FIleItem是普通的上传组件还是文件上传组件
        1. 普通的上传组件：`if(fi.isFormField())`
            1. 获取name属性名：`String username = fi.getName();`
            2. 获取name的属性值：`String value = fi.getString("utf-8");`
        2. 文件的上传组件
            1. 获取name属性名：`String username = fi.getFieldName();`
            2. 获取文件的名称：`String fileName = fi.getName();`
            3. 获取文件的内容：`InputStream is = fi.getInputStream();`
## 案例3-订单管理
### 查询订单
1. 所有的订单，不区分用户
2. 基本的sql：`select * from orders where 1=1 `
    1. 判断是否有state，若有则添加state，最后order by ordertime desc
3. 在left.jsp上添加5个连接，连接为：`/onlineshop?adminOrder?method=findAllByState`根据情况传入合适state就可以
4. 在adminOrderServlet的findAllByState中
    1. 接受state
    2. 调用orderservice查询得到list
    3. 将list放入request域中，请求转发到`/order/list.jsp`中
5. orderDao
    1. 先写一个基本sql
    2. 判断state是否为空
    3. 最后添加order by
    
### 扩展-查询订单详情
1. 点击每一个订单后面的订单详情，将订单的oid通过ajax传递到后台查询 
2. 步骤分析
    1. 给按钮添加事件
    
### 更新订单状态
1. 例如：在后台页面上点击发货，需要将订单的状态修改为2
2. 点击发货，修改状态`/onlineshop/adminOrder?method=updateOrderState&oid=#&state=#`
3. 在adminOrder#updateOrderState方法中
    1. 接受参数：oid、state
    2. 调用orderService修改状态
    3. 修改完成以后，页面重定向到查询所有订单页面



### 扩展
1. 在所有的方法上做日志记录，使用动态代理
```java
    /**
     * 对service中相关添加操作的方法进行增强，使用动态代理
     * @param id 类路径
     * @param obj 被代理对象
     * @return
     */
    public static Object enhance(String id, Object obj) {
        //加强的是service的实现类
        if (id.endsWith("Service")) {
            Object proxyObj = Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    //继续判断是否调用的是add方法或者是regist方法
                    if (method.getName().contains("add") || "regist".equals(method.getName())) {
                        System.out.println("添加操作");
                    }
                    return method.invoke(obj,args);
                }
            });
            return proxyObj;
        }
        return obj;
    }
```

