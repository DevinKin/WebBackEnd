package cn.devinkin.hadoop.mr.areapartition;

import org.apache.hadoop.mapreduce.Partitioner;

import java.util.HashMap;

public class AreaPartitioner<KEY, VALUE> extends Partitioner<KEY, VALUE> {
    private static HashMap<String, Integer> areaMap = new HashMap<String, Integer>();

    static {
        areaMap.put("135", 0);
        areaMap.put("136", 1);
        areaMap.put("137", 2);
        areaMap.put("138", 3);
        areaMap.put("139", 4);
    }

    public int getPartition(KEY key, VALUE value, int i) {
        // 1. 从key中拿到手机号
        // 2. 查看手机归属字典
        // 3. 不同的省份返回不同的编号

        int areaCode = areaMap.get(key.toString().substring(0,3)) == null?5:areaMap.get(key.toString().substring(0,3));
        return areaCode;
    }
}
