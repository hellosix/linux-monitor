package com.lzz.dao;

import com.lzz.model.LinuxMonitor;
import com.lzz.util.MysqlUtil;

import java.util.List;

/**
 * Created by gl49 on 2017/11/7.
 */
public class LinuxMonitorDao {
    public boolean addMonitor(LinuxMonitor monitor){
        String sql = MysqlUtil.insertStr(monitor, "linux_monitor");
        int res = MysqlUtil.insert( sql );
        if( res > 0 ){
            return true;
        }
        return false;
    }

    public List getLinuxNodes(){
        String sql = "select * from linux_nodes limit 200";
        List list = MysqlUtil.select( sql );
        return  list;
    }

    public List getLinuxNodeDetail(String sql){
        List result = MysqlUtil.select( sql );
        return result;
    }

    public List getTopDate(String ip, long start_time, long end_time){
        String sql = "select * from linux_monitor where ip='" + ip + "' and add_time > " + start_time+ " and add_time < " + end_time + " limit 200";
        List list = MysqlUtil.select(sql);
        return list;
    }
}
