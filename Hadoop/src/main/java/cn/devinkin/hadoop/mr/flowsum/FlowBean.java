package cn.devinkin.hadoop.mr.flowsum;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FlowBean implements WritableComparable<FlowBean> {

    private String phoneNB;
    private long upFlow;
    private long downFlow;
    private long sumFlow;

    /**
     * 显式的加入一个无参的构造函数,反序列化时,反射机制需要调用空参的构造函数
     */
    public FlowBean() {
    }

    /**
     * 为了数据的初始化方便,加入一个构造函数
     * @param phoneNB 电话号码
     * @param upFlow 上行流量
     * @param downFlow 下行流量
     */
    public FlowBean(String phoneNB, long upFlow, long downFlow) {
        this.phoneNB = phoneNB;
        this.upFlow = upFlow;
        this.downFlow = downFlow;
        this.sumFlow = upFlow + downFlow;
    }

    public String getPhoneNB() {
        return phoneNB;
    }

    public void setPhoneNB(String phoneNB) {
        this.phoneNB = phoneNB;
    }

    public long getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(long upFlow) {
        this.upFlow = upFlow;
    }

    public long getDownFlow() {
        return downFlow;
    }

    public void setDownFlow(long downFlow) {
        this.downFlow = downFlow;
    }

    public long getSumFlow() {
        return sumFlow;
    }

    public void setSumFlow(long sumFlow) {
        this.sumFlow = sumFlow;
    }

    /**
     * 将对象数据序列化到流中
     * @param out
     * @throws IOException
     */
    public void write(DataOutput out) throws IOException {
        out.writeUTF(phoneNB);
        out.writeLong(upFlow);
        out.writeLong(downFlow);
        out.writeLong(sumFlow);
    }

    /**
     * 从数据流中反序列出对象的数据
     * 从数据流中独处的对象字段时,必须跟序列化时的顺序保持一致
     * @param in
     * @throws IOException
     */
    public void readFields(DataInput in) throws IOException {
        phoneNB = in.readUTF();
        upFlow = in.readLong();
        downFlow = in.readLong();
        sumFlow = in.readLong();
    }

    @Override
    public String toString() {
        return "\t" + upFlow + "\t" + downFlow + "\t" +sumFlow;
    }

    public int compareTo(FlowBean fb) {
        // 倒序排序
        return sumFlow > fb.sumFlow ? -1 : 1;
    }
}
