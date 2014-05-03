package com.cloudstone.emenu.storage.mysql;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.storage.dao.IDb;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by charliez on 5/2/14.
 */
public abstract class MysqlDB implements IDb {
    protected DataSource dataSource;
    protected JdbcTemplate jdbc;

    abstract protected String tableSchema();

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbc = new JdbcTemplate(dataSource);
    }

    public int getMaxId(EmenuContext context) {
        throw new NotImplementedException();
    }

    public void delete(EmenuContext context, int id) {
        throw new NotImplementedException();
    }

    public int count(EmenuContext context) {
        throw new NotImplementedException();
    }

    public void init() {
        throw new NotImplementedException();
    }
}
