package cn.devinkin.test;

import org.testng.annotations.Test;

import java.util.Date;

public class testDate {
    @Test
    public void func1() {
        Date date = new Date();
        System.out.println(date.toString());
    }
}
