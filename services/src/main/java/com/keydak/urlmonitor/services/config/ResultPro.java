package com.keydak.urlmonitor.services.config;

import com.keydak.utils.LogUtil;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.Properties;

/**
 * 返回结果常量配置
 * Created by Sammy Leung on 2016/6/7.
 */
public class ResultPro {

    public static void init(ServletContext context){
        try {
            InputStream ins = context.getResourceAsStream("WEB-INF/webConfig/result_code.properties");
            resultCodeProp.load(new InputStreamReader(ins, "UTF-8"));
        } catch(IOException e) {
            LogUtil.e("ResultPro-init", e.getMessage());
            e.printStackTrace();
        }
    }

    public static void init(){
        try {
            resultCodeProp.load(new FileInputStream(
                    new File("D:\\software\\java\\IdeaProjects2017\\Keydak.Asset.Manager\\AssetWeb\\src\\main\\webapp\\WEB-INF\\webConfig\\result_code.properties")));
        } catch(IOException e) {
            LogUtil.e("ResultPro-init", e.getMessage());
            e.printStackTrace();
        }
    }

    public static int code(String key){
        try {
            key += "_code";
            String codeStr = resultCodeProp.getProperty(key);
            return Integer.parseInt(codeStr);
        }catch (Exception e){
            LogUtil.e("ResultPro-code", e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    public static String value(String key){
        key += "_value";
        String str = resultCodeProp.getProperty(key);
        str = (str == null) ? "" : str;
        return str;
    }

    private static Properties resultCodeProp = new Properties();
}
