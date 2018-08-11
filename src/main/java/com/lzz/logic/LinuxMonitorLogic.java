package com.lzz.logic;

import com.google.common.collect.Lists;
import com.lzz.dao.LinuxMonitorDao;
import com.lzz.model.LinuxMonitor;
import com.lzz.model.LinuxNode;
import com.lzz.util.CommonUtil;
import com.lzz.util.LinuxMonitorUtil;
import com.lzz.util.MysqlUtil;
import com.lzz.util.Result;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gl49 on 2017/11/7.
 */
public class LinuxMonitorLogic {
    public JSONObject getLinuxMonitorDetail(JSONObject reqObject) {
        String ip = reqObject.getString("ip");
        String type = reqObject.getString("type");
        String date = reqObject.getString("date");
        String startTime = "";
        if( reqObject.containsKey("start_time") ){
            startTime = reqObject.getString("start_time");
        }
        String endTime = "";
        if( reqObject.containsKey("end_time") ){
            endTime = reqObject.getString("end_time");
        }
        String sql = "select add_time, ";
        String groupby = " group by " + date + " ";
        int add_time = 0;
        long currentTime = System.currentTimeMillis() / 1000;
        if(date.equals( "day" )){
            sql += "day as date, ";
            add_time = (int) (currentTime - (7 * 24 * 60 * 60));
        }else if( date.equals("hour")){
            sql += "concat(CAST(hour AS CHAR), ':', CAST(minute AS CHAR)) as date, ";
            groupby += ",day order by day,hour";
            add_time = (int) (currentTime - (24 * 60 * 60));
        }else {
            sql += "concat(CAST(hour AS CHAR), ':', CAST(minute AS CHAR)) as date, ";
            groupby += ",hour,day order by day,hour,minute";
            add_time = (int) (currentTime - (60 * 60));
        }

        int start_time = 0;
        int end_time = 0;
        if( org.apache.commons.lang.StringUtils.isBlank( startTime ) && !org.apache.commons.lang.StringUtils.isBlank( endTime ) ){
            end_time = CommonUtil.getTimeStamp( endTime, "yyyy-MM-dd HH:mm" );
            start_time = end_time - 24*60*60;
        }else if( org.apache.commons.lang.StringUtils.isBlank( endTime ) && !org.apache.commons.lang.StringUtils.isBlank( startTime ) ){
            start_time = CommonUtil.getTimeStamp( startTime, "yyyy-MM-dd HH:mm" );
            end_time = CommonUtil.getTime();
        }else if( !org.apache.commons.lang.StringUtils.isBlank( endTime ) && !org.apache.commons.lang.StringUtils.isBlank( startTime ) ){
            start_time = CommonUtil.getTimeStamp( startTime, "yyyy-MM-dd HH:mm" );
            end_time = CommonUtil.getTimeStamp( endTime, "yyyy-MM-dd HH:mm" );
            if(start_time > end_time ){
                start_time = 0;
                end_time = 0;
            }
        }

        String all_tag = ip;
        String all_tag_where = " where ip like '%" + all_tag + "%' and ";
        if( reqObject.containsKey("clusterid") ){
            all_tag = reqObject.getString("clusterid");
            all_tag_where = " where service like '%" + all_tag + "%' and ";
        }
        String whereStr = all_tag_where;
        if( ip.split("\\.").length == 4 ){
            whereStr = " where ip='" + ip + "' and ";
        }
        String timeRange = "";
        if( 0 == start_time && 0 == end_time ){
            timeRange = "add_time > " + add_time;
        }else{
            timeRange = "add_time > " + start_time + " and add_time < " + end_time;
        }
        all_tag_where = all_tag_where + timeRange;
        whereStr = whereStr + timeRange;
        LinuxMonitor linuxMonitor = new LinuxMonitor();
        List filterField = Lists.newArrayList("id","service","day","hour","minute","add_time","ip","top_1","top_2","top_3");
        sql += MysqlUtil.groupStr(linuxMonitor, "linux_monitor", type, filterField);
        sql = sql + whereStr + groupby;

        LinuxMonitorDao dao = new LinuxMonitorDao();
        List list = dao.getLinuxNodeDetail(sql);
        String topLoadAverage = "select * from linux_monitor " + all_tag_where + " order by load_average desc  limit 2";
        List topList = MysqlUtil.select( topLoadAverage );
        JSONObject res = new JSONObject();
        res.put("topLoadAverage", topList);
        res.put("list", list);
        return Result.common( res );
    }

    public JSONObject getHostGroups() {
        LinuxNodeDao dao = new LinuxNodeDao();
        List<Map> list = dao.getAllLinuxNodeList();
        Map<String, List> groups = new HashMap<>();
        for(Map map : list){
            String ip = (String) map.get("ip");
            String key = StringUtils.substringBeforeLast(ip, ".");
            if( groups.containsKey( key ) ){
                List ipList = groups.get( key );
                ipList.add( ip );
            }else{
                List ipList = new ArrayList<>();
                ipList.add( ip );
                groups.put( key, ipList );
            }
        }
        JSONObject jsonObject = new JSONObject();
        if( !groups.isEmpty() ){
            jsonObject.putAll( groups );
        }
        return jsonObject;
    }

    public boolean linuxNodeAdd(JSONObject reqObject) {
        String groups = reqObject.getString("groups");
        String password = reqObject.getString("password");
        String ip = reqObject.getString("ip");
        String username = reqObject.getString("username");
        LinuxNode node = new LinuxNode();
        node.setAdd_time( CommonUtil.getTime() );
        node.setUsername( username );
        node.setPassword( password );
        node.setIp( ip );
        node.setGroups( groups );
        LinuxNodeDao dao = new LinuxNodeDao();
        Map resMap = dao.getLinuxNode(ip);
        boolean res = true;
        if( null == resMap || resMap.isEmpty() ){
            res = dao.addNode( node );
        }else{
            if( reqObject.getString("isedit").equals("true") ){
                res = dao.removeNode( ip );
                if( res ){
                    res = dao.addNode( node );
                }
            }else{
                String oldGroups = (String) resMap.get("groups");
                groups = oldGroups + "," + groups;
                node.setGroups( groups );
                res = dao.removeNode( ip );
                if( res ){
                    res = dao.addNode( node );
                }
            }
        }
        return res;
    }

    public boolean linuxNodeRemove(JSONObject reqObject) {
        String ip = reqObject.getString("ip");
        LinuxNodeDao dao = new LinuxNodeDao();
        return dao.removeNode( ip );
    }

    public List getTopDetail(JSONObject reqObject) {
        String ip = reqObject.getString("ip");
        long add_time = reqObject.getLong("add_time");
        long start_time = add_time - 60*1000;
        long end_time = add_time + 60*60*1000;
        LinuxMonitorDao dao = new LinuxMonitorDao();
        List list = dao.getTopDate(ip, start_time, end_time);
        return  list;
    }

    public String getLinuxPsDetail(JSONObject reqObject) {
        String ip = reqObject.getString("ip");
        String pid = reqObject.getString("pid");
        LinuxNodeDao dao = new LinuxNodeDao();
        Map resmap = dao.getLinuxNode(ip);
        String username = (String) resmap.get("username");
        String password = (String) resmap.get("password");
        return LinuxMonitorUtil.getLinuxPsDetail(ip, username, password, pid);
    }
}
