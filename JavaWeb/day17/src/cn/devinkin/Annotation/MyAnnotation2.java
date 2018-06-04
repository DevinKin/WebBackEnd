package cn.devinkin.Annotation;

import cn.devinkin.Enum.Color;

import java.util.Date;

public @interface MyAnnotation2 {
    //注解属性
    int i();
    String s();
    String[] ss();
    Class cl();
    MyAnnotation1 m1();

    Color RED();
}
