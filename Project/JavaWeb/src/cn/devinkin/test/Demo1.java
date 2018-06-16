package cn.devinkin.test;

import cn.devinkin.contant.Constant;
import org.junit.Test;

import java.text.MessageFormat;

public class Demo1 {
    public static void main(String[] args) {
        String test = "f:/store/test.jsp";
        System.out.println("sub[0]: " + test.split(":")[0]);
        System.out.println("sub[1]: " + test.split(":")[1]);
    }

    @Test
    public void func1() {
        String test = "{0},{1}";
        String fill = MessageFormat.format(test,1,2);
        System.out.println(fill );
        fill = MessageFormat.format(Constant.EMAILCONTENT, "1234");
        System.out.println(fill);
    }
}
