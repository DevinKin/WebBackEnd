package cn.devinkin.jk.service.impl.cargo;

import cn.devinkin.jk.dao.BaseDao;
import cn.devinkin.jk.domain.*;
import cn.devinkin.jk.service.cargo.ExportService;
import cn.devinkin.jk.utils.Page;
import cn.devinkin.jk.utils.UtilFuns;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

@Transactional
public class ExportServiceImpl implements ExportService{


    private BaseDao baseDao;

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public List<Export> find(String hql, Class<Export> entityClass, Object[] params) {
        return baseDao.find(hql, entityClass, params);
    }

    @Override
    public Export get(Class<Export> entityClass, Serializable id) {
        return baseDao.get(entityClass, id);
    }

    @Override
    public Page<Export> findPage(String hql, Page<Export> page, Class<Export> entityClass, Object[] params) {
        return baseDao.findPage(hql, page, entityClass,params);
    }

    @Override
    public void saveOrUpdate(Export entity) {
        if (UtilFuns.isEmpty(entity.getId())) {
            // 新增报运单
            // 设置状态字段
            entity.setState(0);
            // 设置制单日期
            entity.setInputDate(new Date());

            // 设置购销合同对象
            String[] ids = entity.getContractIds().split(", ");
            StringBuilder sb = new StringBuilder();
            // 遍历每个购销合同的id,得到每个购销合同对象,并修改状态为2
            for (String id : ids) {
                Contract contract = baseDao.get(Contract.class, id);
                // 修改状态
                contract.setState(2);
                sb.append(contract.getContractNo()).append(" ");
            }
            // 设置合同及确认书号
            entity.setCustomerContract(sb.toString());
            entity.setContractIds(UtilFuns.joinStr(ids, ","));
            // 通过购销合同的集合,跳跃查询出购销合同下面的货物列表
            String hql = "from ContractProduct where contract.id in (" + UtilFuns.joinInStr(ids) + ")";
            List<ContractProduct> list = baseDao.find(hql, ContractProduct.class, null);

            // 遍历购销合同的货物集合,数据搬家
            Set<ExportProduct> epSet = new HashSet<ExportProduct>();
            for (ContractProduct cp : list) {
                // 报运单下的商品
                ExportProduct ep = new ExportProduct();
                ep.setBoxNum(cp.getBoxNum());
                ep.setCnumber(cp.getCnumber());
                ep.setFactory(cp.getFactory());
                ep.setOrderNo(cp.getOrderNo());
                ep.setPackingUnit(cp.getPackingUnit());
                ep.setPrice(cp.getPrice());
                ep.setProductNo(cp.getProductNo());
                // 设置商品与报运单,多对一
                ep.setExport(entity);

                epSet.add(ep);

                // 加载购销合同下当前货物下的附件列表
                Set<ExtCproduct> extCSet = cp.getExtCproducts();

                // 报运单下的附件列表
                Set<ExtEproduct> extESet = new HashSet<ExtEproduct>();
                for (ExtCproduct extC : extCSet) {
                    ExtEproduct extE = new ExtEproduct();

                    // 拷贝对象的属性
                    BeanUtils.copyProperties(extC,extE);
                    // 由uuid生成,需要置空
                    extE.setId(null);
                    // 设置附件与货物,多对一
                    extE.setExportProduct(ep);
                    // 向列表中添加元素
                    extESet.add(extE);

                }

                // 向报运单下的商品对象中添加附件
                ep.setExtEproducts(extESet);
            }

            // 外层循环退出时,设置一个报运单下有多个商品
            entity.setExportProducts(epSet);
        }
        baseDao.saveOrUpdate(entity);
    }

    @Override
    public void saveOrUpdateAll(Collection<Export> entitys) {
        baseDao.saveOrUpdateAll(entitys);
    }

    @Override
    public void deleteById(Class<Export> entityClass, Serializable id) {
        baseDao.deleteById(entityClass, id); // 删除用户
    }

    @Override
    public void delete(Class<Export> entityClass, Serializable[] ids) {
        for (Serializable id : ids) {
            this.deleteById(Export.class, id);
        }
    }

    @Override
    public void changeState(String[] ids, Integer state) {
        for (String id : ids) {
            Export export = this.get(Export.class, id);
            export.setState(state);
            System.out.println(export.getId());
            // 可以不写，export是持久态对象
            this.saveOrUpdate(export);
        }
    }
}
