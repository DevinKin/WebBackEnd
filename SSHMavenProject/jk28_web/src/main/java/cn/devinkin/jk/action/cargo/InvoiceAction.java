package cn.devinkin.jk.action.cargo;

import java.util.Date;
import java.util.List;

import cn.devinkin.jk.action.BaseAction;
import cn.devinkin.jk.domain.Invoice;
import cn.devinkin.jk.domain.PackingList;
import cn.devinkin.jk.domain.ShippingOrder;
import cn.devinkin.jk.service.cargo.PackingListService;
import cn.devinkin.jk.service.cargo.ShippingOrderService;
import cn.devinkin.jk.utils.Page;
import cn.devinkin.jk.service.cargo.InvoiceService;

import cn.devinkin.jk.utils.UtilFuns;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description:	Invoice
 * @Author:	devinkin
 * @Company:
 * @CreateDate:		2018-9-9 8:37:01
 */

public class InvoiceAction extends BaseAction implements ModelDriven<Invoice> {
	//注入service
	private InvoiceService invoiceService;
	public void setInvoiceService(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	private ShippingOrderService shippingOrderService;

	public void setShippingOrderService(ShippingOrderService shippingOrderService) {
		this.shippingOrderService = shippingOrderService;
	}

	private PackingListService packingListService;

	public void setPackingListService(PackingListService packingListService) {
		this.packingListService = packingListService;
	}

	//model驱动
	private Invoice model = new Invoice();
	@Override
	public Invoice getModel() {
		return this.model;
	}
	
	//作为属性驱动，接收并封装页面参数
	private Page page = new Page();			//封装页面的参数，主要当前页参数
	public void setPage(Page page) {
		this.page = page;
	}


	//列表展示
	public String list(){
		String hql = "from Invoice o";			//查询所有内容
		//给页面提供分页数据
		page.setUrl("invoiceAction_list");		//配置分页按钮的转向链接
		page = invoiceService.findPage(hql, page, Invoice.class, null);
		super.put("page", page);
		
		return "plist";						//page list
	}

	//转向新增页面
	public String tocreate(){
		//准备数据
		String hql = "from ShippingOrder where state = 1";
		List<ShippingOrder> shippingOrderList =  shippingOrderService.find(hql, ShippingOrder.class, null);
		super.put("shippingOrderList", shippingOrderList);

		return "pcreate";
	}
	
	//新增保存
	public String insert(){
		// 设置发票状态为0
		model.setState(0);
	    // 设置装箱单的发票号,从装箱单中获取发票号
		// 获取装箱单,发票id和装箱单id一致
		PackingList packingList = packingListService.get(PackingList.class, model.getId());
		// 设置发票号
        packingList.setInvoiceNo(model.getScNo());
        // 设置发票日期
		packingList.setInvoiceDate(new Date());
		invoiceService.saveOrUpdate(model);
		
		return "alist";			//返回列表，重定向action_list
	}

	//转向修改页面
	public String toupdate(){
		//准备数据
//		List<Invoice> invoiceList = invoiceService.invoiceList();
//		super.put("invoiceList", invoiceList);		//页面就可以访问invoiceList
				
		//准备修改的数据
		Invoice obj = invoiceService.get(Invoice.class, model.getId());
		super.push(obj);

		return "pupdate";
	}
	
	//修改保存
	public String update(){
		Invoice invoice = invoiceService.get(Invoice.class, model.getId());
		
		//设置修改的属性，根据业务去掉自动生成多余的属性
		invoice.setScNo(model.getScNo());
		invoice.setBlNo(model.getBlNo());
		invoice.setTradeTerms(model.getTradeTerms());
		invoiceService.saveOrUpdate(invoice);
		
		return "alist";
	}
	
	//删除一条
	public String deleteById(){
		invoiceService.deleteById(Invoice.class, model.getId());
		
		return "alist";
	}
	
	
	//删除多条
	public String delete(){
		invoiceService.delete(Invoice.class, model.getId().split(", "));
		
		return "alist";
	}
	
	//查看
	public String toview(){
		Invoice obj = invoiceService.get(Invoice.class, model.getId());
		super.push(obj);
		
		return "pview";			//转向查看页面
	}

	/**
	 * 提交
	 */
	public String submit() throws Exception {
		// 1.获取选中的所有id
		String[] ids = model.getId().split(", ");

		// 2. 遍历ids，并加载出每个装箱单的对象，再修改装箱单的状态
		invoiceService.changeState(ids, 1);

		return "alist";
	}


	/**
	 * 取消
	 */
	public String cancel() throws Exception {
		// 1.获取选中的所有id
		String[] ids = model.getId().split(", ");
		// 2. 遍历ids，并加载出每个装箱单的对象，再修改装箱单的状态
		invoiceService.changeState(ids, 0);

		return "alist";
	}
}
