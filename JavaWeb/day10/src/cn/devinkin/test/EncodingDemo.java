package cn.devinkin.test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class EncodingDemo {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = "测试";
        String s8 = URLEncoder.encode(s, "utf-8");
        System.out.println(s8);
        String si = URLDecoder.decode(s8,"ISO-8859-1");
        System.out.println(si);

        byte[] b = si.getBytes("iso8859-1");
        String su = new String(b, "utf-8");
        System.out.println(su);
    }
}
