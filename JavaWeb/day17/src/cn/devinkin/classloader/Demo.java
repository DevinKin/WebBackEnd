package cn.devinkin.classloader;

import sun.net.spi.nameservice.dns.DNSNameService;

public class Demo {
    public static void main(String[] args) {
        ClassLoader ap =  Demo.class.getClassLoader();
        System.out.println(ap);

        ClassLoader ext = DNSNameService.class.getClassLoader();
        System.out.println(ext);

        ClassLoader co =  String.class.getClassLoader();
        System.out.println(co);
    }
}
