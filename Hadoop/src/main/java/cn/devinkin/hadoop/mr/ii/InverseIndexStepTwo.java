package cn.devinkin.hadoop.mr.ii;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
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

public class InverseIndexStepTwo {

    public static class StepTwoMapper extends Mapper<LongWritable, Text, Text, Text> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            // k: 起始行偏移量, v: {hello-->a.txt 3}
            String line = value.toString();

            String[] fields = StringUtils.split(line, "\t");
            String[] wordAndfileName = StringUtils.split(fields[0], "-->");

            String word = wordAndfileName[0];
            String fileName = wordAndfileName[1];

            long count = Long.parseLong(fields[1]);
            // map的输出: <hello {a.txt-->3,b.txt-->2}>
            context.write(new Text(word), new Text(fileName + "-->" + count));
        }
    }

    public static class StepTwoReducer extends Reducer<Text, Text, Text, Text> {
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            String result = "";
            for (Text value : values) {
                result += value.toString() + "\t";
            }
            //reduce输出的结果:<hello {a.txt-->3 b.txt-->2 ...}>
            context.write(key, new Text(result));
        }
    }

    public static class StepTwoRunner extends Configured implements Tool {
        public int run(String[] args) throws Exception {
            Configuration conf = new Configuration();
            Job job = Job.getInstance(conf);

            job.setJarByClass(StepTwoRunner.class);

            job.setMapperClass(StepTwoMapper.class);
            job.setReducerClass(StepTwoReducer.class);

            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);

            FileInputFormat.setInputPaths(job, new Path(args[1]));

            Path output = new Path(args[2]);
            // 检查参数的输出路径是否存在,如果已存在,删除
            FileSystem fs = FileSystem.get(conf);
            if (fs.exists(output)) {
                fs.delete(output, true);
            }
            FileOutputFormat.setOutputPath(job, output);

            return job.waitForCompletion(true)?0:1;
        }
    }

    public static void main(String[] args) throws Exception {
        int res1 = ToolRunner.run(new Configuration(), new InverseIndexStepOne.StepOneRunner(), args);
        if (res1 == 0) {
            int res2 = ToolRunner.run(new Configuration(), new StepTwoRunner(), args);
            System.exit(res2);
        }
        System.exit(1);
    }
}
