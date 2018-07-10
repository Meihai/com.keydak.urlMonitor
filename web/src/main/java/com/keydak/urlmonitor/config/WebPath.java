package com.keydak.urlmonitor.config;

import com.keydak.utils.LogUtil;
import com.keydak.utils.TextUtils;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.Properties;

/**
 * 配置文件路径
 * Created by Sammy Leung on 2016/9/19.
 */
public class WebPath {

    public static void init(ServletContext context){
        initOS();
        initSystemDirs(context);
        g_context = context;
    }

    /**
     * 获取Web额外文件的根路径，默认路径为项目根目录的向上两层
     * 如：
     * 传入：java\apache-tomcat-8.0.33\webapps\csj\
     * 结果：java\apache-tomcat-8.0.33\
     * @return
     */
    private static String getFileRoot(){

        StringBuilder result = new StringBuilder("");
        if (TextUtils.isNotEmpty(systemPath)){
            String[] strArray;

            strArray = systemPath.split(dir_p2);

            if (strArray.length > 2){
                for (int i=0; i<strArray.length - 2; i++){
                    result.append(strArray[i]).append(dir_p);
                }
            }else if (strArray.length == 1){
                result.append(strArray[0]).append(dir_p);
            }
        }
        LogUtil.i("WebPath file root:" + result);
        return TextUtils.isNotEmpty(result.toString()) ?  result.toString() + "web_file" + dir_p : "";
    }

    /**
     * 获取上传文件的保存目录
     * @return
     */
    public static String getUploadPath(){
        String path = getFileRoot();
        return (TextUtils.isNotEmpty(path)) ? path + "upload" : "";
    }

    /**
     * 获取配置文件目录
     * @return
     */
    public static String getConfigPath(){
        String path = getFileRoot();
        return (TextUtils.isNotEmpty(path)) ? path + "config" : "";
    }

    /**
     * 获取临时文件目录
     * @return
     */
    public static String getTempPath(){
        String path = getFileRoot();
        return (TextUtils.isNotEmpty(path)) ? path + "temp" : "";
    }


    public static String getUserPicPath(){
        String path = resPath;
        return (TextUtils.isNotEmpty(path)) ? path + "user_pic" : "";
    }

    /**
     * 初始化系统目录
     * @param context
     */
    public static void initSystemDirs(ServletContext context){
        initSystemDirs(context.getRealPath(""));
    }

    public static void initSystemDirs(String rootPath){
        if (rootPath != null){
            systemPath = rootPath;
            String configPath = getConfigPath();
            String tempPath = getTempPath();
            String uploadPath = getUploadPath();

            File configDir = new File(configPath);
            if (!configDir.exists() && !configDir.mkdirs()){
                LogUtil.e("WebPath.initDirs", "create config dir error, path:" + configPath);
            }

            File tempDir = new File(tempPath);
            if (!tempDir.exists() && !tempDir.mkdirs()){
                LogUtil.e("WebPath.initDirs", "create temp dir error, path:" + tempPath);
            }

            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists() && !uploadDir.mkdirs()){
                LogUtil.e("WebPath.initDirs", "create upload dir error, path:" + uploadPath);
            }
        }
    }

    public static void initResDirs(String rootPath){
        if (rootPath != null){
            resPath = rootPath;
            String userPicPath = getUserPicPath();

            File userPicDir = new File(userPicPath);
            if (!userPicDir.exists() && !userPicDir.mkdirs()){
                LogUtil.e("WebPath.initDirs", "create user pic dir error, path:" + userPicPath);
            }
        }
    }

    /**
     * 初始化系统相关信息
     */
    public static void initOS(){
        Properties properties = System.getProperties();
        String osName = properties.getProperty("os.name");
        if (TextUtils.isNotEmpty(osName)){
            osName = osName.toLowerCase();
            if (osName.contains("windows")){
                dir_p = "\\";
                dir_p2 = "\\\\";
                os_system = OS_WINDOWS;
            }else {
                dir_p = "/";
                dir_p2 = "//";
                os_system = OS_LINUX;
            }
        }else {
            dir_p = "/";
            dir_p2 = "//";
        }
    }

    public static String getContextPath(){
        return systemPath;
    }

    public static ServletContext getContext() {
        return g_context;
    }

    private static String systemPath;
    private static String resPath;
    private static ServletContext g_context;
    public static String dir_p;
    public static String dir_p2;

    public static int os_system;
    public static final int OS_WINDOWS = 1;
    public static final int OS_LINUX = 2;
}
