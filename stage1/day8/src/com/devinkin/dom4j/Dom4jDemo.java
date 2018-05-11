package com.devinkin.dom4j;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.List;

public class Dom4jDemo {
    public static void main(String[] args) throws DocumentException {
        /**
         * 1. 创建核心对象
         * 2. 获取dom树
         * 3. 获取根节点
         * 4. 获取其他节点
         */
        SAXReader saxReader = new SAXReader();

        Document doc = saxReader.read("src/../web/xmls/web.xml");
        Element root = doc.getRootElement();
        List<Element> elementList = root.elements();

        for (Element ele : elementList) {
            System.out.println(ele.elementText("servlet-name"));
            System.out.println(ele.elementText("servlet-class"));
            System.out.println(ele.elementText("url-pattern"));
        }

        //获取root标签上的属性
        System.out.println(root.attributeValue("version"));
    }
}
