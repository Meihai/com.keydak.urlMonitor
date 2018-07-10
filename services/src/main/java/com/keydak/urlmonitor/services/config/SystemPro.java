package com.keydak.urlmonitor.services.config;


import com.keydak.utils.LogUtil;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.Properties;

/**
 * 系统常量配置
 * Created by Sammy Leung on 2016/6/16.
 */
public class SystemPro {

    public static void init(ServletContext context){
        try {
            InputStream ins = context.getResourceAsStream("WEB-INF/webConfig/system.properties");
            systemCodeProp.load(new InputStreamReader(ins, "UTF-8"));
        } catch(IOException e) {
            LogUtil.e("SystemPro-init", e.getMessage());
            e.printStackTrace();
        }
    }

    public static void init(){
        try {
            systemCodeProp.load(new FileInputStream(
                    new File("src/main/webapp/WEB-INF/webConfig/system.properties")));
        } catch(IOException e) {
            LogUtil.e("SystemPro-init", e.getMessage());
            e.printStackTrace();
        }
    }

    public static String getValue(String key){
        String str = systemCodeProp.getProperty(key);
        str = (str == null) ? "" : str;
        return str;
    }

    private static Properties systemCodeProp = new Properties();
}
