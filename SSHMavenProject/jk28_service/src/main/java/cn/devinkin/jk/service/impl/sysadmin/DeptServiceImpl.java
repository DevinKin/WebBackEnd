package cn.devinkin.jk.service.impl.sysadmin;

import cn.devinkin.jk.dao.BaseDao;
import cn.devinkin.jk.domain.Dept;
import cn.devinkin.jk.service.sysadmin.DeptService;
import cn.devinkin.jk.utils.Page;
import cn.devinkin.jk.utils.UtilFuns;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Transactional
public class DeptServiceImpl implements DeptService{


    private BaseDao baseDao;

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public List<Dept> find(String hql, Class<Dept> entityClass, Object[] params) {
        return baseDao.find(hql, entityClass, params);
    }

    @Override
    public Dept get(Class<Dept> entityClass, Serializable id) {
        return baseDao.get(entityClass, id);
    }

    @Override
    public Page<Dept> findPage(String hql, Page<Dept> page, Class<Dept> entityClass, Object[] params) {
        return baseDao.findPage(hql, page, entityClass,params);
    }

    @Override
    public void saveOrUpdate(Dept entity) {
        if (UtilFuns.isEmpty(entity.getId())) {
            // id没有值，新增部门
            // 1是启用，0是停用，默认为启用
            entity.setState(1);

        }
        baseDao.saveOrUpdate(entity);
    }

    @Override
    public void saveOrUpdateAll(Collection<Dept> entitys) {
        baseDao.saveOrUpdateAll(entitys);
    }

    @Override
    public void deleteById(Class<Dept> entityClass, Serializable id) {
        // 有哪些子部门，它的父部门编号为第二个参数
        String hql = "from Dept where parent.id = ?";

        // 查询当前父部门下子部门的列表
        List<Dept> list = baseDao.find(hql, Dept.class, new Object[] {id});
        if (list != null && list.size() > 0) {
            for (Dept dept : list) {
                // 递归调用
                this.deleteById(Dept.class, dept.getId());
            }
        }
        baseDao.deleteById(entityClass, id); // 删除父部门
    }

    @Override
    public void delete(Class<Dept> entityClass, Serializable[] ids) {
        for (Serializable id : ids) {
            this.deleteById(Dept.class, id);
        }
    }
}
