package cn.devinkin.wrapper;

public class QQ implements Car{

    @Override
    public void run() {
        System.out.println("QQ在跑");
    }

    @Override
    public void stop() {
        System.out.println("正常刹车");
    }
}
