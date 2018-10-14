package cn.devinkin.hadoop.hdfs;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.FileOutputStream;
import java.io.IOException;

public class HdfsUtil1 {

    public static void main(String[] args) throws IOException {
        // 从hdfs下载文件
        Configuration conf = new Configuration();

        FileSystem fs = FileSystem.get(conf);
        Path src = new Path("hdfs://devinkin:9000/jdk-7u65-linux-x64.tar.gz");
        FSDataInputStream inputStream = fs.open(src);
        FileOutputStream outputStream = new FileOutputStream("/home/devinkin/jdk-7u65-linux-x64.tar.gz");

        IOUtils.copy(inputStream, outputStream);
        outputStream.close();
        inputStream.close();
    }
}
