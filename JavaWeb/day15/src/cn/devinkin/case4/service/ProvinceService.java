package cn.devinkin.case4.service;

import cn.devinkin.case4.dao.ProvinceDao;
import cn.devinkin.case4.domain.Province;

import java.sql.SQLException;
import java.util.List;

public class ProvinceService {
    private ProvinceDao provinceDao = new ProvinceDao();

    /**
     * 查询所有的省份
     * @return 省份集合
     */
    public List<Province> findAllPro() throws SQLException {
        return provinceDao.findAllPro();
    }
}
