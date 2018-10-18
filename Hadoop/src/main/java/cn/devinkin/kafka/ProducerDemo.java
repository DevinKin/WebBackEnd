package cn.devinkin.kafka;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

public class ProducerDemo {
    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
        props.put("zk.connect", "namenode01:2181,namenode02:2181,resourcemanager01:2181");
        props.put("metadata.broker.list","namenode01:9092,namenode02:9092,resourcemanager01:9092");
        props.put("serializer.class","kafka.serializer.StringEncoder");
        ProducerConfig config = new ProducerConfig(props);
        Producer<String, String> producer = new Producer<String, String>(config);

        // 发送业务消息
        // 读取文件, 读取内存数据库,读socket端口
        for (int i = 0; i < 1000; i++) {
            Thread.sleep(500);
            producer.send(new KeyedMessage<String, String>("mygirls",
                    "i said love you baby for " + i + " times"));
        }
    }
}
