package cn.devinkin.test;

import cn.devinkin.utils.JsonUtil;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class JsonDemo {
    public static void main(String[] args) {
        Person p = new Person("123","Tom", "1234");
        String s = JsonUtil.object2json(p);
        System.out.println(s);
        JsonConfig jsonConfig = JsonUtil.configJson(new String[]{"password","class","id"});
        JSONObject json = JSONObject.fromObject(p,jsonConfig);
        System.out.println(json.toString());
    }
}
