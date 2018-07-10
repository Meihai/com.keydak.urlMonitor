package com.keydak.urlmonitor.db;

import com.keydak.database.DatabaseUtils;
import com.keydak.urlmonitor.constants.DBCon;

/**
 * Created by admin on 2018/7/10.
 */
public class DBUtils {
    public static void initDB(String url,String account,String password){
        DatabaseUtils.getInstance().init(url,account,password);
        DatabaseUtils.getInstance().getConnection(DBCon.DB_URLMONITOR);
    }
}
