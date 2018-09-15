package cn.devinkin.dao;

import cn.devinkin.pojo.BaseDict;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictMapper {
    public List<BaseDict> findDictByCode(String code);
}
