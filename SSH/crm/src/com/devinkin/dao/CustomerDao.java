package com.devinkin.dao;


import com.devinkin.domain.Customer;
import com.devinkin.domain.PageBean;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

public interface CustomerDao extends BaseDao<Customer>{

    List<Object[]> findBySource();

    List<Object[]> findByIndustry();
}
