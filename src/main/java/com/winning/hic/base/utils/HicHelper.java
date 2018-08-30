package com.winning.hic.base.utils;

import com.winning.hic.model.HlhtRyjlRyswjl;
import com.winning.hic.model.MbzDataSet;
import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: LENOVO
 * Date: 2018-08-08
 * Time: 13:04
 */
public class HicHelper {

    private static final Logger logger = LoggerFactory.getLogger(HicHelper.class);

    /**
     * 解析xml信息获取对象字段信息
     * @param mbzDataSets 接口表中定义字段名称信息
     * @param document xml解析dom
     * @param obj 初始化对象
     * @param paramTypeMap 对象定义变量信息 map集合
     * @return
     * @throws ParseException
     */
    public static Object initModelValue(List<MbzDataSet> mbzDataSets, Document document,
                                        Object obj, Map<String, String> paramTypeMap) throws ParseException {

        String bltd ="";
        for (MbzDataSet dataSet : mbzDataSets) {
            //获取属性名
            String pyCode = dataSet.getPyCode();
            if(pyCode.equals("yfjzs")){
                System.out.println("yfjzs");
            }
            String methodName = "set" + StringUtil.titleCase(pyCode);
            String strValue = null ;

            //判断是否可以取值到，不能则提供默认值
            try {
                strValue =  DomUtils.getAttrValueByDataSet(document, dataSet);
                logger.info("pyCode:{};methodName:{};strValue:{};", pyCode, methodName, strValue);
            }catch (NullPointerException e){
                logger.info("pyCode:{};methodName:{};strValue:{};using default value", pyCode, methodName, strValue);
            }
            Object value = null;
            if(strValue == null){
                String paramType = paramTypeMap.get(pyCode.trim());
                if (paramType.contains("String")) {
                    value = "N";
                }else if (paramType.contains("Short")) {
                    value = new Short("-9");
                }else if (paramType.contains("Timestamp")) {
                    String pattern = "yyyy-MM-dd HH:mm:ss";
                    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                    String dateStr = "1990-01-01 00:00:00";
                    java.sql.Timestamp sqlDate = new java.sql.Timestamp(sdf.parse(dateStr).getTime());
                    value = sqlDate;
                }else if (paramType.contains("Date")) {
                    String pattern = "yyyy-MM-dd HH:mm:ss";
                    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                    String dateStr = "1990-01-01 00:00:00";
                    java.sql.Date sqlDate = new java.sql.Date(sdf.parse(dateStr).getTime());
                    value = sqlDate;
                }else if (paramType.contains("BigDecimal")) {
                    value = new BigDecimal(-9);
                } else if (paramType.contains("Integer")) {
                    value = new Integer(-9);
                }
            }else{
                String paramType = paramTypeMap.get(pyCode);
                if (paramType.contains("String")) {
                    value = StringUtil.isEmptyOrNull(strValue) ? "N" : strValue;
                } else if (paramType.contains("Short")) {
                    //格式：50`50`50
                    String shortStr = StringUtil.isEmptyOrNull(strValue) ? null : strValue;
                    value = StringUtil.isEmptyOrNull(shortStr) ? -9 : Short.parseShort(shortStr);
                } else if (paramType.contains("Timestamp")) {
                    String dateStr = StringUtil.isEmptyOrNull(strValue) ? "1990-01-01 00:00:00" : strValue;
                    String pattern = "yyyy-MM-dd HH:mm:ss";
                    if(StringUtil.hasChinese(dateStr)){
                        if(dateStr.contains("年")){
                            dateStr = dateStr.replace("年","-");
                        }
                        if(dateStr.contains("月")){
                            dateStr = dateStr.replace("月","-");
                        }
                        if(dateStr.contains("日")){
                            dateStr = dateStr.replace("日"," ");
                        }
                        if(dateStr.contains("时")){
                            dateStr = dateStr.replace("时",":");
                        }
                        if(dateStr.contains("分")){
                            dateStr = dateStr.replace("分",":00");
                        }
                    }
                    dateStr=dateStr.trim();
                    if(dateStr.length()<=10) {
                        pattern="yyyy-MM-dd";
                    } else if(dateStr.contains("yyyy-MM-dd HH:mm:00")){
                        dateStr = dateStr.substring(0,19);
                        pattern="yyyy-MM-dd HH:mm:ss";
                    }else if(dateStr.contains("yyyy-MM-dd")){
                        dateStr = dateStr.substring(0,10);
                        pattern="yyyy-MM-dd";
                    }else{
                        dateStr=dateStr.concat(":00").substring(0,18);
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                    try {
                        Date date = StringUtil.isEmptyOrNull(dateStr) ? null : sdf.parse(dateStr);
                        if (date != null) {
                            java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
                            value = sqlDate;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else if (paramType.contains("Date")) {
                    //格式：636467930400000000`2017-11-20,16:44
                    String dateStr = StringUtil.isEmptyOrNull(strValue) ? "1990-01-01 00:00:00" : strValue;
                    String pattern = "yyyy-MM-dd HH:mm:ss";
                    if(StringUtil.hasChinese(dateStr)){
                        if(dateStr.contains("年")){
                            dateStr = dateStr.replace("年","-");
                        }
                        if(dateStr.contains("月")){
                            dateStr = dateStr.replace("月","-");
                        }
                        if(dateStr.contains("日")){
                            dateStr = dateStr.replace("日","");
                        }
                    }
                    if(dateStr.length()<=10) {
                        pattern="yyyy-MM-dd";
                    } else if(dateStr.contains("yyyy-MM-dd HH:mm:00")){
                        dateStr = dateStr.substring(0,19);
                        pattern="yyyy-MM-dd HH:mm:ss";
                    }else if(dateStr.contains("yyyy-MM-dd")){
                        dateStr = dateStr.substring(0,10);
                        pattern="yyyy-MM-dd";
                    }else{
                        dateStr=dateStr.concat(":00").substring(0,18);
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                    try {
                        Date date = StringUtil.isEmptyOrNull(dateStr) ? null : sdf.parse(dateStr);
                        if (date != null) {
                            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                            value = sqlDate;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else if (paramType.contains("BigDecimal")) {
                    String dateStr = StringUtil.isEmptyOrNull(strValue) ? "-9" : strValue;
                    value = StringUtil.isEmptyOrNull(dateStr) ? null : new BigDecimal(dateStr);
                } else if (paramType.contains("Integer")) {
                    String dateStr = StringUtil.isEmptyOrNull(strValue) ? "-9" : strValue;
                    value = StringUtil.isEmptyOrNull(dateStr) ? null : Integer.parseInt(dateStr);
                }
            }
            //类型
            try {
                if(value!=null){
                     /*String info = "长度正常，可以入库";
                   if(value instanceof String){
                        String str = value.toString();
                        Long datalength = (long)str.length();
                        boolean hasChs = StringUtil.hasChinese(str);
                        if(hasChs){
                            datalength = datalength * 2;
                        }
                        if(dataSet.getDataLength() != 0 && datalength > dataSet.getDataLength()){
                            info = "发生截断，需要调整数据长度";
                        }
                        logger.info("pyCode:{};methodName:{};strValue:{};info:{}", pyCode, methodName, value,info);
                    }
                    logger.info("pyCode:{};methodName:{};strValue:{};info:{}", pyCode, methodName, value,info);*/
                    logger.info("pyCode:{};methodName:{};strValue:{}", pyCode, methodName, value);
                    if(dataSet.getSourceType().equals("6")&&pyCode.equals("bltd")){
                        if(!"N".equals(value)){
                            bltd =bltd+" "+value;
                            ReflectUtil.setParamKind(obj, methodName, bltd);
                        }
                    }else{
                        ReflectUtil.setParam(obj, methodName, value);

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            logger.info("Model:{}",obj);
        }
        return  obj;
    }
}
