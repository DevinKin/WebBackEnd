import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;

public class HdfsUtil {
    private FileSystem fs = null;

    @Before
    public void init() throws Exception {
        // 读取classpath下的xxx-site.xml配置文件,并解析其内容,封装到conf对象中
        Configuration conf = new Configuration();
        // 创建文件系统
        fs = FileSystem.get(new URI("hdfs://hadoop01:9000/"), conf, "hadoop");
    }


    /**
     * 上传文件,传统写法
     */
    @Test
    public void upload() throws IOException {

        // 设置上传的路径
        Path dst = new Path("hdfs://hadoop01:9000/aa/qingshu.txt");

        // 获取输出流
        FSDataOutputStream os = fs.create(dst);

        // 获取输入流
        FileInputStream is = new FileInputStream("/home/devinkin/JavaCode/WebBackEnd/Hadoop/src/test/resources/qingshu.txt");
        IOUtils.copy(is, os);
        is.close();
        os.close();

    }

    /**
     * 上传文件,封装后的写法
     * @throws IOException
     */
    @Test
    public void upload2() throws IOException {
        fs.copyFromLocalFile(new Path("/home/devinkin/JavaCode/WebBackEnd/Hadoop/src/test/resources/qingshu.txt"), new Path("hdfs://hadoop01:9000/qingshu2.txt"));
    }

    /**
     * 下载文件
     */
    @Test
    public void download() throws IOException {
        fs.copyToLocalFile(new Path("hdfs://devinkin:9000/aa/qingshu2.txt"),
                new Path("/home/devinkin/JavaCode/WebBackEnd/Hadoop/src/test/resources/qingshu2.txt"));
    }

    /**
     * 查看文件信息
     */
    @Test
    public void listFiles() throws IOException {
        // 只查看文件
        RemoteIterator<LocatedFileStatus> files = fs.listFiles(new Path("/"), true);
        while (files.hasNext()) {
            LocatedFileStatus file = files.next();
            Path path = file.getPath();
            String name = path.getName();
            System.out.println("fileName: " + name);
        }

        System.out.println("============================================");
        FileStatus[] fileStatuses = fs.listStatus(new Path("/"));
        for (FileStatus status : fileStatuses) {
            System.out.println("fileName: " + status.getPath().getName() + (status.isDirectory() ? " is Directory":""));
        }

    }

    /**
     * 创建目录
     */
    @Test
    public void mkDir() throws IOException {
        fs.mkdirs(new Path("/aaa/bbb/ccc"));
    }

    /**
     * 删除目录
     */
    @Test
    public void rmDir() throws IOException {
        fs.delete(new Path("/aaa"), true);
    }
}
