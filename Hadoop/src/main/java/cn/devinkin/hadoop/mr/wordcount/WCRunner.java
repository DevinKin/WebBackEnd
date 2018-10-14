package cn.devinkin.hadoop.mr.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * 用来描述一个特定的作业
 * 比如该作用使用哪个类作为逻辑处理中的map,哪个作为reduce
 * 还可以指定该作业要处理的数据所在的路径
 * 还可以指定该作业输出的结果,放到哪个路径
 * ...
 * @author devinkin
 */
public class WCRunner {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
//        conf.set("mapreduce.job.jar", "wc.jar");
        Job job = Job.getInstance(conf);


        // 设置整个job使用的类在哪个jar包
        job.setJarByClass(WCRunner.class);

        // 设置使用哪个类作为mapper
        job.setMapperClass(WCMapper.class);

        // 设置使用哪个类作为reducer
        job.setReducerClass(WCReducer.class);

        // 设置reduced输出的key,value的类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        // 设置mapper输出的key,value的类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);

        // 指定输入数据存放的路径
        FileInputFormat.setInputPaths(job, new Path("hdfs://hadoop01:9000/wc/srcdata/"));
//        FileInputFormat.setInputPaths(job, new Path("/home/devinkin/JavaCode/hadoop/wordcount/srcdata/"));

        // 处理结果的输出数据存放的路径
        FileOutputFormat.setOutputPath(job, new Path("hdfs://hadoop01:9000/wc/output3"));
//        FileOutputFormat.setOutputPath(job, new Path("/home/devinkin/JavaCode/hadoop/wordcount/output"));

        // 将job提交给集群运行
        job.waitForCompletion(true);
    }
}
