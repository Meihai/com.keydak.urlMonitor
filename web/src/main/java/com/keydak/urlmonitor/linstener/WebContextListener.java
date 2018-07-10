package com.keydak.urlmonitor.linstener;


import com.keydak.urlmonitor.config.WebPath;
import com.keydak.urlmonitor.constants.DBCon;
import com.keydak.urlmonitor.db.DBUtils;
import com.keydak.urlmonitor.services.config.ResultPro;
import com.keydak.urlmonitor.services.config.SystemPro;
import com.keydak.utils.HttpUtils;
import com.keydak.utils.LogLevel;
import com.keydak.utils.LogUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * Created by Sammy Leung on 2017/7/25.
 */
public class WebContextListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        LogUtil.setLevel(LogLevel.WARN);
        WebPath.initOS();
        WebPath.init(sce.getServletContext());
        ResultPro.init(sce.getServletContext());
        SystemPro.init(sce.getServletContext());
        HttpUtils.init();
        String dbUrl = SystemPro.getValue(DBCon.System_URL_DB_Base);
        String dbAccount = SystemPro.getValue(DBCon.System_DB_Account);
        String dbPwd = SystemPro.getValue(DBCon.System_DB_Password);
        DBUtils.initDB(dbUrl, dbAccount, dbPwd);




    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
