package cn.devinkin.case2.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JDBCInfo {
    String driverClass() default "com.mysql.jdbc.Driver";
    String url();
    String username() default "dbuser";
    String password() default "secret";
}
