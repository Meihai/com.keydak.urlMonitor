package com.keydak.urlmonitor.controller;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.keydak.urlmonitor.config.WebPath;
import com.keydak.utils.LogUtil;
import com.keydak.utils.TextUtils;
import com.keydak.utils.expression.Condition;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Sammy Leung on 2016/6/8.
 */
public abstract class AbsController {

    // 返回纯字符串（UTF-8编码）
    protected void doResponseJson(HttpServletResponse response,
                                  String str) {
        if (TextUtils.isNotEmpty(str)) {
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = null;
            try {
                pw = response.getWriter();
                pw.print(str);
                pw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (pw != null) {
                    pw.close();
                }
            }
        } else {
            System.out.println("AbsController-doResponseJson:string is null");
        }
    }

    // 请求转发
    protected void dispath(String path, HttpServletRequest request, HttpServletResponse response) {

        try {
            request.getRequestDispatcher(path).forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
            LogUtil.e("dispath ServletException:" + path, e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("dispath IOException:" + path, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("dispath Exception:" + path, e.getMessage());
        }
    }

    // 重定向
    protected void sendRedirect(String url, HttpServletResponse response) {
        try {
            response.sendRedirect(url);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("dispath Exception:" + url, e.getMessage());
        }
    }

    protected <T> T getObjectFromJson(String json) throws Exception {
        TypeToken<T> typeToken = new TypeToken<T>() {
        };
        Type type = typeToken.getType();
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    protected <T> T getObjectFromJson(String json, Class<T> clazz) throws Exception {

        JsonSerializer<Date> ser = new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext
                    context) {
                return src == null ? null : new JsonPrimitive(src.getTime());
            }
        };

        JsonDeserializer<Date> deser = new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT,
                                    JsonDeserializationContext context) throws JsonParseException {
                if (json == null) {
                    return null;
                } else {
                    String dateSrc = json.getAsString();
                    try {
                        return dateSrc.length() > 13 ?
                                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(json.getAsString()) :
                                new Date(json.getAsLong());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, ser)
                .registerTypeAdapter(Date.class, deser).create();

        return gson.fromJson(json, clazz);
    }

    /**
     * 解析包含上传文件的参数列表
     *
     * @param request
     * @param response
     * @return
     */
    protected List<FileItem> parseUploadParams(HttpServletRequest request, HttpServletResponse response) {

        response.setCharacterEncoding("UTF-8");
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //临时文件路径
        String tempPath = WebPath.getTempPath();

        //解析请求参数的File
        FileItemFactory factory = new DiskFileItemFactory(1024 * 100, new File(tempPath));
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(100 * 1024 * 1024);//文件大小不超过5M

        List<FileItem> fileItems = new ArrayList<>();
        try {
            fileItems = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
            LogUtil.e("AbsController.parseUploadParams", e.getMessage());
        }

        return fileItems;
    }

    protected void downloadFile(String inFilePath, String outFileName, String outFileSuffix,
                                HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (!TextUtils.isNotEmpty(inFilePath)) {
            throw new IllegalArgumentException("input file path empty : " + inFilePath);
        }

        InputStream fins = new FileInputStream(inFilePath);

        /*
        为了防止客户端浏览器直接打开目标文件（例如在装了MS Office套件的Windows中的IE浏览器可能
        就会直接在IE浏览器中打开你想下载的doc或者xls文件），你必须在响应头里加入强制下载的MIME
        类型: response.setContentType("application/force-download");
        设置为下载application/force-download 这样，就可以保证在用户点击下载链接的时候浏览器一
        定会弹出提示窗口来询问你是下载还是直接打开并允许你选择要打开的应用程序，除非你设置了浏览
        器的一些默认行为。
         */
        response.setHeader("Content-Type", "application/force-download");

        //把文件名转码使前台可以显示中文名

        try {
            byte[] nameBytes;
            nameBytes = outFileName.getBytes("UTF-8");
            outFileName = new String(nameBytes, "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            LogUtil.e("downloadFile", e.getMessage());
            e.printStackTrace();
        }

        response.setHeader("Content-Disposition", "attachment; filename=\"" + outFileName + "." + outFileSuffix + "\"");

        ServletOutputStream os;
        BufferedInputStream fin;

        os = response.getOutputStream();
        fin = new BufferedInputStream(fins);

        byte[] content = new byte[1024];
        int length;
        while ((length = fin.read(content, 0, content.length)) != -1) {
            os.write(content, 0, length);
        }

        os.flush();

        fin.close();
        os.close();
    }

    /**
     * 修正开始日期，使其时分秒正确调整为 00:00:00
     * @return 修正后Date
     */
    protected Date reviseStartDate(Date srcDate){
        if (srcDate != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(srcDate);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            calendar.set(year, month, day, 0, 0, 0);

            return calendar.getTime();
        }
        return null;
    }

    /**
     * 修正结束日期，使其时分秒正确调整为 23:59:59
     * @return 修正后Date
     */
    protected Date reviseEndDate(Date srcDate){
        if (srcDate != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(srcDate);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            calendar.set(year, month, day, 0, 0, 0);

            return calendar.getTime();
        }
        return null;
    }

    /**
     * 添加条件参数
     * @param conditionMap
     * @param col
     * @param operator
     * @param value
     */
    protected void addConditions(Map<String, Condition> conditionMap,
                                 String col, String operator, Object value){
        if (conditionMap!= null && value != null){
            conditionMap.put(col, new Condition(operator, value));
        }
    }

//    protected IRsp checkVerifyCode(String phone, Integer code, HttpServletRequest request){
//        IRsp rsp = new DefaultResp();
//        rsp.failure(-1, ResultPro.value(ProCon.res_code_verifyCode_time_out));
//
//        Integer sessionCode = (Integer) request.getSession().getAttribute(phone);
//        LogUtil.i("check.ac -> key", phone);
//        LogUtil.i("check.ac -> sessionCode", String.valueOf(sessionCode));
//        if (sessionCode != null){
//            if (code.equals(sessionCode)){
//                request.getSession().removeAttribute(phone);
//                rsp.success();
//            }else {
//                rsp.failure(-1, ResultPro.value(ProCon.res_code_verifyCode_error));
//            }
//        }
//
//        return rsp;
//    }


    /**
     * 判断参数是否为空并返回
     * @param verParam
     * @return 1.空字符串（全部非空）  2.接口通用json返回结构
     */
//    protected String verifyParamsEmpty(VerParam verParam){
//
//        if (verParam != null){
//            int code = ResultPro.code(ProCon.ResCode_Params_Empty);
//            String msg = ResultPro.value(ProCon.ResCode_Params_Empty);
//            DefaultResp resp = new DefaultResp();
//
//            for (String key : verParam.getKeys()){
//                String param = verParam.getParam(key);
//                if (!TextUtils.isNotEmpty(param)){
//                    resp.failure(code, msg + "->" + key);
//                    return resp.toJson();
//                }
//            }
//        }
//
//        return "";
//    }

    /**
     * 判断参数是否包含非法字符
     * @param verParam
     * @return 1.空字符串（全部合法）  2.接口通用json返回结构
     */
//    protected String verifyParamsError(VerParam verParam){
//
//        if (verParam != null){
//            int code = ResultPro.code(ProCon.ResCode_Reject_Char);
//            String msg = ResultPro.value(ProCon.ResCode_Reject_Char);
//            DefaultResp resp = new DefaultResp();
//
//            for (String key : verParam.getKeys()){
//                String param = verParam.getParam(key);
//                String result = FilterUtils.filterRejectChar(param);
//
//                if (TextUtils.isNotEmpty(result)){
//                    LogUtil.e("not verify param:" + param + "; char:" + result);
//                    resp.failure(code, msg + "->" + "char : " + result);
//                    return resp.toJson();
//                }
//            }
//        }
//
//        return "";
//    }

//    protected int getCode(String key) {
//        return ResultPro.code(key);
//    }
//
//    protected String getValue(String key) {
//        return ResultPro.value(key);
//    }

}
