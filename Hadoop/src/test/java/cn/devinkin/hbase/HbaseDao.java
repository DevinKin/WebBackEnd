package cn.devinkin.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class HbaseDao {

    private HBaseAdmin admin = null;
    private Configuration conf = null;

    @Before
    public void init() throws Exception {
        conf = HBaseConfiguration.create();
        conf.set("hbase.cluster.distributed", "true");
        conf.set("hbase.zookeeper.quorum", "zookeeper01:2181,zookeeper02:2181,zookeeper03:2181");

        admin = new HBaseAdmin(conf);
    }

    @Test
    public void testCreateTable() throws Exception {

        // 设置表名
        TableName tableName = TableName.valueOf("hbaseJavaapi");
        // 设置表信息描述
        HTableDescriptor descriptor = new HTableDescriptor(tableName);

        // 添加列族
        HColumnDescriptor baseInfo = new HColumnDescriptor("base_info");
        HColumnDescriptor extraInfo = new HColumnDescriptor("extra_info");
        // 设置列族版本号,最多保存5个版本
        baseInfo.setMaxVersions(5);
        extraInfo.setMaxVersions(5);
        descriptor.addFamily(baseInfo);
        descriptor.addFamily(extraInfo);

        admin.createTable(descriptor);

    }

    @Test
    public void testPut() throws Exception {
        // 获得表对象
        HTable hbaseJavaapi = new HTable(conf, "hbaseJavaapi");

        // 获得Put对象,给定行键初始化
        Put name = new Put(Bytes.toBytes("rk0003"));
        // 设定列族,键,值
        name.add(Bytes.toBytes("base_info"), Bytes.toBytes("name"), Bytes.toBytes("hanxin"));

        Put age = new Put(Bytes.toBytes("rk0003"));
        age.add(Bytes.toBytes("base_info"), Bytes.toBytes("age"), Bytes.toBytes(30));

        // 将put对象加入put数组
        ArrayList<Put> puts = new ArrayList<Put>();
        puts.add(name);
        puts.add(age);

        // 插入数据
        hbaseJavaapi.put(puts);
        hbaseJavaapi.close();
    }

    @Test
    public void testGet() throws Exception {
        // 获取表对象
        HTable hbaseJavaapi = new HTable(conf, "hbaseJavaapi");

        // 获取Get对象
        Get get = new Get(Bytes.toBytes("rk0001"));
        // 设置get的最大版本数
        get.setMaxVersions(5);
        // 获取结果对象
        Result result = hbaseJavaapi.get(get);
        List<Cell> cells = result.listCells();

        // 使用KeyValue遍历结果
        for (KeyValue kv : result.list()) {
            // 获取族名
            String family = new String(kv.getFamily());
            System.out.println(family);
            // 获取列名(key)
            String quaifier = new String(kv.getQualifier());
            System.out.println(quaifier);
            // 获取列值(value)
            System.out.println(kv.getValue());

            // 可以从reesult取出特定的value
            byte[] bytes = result.getValue(Bytes.toBytes("base_info"), Bytes.toBytes("name"));
            System.out.println(new String(bytes));
        }
        hbaseJavaapi.close();
    }

    @Test
    public void testScan() throws Exception {
        // 获取表对象
        HTable hbaseJavaapi = new HTable(conf, "hbaseJavaapi");

        // 获取Scan对象,设置起始行键,结束行键
        Scan scan = new Scan(Bytes.toBytes("rk0001"), Bytes.toBytes("rk0003"));

        // 获取Filter对象,前缀过滤器,针对行键
        Filter filter = new PrefixFilter(Bytes.toBytes("rk"));

        // 添加列族
        scan.addFamily(Bytes.toBytes("base_info"));
        // 获取结果过滤器
        ResultScanner scanner = hbaseJavaapi.getScanner(scan);

        // 遍历结果
        for (Result r : scanner) {
            byte[] bytes = r.getValue(Bytes.toBytes("base_info"), Bytes.toBytes("name"));
            System.out.println(new String(bytes));
        }
        hbaseJavaapi.close();
    }

    @Test
    public void testDel() throws Exception {
        // 获取表对象
        HTable hbaseJavaapi = new HTable(conf, "hbaseJavaapi");

        // 获取删除对象,用行键进行初始化
        Delete del = new Delete(Bytes.toBytes("rk0001"));
        // 删除列,指定列族和列名
        del.deleteColumn(Bytes.toBytes("base_info"), Bytes.toBytes("age"));
        // 执行删除
        hbaseJavaapi.delete(del);
        hbaseJavaapi.close();

    }

    @Test
    public void testDrop() throws Exception {
        // 获取admin对象
        admin.disableTable("hbaseJavaapi");
        admin.deleteTable("hbaseJavaapi");
        admin.close();
    }

}
