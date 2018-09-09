package cn.devinkin.jk.action.basicinfo;


import cn.devinkin.jk.action.BaseAction;
import cn.devinkin.jk.domain.Factory;
import cn.devinkin.jk.utils.Page;
import cn.devinkin.jk.service.basicinfo.FactoryService;

import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description:	Factory
 * @Author:	devinkin
 * @Company:
 * @CreateDate:		2018-9-9 12:47:29
 */

public class FactoryAction extends BaseAction implements ModelDriven<Factory> {
	//注入service
	private FactoryService factoryService;
	public void setFactoryService(FactoryService factoryService) {
		this.factoryService = factoryService;
	}
	
	//model驱动
	private Factory model = new Factory();
	@Override
	public Factory getModel() {
		return this.model;
	}
	
	//作为属性驱动，接收并封装页面参数
	private Page page = new Page();			//封装页面的参数，主要当前页参数
	public void setPage(Page page) {
		this.page = page;
	}


	//列表展示
	public String list(){
		String hql = "from Factory o";			//查询所有内容
		//给页面提供分页数据
		page = factoryService.findPage(hql, page, Factory.class, null);
		page.setUrl("factoryAction_list");		//配置分页按钮的转向链接
        super.push(page);

		return "plist";						//page list
	}
	
	//转向新增页面
	public String tocreate(){
		//准备数据

		return "pcreate";
	}
	
	//新增保存
	public String insert(){
		// 设置厂家的状态为合作
		model.setState(1);
		factoryService.saveOrUpdate(model);
		return "alist";			//返回列表，重定向action_list
	}

	//转向修改页面
	public String toupdate(){
		//准备数据

		//准备修改的数据
		Factory obj = factoryService.get(Factory.class, model.getId());
		super.push(obj);

		return "pupdate";
	}
	
	//修改保存
	public String update(){
		Factory factory = factoryService.get(Factory.class, model.getId());
		
		//设置修改的属性，根据业务去掉自动生成多余的属性
		factory.setCtype(model.getCtype());
		factory.setFullName(model.getFullName());
		factory.setFactoryName(model.getFactoryName());
		factory.setContacts(model.getContacts());
		factory.setPhone(model.getPhone());
		factory.setMobile(model.getMobile());
		factory.setFax(model.getFax());
		factory.setAddress(model.getAddress());
		factory.setInspector(model.getInspector());
		factory.setRemark(model.getRemark());
		factory.setOrderNo(model.getOrderNo());

		factoryService.saveOrUpdate(factory);
		
		return "alist";
	}
	
	//删除一条
	public String deleteById(){
		factoryService.deleteById(Factory.class, model.getId());
		
		return "alist";
	}
	
	
	//删除多条
	public String delete(){
		factoryService.delete(Factory.class, model.getId().split(", "));
		
		return "alist";
	}
	
	//查看
	public String toview(){
		Factory obj = factoryService.get(Factory.class, model.getId());
		super.push(obj);
		
		return "pview";			//转向查看页面
	}

	/**
	 * 提交
	 */
	public String cooperate() throws Exception {
		// 1.获取选中的所有id
		String[] ids = model.getId().split(", ");

		// 2. 遍历ids，并加载出每个厂家对象，再修改厂家的状态
		factoryService.changeState(ids, 1);

		return "alist";
	}


	/**
	 * 取消
	 */
	public String cancel() throws Exception {
		// 1.获取选中的所有id
		String[] ids = model.getId().split(", ");
		// 2. 遍历ids，并加载出每个厂家对象，再修改厂家的状态
		factoryService.changeState(ids, 2);

		return "alist";
	}
}
