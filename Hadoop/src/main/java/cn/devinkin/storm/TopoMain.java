package cn.devinkin.storm;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;

/**
 * 组织各个处理组件形成一个完整的处理流程,就是topology
 * 并且该topology提交给storm集群去运行,topology提交到集群后就将永无休止的执行,不会停止
 * @author devinkin
 */
public class TopoMain {
    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
        TopologyBuilder builder = new TopologyBuilder();

        // 将我们的spout组件设置到topology中,并发度是4,executor数量
        // parallelism_hit:4表示用4个executor来执行这个组件
        // setNumTasks(8)设置的是该组件执行时并发task数量,也就是一个executor会运行2个task
        builder.setSpout("randomspout", new RandomWordSpout(), 4);

        // 将大写转换bolt组件设置到toplogy中,并指定它的randomspout组件的消息m,线程数量executor数量
        builder.setBolt("upperbolt", new UpperBolt(), 4).shuffleGrouping("randomspout");

        // 将后缀转换bolt组件设置到toplogy中,并指定它的upperbolt组件的消息,并发度是4,线程数量executor数量
        builder.setBolt("suffixbolt", new SuffixBolt(), 4).shuffleGrouping("upperbolt");

        // 用builder来创建一个topology
        StormTopology demotop = builder.createTopology();

        // 配置一些topology在集群中运行的参数
        Config conf = new Config();
        // topology设置worker数量
        conf.setNumWorkers(4);
        conf.setDebug(true);
        // 取消toplogy事务机制
        conf.setNumAckers(0);

        // 将这个topology提交给storm集群运行
        StormSubmitter.submitTopology("demotopo", conf, demotop);

    }
}
