package cn.devinkin.storm;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class UpperBolt extends BaseBasicBolt {
    /**
     * 业务处理逻辑
     * @param tuple
     * @param basicOutputCollector
     */
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        // 先获取到上一个组件传递过来的数据,数据在tuple里面
        String godName = tuple.getString(0);

        // 将商品名称转换成大写
        String godNameUpper = godName.toUpperCase();

        // 将转换完成的商品发送出去
        basicOutputCollector.emit(new Values(godNameUpper));
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("uppername"));
    }
}
