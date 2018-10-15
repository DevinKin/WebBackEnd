package cn.devinkin.hadoop.mr.flowsum;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FlowSumReducer extends Reducer<Text, FlowBean, Text, FlowBean> {

    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
        /**
         * 框架每传递一组数据:<138172712312, {flowBean,flowBean,flowBean...}
         * 1. 遍历values,累加求和
         * 2. 输出
         */
        long upFlowCount = 0;
        long downFlowCount = 0;
        for (FlowBean bean : values) {
            upFlowCount += bean.getUpFlow();
            downFlowCount += bean.getDownFlow();
        }

        context.write(key, new FlowBean(key.toString(), upFlowCount, downFlowCount));
    }
}
