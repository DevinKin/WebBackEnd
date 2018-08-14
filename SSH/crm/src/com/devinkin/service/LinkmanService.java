package com.devinkin.service;

import com.devinkin.domain.Linkman;
import com.devinkin.domain.PageBean;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

public interface LinkmanService {
    PageBean<Linkman> findByPage(Integer pageCode, Integer pageSize, DetachedCriteria criteria);

    Linkman findById(Long lkm_id);

    void update(Linkman linkman);

    List<Linkman> findByCustId(Long cust_id);

    void delete(Linkman linkman);

    void save(Linkman linkman);

    List<Linkman> findAll();
}
