package cn.devinkin.case4.service;

import cn.devinkin.case4.dao.CityDao;
import cn.devinkin.case4.domain.City;

import java.sql.SQLException;
import java.util.List;

public class CityService {
    private CityDao cityDao = new CityDao();

    public List<City> findCitiesByPid(String pid) throws SQLException {
        return cityDao.findCitiesByPid(pid);
    }
}
