package cn.devinkin.common.pojo;

import java.io.Serializable;

/**
 * @author devinkin
 * <p>Title: EasyUITreeNode</p>
 * <p>Description: </p>
 * @version 1.0
 * @see
 * @since 8:37 2018/9/20
 */
public class EasyUITreeNode implements Serializable {
    private long id;
    private String text;
    private String state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
