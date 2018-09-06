package cn.devinkin.webservice;

import javax.jws.WebService;

@WebService  // 它作为一个WebService标志
public class IWeatherServiceImpl implements IWeatherService{
    @Override
    public String getWeatherByCityName(String cityName) {
        if ("北京".equals(cityName)) {
            return "晴";
        } else if  ("成都".equals(cityName)) {
            return "小雨";
        }
        return "查询失败";
    }
}
