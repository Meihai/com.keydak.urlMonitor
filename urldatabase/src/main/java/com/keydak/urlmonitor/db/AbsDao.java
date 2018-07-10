package com.keydak.urlmonitor.db;

import com.keydak.database.IDBExecutor;
import com.keydak.database.dbutils.DbUtilsExecutor;
import com.keydak.database.sql.ISQLCreator;
import com.keydak.database.sql.MySqlSQLCreator;
import com.keydak.urlmonitor.constants.DBCon;

/**
 * Created by admin on 2018/7/10.
 */
public abstract class AbsDao {
    public AbsDao() {
        mSqlCreator = new MySqlSQLCreator();
        mDao = new DbUtilsExecutor(DBCon.DB_URLMONITOR, mSqlCreator);
    }

    protected IDBExecutor mDao;
    protected ISQLCreator mSqlCreator;
}
