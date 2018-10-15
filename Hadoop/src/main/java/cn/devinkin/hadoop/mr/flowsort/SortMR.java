package cn.devinkin.hadoop.mr.flowsort;

import cn.devinkin.hadoop.mr.flowsum.FlowBean;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

public class SortMR {


    public static class SortMapper extends Mapper<LongWritable, Text, FlowBean, NullWritable> {

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            /**
             * 1. 拿到一行数据
             * 2. 切分出各个字段
             * 3. 封装为一个flowbean,作为key输出
             */
            String line = value.toString();

            String[] fields = StringUtils.split(line);

            String phoneNB = fields[0];
            long upFlow = Long.parseLong(fields[1]);
            long downFlow = Long.parseLong(fields[2]);

            context.write(new FlowBean(phoneNB, upFlow, downFlow), NullWritable.get());
        }
    }

    public static class SortReducer extends Reducer<FlowBean, NullWritable,Text, FlowBean> {
        @Override
        protected void reduce(FlowBean key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            String phoneNB = key.getPhoneNB();
            // 直接输出key即可
            context.write(new Text(phoneNB), key);
        }
    }

    public static class SortRunner extends Configured implements Tool {

        public int run(String[] args) throws Exception {
            Configuration conf = new Configuration();
            Job job = Job.getInstance(conf);

            // 指定执行的Runner类
            job.setJarByClass(SortMR.SortRunner.class);

            // 指定执行的Mapper类和Reducer类
            job.setMapperClass(SortMR.SortMapper.class);
            job.setReducerClass(SortMR.SortReducer.class);

            // 指定Mapper输出的kv类型
            job.setMapOutputKeyClass(FlowBean.class);
            job.setMapOutputValueClass(NullWritable.class);

            // 指定Reducer输出的kv类型
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(FlowBean.class);

            FileInputFormat.setInputPaths(job, new Path(args[0]));

            FileOutputFormat.setOutputPath(job, new Path(args[1]));


            return job.waitForCompletion(true)?1:0;
        }
    }

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new SortRunner(), args);
        System.exit(res);
    }
}
