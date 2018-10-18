package cn.devinkin.storm.topology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import cn.devinkin.storm.bolt.WordSpliter;
import cn.devinkin.storm.bolt.WriteBolt;
import cn.devinkin.storm.spout.MessageScheme;
import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;

public class KafkaTopo {
    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
        String topic = "sufei";
        String zkRoot = "/kafka-storm";
        String spoutId = "KafkaSpout";
        BrokerHosts brokerHosts = new ZkHosts("namenode01:2181,namenode02:2181,resourcemanager01:2181");
        SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, topic, zkRoot, spoutId);
        spoutConfig.forceFromStart = true;
        spoutConfig.scheme = new SchemeAsMultiScheme(new MessageScheme());
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout(spoutId, new KafkaSpout(spoutConfig));
        builder.setBolt("word-spilter", new WordSpliter()).shuffleGrouping(spoutId);
        builder.setBolt("writer", new WriteBolt(), 4).fieldsGrouping("word-spilter", new Fields("word"));

        Config conf = new Config();
        conf.setNumWorkers(4);
        conf.setDebug(false);
        LocalCluster cluster = new LocalCluster();
//        cluster.submitTopology("sufei-topo", conf, builder.createTopology());
        StormSubmitter.submitTopology("sufei-topo", conf, builder.createTopology());
    }
}
