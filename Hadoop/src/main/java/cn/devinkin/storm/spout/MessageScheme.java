package cn.devinkin.storm.spout;

import backtype.storm.spout.Scheme;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class MessageScheme implements Scheme {
    public List<Object> deserialize(byte[] bytes) {
        try {
            String msg = new String(bytes, "UTF-8");
            return new Values(msg);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Fields getOutputFields() {
        return new Fields("msg");
    }
}
