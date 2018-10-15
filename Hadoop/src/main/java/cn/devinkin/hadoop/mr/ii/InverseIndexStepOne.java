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
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

/**
 * 倒排索引步骤一job
 * @author devinkin
 */
public class InverseIndexStepOne {
    public static class StepOneMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            // 1. 拿到一行数据
            String line = value.toString();

            // 2. 切分一行的单词
            String[] fields = StringUtils.split(line, " ");

            // 3. 从数据切片split中获取该行数据所在的文件的路径
            FileSplit fileSplit = (FileSplit) context.getInputSplit();

            // 4. 从文件切片中获取文件名
            String fileName = fileSplit.getPath().getName();
            for (String field : fields) {
                //封装kv输出,k:hello-->a.txt,v:1
                context.write(new Text(field + "-->" + fileName), new LongWritable(1));
            }
        }
    }

    public static class StepOneReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
        @Override
        protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
            // <hello--a.txt {1,1,1,...}>
            long counter = 0;
            for (LongWritable value : values) {
                counter += 1;
            }
            context.write(key, new LongWritable(counter));
        }
    }

    public static class StepOneRunner extends Configured implements Tool {

        public int run(String[] args) throws Exception {
            Configuration conf = new Configuration();
            Job job = Job.getInstance(conf);

            job.setJarByClass(StepOneRunner.class);

            job.setMapperClass(StepOneMapper.class);
            job.setReducerClass(StepOneReducer.class);

            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(LongWritable.class);

            FileInputFormat.setInputPaths(job, new Path(args[0]));

            Path output = new Path(args[1]);
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
        int res = ToolRunner.run(new Configuration(), new StepOneRunner(), args);
        System.exit(res);
    }
}
