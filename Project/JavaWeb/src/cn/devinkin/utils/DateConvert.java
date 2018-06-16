package cn.devinkin.utils;

import org.apache.commons.beanutils.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConvert implements Converter{
    /**
     * 自定义转换器
     * @param aClass 要转换成的类型
     * @param o 传入的值
     * @return 转换后的值
     */
    @Override
    public Object convert(Class aClass, Object o) {

        if(o == null) return null;//如果要转换成值为null，那么直接返回null
        if(!(o instanceof String)) {//如果要转换的值不是String，那么就不转换了，直接返回
            return o;
        }
        String val = (String) o;//把值转换成String

        //将Object转换成Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date =  sdf.parse((String) o);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
