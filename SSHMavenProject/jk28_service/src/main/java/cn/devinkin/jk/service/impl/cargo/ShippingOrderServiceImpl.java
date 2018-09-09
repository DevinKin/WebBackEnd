package cn.devinkin.jk.service.impl.cargo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.devinkin.jk.dao.BaseDao;
import cn.devinkin.jk.domain.ShippingOrder;
import cn.devinkin.jk.service.cargo.ShippingOrderService;
import cn.devinkin.jk.utils.Page;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description:	ShippingOrder
 * @Author:	devinkin
 * @Company:
 * @CreateDate:		2018-9-8 16:06:08
 */

@Transactional
public class ShippingOrderServiceImpl implements ShippingOrderService {
	//spring注入dao
	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<ShippingOrder> find(String hql, Class<ShippingOrder> entityClass, Object[] params) {
		return baseDao.find(hql, ShippingOrder.class, params);
	}

	@Override
	public ShippingOrder get(Class<ShippingOrder> entityClass, Serializable id) {
		return baseDao.get(ShippingOrder.class, id);
	}

	@Override
	public Page<ShippingOrder> findPage(String hql, Page<ShippingOrder> page, Class<ShippingOrder> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, ShippingOrder.class, params);
	}

	@Override
	public void saveOrUpdate(ShippingOrder entity) {
		if(entity.getId()==null){								//代表新增
			entity.setState(1);									//状态：0停用1启用 默认启用
		}
		baseDao.saveOrUpdate(entity);
	}



	@Override
	public void saveOrUpdateAll(Collection<ShippingOrder> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	@Override
	public void deleteById(Class<ShippingOrder> entityClass, Serializable id) {
		baseDao.deleteById(ShippingOrder.class, id);
	}

	@Override
	public void delete(Class<ShippingOrder> entityClass, Serializable[] ids) {
		baseDao.delete(ShippingOrder.class, ids);
	}


	@Override
	public void changeState(String[] ids, Integer state) {
		for (String id : ids) {
			ShippingOrder shippingOrder = this.get(ShippingOrder.class, id);
			shippingOrder.setState(state);
			// 可以不写，shippingOrder是持久态对象
			this.saveOrUpdate(shippingOrder);
		}
	}
}
