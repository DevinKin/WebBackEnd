package cn.devinkin.jk.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @Description:	ShippingOrder
 * @Author:	devinkin
 * @Company:
 * @CreateDate:		2018-9-8 15:53:13
 */

public class ShippingOrder extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String id;	  	
	private String orderType;			//SEA海运AIR空运
	private String shipper;			
	private String consignee;			
	private String notifyParty;			
	private String lcNo;			
	private String portOfLoading;			
	private String portOfTrans;			
	private String portOfDischarge;			
	private Date loadingDate;			
	private Date limitDate;			
	private String isBatch;			//1是0否
	private String isTrans;			//1是0否
	private String copyNum;			
	private String remark;			
	private String specialCondition;			
	private String freight;			
	private String checkBy;			
	private Integer state;			//0草稿 1已上报

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getOrderType() {
		return this.orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}	
	
	public String getShipper() {
		return this.shipper;
	}
	public void setShipper(String shipper) {
		this.shipper = shipper;
	}	
	
	public String getConsignee() {
		return this.consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}	
	
	public String getNotifyParty() {
		return this.notifyParty;
	}
	public void setNotifyParty(String notifyParty) {
		this.notifyParty = notifyParty;
	}	
	
	public String getLcNo() {
		return this.lcNo;
	}
	public void setLcNo(String lcNo) {
		this.lcNo = lcNo;
	}	
	
	public String getPortOfLoading() {
		return this.portOfLoading;
	}
	public void setPortOfLoading(String portOfLoading) {
		this.portOfLoading = portOfLoading;
	}	
	
	public String getPortOfTrans() {
		return this.portOfTrans;
	}
	public void setPortOfTrans(String portOfTrans) {
		this.portOfTrans = portOfTrans;
	}	
	
	public String getPortOfDischarge() {
		return this.portOfDischarge;
	}
	public void setPortOfDischarge(String portOfDischarge) {
		this.portOfDischarge = portOfDischarge;
	}	
	
	public Date getLoadingDate() {
		return this.loadingDate;
	}
	public void setLoadingDate(Date loadingDate) {
		this.loadingDate = loadingDate;
	}	
	
	public Date getLimitDate() {
		return this.limitDate;
	}
	public void setLimitDate(Date limitDate) {
		this.limitDate = limitDate;
	}	
	
	public String getIsBatch() {
		return this.isBatch;
	}
	public void setIsBatch(String isBatch) {
		this.isBatch = isBatch;
	}	
	
	public String getIsTrans() {
		return this.isTrans;
	}
	public void setIsTrans(String isTrans) {
		this.isTrans = isTrans;
	}	
	
	public String getCopyNum() {
		return this.copyNum;
	}
	public void setCopyNum(String copyNum) {
		this.copyNum = copyNum;
	}	
	
	public String getRemark() {
		return this.remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}	
	
	public String getSpecialCondition() {
		return this.specialCondition;
	}
	public void setSpecialCondition(String specialCondition) {
		this.specialCondition = specialCondition;
	}	
	
	public String getFreight() {
		return this.freight;
	}
	public void setFreight(String freight) {
		this.freight = freight;
	}	
	
	public String getCheckBy() {
		return this.checkBy;
	}
	public void setCheckBy(String checkBy) {
		this.checkBy = checkBy;
	}	
	
	public Integer getState() {
		return this.state;
	}
	public void setState(Integer state) {
		this.state = state;
	}	
	
	
	
}
