package com.cloudstone.emenu.stoge.db;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.PayType;
import com.cloudstone.emenu.storage.db.IMenuDb;
import com.cloudstone.emenu.storage.db.MenuDb;
import com.cloudstone.emenu.storage.db.PayTypeDb;
import com.cloudstone.emenu.storage.db.util.SqliteDataSource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by nicholaszhao on 4/19/14.
 */
public class MenuDbTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private MenuDb menuDb;

    @Before
    public void createDb() throws Exception {
        SqliteDataSource dataSource = new SqliteDataSource();
        File dbFile = tempFolder.newFile();
        dataSource.setDbFile(dbFile);
        menuDb = new MenuDb();
        menuDb.setDataSource(dataSource);
    }

}
