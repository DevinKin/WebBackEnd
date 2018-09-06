package cn.devinkin.jk.service.impl.cargo;

import cn.devinkin.jk.dao.BaseDao;
import cn.devinkin.jk.domain.ExtEproduct;
import cn.devinkin.jk.service.cargo.ExtEproductService;
import cn.devinkin.jk.utils.Page;
import cn.devinkin.jk.utils.UtilFuns;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Transactional
public class ExtEproductServiceImpl implements ExtEproductService{


    private BaseDao baseDao;

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public List<ExtEproduct> find(String hql, Class<ExtEproduct> entityClass, Object[] params) {
        return baseDao.find(hql, entityClass, params);
    }

    @Override
    public ExtEproduct get(Class<ExtEproduct> entityClass, Serializable id) {
        return baseDao.get(entityClass, id);
    }

    @Override
    public Page<ExtEproduct> findPage(String hql, Page<ExtEproduct> page, Class<ExtEproduct> entityClass, Object[] params) {
        return baseDao.findPage(hql, page, entityClass,params);
    }

    @Override
    public void saveOrUpdate(ExtEproduct entity) {

        if (UtilFuns.isEmpty(entity.getId())) {
            // 新增
            // 0-草稿，1-已上报 2-已报运
        }
        baseDao.saveOrUpdate(entity);
    }

    @Override
    public void saveOrUpdateAll(Collection<ExtEproduct> entitys) {
        baseDao.saveOrUpdateAll(entitys);
    }

    @Override
    public void deleteById(Class<ExtEproduct> entityClass, Serializable id) {
        baseDao.deleteById(entityClass, id); // 删除用户
    }

    @Override
    public void delete(Class<ExtEproduct> entityClass, Serializable[] ids) {
        for (Serializable id : ids) {
            this.deleteById(ExtEproduct.class, id);
        }
    }

    @Override
    public void changeState(String[] ids, Integer state) {
        for (String id : ids) {
            ExtEproduct extEproduct = this.get(ExtEproduct.class, id);
            // 可以不写，extEproduct是持久态对象
            this.saveOrUpdate(extEproduct);
        }
    }
}
