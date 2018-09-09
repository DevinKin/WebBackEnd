package cn.devinkin.jk.domain;

import java.util.Date;

/**
 * @Description:	Finance
 * @Author:	devinkin
 * @Company:
 * @CreateDate:		2018-9-9 10:43:29
 */

public class Finance extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String id;	  	
	private Date inputDate;			
	private String inputBy;			
	private Integer state;			//0草稿 1已上报

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public Date getInputDate() {
		return this.inputDate;
	}
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}	
	
	public String getInputBy() {
		return this.inputBy;
	}
	public void setInputBy(String inputBy) {
		this.inputBy = inputBy;
	}	
	
	public Integer getState() {
		return this.state;
	}
	public void setState(Integer state) {
		this.state = state;
	}	
	
	
	
}
