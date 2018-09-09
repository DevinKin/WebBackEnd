package cn.devinkin.jk.service.impl.cargo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import cn.devinkin.jk.dao.BaseDao;
import cn.devinkin.jk.domain.Export;
import cn.devinkin.jk.domain.PackingList;
import cn.devinkin.jk.service.cargo.PackingListService;
import cn.devinkin.jk.utils.Page;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description:	PackingList
 * @Author:	devinkin
 * @Company:
 * @CreateDate:		2018-9-7 10:54:23
 */

@Transactional
public class PackingListServiceImpl implements PackingListService {
	//spring注入dao
	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<PackingList> find(String hql, Class<PackingList> entityClass, Object[] params) {
		return baseDao.find(hql, PackingList.class, params);
	}

	@Override
	public PackingList get(Class<PackingList> entityClass, Serializable id) {
		return baseDao.get(PackingList.class, id);
	}

	@Override
	public Page<PackingList> findPage(String hql, Page<PackingList> page, Class<PackingList> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, PackingList.class, params);
	}


	@Override
	public void saveOrUpdate(PackingList entity) {
		String exportNos = "";
		if(entity.getId()==null){								//代表新增
			// 获取通过海关的报运单id集合
		    String[] exportIds = entity.getExportIds().split(",");
			System.out.println(Arrays.toString(exportIds));
		    for (String exportId : exportIds) {
				// 获取报运单对象
				exportId = exportId.trim();
				Export export = baseDao.get(Export.class, exportId);
				// 获取报运单下的购销合同号
				String customerContract = export.getCustomerContract();
				System.out.println(customerContract);
				// 拼接购销合同号
				exportNos += customerContract;
				exportNos += " ";
			}
			// 设置购销合同号
			entity.setExportNos(exportNos);
		}
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void changeState(String[] ids, Integer state) {
		for (String id : ids) {
			PackingList packingList = this.get(PackingList.class, id);
			packingList.setState(state);
			System.out.println(packingList.getId());
			// 可以不写，packingList是持久态对象
			this.saveOrUpdate(packingList);
		}
	}



	@Override
	public void saveOrUpdateAll(Collection<PackingList> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	@Override
	public void deleteById(Class<PackingList> entityClass, Serializable id) {
		baseDao.deleteById(PackingList.class, id);
	}

	@Override
	public void delete(Class<PackingList> entityClass, Serializable[] ids) {
		baseDao.delete(PackingList.class, ids);
	}

}
