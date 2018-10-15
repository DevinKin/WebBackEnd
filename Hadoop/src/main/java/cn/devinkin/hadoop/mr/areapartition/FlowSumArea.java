package cn.devinkin.hadoop.mr.areapartition;

import cn.devinkin.hadoop.mr.flowsum.FlowBean;
import cn.devinkin.hadoop.mr.flowsum.FlowSumMapper;
import cn.devinkin.hadoop.mr.flowsum.FlowSumRunner;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

/**
 * 对流量原始日志进行统计分析,将不同省份的用户统计结果输出到不同的文件
 * 需要自定义两个机制
 * 1. 自定义分区的逻辑,自定义一个partitioner
 * 2. 自定义reducer task的并发任务数
 *
 * @author devinkin
 */
public class FlowSumArea {

    public static class FlowSumAreaMapper extends Mapper<LongWritable, Text, Text, FlowBean> {
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

    public static class FlowSumAreaReducer extends Reducer<Text, FlowBean, Text, FlowBean> {
        @Override
        protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
            long upFlowCount = 0;
            long downFlowCount = 0;

            for (FlowBean bean : values) {
                upFlowCount += bean.getUpFlow();
                downFlowCount += bean.getDownFlow();
            }

            context.write(key, new FlowBean(key.toString(), upFlowCount, downFlowCount));
        }
    }

    public static class FlowSumAreaRunner extends Configured implements Tool {

        public int run(String[] args) throws Exception {
            Configuration conf = new Configuration();
            Job job = Job.getInstance(conf);

            job.setJarByClass(FlowSumRunner.class);

            job.setMapperClass(FlowSumAreaMapper.class);

            job.setReducerClass(FlowSumAreaReducer.class);

            // 自定义分组逻辑的定义
            job.setPartitionerClass(AreaPartitioner.class);

            // 设置reduce的并发数,应该跟分组的数量保持一致
            job.setNumReduceTasks(10);

            job.setOutputKeyClass(Text.class);

            job.setOutputValueClass(FlowBean.class);

            FileInputFormat.setInputPaths(job, new Path(args[0]));

            FileOutputFormat.setOutputPath(job, new Path(args[1]));

            return job.waitForCompletion(true) ? 1 : 0;
        }
    }

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new FlowSumAreaRunner(), args);
        System.exit(res);
    }
}
