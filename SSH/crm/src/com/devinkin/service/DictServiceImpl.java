package com.devinkin.service;

import com.devinkin.dao.DictDao;
import com.devinkin.domain.Dict;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class DictServiceImpl implements DictService{
    private DictDao dictDao;

    public void setDictDao(DictDao dictDao) {
        this.dictDao = dictDao;
    }


    /**
     * 通过客户类别编码查询字段
     * @param dict_type_code 客户类别编码字段
     * @return
     */
    @Override
    public List<Dict> findByCode(String dict_type_code) {
        return dictDao.findByCode(dict_type_code);
    }
}
