package cn.devinkin.publisher;


import cn.devinkin.webservice.IWeatherServiceImpl;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

public class PublisherTest {
    public static void main(String[] args) {
        // 1. 创建一个工厂类
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();

        // 2. 设置服务的地址
        factory.setAddress("http://localhost:12345/weather");

        // 3. 设置背后工作的实现类(设置服务类)
        factory.setServiceBean(new IWeatherServiceImpl());

        // 4. 创建webservice服务
        factory.create();
    }
}
