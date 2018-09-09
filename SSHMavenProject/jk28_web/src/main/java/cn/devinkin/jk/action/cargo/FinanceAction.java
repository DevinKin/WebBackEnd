package cn.devinkin.jk.action.cargo;

import java.util.List;

import cn.devinkin.jk.action.BaseAction;
import cn.devinkin.jk.domain.Finance;
import cn.devinkin.jk.domain.Invoice;
import cn.devinkin.jk.service.cargo.FinanceService;
import cn.devinkin.jk.service.cargo.InvoiceService;
import cn.devinkin.jk.utils.Page;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description:	Finance
 * @Author:	devinkin
 * @Company:
 * @CreateDate:		2018-9-9 10:43:29
 */

public class FinanceAction extends BaseAction implements ModelDriven<Finance> {
	//注入service
	private FinanceService financeService;
	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}

	private InvoiceService invoiceService;

	public void setInvoiceService(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	//model驱动
	private Finance model = new Finance();
	@Override
	public Finance getModel() {
		return this.model;
	}
	
	//作为属性驱动，接收并封装页面参数
	private Page page = new Page();			//封装页面的参数，主要当前页参数
	public void setPage(Page page) {
		this.page = page;
	}


	//列表展示
	public String list(){
		String hql = "from Finance o";			//查询所有内容
		//给页面提供分页数据
		page.setUrl("financeAction_list");		//配置分页按钮的转向链接
		page = financeService.findPage(hql, page, Finance.class, null);
		super.put("page", page);
		
		return "plist";						//page list
	}
	
	//转向新增页面
	public String tocreate(){
		//准备数据,获取已上报的发票列表
		String hql = "from Invoice where state = 1";
		List<Invoice> invoiceList = invoiceService.find(hql, Invoice.class, null);
		super.put("invoiceList", invoiceList);
		return "pcreate";
	}
	
	//新增保存
	public String insert(){
		// 设置财务报运单的状态
		model.setState(0);
		financeService.saveOrUpdate(model);
		
		return "alist";			//返回列表，重定向action_list
	}

	//转向修改页面
	public String toupdate(){
		//准备数据
//		List<Finance> financeList = financeService.financeList();
//		super.put("financeList", financeList);		//页面就可以访问financeList
				
		//准备修改的数据
		Finance obj = financeService.get(Finance.class, model.getId());
		super.push(obj);

		return "pupdate";
	}
	
	//修改保存
	public String update(){
		Finance finance = financeService.get(Finance.class, model.getId());
		
		//设置修改的属性，根据业务去掉自动生成多余的属性
		finance.setInputDate(model.getInputDate());
		finance.setInputBy(model.getInputBy());

		financeService.saveOrUpdate(finance);
		
		return "alist";
	}
	
	//删除一条
	public String deleteById(){
		financeService.deleteById(Finance.class, model.getId());
		
		return "alist";
	}
	
	
	//删除多条
	public String delete(){
		financeService.delete(Finance.class, model.getId().split(", "));
		
		return "alist";
	}
	
	//查看
	public String toview(){
		Finance obj = financeService.get(Finance.class, model.getId());
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
		financeService.changeState(ids, 1);

		return "alist";
	}


	/**
	 * 取消
	 */
	public String cancel() throws Exception {
		// 1.获取选中的所有id
		String[] ids = model.getId().split(", ");
		// 2. 遍历ids，并加载出每个装箱单的对象，再修改装箱单的状态
		financeService.changeState(ids, 0);

		return "alist";
	}
}
