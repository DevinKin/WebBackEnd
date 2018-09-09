package cn.devinkin.jk.service.impl.cargo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.devinkin.jk.dao.BaseDao;
import cn.devinkin.jk.domain.Finance;
import cn.devinkin.jk.service.cargo.FinanceService;
import cn.devinkin.jk.utils.Page;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description:	Finance
 * @Author:	devinkin
 * @Company:
 * @CreateDate:		2018-9-9 10:43:29
 */

@Transactional
public class FinanceServiceImpl implements FinanceService {
	//spring注入dao
	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<Finance> find(String hql, Class<Finance> entityClass, Object[] params) {
		return baseDao.find(hql, Finance.class, params);
	}

	@Override
	public Finance get(Class<Finance> entityClass, Serializable id) {
		return baseDao.get(Finance.class, id);
	}

	@Override
	public Page<Finance> findPage(String hql, Page<Finance> page, Class<Finance> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, Finance.class, params);
	}


	@Override
	public void saveOrUpdate(Finance entity) {
		if(entity.getId()==null){								//代表新增
			entity.setState(1);									//状态：0停用1启用 默认启用
		}
		baseDao.saveOrUpdate(entity);
	}



	@Override
	public void saveOrUpdateAll(Collection<Finance> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	@Override
	public void deleteById(Class<Finance> entityClass, Serializable id) {
		baseDao.deleteById(Finance.class, id);
	}

	@Override
	public void delete(Class<Finance> entityClass, Serializable[] ids) {
		baseDao.delete(Finance.class, ids);
	}

	@Override
	public void changeState(String[] ids, Integer state) {
		for (String id : ids) {
			Finance obj = this.get(Finance.class, id);
			obj.setState(state);
		}
	}
}
