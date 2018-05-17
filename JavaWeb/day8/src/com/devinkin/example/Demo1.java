package com.devinkin.example;

import com.devinkin.servlet.HelloServlet;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Demo1 {
    @Test
    public void func1() throws Exception {
        /**
         * 1. 创建一个Map<url-pattern,classUrl>
         * 2. 通过key获取value
         * 3. 通过全限定名创建一个实例
         * 4. 调用空参的add方法
         */
        Map<String,String> map = new HashMap<>();

        map.put("/hello", "com.devinkin.servlet.HelloServlet");

        String className = map.get("/hello");

        Class clazz = Class.forName(className);

        HelloServlet a = (HelloServlet) clazz.newInstance();

        Method method = clazz.getMethod("add");
        method.invoke(a);
    }

    @Test
    public void func2() throws Exception {
        /**
         * 1. 获取document对象
         * 2. 通过xpath解析获得的servlet-class和url-pattern中
         */

        Document doc = new SAXReader().read("src/../web/xmls/web.xml");
        Element sc = (Element) doc.selectSingleNode("//servlet-class");
        Element up = (Element) doc.selectSingleNode("//url-pattern");
        Map<String, String> map = new HashMap<>();
        map.put(up.getText(), sc.getText());
        String className = map.get("/hello");
        System.out.println(className);

        Class clazz = Class.forName(className);

        HelloServlet a = (HelloServlet) clazz.newInstance();

        Method method = clazz.getMethod("add");
        method.invoke(a);
    }
}
