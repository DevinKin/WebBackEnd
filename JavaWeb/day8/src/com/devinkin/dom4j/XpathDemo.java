package com.devinkin.dom4j;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.List;

public class XpathDemo {
    public static void main(String[] args) throws DocumentException {
        /**
         * 1. 加载dom树
         * 2. 获取节点
         */
        Document doc = new SAXReader().read("src/../web/xmls/web.xml");
        System.out.println(doc);

        List<Element> list = doc.selectNodes("/web-app/servlet/servlet-name");
        for (Element ele : list) {
            System.out.println(ele.getText());
        }
        Element ele = (Element) doc.selectSingleNode("//servlet-mapping[@id='2.5']/servlet-name");
        System.out.println(ele.getText());

    }
}
