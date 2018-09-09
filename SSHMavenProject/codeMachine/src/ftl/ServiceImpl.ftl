package ${ package }.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import ${ package }.dao.BaseDao;
import ${ package }.domain.${ className };
import ${ package }.utils.Page;
import ${ package }.service.${ className }Service;
import org.springframework.transaction.annotation.Transactional;

<#import "CopyRight.ftl" as my>
<@my.CopyRight/>

@Transactional
public class ${ className }ServiceImpl implements ${ className }Service {
	//spring注入dao
	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<${ className }> find(String hql, Class<${ className }> entityClass, Object[] params) {
		return baseDao.find(hql, ${ className }.class, params);
	}

	@Override
	public ${ className } get(Class<${ className }> entityClass, Serializable id) {
		return baseDao.get(${ className }.class, id);
	}

	@Override
	public Page<${ className }> findPage(String hql, Page<${ className }> page, Class<${ className }> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, ${ className }.class, params);
	}


	@Override
	public void saveOrUpdate(${ className } entity) {
		if(entity.getId()==null){								//代表新增
			entity.setState(1);									//状态：0停用1启用 默认启用
		}
		baseDao.saveOrUpdate(entity);
	}



	@Override
	public void saveOrUpdateAll(Collection<${ className }> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	@Override
	public void deleteById(Class<${ className }> entityClass, Serializable id) {
		baseDao.deleteById(${ className }.class, id);
	}

	@Override
	public void delete(Class<${ className }> entityClass, Serializable[] ids) {
		baseDao.delete(${ className }.class, ids);
	}

	@Override
	public void changeState(String[] ids, Integer state) {
		for (String id : ids) {
			${ className } obj = this.get(${ className }.class, id);
			obj.setState(state);
		}
	}
}
