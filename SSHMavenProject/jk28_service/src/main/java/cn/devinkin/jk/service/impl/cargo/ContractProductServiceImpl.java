package cn.devinkin.jk.service.impl.cargo;

import cn.devinkin.jk.dao.BaseDao;
import cn.devinkin.jk.domain.Contract;
import cn.devinkin.jk.domain.ContractProduct;
import cn.devinkin.jk.domain.ExtCproduct;
import cn.devinkin.jk.service.cargo.ContractProductService;
import cn.devinkin.jk.utils.Page;
import cn.devinkin.jk.utils.UtilFuns;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Transactional
public class ContractProductServiceImpl implements ContractProductService{


    private BaseDao baseDao;

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public List<ContractProduct> find(String hql, Class<ContractProduct> entityClass, Object[] params) {
        return baseDao.find(hql, entityClass, params);
    }

    @Override
    public ContractProduct get(Class<ContractProduct> entityClass, Serializable id) {
        return baseDao.get(entityClass, id);
    }

    @Override
    public Page<ContractProduct> findPage(String hql, Page<ContractProduct> page, Class<ContractProduct> entityClass, Object[] params) {
        return baseDao.findPage(hql, page, entityClass,params);
    }

    @Override
    public void saveOrUpdate(ContractProduct entity) {
        double amount = 0d;
        if (UtilFuns.isEmpty(entity.getId())) {
            // 新增
            if (UtilFuns.isNotEmpty(entity.getPrice()) && UtilFuns.isNotEmpty(entity.getCnumber())) {
                // 计算得货物总金额
                amount = entity.getPrice() * entity.getCnumber();
                entity.setAmount(amount);
            }

            // 根据购销合同的id，得到购销合同对象
            Contract contract = baseDao.get(Contract.class, entity.getContract().getId());
            // 修改购销合同的总金额
            contract.setTotalAmount(contract.getTotalAmount() + amount);
            // 修改购销合同的货物数量,新增货物,货物数量加contractProduct.cnumber
            contract.setProdAmount(contract.getProdAmount() + entity.getCnumber());


            // 保存购销合同
//            baseDao.saveOrUpdate(contract);
        } else {
            // 修改操作
            // 取出货物的原总金额
            double oldAmount = entity.getAmount();
            // 取出
            if (UtilFuns.isNotEmpty(entity.getPrice()) && UtilFuns.isNotEmpty(entity.getCnumber())) {
                // 计算得货物总金额
                amount = entity.getPrice() * entity.getCnumber();
                entity.setAmount(amount);
            }
            // 根据购销合同的id，得到购销合同对象
            Contract contract = baseDao.get(Contract.class, entity.getContract().getId());
            // 修改购销合同的总金额
            contract.setTotalAmount(contract.getTotalAmount() - oldAmount + amount);

        }
        baseDao.saveOrUpdate(entity);
    }

    @Override
    public void saveOrUpdateAll(Collection<ContractProduct> entitys) {
        baseDao.saveOrUpdateAll(entitys);
    }

    @Override
    public void deleteById(Class<ContractProduct> entityClass, Serializable id) {
        baseDao.deleteById(entityClass, id); // 删除货物
    }

    @Override
    public void delete(Class<ContractProduct> entityClass, Serializable[] ids) {
        for (Serializable id : ids) {
            this.deleteById(ContractProduct.class, id);
        }
    }


    /**
     * 删除货物对象
     * @param entityClass
     * @param entity
     */
    @Override
    public void delete(Class<ContractProduct> entityClass, ContractProduct entity) {
        // 1. 加载出要删除的货物对象
        ContractProduct contractProduct = baseDao.get(ContractProduct.class, entity.getId());

        // 2. 通过关联级别的数据加载，得到当前货物下的所有附件列表
        Set<ExtCproduct> extCset = contractProduct.getExtCproducts();

        // 3. 加载购销合同对象
        Contract contract = baseDao.get(Contract.class, entity.getContract().getId());

        // 4. 遍历附件列表，并修改购销合同的总金额
        for (ExtCproduct ec : extCset) {
            contract.setTotalAmount(contract.getTotalAmount() - ec.getAmount());
            // 修改购销合同附件总数量
            contract.setExtAmount(contract.getExtAmount() - ec.getCnumber());
        }

        // 5. 减去货物总金额，修改购销合同总金额
        contract.setTotalAmount(contract.getTotalAmount() - contractProduct.getAmount());

        // 减去货物总数量,修改购销合同货物总数量
        contract.setProdAmount(contract.getProdAmount() - contractProduct.getCnumber());

        // 6. 更新购销合同总金额
//        baseDao.saveOrUpdate(contract);

        // 7. 删除货物对象，级联删除
        baseDao.deleteById(ContractProduct.class, entity.getId());
    }
}
