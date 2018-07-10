package com.keydak.urlmonitor.db;

import com.keydak.database.SQLParam;
import com.keydak.urlmonitor.constants.DBCon;
import com.keydak.utils.LogUtil;
import com.keydak.utils.TextUtils;
import com.keydak.utils.TimeUtils;
import com.keydak.utils.expression.Condition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by admin on 2018/7/10.
 */
public class UrlConverterDao extends AbsDao{
    public UrlConverterDao() {
        super();
    }
    /**
     * 列表筛选用
     * @param conditions
     * @param start
     * @param size
     * @return
     */
    public List<UrlConverter> getAll(Map<String, Condition> conditions, Integer start, Integer size){
        List<UrlConverter> urlConverterList = new ArrayList<>();
        if (conditions != null){
            List<SQLParam> sqlParamList = new ArrayList<>();
            Set<String> keys = conditions.keySet();
            for (String key : keys){
                Condition condition = conditions.get(key);
                Object value = condition.getValue();
                if (value != null){
                    String strVal = String.valueOf(value);
                    if (TextUtils.isNotEmpty(strVal)){
                        sqlParamList.add(new SQLParam(key, strVal, false).setOperate(condition.getOperator()));
                    }
                }
            }
            try {
                if (start != null && size != null){
                    urlConverterList = mDao.findList(DBCon.VIEW_URLCONVERTER, null, "and", sqlParamList, UrlConverter.class, start, size);
                }else {
                    urlConverterList = mDao.findList(DBCon.VIEW_URLCONVERTER, null, "and", sqlParamList, UrlConverter.class);
                }
            }catch (Exception e){
                e.printStackTrace();
                LogUtil.e("UrlConverterDao.getAll:", e.getMessage());
            }
        }else{
            try {
                urlConverterList = mDao.findList(DBCon.VIEW_URLCONVERTER, null, "and", new ArrayList<>(), UrlConverter.class);
            }catch (Exception e){
                e.printStackTrace();
                LogUtil.e("TicketDao.getAll", e.getMessage());
            }
        }
        return urlConverterList;
    }

    public boolean updateUrlConverter(UrlConverter urlConverter){
        if (urlConverter != null){
            List<SQLParam> sqlParamList = new ArrayList<>();
            sqlParamList.add(new SQLParam(DBCon.COL_LONGURL, urlConverter.getLongUrl(), true));
            sqlParamList.add(new SQLParam(DBCon.COL_SHORTURL, urlConverter.getShortUrl(), false));
            try {
                return mDao.call(DBCon.PROC_UPDATEURLCONVERTER, sqlParamList);
            }catch (Exception e){
                e.printStackTrace();
                LogUtil.e("UrlConverterDao.updateUrlConverter:", e.getMessage());
            }
        }
        return false;
    }

    public boolean insertUrlConverter(UrlConverter urlConverter){
        if (urlConverter != null){
            List<SQLParam> sqlParamList = new ArrayList<>();
            sqlParamList.add(new SQLParam(DBCon.COL_LONGURL, urlConverter.getLongUrl(), true));
            sqlParamList.add(new SQLParam(DBCon.COL_SHORTURL, urlConverter.getShortUrl(), false));
            sqlParamList.add(new SQLParam(DBCon.COL_CREATETIME, TimeUtils.toMysqlDateTime(urlConverter.getCreateTime()), false));
            try {
                return mDao.call(DBCon.PROC_INSERTURLCONVERTER, sqlParamList);
            }catch (Exception e){
                e.printStackTrace();
                LogUtil.e("UrlConverterDao.insertUrlConverter:", e.getMessage());
            }
        }
        return false;
    }

    public boolean deleteUrlConverter(UrlConverter urlConverter){
        if (urlConverter != null){
            List<SQLParam> sqlParamList = new ArrayList<>();
            sqlParamList.add(new SQLParam(DBCon.COL_LONGURL, urlConverter.getLongUrl(), true));
            try {
                return mDao.call(DBCon.PROC_DELETEURLCONVERTER, sqlParamList);
            }catch (Exception e){
                e.printStackTrace();
                LogUtil.e("UrlConverterDao.deleteUrlConverter:", e.getMessage());
            }
        }
        return false;
    }




}
