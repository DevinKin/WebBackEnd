package cn.devinkin.jk.domain;


/**
 * @Description:	Invoice
 * @Author:	devinkin
 * @Company:
 * @CreateDate:		2018-9-9 8:37:01
 */

public class Invoice extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String id;	  	
	private String scNo;			//packing.getExportNos S/C就是报运的合同号
	private String blNo;			//提单号
	private String tradeTerms;			
	private Integer state;			//0草稿 1已上报

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getScNo() {
		return this.scNo;
	}
	public void setScNo(String scNo) {
		this.scNo = scNo;
	}	
	
	public String getBlNo() {
		return this.blNo;
	}
	public void setBlNo(String blNo) {
		this.blNo = blNo;
	}	
	
	public String getTradeTerms() {
		return this.tradeTerms;
	}
	public void setTradeTerms(String tradeTerms) {
		this.tradeTerms = tradeTerms;
	}	
	
	public Integer getState() {
		return this.state;
	}
	public void setState(Integer state) {
		this.state = state;
	}	
	
	
	
}
