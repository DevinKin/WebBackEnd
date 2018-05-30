package cn.devinkin.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class testList {
    @Test
    public void func1() {
        List<String> list = new ArrayList<String>();
        list.add("123");
        list.add("123");
        list.add("lkjas");
        list.add("lkjas");
        list.add("zkj");
        System.out.println(list.toString());
    }
}
