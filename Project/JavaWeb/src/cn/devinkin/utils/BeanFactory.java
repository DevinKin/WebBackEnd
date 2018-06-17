package cn.devinkin.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 实体工厂类
 * @author king
 */
public class BeanFactory {
    public static Object getBean(String id) {
        //通过id获取一个指定的实现类
        /**
         * 1. 获取document对象
         * 2. 通过xpath表达式获取指定的bean对象
         * 3. 获取bean独享的class属性
         * 4. 反射
         */
        try {
            Document doc = new SAXReader().read(BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml"));
            Element element = (Element) doc.selectSingleNode("//bean[@id='" + id + "']");
            String value = element.attributeValue("class");

            //以前的逻辑直接返回的是实例
//            return Class.forName(value).newInstance();

            //5. 现在对service的add方法进行加强，返回值是代理对象
            Object obj = Class.forName(value).newInstance();
            return enhance(id,obj);


        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

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

    public static void main(String[] args) {
        System.out.println(getBean("ProductDao"));
    }
}
