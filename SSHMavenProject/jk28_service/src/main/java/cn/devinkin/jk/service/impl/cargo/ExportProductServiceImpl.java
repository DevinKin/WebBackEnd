package cn.devinkin.jk.service.impl.cargo;

import cn.devinkin.jk.dao.BaseDao;
import cn.devinkin.jk.domain.ExportProduct;
import cn.devinkin.jk.service.cargo.ExportProductService;
import cn.devinkin.jk.utils.Page;
import cn.devinkin.jk.utils.UtilFuns;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Transactional
public class ExportProductServiceImpl implements ExportProductService{


    private BaseDao baseDao;

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public List<ExportProduct> find(String hql, Class<ExportProduct> entityClass, Object[] params) {
        return baseDao.find(hql, entityClass, params);
    }

    @Override
    public ExportProduct get(Class<ExportProduct> entityClass, Serializable id) {
        return baseDao.get(entityClass, id);
    }

    @Override
    public Page<ExportProduct> findPage(String hql, Page<ExportProduct> page, Class<ExportProduct> entityClass, Object[] params) {
        return baseDao.findPage(hql, page, entityClass,params);
    }

    @Override
    public void saveOrUpdate(ExportProduct entity) {

        if (UtilFuns.isEmpty(entity.getId())) {
            // 新增
            // 0-草稿，1-已上报 2-已报运
        }
        baseDao.saveOrUpdate(entity);
    }

    @Override
    public void saveOrUpdateAll(Collection<ExportProduct> entitys) {
        baseDao.saveOrUpdateAll(entitys);
    }

    @Override
    public void deleteById(Class<ExportProduct> entityClass, Serializable id) {
        baseDao.deleteById(entityClass, id); // 删除用户
    }

    @Override
    public void delete(Class<ExportProduct> entityClass, Serializable[] ids) {
        for (Serializable id : ids) {
            this.deleteById(ExportProduct.class, id);
        }
    }

    @Override
    public void changeState(String[] ids, Integer state) {
        for (String id : ids) {
            ExportProduct exportProduct = this.get(ExportProduct.class, id);
            // 可以不写，exportProduct是持久态对象
            this.saveOrUpdate(exportProduct);
        }
    }
}
