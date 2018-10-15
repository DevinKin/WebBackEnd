package cn.devinkin.hadoop.mr.flowsum;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


// 这是job描述和类提交的规范写法
public class FlowSumRunner extends Configured implements Tool {
    public int run(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 指定执行的Runner类
        job.setJarByClass(FlowSumRunner.class);

        // 指定执行的Mapper类和Reducer类
        job.setMapperClass(FlowSumMapper.class);
        job.setReducerClass(FlowSumReducer.class);

        // 指定Mapper输出的kv类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);

        // 指定Reducer输出的kv类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));

        FileOutputFormat.setOutputPath(job, new Path(args[1]));


        return job.waitForCompletion(true)?1:0;
    }

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new FlowSumRunner(), args);
        System.exit(res);
    }
}
