package com.devinkin.demo4;

public class Car2 {
    private String cname;
    private Double price;

    @Override
    public String toString() {
        return "Car2{" +
                "cname='" + cname + '\'' +
                ", price=" + price +
                '}';
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
