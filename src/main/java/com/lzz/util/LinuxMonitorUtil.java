package com.lzz.util;

import com.lzz.dao.LinuxMonitorDao;
import com.lzz.model.LinuxMonitor;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gl49 on 2017/11/7.
 * 获取机器一个网卡的流量 sar -n DEV 1 1 | grep Average |  grep lo | awk '{print $3 "|" $4 $5 $6 $7 $8 $9}'
 */
public class LinuxMonitorUtil {
    private static final Logger logger = Logger.getLogger(LinuxMonitorUtil.class);

    static {
        MysqlUtil.initMonitorTable();
    }

    public static void start(){
        Thread t = new Thread(){
            @Override
            public void run() {
                startMonitor();
            }
        };
        t.setName("linux-monitor");
        t.setDaemon( true );
        t.start();
    }


    public static void deleteData() {
        Thread t = new Thread(){
            @Override
            public void run() {
                while (true){
                    startDeleteData();
                    try {
                        long sleepTime = 60*1000*60*24;
                        Thread.sleep( sleepTime );
                    } catch ( Exception e ) {
                        logger.error( e );
                    }
                }
            }
        };
        t.setName("linux-delete-monitor-data");
        t.setDaemon( true );
        t.start();
    }

    public static void startDeleteData(){
        int sevcenTime = CommonUtil.getTime() - 7*24*60*60;
        String sql = "delete from linux_monitor where add_time < " + sevcenTime;
        MysqlUtil.delete( sql );
    }

    public static void startMonitor(){
        LinuxMonitorDao linuxMonitorDao = new LinuxMonitorDao();
        while ( true ){
            List<Map> list = linuxMonitorDao.getLinuxNodes();
            for(Map node : list){
                try {
                    String ip = (String)node.get("ip");
                    String username = (String)node.get("username");
                    String password = (String)node.get("password");
                    String service = (String)node.get("groups");
                    Map map = getMonitor(ip, username, password);
                    if( null == map || map.isEmpty() ){
                        logger.error( "error ip, username or pass " + node );
                        continue;
                    }
                    map.put("ip", ip);
                    map.put("service", service);
                    LinuxMonitor linuxMonitor = getFormatResult(map);
                    System.out.println( linuxMonitor );
                    linuxMonitorDao.addMonitor( linuxMonitor );
                }catch (Exception e){
                    logger.error( e );
                }
            }

            try {
                Thread.sleep( 60000 );
            } catch (InterruptedException e) {
                logger.error( e );
            }
        }
    }

    public static String getLinuxPsDetail(String ip, String username, String password, String pid){
        String cmd = "ps -up " + pid;
        RemoteShellUtil rms = new RemoteShellUtil(ip, username, password);
        String result = rms.exec( cmd );
        return  result;
    }

    public static Map getMonitor(String ip, String username, String pass){
        String cmd = "export TERM=linux;res=\"\"\n" +
                formatMonitorCmd("load_average", "`uptime | awk '{print $NF}'`")+
                formatMonitorCmd("processor", "`grep -c 'model name' /proc/cpuinfo`")+
                formatMonitorCmd("netstat", "`netstat -nat | wc -l`")+
                formatMonitorCmd("memory_total", "`free -g | grep Mem | awk '{print $2}'`")+
                formatMonitorCmd("memory_use", "`free -g | grep Mem | awk '{print $3}'`")+
                formatMonitorCmd("memory_free", "`free -g | grep Mem | awk '{print $4}'`")+
                formatMonitorCmd("memory_available", "`free -g | grep Mem | awk '{print $7}'`")+
                formatMonitorCmd("ps_num", "`ps -ea | wc -l`")+
                formatMonitorCmd("thread_num", "`ps -eo nlwp | tail -n +2 | awk '{num_threads += $1} END {print num_threads}'`")+
                formatMonitorCmd("top", "`top -b | head -n 10 | tail -n 3 | awk 'BEGIN{count=0} {print $0 \"|\"}'`")+
                "echo -e $res";
        RemoteShellUtil rms = new RemoteShellUtil(ip, username, pass);
        String result = rms.exec( cmd );
        if(StringUtils.isBlank( result )){
            return null;
        }
        Map map = formatResult( result );
        return map;
    }

    public static LinuxMonitor getFormatResult(Map map) throws InvocationTargetException, IllegalAccessException {
        LinuxMonitor linuxMonitor = new LinuxMonitor();
        BeanUtils.populate(linuxMonitor, map);
        linuxMonitor.setAdd_time( CommonUtil.getTime() );
        linuxMonitor.setDay( CommonUtil.getDay() );
        linuxMonitor.setHour( CommonUtil.getHour() );
        linuxMonitor.setMinute( CommonUtil.getMinute());
        return linuxMonitor;
    }


    private static String formatMonitorCmd(String field, String cmd){
        return "res=${res}\"" + field + "'" + cmd + "\\n" +"\"\n";
    }

    private static Map formatResult(String result){
        Map<String,String> map = new HashMap();
        String[] lines = result.split("\n");
        for (String line : lines) {
            if( StringUtils.isBlank( line ) ){
                continue;
            }
            String[] tmp = line.split("'");
            if( "top".equals(tmp[0]) ){
                String arr[] = tmp[1].split("\\|");
                map.put("top_1", arr[0].trim());
                map.put("top_2", arr[1].trim());
                map.put("top_3", arr[2].trim());
            }else{
                map.put(tmp[0].trim(), tmp[1].trim());
            }
        }
        return map;
    }
}
