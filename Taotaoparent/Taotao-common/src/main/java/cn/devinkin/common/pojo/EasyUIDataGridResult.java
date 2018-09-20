package cn.devinkin.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author devinkin
 * <p>Title: EasyUIDataGridResult</p>
 * <p>Description: </p>
 * @version 1.0
 * @see
 * @since 16:04 2018/9/19
 */
public class EasyUIDataGridResult implements Serializable {
    private Long total;
    private List rows;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
