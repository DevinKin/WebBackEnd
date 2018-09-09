package cn.devinkin.jk.service.impl.cargo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.devinkin.jk.dao.BaseDao;
import cn.devinkin.jk.domain.Invoice;
import cn.devinkin.jk.service.cargo.InvoiceService;
import cn.devinkin.jk.utils.Page;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description:	Invoice
 * @Author:	devinkin
 * @Company:
 * @CreateDate:		2018-9-9 8:37:01
 */

@Transactional
public class InvoiceServiceImpl implements InvoiceService {
	//spring注入dao
	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<Invoice> find(String hql, Class<Invoice> entityClass, Object[] params) {
		return baseDao.find(hql, Invoice.class, params);
	}

	@Override
	public Invoice get(Class<Invoice> entityClass, Serializable id) {
		return baseDao.get(Invoice.class, id);
	}

	@Override
	public Page<Invoice> findPage(String hql, Page<Invoice> page, Class<Invoice> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, Invoice.class, params);
	}


	@Override
	public void saveOrUpdate(Invoice entity) {
		if(entity.getId()==null){								//代表新增
			entity.setState(1);									//状态：0停用1启用 默认启用
		}
		baseDao.saveOrUpdate(entity);
	}



	@Override
	public void saveOrUpdateAll(Collection<Invoice> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	@Override
	public void deleteById(Class<Invoice> entityClass, Serializable id) {
		baseDao.deleteById(Invoice.class, id);
	}

	@Override
	public void delete(Class<Invoice> entityClass, Serializable[] ids) {
		baseDao.delete(Invoice.class, ids);
	}

	@Override
	public void changeState(String[] ids, Integer state) {
		for (String id : ids) {
			Invoice obj = this.get(Invoice.class, id);
			obj.setState(state);
		}
	}
}
