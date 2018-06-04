package cn.devinkin.proxy;

import cn.devinkin.classloader.Car;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TT {
    public static void main(String[] args) {
        QQ qq = new QQ();
        Car qqProxy = (Car) Proxy.newProxyInstance(QQ.class.getClassLoader(), new Class[]{Car.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                /**
                 * 对所有方法进行加强
                 */
//                System.out.println(method.getName());
//                System.out.println("加上电池");
//                method.invoke(qq,args);
//                System.out.println("5秒破百");
//                return null;

                /**
                 * 只对run方法进行加强
                 */
                if ("run".equals(method.getName())) {
                    System.out.println("加上电池");
                    Object obj =  method.invoke(qq, args);
                    System.out.println("5秒破百");
                    return obj;
                }
                return method.invoke(qq,args);
            }
        });
        qqProxy.run();
        qq.stop();
    }
}
