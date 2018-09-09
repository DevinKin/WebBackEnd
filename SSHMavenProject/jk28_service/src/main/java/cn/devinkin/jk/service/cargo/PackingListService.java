package cn.devinkin.jk.service.cargo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.devinkin.jk.domain.PackingList;
import cn.devinkin.jk.utils.Page;

/**
 * @Description:	PackingList
 * @Author:	devinkin
 * @Company:
 * @CreateDate:		2018-9-7 10:54:23
 */

public interface PackingListService {

	public List<PackingList> find(String hql, Class<PackingList> entityClass, Object[] params);
	public PackingList get(Class<PackingList> entityClass, Serializable id);
	public Page<PackingList> findPage(String hql, Page<PackingList> page, Class<PackingList> entityClass, Object[] params);
	
	public void saveOrUpdate(PackingList entity);
	public void saveOrUpdateAll(Collection<PackingList> entitys);
	
	public void deleteById(Class<PackingList> entityClass, Serializable id);
	public void delete(Class<PackingList> entityClass, Serializable[] ids);

    void changeState(String[] ids, Integer i);
}
