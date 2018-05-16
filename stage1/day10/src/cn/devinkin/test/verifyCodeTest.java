package cn.devinkin.test;

import cn.devinkin.utils.VerifyCode;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class verifyCodeTest {
    @Test
    public void func1() {
        try {
            FileOutputStream output = new FileOutputStream(new File("test.jpg"));
            VerifyCode.OutputVerifyCode(output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
