package com.devinkin.service;

import com.devinkin.domain.Dict;

import java.util.List;

public interface DictService {
    List<Dict> findByCode(String dict_type_code);
}
