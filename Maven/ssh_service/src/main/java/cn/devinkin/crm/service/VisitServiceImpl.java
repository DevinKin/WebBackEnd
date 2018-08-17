package cn.devinkin.crm.service;

import cn.devinkin.crm.dao.VisitDao;
import cn.devinkin.crm.domain.PageBean;
import cn.devinkin.crm.domain.Visit;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 客户拜访业务层
 * @author king
 */
@Service(value = "visitService")
@Transactional
public class VisitServiceImpl implements VisitService{

    @Resource(name = "visitDao")
    private VisitDao visitDao;


    /**
     * 分页查询
     * @param pageCode 当前页码
     * @param pageSize 当前页显示数量
     * @param criteria 离线条件查询
     * @return
     */
    @Override
    public PageBean<Visit> findByPage(Integer pageCode, Integer pageSize, DetachedCriteria criteria) {
        return visitDao.findByPage(pageCode, pageSize, criteria);
    }


    /**
     * 保存拜访记录
     */
    @Override
    public void save(Visit visit) {
        visitDao.save(visit);
    }
}
