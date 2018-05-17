package cn.devinkin.wrapper;

public class CarWapper implements Car {
    private Car car;

    public CarWapper(Car car) {
        this.car = car;
    }

    @Override
    public void run() {
        System.out.println("上发动机");
        System.out.println("终于可以5秒过百了");
    }

    @Override
    public void stop() {
        car.stop();
    }
}
