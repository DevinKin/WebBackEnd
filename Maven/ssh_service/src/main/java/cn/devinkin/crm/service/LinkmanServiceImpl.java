package cn.devinkin.crm.service;

import cn.devinkin.crm.dao.LinkmanDao;
import cn.devinkin.crm.domain.Linkman;
import cn.devinkin.crm.domain.PageBean;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class LinkmanServiceImpl implements LinkmanService {

    private LinkmanDao linkmanDao;

    public void setLinkmanDao(LinkmanDao linkmanDao) {
        this.linkmanDao = linkmanDao;
    }


    /**
     * 分页查找联系人
     * @param pageCode 当前页码
     * @param pageSize 当前页显示数量
     * @param criteria 离线条件查询对象
     * @return
     */
    @Override
    public PageBean<Linkman> findByPage(Integer pageCode, Integer pageSize, DetachedCriteria criteria) {
        return linkmanDao.findByPage(pageCode,pageSize,criteria);
    }

    @Override
    public Linkman findById(Long lkm_id) {
        return linkmanDao.findById(lkm_id);
    }


    /**
     * 更新联系人
     * @param linkman 联系人
     */
    @Override
    public void update(Linkman linkman) {
        linkmanDao.update(linkman);
    }


    public List<Linkman> findByCustId(Long cust_id) {
        return linkmanDao.findByCustId(cust_id);
    }

    /**
     * 删除联系人
     * @param linkman
     */
    @Override
    public void delete(Linkman linkman) {
        linkmanDao.delete(linkman);
    }


    /**
     * 保存联系人
     * @param linkman 联系人
     */
    @Override
    public void save(Linkman linkman) {
        linkmanDao.save(linkman);
    }

    /**
     * 查找所有联系人
     * @return
     */
    @Override
    public List<Linkman> findAll() {
        return linkmanDao.findAll();
    }
}
