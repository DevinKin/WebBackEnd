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
