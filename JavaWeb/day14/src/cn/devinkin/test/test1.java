package cn.devinkin.test;

import org.junit.Test;

public class test1 {
    @Test
    public void func1() {
        StringBuilder stringBuilder = new StringBuilder("test");
        stringBuilder.append("hello");
        stringBuilder.append(" hey");
        System.out.println(stringBuilder);
    }
}
