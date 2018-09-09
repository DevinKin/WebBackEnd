package cn.devinkin.jk.service.cargo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.devinkin.jk.domain.Invoice;
import cn.devinkin.jk.utils.Page;

/**
 * @Description:	Invoice
 * @Author:	devinkin
 * @Company:
 * @CreateDate:		2018-9-9 8:37:01
 */

public interface InvoiceService {

	public List<Invoice> find(String hql, Class<Invoice> entityClass, Object[] params);
	public Invoice get(Class<Invoice> entityClass, Serializable id);
	public Page<Invoice> findPage(String hql, Page<Invoice> page, Class<Invoice> entityClass, Object[] params);
	
	public void saveOrUpdate(Invoice entity);
	public void saveOrUpdateAll(Collection<Invoice> entitys);
	
	public void deleteById(Class<Invoice> entityClass, Serializable id);
	public void delete(Class<Invoice> entityClass, Serializable[] ids);

	public void changeState(String[] ids, Integer state);
}
