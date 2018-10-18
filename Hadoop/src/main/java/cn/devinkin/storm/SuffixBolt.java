package cn.devinkin.storm;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class SuffixBolt extends BaseBasicBolt {

    public FileWriter fileWriter = null;

    /**
     * 在bolt组件运行过程中只会被调用一次,用于初始化各种实例
     * @param stormConf
     * @param context
     */
    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        try {
            fileWriter = new FileWriter("/tmp/stormdata/stormoutput/" + UUID.randomUUID());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 该bolt组件的核心处理逻辑
     * 回收到一个tuple消息,就会被调用一次
     * @param tuple
     * @param basicOutputCollector
     */
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        // 先拿到上一个组件发送过来的消息名称
        String upperName = tuple.getString(0);
        String suffixName = upperName + "_itisok";

        // 为上一个组件发送过来的商品名称添加后缀
        try {
            fileWriter.write(suffixName);
            fileWriter.write("\n");
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
