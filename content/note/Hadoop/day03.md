# MapReduce框架
1. Map作为局部处理
2. Reduce作为汇总处理


## WordCount示例
1. Mapper:用于读取输入数据,作局部处理
    - 需要继承Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>类
2. Reducer:用于处理mapper输出的数据
    - 需要继承Reducer<KEYIN, VALUEIN, KEYOUT, VALUEOUT>类
3. Runner:指定mapper,reducer,输入,输出文件的路径等

### Mapper程序
```java
package cn.devinkin.hadoop.mr.wordcount;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

// 4个泛型中,前两个是指定mapper输入数据的类型
// KEYIN是输入的key类型
// VALUEIN是输入的value的类型
// KEYOUT是输出的key类型
// VALUEOUT是输出的value的类型
// map和reduce的数据输入输出都是以key-value对的形式封装的
// 默认情况下,框架传递给我们的mapper的输入数据中,key是要处理的文本中一行的起始偏移量,这一行的内容作为value
public class WCMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

    // mapreduce框架每读一行数据就调用一次该方法
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 具体业务逻辑就在此处编写,而且业务要处理的数据已经被框架传递进来,在方法的参数中
        // key是这一行数据的起始偏移量,value是这一行的文本内容

        // 将该行的文本按特定分隔符切分
        String line = value.toString();
        String[] words = StringUtils.split(line, " ");

        // 便利这个单词数组输出的kv形式,k:单词,v:1
        for (String word : words) {
            context.write(new Text(word), new LongWritable(1));
        }
    }
}
```

### Reducer程序
```java
package cn.devinkin.hadoop.mr.wordcount;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class WCReducer extends Reducer<Text, LongWritable, Text, LongWritable> {

    // 框架在map处理完成之后,将所有的kv对缓存起来,进行分组,然后传递一个组<key,values[]>,调用一次reduce方法
    // <hello, [1,1,1,1...]>
    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
        long count = 0;
        // 遍历value的list,进行累加求和
        for (LongWritable value : values) {
            count += value.get();
        }

        // 输出这一个单词的统计结果
        context.write(key, new LongWritable(count));
    }
}
```

### Runner程序
```java
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
        FileInputFormat.setInputPaths(job, new Path("/wc/srcdata/"));

        // 处理结果的输出数据存放的路径
        FileOutputFormat.setOutputPath(job, new Path("/wc/output"));

        // 将job提交给集群运行
        job.waitForCompletion(true);
    }
}

```

## 上传文件并运行
1. `hadoop fs -put words.log /wc/srcdata`
2. `hadoop jar wc.jar cn.devinkin.hadoop.mr.wordcount.WCRunner`


## MR程序的集中提交运行模式
1. 本地模式运行
    - 要处理的输入输出数据可以放在本地路径下
    - 输入输出数据也可以放在hdfs中
    
2. 集群模式运行
    - 要处理的输入输出数据可以放在本地路径下
    - 输入输出数据也可以放在hdfs中

# YARN框架
1. YARN框架主要职责是资源调度

![](../pic/job执行流程.png)


