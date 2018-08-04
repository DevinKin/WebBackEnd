package com.devinkin.demo1;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * 完全解耦合方式，使用ServletAPi
 */
public class Demo1Action extends ActionSupport {
    @Override
    public String execute() throws Exception {
        // 完全解耦合的方式
        ActionContext context = ActionContext.getContext();
//         获取到请求的参数
        Map<String, Object> map = context.getParameters();
        Set<String> keys =  map.keySet();
        for (String key : keys) {
            String[] vals = (String[])map.get(key);
            System.out.println(key + " : " + Arrays.toString(vals));
        }
        System.out.println("执行了...");
        // 如果向request对象中存入值
        context.put("msg", "小东");
        context.getSession().put("msg", "小苍");
        context.getApplication().put("msg","小泽");

        return SUCCESS;
    }
}
