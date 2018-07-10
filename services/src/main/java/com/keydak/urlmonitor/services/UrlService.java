package com.keydak.urlmonitor.services;

import com.keydak.database.Operator;
import com.keydak.urlmonitor.business.ISearchSource;
import com.keydak.urlmonitor.constants.DBCon;
import com.keydak.urlmonitor.constants.ResultCode;
import com.keydak.urlmonitor.constants.SpringCon;
import com.keydak.urlmonitor.constants.SystemCode;
import com.keydak.urlmonitor.services.config.ResultPro;
import com.keydak.urlmonitor.services.config.SystemPro;
import com.keydak.utils.LogUtil;
import com.keydak.utils.TextUtils;
import com.keydak.utils.expression.Condition;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/7/10.
 */
public class UrlService implements IUrlService{
    @Autowired
    @Qualifier(SpringCon.URLCONVERTERSEARCHSOURCE)
    ISearchSource searchSource;

    @Override
    public Result longToShortUrl(UrlEncoder urlEncoder) {
        List<String> longUrls=urlEncoder.getLongurls();
        Map<String,String> urlMap=new HashMap<String,String>();
        for(String longUrl:longUrls){
            String shortUrl=null;
            if(TextUtils.isNotEmpty(longUrl)){
                try{
                    shortUrl=toShortUrl(longUrl);
                }catch(Exception ex){
                    ex.printStackTrace();
                    LogUtil.e("UrlService.longToShortUrl():"+ex.getMessage());
                    return new Result(ResultPro.code(ResultCode.ERROR_BAD_OPERATION),
                            longUrl+"转换失败",
                            null);
                }
                UrlConverter urlConverter=new UrlConverter(longUrl,shortUrl,new Date());
                com.keydak.urlmonitor.business.UrlConverter busUrlConverter=urlConverter.toBusinessUrlConverter(null);
                boolean isSave=busUrlConverter.save(true);
                String shortMapUrl= SystemPro.getValue(SystemCode.SHORT_WEB_URL_PREFIX)+shortUrl;
                urlMap.put(longUrl,shortMapUrl);

            }else{
                return new Result(ResultPro.code(ResultCode.ERROR_BAD_OPERATION),
                        "转换URL不能为空",null);
            }
        }
        return new Result(ResultPro.code(ResultCode.SUCCESS),
                null,urlMap);

    }

    @Override
    public String getLongUrl(String shortUrl) {

        Map<String, Condition> conditionMap = new HashMap<>();
        if (shortUrl != null){
            conditionMap.put(DBCon.COL_SHORTURL, new Condition(Operator.EQ,shortUrl));
        }
        List<com.keydak.urlmonitor.db.UrlConverter> busUrlConverterList=searchSource.Search(conditionMap);
        if(busUrlConverterList.size()>0){
            com.keydak.urlmonitor.business.UrlConverter busUrlConverter=new com.keydak.urlmonitor.business.UrlConverter().fromDbUrlConverter(busUrlConverterList.get(0));
            return busUrlConverter.getLongUrl();
        }
        return null;
    }

    public static String toShortUrl(String url) {
        // 可以自定义生成 MD5 加密字符传前的混合 KEY
        String key = "";
        // 要使用生成 URL 的字符
        String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z" };
        // 对传入网址进行 MD5 加密
        String sMD5EncryptResult = DigestUtils.md5Hex(key + url);
        String hex = sMD5EncryptResult;
        String[] resUrl = new String[4];
        for (int i = 0; i < 4; i++) {
            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);
            // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用
            // long ，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            String outChars = "";
            for (int j = 0; j < 6; j++) {
                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                long index = 0x0000003D & lHexLong;
                // 把取得的字符相加
                outChars += chars[(int) index];
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;
            }
            // 把字符串存入对应索引的输出数组
            resUrl[i] = outChars;
        }
        return resUrl[0];
    }
}
