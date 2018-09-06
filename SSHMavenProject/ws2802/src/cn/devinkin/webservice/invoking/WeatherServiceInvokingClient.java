package cn.devinkin.webservice.invoking;

import cn.devinkin.webservice.IWeatherServiceImpl;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.Test;

public class WeatherServiceInvokingClient {
    @Test
    public void testWeather(){
        // 1. 生成一个客户代理工厂
        JaxWsProxyFactoryBean client = new JaxWsProxyFactoryBean();

        // 2.设置服务端的访问地址
        client.setAddress("http://localhost:12345/weather?wsdl");

        // 3. 设置服务端的接口
        client.setServiceClass(IWeatherServiceImpl.class);

        // 4. 创建客户端对象
       IWeatherServiceImpl iws = (IWeatherServiceImpl) client.create();

       // 5. 调用远程的服务端提供的方法
        String result = iws.getWeatherByCityName("北京");
        System.out.println(result);
    }
}
