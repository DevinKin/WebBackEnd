package com.devinkin.demo1;

import com.opensymphony.xwork2.util.ValueStack;
import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;
import org.junit.Test;

/**
 * 演示OGNL表达式
 */
public class OgnlDemo {
    /**
     * 测试OGNL表达式
     */
    @Test
    public void run1() throws OgnlException {
        //Ognl上下文对象
        OgnlContext context = new OgnlContext();
        //获取根对象
        Object root = context.getRoot();
        // 存储数据
        context.put("name", "美美");
        // 获取值，表达式
        Object value = Ognl.getValue("#name", context, root);
        System.out.println(value);
    }

    /**
     * OGNL表达式可以调用方法
     * @throws OgnlException
     */
    @Test
    public void run2() throws OgnlException {
        //Ognl上下文对象
        OgnlContext context = new OgnlContext();
        //获取根对象
        Object root = context.getRoot();
        // 存储数据
        context.put("name", "美美");
        // 获取值，表达式
        Object value = Ognl.getValue("'haha'.length()", context, root);
        System.out.println(value);
    }
}
