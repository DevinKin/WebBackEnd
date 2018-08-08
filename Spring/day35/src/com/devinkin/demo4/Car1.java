package com.devinkin.demo4;

/**
 * 演示构造方法的注入方式
 * @author king
 */
public class Car1 {
    private String cname;
    private Double price;

    @Override
    public String toString() {
        return "Car1{" +
                "cname='" + cname + '\'' +
                ", price=" + price +
                '}';
    }

    public Car1(String cname, Double price) {
        this.cname = cname;
        this.price = price;
    }
}
