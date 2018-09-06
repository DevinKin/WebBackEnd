# 使用DHTML实现动态更新出口报运单
1. DHTML:动态的HTML,html+css+js
2. 常用的DHTML插件
    - EasyUI,Extjs,tabledo.js
    
    
# 读程(阅读程序)的方法?
1. 通读(需要大量时间)
2. 带着问题去读代码(客户反馈一些bug,带着一些学习上的问题去读程)

# Quartz实现定时任务调度

## 需求及实现思路
1. 当指定的交期到时,给相关人员发送一封邮件进行提醒,需要与工厂联系,定时查询库存预警信息,如果存在库存预警信息,发送邮件通知给相关工作人员.

## 什么是Quartz框架
1. Quartz是一个开源的作业调度框架,它完全由Java写成,并设计用于J2SE和J2EE应用中.它提供了巨大的灵活性而不牺牲简单性.你能够用它来为执行一个作业而创建简单的或复杂的调度

### Job
1. 表示一个任务(工作,要执行的具体内容)

### JobDetail
1. JobDetail表示一个具体的可执行的调度程序,Job是这个可执行调度程序所要执行的内容,另外JobDetail还包含了这个任务调度的方案和策略.
2. 另外JobDetail还包含了这个任务调度的方案和策略.

### Trigger
1. Trigger代表一个调度参数的配置,什么时候去调用.

### Scheduler
1. Scheduler代表一个调度容器,一个调度容器可以注册多个JobDetail和Trigger.当Trigger与JobDetail组合,就可以被Scheduler容器调度了


## 准备工作

### 引入Quartz框架
```xml
        <dependency>
            <groupId>org.quartz-scheduler</groupId>
            <artifactId>quartz</artifactId>
            <version>2.2.1</version>
        </dependency>
```

### 编写测试代码
```java
package cn.devinkin.jk.job;

import java.util.Date;

/**
 * 定义了一个任务类
 * @author devinkin
 */
public class MailJob {

    public void send() throws Exception {
        System.out.printf("任务执行完成了: " + new Date());
    }
}
```

### 编写Spring配置文件
```xml
    <description>Quartz配置文件</description>
    <!-- 定义一个任务类 -->
    <bean id="mailJob" class="cn.devinkin.jk.job.MailJob">

    </bean>

    <!-- 定义一个methodInvokingJobDetailFactoryBean -->
    <bean id="methodInvokingJobDetail" class="org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean">
        <property name="targetObject" ref="mailJob"/>
        <property name="targetMethod" value="send"/>
    </bean>

    <!-- 指定时间 -->
    <bean id="cronTrigger" class="org.springframework.scheduling.quartz.CronTriggerFactoryBean">
        <property name="jobDetail" ref="methodInvokingJobDetail"/>
        <!-- 从几秒开始/每隔x秒 分 时 日 月 星期 年 ,日是*,星期必须是?-->
        <property name="cronExpression" value="0/10 * * * * * ? *"/>
    </bean>

    <!-- 指定一个总调度器 -->
    <bean id="scheduler" class="org.springframework.scheduling.quartz.SchedulerFactoryBean">
        <property name="triggers" >
            <list>
                <ref bean="cronTrigger"></ref>
            </list>
        </property>
    </bean>

```

