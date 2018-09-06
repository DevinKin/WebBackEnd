package cn.devinkin.webservice;

public interface IWeatherService {
    /**
     * 根据城市名返回天气结果信息
     * @param cityName 城市名
     * @return
     */
    public String getWeatherByCityName(String cityName);
}
