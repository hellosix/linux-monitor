package com.lzz.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gl49 on 2017/11/7.
 */
public class CommonUtil {
    private static final Logger logger = Logger.getLogger(CommonUtil.class);
    private static final Log createLogger = LogFactory.getLog("create");

    private CommonUtil(){
        //ignore
    }

    public static String getAddress(){
        String ip = "";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch ( UnknownHostException e ) {
            logger.error( e );
        }
        return ip;
    }

    public static String getCurrentDetailHour(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(new Date());
    }

    public static String getCurrentDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    public static int getTime(){
        return (int) (System.currentTimeMillis() / 1000);
    }

    public static int getDay(){
        Date dNow = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String currentDay = sdf.format(dNow); //格式化当前时间
        return Integer.valueOf( currentDay );
    }
    public static int getHour(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MINUTE);
    }

    public static int getTimeStamp(String dateStr, String format){
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat( format );
        int timeStemp = 0;
        Date date= null;
        try {
            date = simpleDateFormat .parse( dateStr );
        } catch (ParseException e) {
            logger.error( e );
        }
        if( null == date ){
            return 0;
        }
        long tmp = date.getTime();
        timeStemp = (int) (tmp / 1000);
        return timeStemp;
    }


    public static Map<String, String> changeStrToMap(String info){
        Map<String, String> resMap = new HashMap<>();
        if( !StringUtils.isBlank( info ) ){
            String[] infoArr = info.split(",");
            for(String item : infoArr){
                String[] temps = item.split("=");
                if( temps.length == 2 ){
                    resMap.put( temps[0], temps[1] );
                }
            }
        }
        return resMap;
    }

    public static void formLogger(String id, String msg, boolean hasTime){
        if( hasTime ){
            createLogger.info( id + "'" + CommonUtil.getCurrentDetailHour() + " " + msg );
        }else{
            createLogger.info( id + "'" + msg );
        }
    }

    public static void formLogger(String id, String msg){
        formLogger(id, msg, true);
    }
}
