package cn.devinkin.case1.web.servlet;

import java.util.UUID;

public class UUIDUtils {
    public static String UUID() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    public static void main(String[] args) {
        System.out.println(UUID().replace("-", "").toUpperCase());
    }
}
