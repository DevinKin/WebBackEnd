package cn.devinkin.jk.domain;

import java.io.Serializable;

/**
 * @Description:	ExtCproductService接口
 * @Author:			传智播客 java学院	传智宋江
 * @Company:		http://java.itcast.cn
 * @CreateDate:		2015-1-10 16:01:08
 */

public class ExtCproduct extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private ContractProduct contractProduct;	//附件和货物，多对一
	private Factory factory;					//附件和厂家，多对一
	
	private String id;	  	
	private String factoryName;			//工厂名
	private String productNo;			//产品号
	private String productImage;		//图片	
	private String productDesc;			//产品描述
	private String packingUnit;			//  包装单位   PCS/SETS
	private Integer cnumber;			//数量
	private Double price;			    //单价
	private Double amount;				//总金额 　自动计算: 数量x单价
	private String productRequest;		//要求	
	private Integer orderNo;		   //排序号	

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getFactoryName() {
		return this.factoryName;
	}
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}	
	
	public String getProductNo() {
		return this.productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}	
	
	public String getProductImage() {
		return this.productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}	
	
	public String getProductDesc() {
		return this.productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}	
	
	public String getPackingUnit() {
		return this.packingUnit;
	}
	public void setPackingUnit(String packingUnit) {
		this.packingUnit = packingUnit;
	}	
	
	public Integer getCnumber() {
		return this.cnumber;
	}
	public void setCnumber(Integer cnumber) {
		this.cnumber = cnumber;
	}	
	
	public Double getPrice() {
		return this.price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}	
	
	public Double getAmount() {
		return this.amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}	
	
	public String getProductRequest() {
		return this.productRequest;
	}
	public void setProductRequest(String productRequest) {
		this.productRequest = productRequest;
	}	
	
	public Integer getOrderNo() {
		return this.orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public ContractProduct getContractProduct() {
		return contractProduct;
	}
	public void setContractProduct(ContractProduct contractProduct) {
		this.contractProduct = contractProduct;
	}
	public Factory getFactory() {
		return factory;
	}
	public void setFactory(Factory factory) {
		this.factory = factory;
	}	
}
