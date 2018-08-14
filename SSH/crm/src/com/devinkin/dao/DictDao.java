package com.devinkin.dao;

import com.devinkin.domain.Dict;

import java.util.List;

public interface DictDao {
    List<Dict> findByCode(String dict_type_code);
}
