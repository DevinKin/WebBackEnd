package cn.devinkin.wrapper;

public class TTT {
    public static void main(String[] args) {
        QQ qq = new QQ();
        qq.run();
        qq.stop();

        CarWapper wapper = new CarWapper(qq);
        wapper.run();
        wapper.stop();
    }
}
