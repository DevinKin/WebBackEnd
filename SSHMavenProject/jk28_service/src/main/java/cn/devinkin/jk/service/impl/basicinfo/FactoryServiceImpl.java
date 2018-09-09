package cn.devinkin.jk.service.impl.basicinfo;

import cn.devinkin.jk.dao.BaseDao;
import cn.devinkin.jk.domain.Factory;
import cn.devinkin.jk.service.basicinfo.FactoryService;
import cn.devinkin.jk.utils.Page;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Transactional
public class FactoryServiceImpl implements FactoryService{


    private BaseDao baseDao;

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public List<Factory> find(String hql, Class<Factory> entityClass, Object[] params) {
        return baseDao.find(hql, entityClass, params);
    }

    @Override
    public Factory get(Class<Factory> entityClass, Serializable id) {
        return baseDao.get(entityClass, id);
    }

    @Override
    public Page<Factory> findPage(String hql, Page<Factory> page, Class<Factory> entityClass, Object[] params) {
        return baseDao.findPage(hql, page, entityClass,params);
    }

    @Override
    public void saveOrUpdate(Factory entity) {
        baseDao.saveOrUpdate(entity);
    }

    @Override
    public void saveOrUpdateAll(Collection<Factory> entitys) {
        baseDao.saveOrUpdateAll(entitys);
    }

    @Override
    public void deleteById(Class<Factory> entityClass, Serializable id) {
        baseDao.deleteById(entityClass, id); // 删除用户
    }

    @Override
    public void delete(Class<Factory> entityClass, Serializable[] ids) {
        for (Serializable id : ids) {
            this.deleteById(Factory.class, id);
        }
    }

    @Override
    public void changeState(String[] ids, Integer state) {
        for (String id : ids) {
            Factory obj = this.get(Factory.class, id);
            obj.setState(state);
        }
    }
}
