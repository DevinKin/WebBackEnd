package cn.devinkin.case5.service;

import cn.devinkin.case5.dao.KeyWordDao;

import java.sql.SQLException;
import java.util.List;

public class KeyWordService {
    private KeyWordDao keyWordDao = new KeyWordDao();

    public List<Object> getKeyWords4Ajax(String kw) throws SQLException {
        return keyWordDao.getKeyWords4Ajax(kw);
    }
}
