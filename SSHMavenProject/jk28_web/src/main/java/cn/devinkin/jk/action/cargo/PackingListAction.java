package cn.devinkin.jk.action.cargo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.devinkin.jk.action.BaseAction;
import cn.devinkin.jk.domain.Export;
import cn.devinkin.jk.domain.PackingList;
import cn.devinkin.jk.service.cargo.ExportService;
import cn.devinkin.jk.service.cargo.PackingListService;
import cn.devinkin.jk.utils.Page;

import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description:	PackingList
 * @Author:	devinkin
 * @Company:
 * @CreateDate:		2018-9-7 10:54:23
 */

public class PackingListAction extends BaseAction implements ModelDriven<PackingList> {
	//注入service
	private PackingListService packingListService;
	public void setPackingListService(PackingListService packingListService) {
		this.packingListService = packingListService;
	}
	// 注入ExportService
    private ExportService exportService;

	public void setExportService(ExportService exportService) {
		this.exportService = exportService;
	}

	//model驱动
	private PackingList model = new PackingList();
	@Override
	public PackingList getModel() {
		return this.model;
	}
	
	//作为属性驱动，接收并封装页面参数
	private Page page = new Page();			//封装页面的参数，主要当前页参数
	public void setPage(Page page) {
		this.page = page;
	}


	//列表展示
	public String list(){
		String hql = "from PackingList o";			//查询所有内容
		//给页面提供分页数据
		page.setUrl("packingListAction_list");		//配置分页按钮的转向链接
		page = packingListService.findPage(hql, page, PackingList.class, null);
		super.put("page", page);
		
		return "plist";						//page list
	}
	
	//转向新增页面
	public String tocreate(){
		//准备数据
		// 海关申报成功的出货表
		String hql = "from Export where state = 2";
		List<Export> exportList = exportService.find(hql, Export.class, null);
		super.put("exportList", exportList);

		return "pcreate";
	}
	
	//新增保存l
	public String insert(){
	    // 新增装箱单时状态为0
		model.setState(0);
		packingListService.saveOrUpdate(model);
		
		return "alist";			//返回列表，重定向action_list
	}

	//转向修改页面
	public String toupdate(){

		//准备修改的数据
		PackingList obj = packingListService.get(PackingList.class, model.getId());
		super.push(obj);
		
		return "pupdate";
	}
	
	//修改保存
	public String update(){
		PackingList packingList = packingListService.get(PackingList.class, model.getId());
		
		//设置修改的属性，根据业务去掉自动生成多余的属性
		packingList.setSeller(model.getSeller());
		packingList.setBuyer(model.getBuyer());
		packingList.setInvoiceNo(model.getInvoiceNo());
		packingList.setInvoiceDate(model.getInvoiceDate());
		packingList.setMarks(model.getMarks());
		packingList.setDescriptions(model.getDescriptions());

		packingListService.saveOrUpdate(packingList);
		
		return "alist";
	}
	
	//删除一条
	public String deleteById(){
		packingListService.deleteById(PackingList.class, model.getId());
		
		return "alist";
	}
	
	
	//删除多条
	public String delete(){
		packingListService.delete(PackingList.class, model.getId().split(", "));
		
		return "alist";
	}
	
	//查看
	public String toview(){
		// 获取对应的装箱单
		PackingList obj = packingListService.get(PackingList.class, model.getId());

		// 获取报运单号列表
		String ids = obj.getExportIds();
		System.out.println(ids);
		String[] exportIds = ids.split(",");
		System.out.println(Arrays.toString(exportIds));
		List<Export> exportList = new ArrayList<Export>();
		// 遍历宝运单号列表,获取报运单对象,并放到集合中
		for (String exportId : exportIds) {
			Export export = exportService.get(Export.class, exportId.trim());
			exportList.add(export);
		}
		// 将报运单放入值栈
		super.put("exportList", exportList);
		// 将装箱单放入值栈
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
		packingListService.changeState(ids, 1);

		return "alist";
	}


	/**
	 * 取消
	 */
	public String cancel() throws Exception {
		// 1.获取选中的所有id
		String[] ids = model.getId().split(", ");
		// 2. 遍历ids，并加载出每个装箱单的对象，再修改装箱单的状态
		packingListService.changeState(ids, 0);

		return "alist";
	}

	/**
	 * 装箱
	 */
	public String packed() throws Exception {
		// 1.获取选中的所有id
		String[] ids = model.getId().split(", ");
		// 2. 遍历ids，并加载出每个装箱单的对象，再修改装箱单的状态
		packingListService.changeState(ids, 2);

		return "alist";
	}
}
