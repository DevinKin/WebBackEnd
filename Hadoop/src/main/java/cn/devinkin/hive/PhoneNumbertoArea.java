package cn.devinkin.hive;

import org.apache.hadoop.hive.ql.exec.UDF;

import java.util.HashMap;

public class PhoneNumbertoArea extends UDF {
    private static HashMap<String, String > areaMap = new HashMap<String, String>();
    static {
        areaMap.put("1388", "beijing");
        areaMap.put("1399", "tianjin");
        areaMap.put("1366", "nanjing");
    }
    public String evaluate(String phoneNumber) {
        String result = areaMap.get(phoneNumber.substring(0,4)) == null? phoneNumber + "\thuoxing" : phoneNumber + "\t" + areaMap.get(phoneNumber.substring(0,4));
        return result;
    }
}
