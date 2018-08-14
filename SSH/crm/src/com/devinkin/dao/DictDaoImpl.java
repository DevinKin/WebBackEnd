package com.devinkin.dao;

import com.devinkin.domain.Dict;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

public class DictDaoImpl extends HibernateDaoSupport implements DictDao{

    /**
     * 通过客户类别编码查询字段
     * @param dict_type_code 客户类别编码字段
     * @return
     */
    @Override
    public List<Dict> findByCode(String dict_type_code) {
        return (List<Dict>) this.getHibernateTemplate().find("from Dict where dict_type_code = ?", dict_type_code);
    }
}
