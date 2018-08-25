package cn.devinkin.jk.service.impl;

import cn.devinkin.jk.dao.BaseDao;
import cn.devinkin.jk.domain.Contract;
import cn.devinkin.jk.service.ContractService;
import cn.devinkin.jk.utils.Page;
import cn.devinkin.jk.utils.UtilFuns;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Transactional
public class ContractServiceImpl implements ContractService{


    private BaseDao baseDao;

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public List<Contract> find(String hql, Class<Contract> entityClass, Object[] params) {
        return baseDao.find(hql, entityClass, params);
    }

    @Override
    public Contract get(Class<Contract> entityClass, Serializable id) {
        return baseDao.get(entityClass, id);
    }

    @Override
    public Page<Contract> findPage(String hql, Page<Contract> page, Class<Contract> entityClass, Object[] params) {
        return baseDao.findPage(hql, page, entityClass,params);
    }

    @Override
    public void saveOrUpdate(Contract entity) {
        if (UtilFuns.isEmpty(entity.getId())) {
            // 新增
            entity.setTotalAmount(0d);
            // 0-草稿，1-已上报 2-已报运
            entity.setState(0);
        }
        baseDao.saveOrUpdate(entity);
    }

    @Override
    public void saveOrUpdateAll(Collection<Contract> entitys) {
        baseDao.saveOrUpdateAll(entitys);
    }

    @Override
    public void deleteById(Class<Contract> entityClass, Serializable id) {
        baseDao.deleteById(entityClass, id); // 删除用户
    }

    @Override
    public void delete(Class<Contract> entityClass, Serializable[] ids) {
        for (Serializable id : ids) {
            this.deleteById(Contract.class, id);
        }
    }

    @Override
    public void changeState(String[] ids, Integer state) {
        for (String id : ids) {
            Contract contract = this.get(Contract.class, id);
            contract.setState(state);
            // 可以不写，contract是持久态对象
            this.saveOrUpdate(contract);
        }
    }
}
