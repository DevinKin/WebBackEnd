package cn.devinkin.jk.service.impl;

import cn.devinkin.jk.dao.BaseDao;
import cn.devinkin.jk.domain.Module;
import cn.devinkin.jk.service.ModuleService;
import cn.devinkin.jk.utils.Page;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Transactional
public class ModuleServiceImpl implements ModuleService{


    private BaseDao baseDao;

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public List<Module> find(String hql, Class<Module> entityClass, Object[] params) {
        return baseDao.find(hql, entityClass, params);
    }

    @Override
    public Module get(Class<Module> entityClass, Serializable id) {
        return baseDao.get(entityClass, id);
    }

    @Override
    public Page<Module> findPage(String hql, Page<Module> page, Class<Module> entityClass, Object[] params) {
        return baseDao.findPage(hql, page, entityClass,params);
    }

    @Override
    public void saveOrUpdate(Module entity) {
        baseDao.saveOrUpdate(entity);
    }

    @Override
    public void saveOrUpdateAll(Collection<Module> entitys) {
        baseDao.saveOrUpdateAll(entitys);
    }

    @Override
    public void deleteById(Class<Module> entityClass, Serializable id) {
        baseDao.deleteById(entityClass, id); // 删除用户
    }

    @Override
    public void delete(Class<Module> entityClass, Serializable[] ids) {
        for (Serializable id : ids) {
            this.deleteById(Module.class, id);
        }
    }
}
