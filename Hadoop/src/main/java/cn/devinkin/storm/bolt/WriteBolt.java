package cn.devinkin.storm.bolt;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class WriteBolt extends BaseBasicBolt {
    private FileWriter writer = null;

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        try {
            writer = new FileWriter("/tmp/stormdata/kafka/" + UUID.randomUUID());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        String s = tuple.getString(0);
        try {
            writer.write(s);
            writer.write("\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
