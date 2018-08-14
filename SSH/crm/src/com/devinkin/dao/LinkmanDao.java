package com.devinkin.dao;

import com.devinkin.domain.Linkman;

import java.util.List;

public interface LinkmanDao extends BaseDao<Linkman>{
    List<Linkman> findByCustId(Long cust_id);
}
