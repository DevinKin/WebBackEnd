package cn.devinkin.jk.service.impl;

import cn.devinkin.jk.dao.BaseDao;
import cn.devinkin.jk.domain.Contract;
import cn.devinkin.jk.domain.ContractProduct;
import cn.devinkin.jk.domain.ExtCproduct;
import cn.devinkin.jk.service.ExtCproductService;
import cn.devinkin.jk.utils.Page;
import cn.devinkin.jk.utils.UtilFuns;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Transactional
public class ExtCproductServiceImpl implements ExtCproductService{


    private BaseDao baseDao;

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public List<ExtCproduct> find(String hql, Class<ExtCproduct> entityClass, Object[] params) {
        return baseDao.find(hql, entityClass, params);
    }

    @Override
    public ExtCproduct get(Class<ExtCproduct> entityClass, Serializable id) {
        return baseDao.get(entityClass, id);
    }

    @Override
    public Page<ExtCproduct> findPage(String hql, Page<ExtCproduct> page, Class<ExtCproduct> entityClass, Object[] params) {
        return baseDao.findPage(hql, page, entityClass,params);
    }

    @Override
    public void saveOrUpdate(ExtCproduct entity) {
        double amount = 0d;
        if (UtilFuns.isEmpty(entity.getId())) {
            // 新增
            if (UtilFuns.isNotEmpty(entity.getPrice()) && UtilFuns.isNotEmpty(entity.getCnumber())) {
                // 计算得货物总金额
                amount = entity.getPrice() * entity.getCnumber();
                entity.setAmount(amount);
            }

            // 修改购销合同的总金额
            // 根据购销合同的id，得到购销合同对象
            Contract contract = baseDao.get(Contract.class, entity.getContractProduct().getContract().getId());
            contract.setTotalAmount(contract.getTotalAmount() + amount);

            // 保存购销合同
//            baseDao.saveOrUpdate(contract);
        } else {
            // 修改操作
            // 取出附件的原总金额
            double oldAmount = entity.getAmount();
            if (UtilFuns.isNotEmpty(entity.getPrice()) && UtilFuns.isNotEmpty(entity.getCnumber())) {
                // 计算得附件总金额
                amount = entity.getPrice() * entity.getCnumber();
                entity.setAmount(amount);
            }
            // 修改购销合同的总金额
            // 根据购销合同的id，得到购销合同对象
            Contract contract = baseDao.get(Contract.class, entity.getContractProduct().getContract().getId());
            contract.setTotalAmount(contract.getTotalAmount() - oldAmount + amount);


        }
        baseDao.saveOrUpdate(entity);

    }

    @Override
    public void saveOrUpdateAll(Collection<ExtCproduct> entitys) {
        baseDao.saveOrUpdateAll(entitys);
    }

    @Override
    public void deleteById(Class<ExtCproduct> entityClass, Serializable id) {
        baseDao.deleteById(entityClass, id); // 删除用户
    }

    @Override
    public void delete(Class<ExtCproduct> entityClass, Serializable[] ids) {
        for (Serializable id : ids) {
            this.deleteById(ExtCproduct.class, id);
        }
    }

    @Override
    public void delete(Class<ExtCproduct> entityClass, ExtCproduct model) {
        // 得到附件对象
        ExtCproduct extCproduct = baseDao.get(ExtCproduct.class, model.getId());

        // 得到购销合同对象
        Contract contract = baseDao.get(Contract.class, model.getContractProduct().getContract().getId());

        // 修改购销合同的总金额
        contract.setTotalAmount(contract.getTotalAmount() - extCproduct.getAmount());

        // 保存总金额
        baseDao.saveOrUpdate(contract);

        // 删除附件
        baseDao.deleteById(ExtCproduct.class, extCproduct.getId());
    }
}
