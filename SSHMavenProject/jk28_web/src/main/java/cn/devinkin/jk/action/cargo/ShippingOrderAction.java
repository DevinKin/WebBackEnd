package cn.devinkin.jk.action.cargo;


import cn.devinkin.jk.action.BaseAction;
import cn.devinkin.jk.domain.PackingList;
import cn.devinkin.jk.domain.ShippingOrder;
import cn.devinkin.jk.service.cargo.PackingListService;
import cn.devinkin.jk.utils.Page;
import cn.devinkin.jk.service.cargo.ShippingOrderService;

import com.opensymphony.xwork2.ModelDriven;

import java.util.List;

/**
 * @Description:	ShippingOrder
 * @Author:	devinkin
 * @Company:
 * @CreateDate:		2018-9-8 16:15:01
 */

public class ShippingOrderAction extends BaseAction implements ModelDriven<ShippingOrder> {
	//注入service
	private ShippingOrderService shippingOrderService;
	public void setShippingOrderService(ShippingOrderService shippingOrderService) {
		this.shippingOrderService = shippingOrderService;
	}

	private PackingListService packingListService;

	public void setPackingListService(PackingListService packingListService) {
		this.packingListService = packingListService;
	}

	//model驱动
	private ShippingOrder model = new ShippingOrder();
	@Override
	public ShippingOrder getModel() {
		return this.model;
	}
	
	//作为属性驱动，接收并封装页面参数
	private Page page = new Page();			//封装页面的参数，主要当前页参数
	public void setPage(Page page) {
		this.page = page;
	}


	//列表展示
	public String list(){
		String hql = "from ShippingOrder o";			//查询所有内容
		//给页面提供分页数据
		page.setUrl("shippingOrderAction_list");		//配置分页按钮的转向链接
		page = shippingOrderService.findPage(hql, page, ShippingOrder.class, null);
		super.put("page", page);
		
		return "plist";						//page list
	}
	
	//转向新增页面
	public String tocreate(){
		//准备装箱单数据
		String hql = "from PackingList where state = 1";
		List<PackingList> packingLists = packingListService.find(hql, PackingList.class,null);

		super.put("packingLists", packingLists);
		
		return "pcreate";
	}
	
	//新增保存
	public String insert(){
		// 设置委托单状态为0
		model.setState(0);
		shippingOrderService.saveOrUpdate(model);
		
		return "alist";			//返回列表，重定向action_list
	}

	//转向修改页面
	public String toupdate(){
		//准备数据
//		List<ShippingOrder> shippingOrderList = shippingOrderService.shippingOrderList();
//		super.put("shippingOrderList", shippingOrderList);		//页面就可以访问shippingOrderList
				
		//准备修改的数据
		ShippingOrder obj = shippingOrderService.get(ShippingOrder.class, model.getId());
		super.push(obj);
		
		return "pupdate";
	}
	
	//修改保存
	public String update(){
		ShippingOrder shippingOrder = shippingOrderService.get(ShippingOrder.class, model.getId());
		
		//设置修改的属性，根据业务去掉自动生成多余的属性
		shippingOrder.setOrderType(model.getOrderType());
		shippingOrder.setShipper(model.getShipper());
		shippingOrder.setConsignee(model.getConsignee());
		shippingOrder.setNotifyParty(model.getNotifyParty());
		shippingOrder.setLcNo(model.getLcNo());
		shippingOrder.setPortOfLoading(model.getPortOfLoading());
		shippingOrder.setPortOfTrans(model.getPortOfTrans());
		shippingOrder.setPortOfDischarge(model.getPortOfDischarge());
		shippingOrder.setLoadingDate(model.getLoadingDate());
		shippingOrder.setLimitDate(model.getLimitDate());
		shippingOrder.setIsBatch(model.getIsBatch());
		shippingOrder.setIsTrans(model.getIsTrans());
		shippingOrder.setCopyNum(model.getCopyNum());
		shippingOrder.setRemark(model.getRemark());
		shippingOrder.setSpecialCondition(model.getSpecialCondition());
		shippingOrder.setFreight(model.getFreight());
		shippingOrder.setCheckBy(model.getCheckBy());

		shippingOrderService.saveOrUpdate(shippingOrder);
		
		return "alist";
	}
	
	//删除一条
	public String deleteById(){
		shippingOrderService.deleteById(ShippingOrder.class, model.getId());
		
		return "alist";
	}
	
	
	//删除多条
	public String delete(){
		shippingOrderService.delete(ShippingOrder.class, model.getId().split(", "));
		
		return "alist";
	}
	
	//查看
	public String toview(){
		ShippingOrder obj = shippingOrderService.get(ShippingOrder.class, model.getId());
		super.push(obj);
		
		return "pview";			//转向查看页面
	}

	/**
	 * 提交
	 */
	public String submit() throws Exception {
		// 1.获取选中的所有id
		String[] ids = model.getId().split(", ");

		// 2. 遍历ids，并加载出每个出口报运的对象，再修改出口报运的状态
		shippingOrderService.changeState(ids, 1);

		return "alist";
	}


	/**
	 * 取消
	 */
	public String cancel() throws Exception {
		// 1.获取选中的所有id
		String[] ids = model.getId().split(", ");
		// 2. 遍历ids，并加载出每个出口报运的对象，再修改出口报运的状态
		shippingOrderService.changeState(ids, 0);

		return "alist";
	}
}
