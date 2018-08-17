package cn.devinkin.crm.service;

import cn.devinkin.crm.domain.PageBean;
import cn.devinkin.crm.domain.Visit;
import org.hibernate.criterion.DetachedCriteria;

public interface VisitService {
    PageBean<Visit> findByPage(Integer pageCode, Integer pageSize, DetachedCriteria criteria);

    void save(Visit visit);
}
