package cn.devinkin.jk.service.impl.stat;

import cn.devinkin.jk.dao.springdao.SqlDao;
import cn.devinkin.jk.service.stat.StatChartService;

import java.util.List;

public class StatChartServiceImpl implements StatChartService {
    private SqlDao sqlDao;

    public void setSqlDao(SqlDao sqlDao) {
        this.sqlDao = sqlDao;
    }

    @Override
    public List<String> executeSQL(String sql) {
        return sqlDao.executeSQL(sql);
    }
}
