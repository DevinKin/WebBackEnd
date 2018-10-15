package cn.devinkin.hadoop.mr.flowsum;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * FlowBean 是我们自定义的一种数据类型,要在hadoop的各个节点之间传输,应该遵循hadoop的序列化机制
 * 就必须实现hadoop的序列化接口
 * @author devinkin
 */
public class FlowSumMapper extends Mapper<LongWritable, Text, Text, FlowBean> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        /**
         * 1. 拿到文件中的一行数据,切分各个字段,抽取出我们需要的字段:手机号,上行流量,下行流量
         * 2. 封装成kv发送出去
         */
        String line = value.toString();

        String[] fields = StringUtils.split(line, "\t");
        String phoneNB = fields[1];
        long upFlow = Long.parseLong(fields[7]);
        long downFlow = Long.parseLong(fields[8]);

        context.write(new Text(phoneNB), new FlowBean(phoneNB, upFlow, downFlow));
    }
}
