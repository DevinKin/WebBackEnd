# Boostrap
## 什么是boostrap
1. webcss框架
2. 整合了html/css/jquery
3. 创建响应式的页面，适配不同的上网设备

## boostrap入门
1. 下载bootstrap：[bootstrap官网](http://www.bootcss.com)
2. 导入bootstrap.css
3. 导入jquery.js
4. 导入bootstrap.js
5. 添加一个meta标签，支持移动设备
    - `<meta name="viewport" content="width=device-width" initial-scale="1">`
6. 将所有的内容放入到布局容器中即可
    - Bootstrap需要为页面内容和栅格系统包裹一个`.container`容器，我们提供了两个作此用处的类
        - 方式1：`<div class="container"></div>`
        - 方式2：`<div class="container-fluid></div>`
7. 注意：bootstrap将每一行分成12份

## 媒体查询
### 栅格
1. 超小屏幕(`<768px`)
    - 类前缀：col-xs-#该元素每行占用份数,12/#就是每行显示的元素个数

2. 小屏幕`(≥768px)`
    - 类前缀：col-sm-#该元素每行占用份数,12/#就是每行显示的元素个数

3. 中等屏幕`(≥992px)`
    - 类前缀：col-md-#该元素每行占用份数,12/#就是每行显示的元素个数

4. 大屏幕`(≥1200px)`
    - 类前缀：col-lg-#该元素每行占用份数,12/#就是每行显示的元素个数

## bootstrap组成部分
1. 全局css样式
2. 组件
3. JavaScript插件

# 案例1-表单校验
## 需求
1. 通过validate插件来校验表单

## 技术分析
1. validate


# validate
1. validate是jquery的第三方插件，用于表单校验

## 使用步骤
1. 导入jquery.js，
2. 导入validate.js
3. 在页面加载成功之后，对表单进行校验。$("选择器").validate()
4. 在validate中编写校验规则
```
$("选择器").validate({
    rules:{
        属性名:"规则"
        属性名:{
            规则名:规则值
        }
    },
    messages:{}
})
```
5. [规则请看此链接](https://blog.csdn.net/windxxf/article/details/7520340)


# 案例2-创建一个响应式的页面
## 需求
1. 创建一套页面，可以根据上网设备的不同，来自动调节像素

## 技术分析
1. bootstrap

## 步骤分析
1. 先有模板页面
2. 先创建一个导航条
3. 下面创建一个轮播图
4. 下面再创建3个div
    - 中等屏幕的时候，3个在一行
    - 小屏幕的时候，2个一行
    - 最小屏幕的时候，1个一行


# 案例3-布局首页
## 需求
1. 当屏幕为小屏幕和超小屏幕的时候，隐藏热门商品中的广告左图
2. 当屏幕为超小屏幕的时候，隐藏middle图片
## 技术分析
1. hidden-xxx
    - hidden-xs
    - hidden-sm
    - hidden-md
    - hidden-lg
## 步骤分析
1. 创建8个div
2. 第一个：logo
    - 三个div
        - 中等屏幕：三个一行
        - 小屏幕：两个一行
        - 超小屏幕：一个一行
3. 第二个：导航条
4. 第三个：轮播图
5. 第四个：热门商品
    - 先分成左右两个div
    - 左div放一张图片
        - 小屏幕和超小屏幕：隐藏
        - 中等屏幕：占两份
    - 右div
        - 中等屏幕：占10份
        - 小屏幕和超小屏幕：占12份
        - middle图片
            - 中等屏幕：占6份
            - 小屏幕：12份
            - 超小屏幕：隐藏
        - 商品图片
            - 中等屏幕：一个图片占2份
            - 小屏幕：一个图片占4份
            - 超小屏幕：一个图片占12份