# 案例1-展示所有商品
## 需求
1. 点击首页上的超链接，在页面上展示所有的信息

## 步骤分析
1. 创建数据库和表
2. 导入jar包
    1. MYSQL驱动包 
    2. dbutils 
    3. c3p0 
    4. jstl
    5. beanutils
    6. datasourceutils
    7. c3p0配置文件
    8. 新建包结构
3. 新建一个首页index.jsp
    1. 点击超链接，发送到FindAllServlet上
4. FindAllServlet
    1. 调用service，查询所有商品，返回list
    2. 将list放入request域中，请求转发到product_list.jsp


# 案例2-添加商品
## 需求
1. 在index.jsp添加一个超链接，跳转到一个页面，用来填写商品信息，点击保存按钮，将商品保存到数据库中。

## 步骤分析
1. 在index.jsp添加到一个超链接，跳转到add.jsp
2. add.jsp放入一个表单
3. 表单提交到AddProductServlet
4. AddProductServlet
    1. 封装数据
    2. 调用service完成保存操作
    3. 页面跳转到FindAllServlet(请求转发或重定向)
5. 有表单使用的时候若使用请求转发会出现重复提交
    1. 方案1：重定向
    2. 方式2：令牌机制
        1. 在添加页面上随机生成一个字符串，放入session中一份，放入表单中一份，提交的时候在后台获取两个码，然后移除session中码(只使用一次)，然后判断两个码是否一致，若不一致就是重复提交了
        
        
# 案例3-修改商品信息
## 需求
1. 在product-list.jsp每一个商品后面都有修改操作，点击修改操作(edit.jsp)，将原来的数据展示出来，保存修改

## 步骤分析
1. 先查询后修改
2. 查询步骤分析
    1. 点击修改连接的时候请求getProductByIdServlet
    2. getProductByIdServlet
        1. 获取pid
        2. 通过pid获取到商品，返回值为product
        3. 将product放入request域中，请求转发到edit.jsp
3. 修改步骤分析
    1. edit.jsp已经将商品的所有信息展示出来
    2. 需要将商品的id通过吟唱与放入表单中
    3. 点击保存，请求editProductServlet
    4. editProductServlet
        1. 封装数据
        2. 调用service方法修改更新操作
        3. 页面跳转到findAllServlet(使用重定向)

# 案例4-删除商品信息
## 需求
1. 在列表页面上，点击一个商品的删除操作，需要弹出一个提示框，确定是否删除该商品，点击确定，删除商品

## 步骤分析
1. 给删除添加链接，添加onclick事件，弹出提示confirm()，点击确定再去删除商品`location.href="/day14/deleteProductByIdServlet?pid=xxx"`
2. deleteProductByIdServlet
    1. 获取商品id
    2. 调用service
    3. 完成删除操作
    4. 删除完页面重定向到所有商品
    
## 扩展：删除多个商品
### 需求
1. 在每个商品前面添加复选框，勾选需要删除的商品，然后添加一个删除选中的按钮，点击后删除选中的商品

### 步骤分析
1. 给每个商品添加复选框(全选或全部选)
2. 点击删除选中需要将勾选上的商品的id提交到后台
3. 必须把所有的商品放入一个表单中，需要在按钮添加事件，先获取表单，调用表单的submit
4. deleteCheckedProductServlet
    1. 获取所有要删除的商品id(String[] ids)
    2. 调用service完成操作
    3. 页面重定向FindAllServlet
    
# 案例5-多条件查询
## 需求
1. 在product_list.jsp页面上添加一个表单，输入商品名称和关键词，点击确定，将符合条件的商品展示在当前的页面上

## 步骤分析
1. 在product_list.jsp页面上添加一个表单，添加一个查询按钮，提交到FindProductByConditionsServlet
2. FindProductByConditionsServlet
    1. 获取两个个条件
    2. 调用service完成查询，返回值是一个`List<Product>`
    3. 将list放入request域中，请求转发
3. ProductDao
    1. 基本sql：`select * from product where 1=1`
    2. 若商品不为空，拼接：`and pname like ...`
    3. 若商品名不为空，拼接：`and pdec like ...`
    
    
# 案例6-分页展示商品
## 需求
1. 将商品进行分页显示

## 技术分析
1. 分页

### 分页
1. 将数据按照页码划分，提高用户体验度
2. 分类
    1. 物理分页：一次只去数据库中查询当前页需要的数据
    2. 逻辑分页：一次性将所有的数据查询出来，放入集合中，每次查询只需要去集合中截取即可
    
3. mysql中分页
    1. limit
        1. 格式1：`select ... limit m,n`，从索引为m条开始，向后查找n条记录，从m+1条到第m+n条
        2. 格式2：`select ... limit n`，等价于`select ... limit 0,n`

4. 扩展，其他数据库的中分页
    1. oracle中使用`rownum`
    2. sqlserver中使用`top`

5. 每一页需要的数据
    1. 当前页内容，limit查询
    2. 当前页码，前台传递过去
    3. 每页显示的记录数，固定的
    4. 总条数：count关键字查询
    5. 总页数：总条数/每页显示的条数，Math.ceil(double)
    7. 将上述5个参数封装一个javabean(PageBean)
        1. `private List<T> list`：查询
        2. `private int currentPage`：传递
        3. `private int pageSize`：固定
        4. `private int totalCount`：查询
        5. `private int totalPage`：计算

## 步骤分析
1. 最终结果：`[首页][上一页][x][x][x]...[下一页][尾页]`

2. 创建页面栏
    1. 有上面的内容，还需要有当前页的数据

3. 在首页上有一个超链接，点击超链接，将第一页查询出来，`<a href="/day14/ShowProductByPageServlet?currentPage=1">分页展示商品</a>`

4. ShowProductByPageServlet
    1. 获取第几页
    2. 调用service完成查询操作，返回值：PageBean
    3. 将PageBean放入request域中，请求转发到product_page.jsp
    
5. service#PageBean showProductsByPage()
    1. 查询当前页数据
    2. 查询总条数
    
6. 页面上展示
    1. 展示当前页数据`<c:forEach items=${pb.list}">`
    2. 添加首页，上一页等超链接
    3. 判断是否是第一页，如果是第一页，不展示首页和上一页
    4. 判断是否是最后一页，如果是最后一页，不展示尾页和下一页
    5. 展示所有页码`<c:forEach begin='1' end='${pb.totalPage}'>` 
    6. 判断是否当前页
        1. 如果是当前页，不加超链接
        2. 如果不是当前页，添加超链接
      
7. 页面多的时候，前五后四展示页面
    1. begin和end
    2. begin：判断当前页-5>0?当前页-5:1
    3. end: 判断当前页+4>尾页?尾页:当前页