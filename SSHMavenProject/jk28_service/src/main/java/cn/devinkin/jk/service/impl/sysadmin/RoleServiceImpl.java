package cn.devinkin.jk.service.impl.sysadmin;

import cn.devinkin.jk.dao.BaseDao;
import cn.devinkin.jk.domain.Role;
import cn.devinkin.jk.service.sysadmin.RoleService;
import cn.devinkin.jk.utils.Page;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Transactional
public class RoleServiceImpl implements RoleService{


    private BaseDao baseDao;

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public List<Role> find(String hql, Class<Role> entityClass, Object[] params) {
        return baseDao.find(hql, entityClass, params);
    }

    @Override
    public Role get(Class<Role> entityClass, Serializable id) {
        return baseDao.get(entityClass, id);
    }

    @Override
    public Page<Role> findPage(String hql, Page<Role> page, Class<Role> entityClass, Object[] params) {
        return baseDao.findPage(hql, page, entityClass,params);
    }

    @Override
    public void saveOrUpdate(Role entity) {
        baseDao.saveOrUpdate(entity);
    }

    @Override
    public void saveOrUpdateAll(Collection<Role> entitys) {
        baseDao.saveOrUpdateAll(entitys);
    }

    @Override
    public void deleteById(Class<Role> entityClass, Serializable id) {
        baseDao.deleteById(entityClass, id); // 删除用户
    }

    @Override
    public void delete(Class<Role> entityClass, Serializable[] ids) {
        for (Serializable id : ids) {
            this.deleteById(Role.class, id);
        }
    }
}
