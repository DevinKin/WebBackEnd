package cn.devinkin.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import java.util.Date;
import java.util.Map;

public class CommonUtils {

    /**
     * 把map转换成对象
     * @param map 集合
     * @param clazz 对应对象的类
     * @param <T>
     * @return bean对象
     */
    public static <T> T toBean(Map map, Class<T> clazz) {
        try {
            /**
             * 1. 通过参数clazz创建实例
             * 2. 使用BeanUtils.populate(把map的数据封装到bean中
             */
            T bean = clazz.newInstance();
            ConvertUtils.register(new DateConvert(), Date.class);
            BeanUtils.populate(bean, map);
            return bean;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
