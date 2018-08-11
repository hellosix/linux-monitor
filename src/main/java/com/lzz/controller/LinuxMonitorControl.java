package com.lzz.controller;

import com.lzz.logic.LinuxMonitorLogic;
import com.lzz.logic.LinuxNodeDao;
import com.lzz.util.Result;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

/**
 * Created by gl49 on 2017/11/7.
 */

@Controller
@RequestMapping("/monitor")
@Component
public class LinuxMonitorControl {

    @RequestMapping("/index")
    public String index(Model model){
        return initData(model);
    }

    @RequestMapping("/linux")
    public String linux(Model model){
        return initData(model);
    }

    private String initData(Model model){
        LinuxMonitorLogic logic = new LinuxMonitorLogic();
        JSONObject hostGroups = logic.getHostGroups();
        model.addAttribute("hostGroups", hostGroups);
        return "monitor_linux";
    }

    @RequestMapping(value = "/linux_monitor_date_detail", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject monitorDateDetail(@RequestBody String jsonBody){
        JSONObject reqObject =  JSONObject.fromObject( jsonBody );
        LinuxMonitorLogic logic = new LinuxMonitorLogic();
        List list = logic.getTopDetail( reqObject );
        return  Result.common( list );
    }

    @RequestMapping(value = "/linux_monitor_detail", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject monitorDetail(@RequestBody String jsonBody){
        JSONObject reqObject =  JSONObject.fromObject( jsonBody );
        LinuxMonitorLogic logic = new LinuxMonitorLogic();
        JSONObject jsonObject = new JSONObject();
        jsonObject = logic.getLinuxMonitorDetail( reqObject );
        return  jsonObject;
    }

    @RequestMapping(value = "/linux_ps_detail", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject linuxPsDetail(@RequestBody String jsonBody){
        JSONObject reqObject =  JSONObject.fromObject( jsonBody );
        LinuxMonitorLogic logic = new LinuxMonitorLogic();
        String res = logic.getLinuxPsDetail( reqObject );
        System.out.println( res );
        return  Result.common( res );
    }

    @RequestMapping(value = "/linux_node_add", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject linuxNodeAdd(@RequestBody String jsonBody){
        JSONObject reqObject =  JSONObject.fromObject( jsonBody );
        LinuxMonitorLogic logic = new LinuxMonitorLogic();
        boolean res = logic.linuxNodeAdd( reqObject );
        if( res ){
            return Result.OK();
        }else{
            return Result.Fail();
        }
    }
    @RequestMapping(value = "/linux_node_remove", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject linuxNoderemove(@RequestBody String jsonBody){
        JSONObject reqObject =  JSONObject.fromObject( jsonBody );
        LinuxMonitorLogic logic = new LinuxMonitorLogic();
        boolean res = logic.linuxNodeRemove( reqObject );
        if( res ){
            return Result.OK();
        }else{
            return Result.Fail();
        }
    }

    @RequestMapping(value = "/linux_check_node", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject linuxCheckNode(@RequestBody String jsonBody){
        JSONObject reqObject =  JSONObject.fromObject( jsonBody );
        String ip = reqObject.getString("ip");
        String clusterid = "";
        boolean res = false;
        if( reqObject.containsKey("clusterid") ){
            clusterid = reqObject.getString("clusterid");
            LinuxNodeDao dao = new LinuxNodeDao();
            res = dao.checkNodeExist(ip, clusterid);
        }
        if( res ){
            return Result.OK();
        }else{
            return Result.Fail();
        }

    }

    @RequestMapping(value = "/linux_node_list", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject linuxNodeList(@RequestBody String jsonBody){
        LinuxNodeDao dao = new LinuxNodeDao();
        List list = dao.getAllLinuxNodeList();
        return Result.common( list );
    }

}
