# 海量数据导入
1. 海量:早期百万,大数据出阿里之后,上亿
2. Hibernate数据量单标要小于500万
3. Mybatis/jdbc单标数据大于500万,oracle不要超过1亿
4. Excel2003数据量有限制:65536行,256列
5. Excel2007支持的单sheet:1049576行,16384列

## Apache POI
### Workbook实现类
### HSSFWrokbook
1. HSSFWrokbook对象能操作excel2003,xls扩展名
### XSSFWorkbook
1. XSSFWrokbook可以支持excel2007及以上的版本,xlsx扩展名
### SXSSFWorkbook
1. SXSSFWorkbook可以支持百万级别数据的POI
2. 初始化SXSSFWorkbook对象时,可以指定在内存中所产生后的POI到处相关对象的个数(默认是100个)
3. 一旦内存中对象的个数达到了这个指定值时,就会将内存中的这些对象的内容写入磁盘中(xml文件格式)

# 细粒度权限控制
1. 当事人查看自己的记录
2. 部门经理查看当前部门下员工添加的记录
3. 总经理查看所有记录
4. 查询当前部门及下属部门

## 查询当前部门及下属部门
1. 子部门编号是根据父部门的编号进行拓展
2. 相应查询的hql语句如下
```java
if (degree == 2) {
    // 管理本部门及下属部门
    hql = "from Contract where 1 =1 and createDept in" +
                                    " {" +
                                        "select id from Dept" +
                                         " where id like '100%'";
}
```

# 打断设计思想
1. 在传统的一对多关系中,都会在多方加入一个一方主键作为它的外键
2. 在打断设计思想的指导下,不会这样实现,它会在一方的表中加入一个冗余字段,用于保存多方的主键,并且用指定的分隔符进行分割
3. 真正的实现原理:就是通过打断字段,实现数据的冗余,从而一样的可以解决一个报运单下有多个购销合同的问题

## 打断设计
1. 所谓打断设计,就是表中字段冗余,把外键消除了,当存在一对多时,它时在一方加入多方的集合,这个集合用于存储多方的主键,病使用分隔符进行分割(如用逗号分割)

## 跳跃查询
1. 跳跃查询时在打断设计基础上进行查询数据时的优化.它比传统的关联数据加载效率加倍


## 传统做法
1. 出口报运单对象->加载出购销合同的集合->便利得到每个购销合同->通过购销合同得到货物->通过货物得到附件

## 打断设计基础上的做法
1. 出口报运单对象->得到它的一个字段(购销合同id形成的集合)->直接到货物表进行查询(from ContractProduct cp where cp.contract.id in (购销合同id形成的集合)) 


## 使用打断设计原则 
1. 当关联的层级大于4层时,就要考虑打断设计.这样可以借助跳跃查询实现速度翻倍

## 数据库设计上的问题
1. 早期项目开发交付客户使用后,一切正常,但随着数据量的量变到质变,结果就是系统响应时间线性增长,系统运行半年后,客户无法承受等待时间