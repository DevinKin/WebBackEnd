package cn.devinkin.jk.service.cargo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.devinkin.jk.domain.ShippingOrder;
import cn.devinkin.jk.utils.Page;

/**
 * @Description:	ShippingOrder
 * @Author:	devinkin
 * @Company:
 * @CreateDate:		2018-9-8 16:03:15
 */

public interface ShippingOrderService {

	public List<ShippingOrder> find(String hql, Class<ShippingOrder> entityClass, Object[] params);
	public ShippingOrder get(Class<ShippingOrder> entityClass, Serializable id);
	public Page<ShippingOrder> findPage(String hql, Page<ShippingOrder> page, Class<ShippingOrder> entityClass, Object[] params);
	
	public void saveOrUpdate(ShippingOrder entity);
	public void saveOrUpdateAll(Collection<ShippingOrder> entitys);
	
	public void deleteById(Class<ShippingOrder> entityClass, Serializable id);
	public void delete(Class<ShippingOrder> entityClass, Serializable[] ids);

	/**
	 * 修改状态
	 */
	public void changeState(String[] ids, Integer state);
}
