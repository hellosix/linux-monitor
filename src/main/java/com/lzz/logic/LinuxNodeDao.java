package com.lzz.logic;

import com.lzz.model.LinuxNode;
import com.lzz.util.MysqlUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by gl49 on 2017/11/7.
 */
public class LinuxNodeDao {
    public boolean addNode(LinuxNode node){
        String sql = MysqlUtil.insertStr(node, "linux_nodes");
        int res = MysqlUtil.insert( sql );
        if( res > 0 ){
            return true;
        }
        return false;
    }


    public boolean removeNode(String ip){
        String sql = "delete from linux_nodes where ip='" + ip + "'";
        return MysqlUtil.delete( sql );
    }

    public Map getLinuxNode(String ip){
        String sql = "select * from linux_nodes where ip='" + ip + "'";
        return MysqlUtil.selectMap( sql );
    }

    public List getAllLinuxNodeList(){
        String sql = "select * from linux_nodes limit 200";
        List list = MysqlUtil.select(sql);
        return list;
    }

    public boolean checkNodeExist(String ip, String clusterid) {
        boolean res = true;
        String sql = "select * from linux_nodes where groups like '%" + clusterid + "%'";
        List list = MysqlUtil.select( sql );
        if( null == list || list.isEmpty() ){
            res = false;
        }
        return res;
    }
    public boolean checkNodeExist(String ip) {
        boolean res = true;
        String sql = "select * from linux_nodes where ip='" + ip + "'";
        if( ip.split("\\.").length != 4 ){
            sql = "select * from linux_nodes where ip like '%" + ip + "%'";
        }
        List list = MysqlUtil.select( sql );
        if( null == list || list.isEmpty() ){
            res = false;
        }
        return res;
    }
}
