package cn.devinkin.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

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
            return Class.forName(value).newInstance();

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

    public static void main(String[] args) {
        System.out.println(getBean("ProductDao"));
    }
}
