package cn.devinkin.jk.service.cargo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.devinkin.jk.domain.Finance;
import cn.devinkin.jk.utils.Page;

/**
 * @Description:	Finance
 * @Author:	devinkin
 * @Company:
 * @CreateDate:		2018-9-9 10:43:29
 */

public interface FinanceService {

	public List<Finance> find(String hql, Class<Finance> entityClass, Object[] params);
	public Finance get(Class<Finance> entityClass, Serializable id);
	public Page<Finance> findPage(String hql, Page<Finance> page, Class<Finance> entityClass, Object[] params);
	
	public void saveOrUpdate(Finance entity);
	public void saveOrUpdateAll(Collection<Finance> entitys);
	
	public void deleteById(Class<Finance> entityClass, Serializable id);
	public void delete(Class<Finance> entityClass, Serializable[] ids);

	public void changeState(String[] ids, Integer state);
}
