package cn.devinkin.test;

import cn.devinkin.case4.domain.Province;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class json {
    @Test
    //数组
    public void func1() {
        String[] arr = {"aaa", "Bbb", "ccc"};
        JSONArray json = JSONArray.fromObject(arr);
        System.out.println(json);
    }

    @Test
    //集合
    public void func2() {
        List<String> list = new ArrayList<>();
        list.add("11");
        list.add("22");
        list.add("33");
        JSONArray json = JSONArray.fromObject(list);
        System.out.println(json);
    }

    @Test
    //bean
    public void func3() {
        Province province = new Province();
        province.setName("北京");
        province.setProvinceid(1);
        JSONObject json = JSONObject.fromObject(province);
        System.out.println(json);
    }


    @Test
    //Map集合
    public void func4() {
        Map<String, Object> map = new HashMap<>();
        map.put("username", "tom");
        map.put("age", 18);
        JSONObject json = JSONObject.fromObject(map);
        System.out.println(json);
    }

    @Test
    //List里面放JavaBean
    public void func5() {
        List<Province> list = new ArrayList<>();
        list.add(new Province(1,"bj"));
        list.add(new Province(2,"nj"));
        list.add(new Province(3,"dj"));
        JSONArray json = JSONArray.fromObject(list);
        System.out.println(json);
    }
}
