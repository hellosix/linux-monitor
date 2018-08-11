function show_host_list() {
    var data = {};
    data.hostGroups = window.hostGroups;
    smarty.html( "linux_host_center_list", data, "container-list-div",function () {
        $("select").selectpicker();
        last_click();
    });
}

$(document).on("click", "#node-manager", function(){
    smarty.post( "/monitor/linux_node_list", JSON.stringify({}), "linux_host_manager", "center-content-div", function(){
        $("select").selectpicker();
    });
});
$(document).on("click", "#node-monitor", function(){
    var data = {};
    data.hostGroups = window.hostGroups;
    smarty.html( "linux_monitor_content", data, "center-content-div",function () {
        $("select").selectpicker();
        $(".list-group-item[data-ip='" + window.last_host + "']").trigger("click");
    });
});

function makeLinuxCharts(theme, bgColor, field) {
    if( typeof( window.linuxChartData ) == "undefined" ){
        return;
    }
    var len = window.linuxChartData.length;

    var char_data_table = window.linuxChartData;

    var linuxChart;

    if (linuxChart) {
        linuxChart.clear();
    }

    // background
    if (document.body) {
        document.body.style.backgroundColor = bgColor;
    }

    // column chart
    linuxChart = AmCharts.makeChart("chartdiv_table", {
        type: "serial",
        theme: theme,
        dataProvider: char_data_table,
        categoryField: "date",
        startDuration: 1,

        categoryAxis: {
            gridPosition: "start"
        },
        valueAxes: [{
            title: "Metric"
        }],
        graphs: [{
            type: "column",
            title: field,
            valueField: field,
            lineAlpha: 0,
            fillAlphas: 0.8,
            balloonText: "[[title]] in [[category]]  <b>[[value]]</b>"
        }]
    });
}

$(document).on("click", "#linux-field-title > th", function(){
    var field = $(this).data("field");
    window.last_table_title = field;
    if( field != "date" ){
        $("#linux-field-title > th").removeClass("selected");
        $(this).addClass("selected");
        makeLinuxCharts("light", "#FFFFFF", field);
    }
});

$(document).on("click", "#skip-node-manager", function(){
    var url = window.STATIC_URL + "/monitor/linux#node-manager";
    window.location.href = url;
});

$(document).on("click", "#add-machine", function(){
    smarty.open( "linux_add_machine", {}, { title: "Add Machine", width:330, height:450},function(){
    })
});

$(document).on("click", "#linux-add-machine-form-button", function(){
    var form_data = sparrow_form.encode( "linux-add-machine-form",0 ); //0 表示所有字段都提交， 2 表示有改变的才提交
    if ( sparrow.empty( form_data ) ){
        return false;
    }
    form_data.isedit = $(this).data("isedit");
    linux_add_machine(form_data);
});

$(document).on("click", ".linux-remove-machine-button", function(){
    var ip = $(this).data("ip");
    linux_remove_machine(ip);
});

$(document).on("click", ".linux-edit-machine-button", function(){
    var detail = $(this).data("detail");
    detail = parse_tpl_data(detail);
    detail.isedit = true;
    smarty.open( "linux_add_machine", detail, { title: "Edit Machine", width:330, height:450},function(){
    });
});

$(document).on("click", ".last-click-button", function(){
    window.last_host = $(this).data("ip");
});

function linux_add_machine(form_data){
    ajax.post( window.HOST_URL + "/monitor/linux_node_add", JSON.stringify(form_data), function( re, arg ){
        $(".layui-layer-close").trigger("click");
        $("#node-manager").trigger("click");
    });
}

function linux_remove_machine(ip){
    var data = {};
    data.ip = ip;
    ajax.post( window.HOST_URL + "/monitor/linux_node_remove", JSON.stringify(data), function( re, arg ){
        $("#node-manager").trigger("click");
    });
}

$(document).on("click", ".linux-date-detail", function(){
    var data = {};
    data.add_time = $(this).data("add-time");
    data.ip = $(".linux-monitor-request.list-group-item.selected").data("ip");
    smarty.popen("/monitor/linux_monitor_date_detail", JSON.stringify(data), "linux_monitor_date_detail", true,{"title":"Top","width":"1100px","height":"500px","overflow":"auto"},function () {

    } );
});

$(document).on("click", ".linux-ps-detail", function(){
    var data = {};
    data.pid = $(this).data("pid");
    data.ip = $(".linux-monitor-request.list-group-item.selected").data("ip");
    smarty.popen("/monitor/linux_ps_detail", JSON.stringify(data), "linux_monitor_ps_detail", true,{"title":"PS Detail","width":"1100px","height":"500px","overflow":"auto"},function () {

    } );
});

$(document).on("click", ".linux-monitor-request", function(){
    linux_monitor_request();
});
// 监听事件控件
$( document ).on( 'change', 'input.linux-monitor-date-request', function(){
    linux_monitor_request();
} );


function linux_monitor_request(){
    var data = {};
    data.date = $(".linux-monitor-request[name='date']:checked").val();
    if( !data.date ){
        data.date = "minute";
    }
    data.type = $(".linux-monitor-request[name='type']").val();
    if( !data.type ){
        data.type = "avg";
    }
    data.start_time = $("input[name='start_time']").val();
    data.end_time = $("input[name='end_time']").val();
    data.ip = $(".linux-monitor-request.list-group-item.selected").data("ip");
    ajax.post( window.HOST_URL + "/monitor/linux_monitor_detail", JSON.stringify(data), function( re, arg ){
        window.linuxChartData = re.result.list;
        var tpl = "linux_monitor_detail";
        var div = "right-content-div";
        if($("#linux-monitor-right-div-content").length){
            tpl = "linux_monitor_detail_content";
            div = "linux-monitor-right-div-content";
        }
        smarty.html( tpl, re, div,function () {
             $("select").selectpicker();
             $(".form_datetime").datetimepicker({format: 'yyyy-mm-dd hh:ii'});
             if( window.last_table_title ){
                $("th[data-field='" + window.last_table_title + "']").trigger("click");
             }else{
                makeLinuxCharts("light", "#FFFFFF", "load_average");
             }
         });
    });
}

smarty.register_function( 'machine_load_average_format', function(params){
    var result = params["result"];
    return get_machine_status_str(result);

});

smarty.register_function( 'linux_top_data_detail_format', function(params){
    var item = params["item"];
    var add_time = sparrow.date(item.add_time);
    var trstr1 = "";
    var tmp1 = item.top_1.split(" ");
    if( tmp1 ){
        trstr1 = "<tr class='linux-ps-detail pointer' data-pid='" + tmp1[0] + "'>";;
        $.each(tmp1, function(index){
            trstr1 += "<td>" +tmp1[index]+ "</td>"
        });
        trstr1 += "<td>" + add_time + "</td>"
        trstr1 += "</tr>";
    }

    var trstr2 = "";
    var tmp2 = item.top_2.split(" ");
    if( tmp2 ){
       trstr2 = "<tr class='linux-ps-detail pointer' data-pid='" + tmp2[0] + "'>";
       $.each(tmp2, function(index){
           trstr2 += "<td>" +tmp2[index]+ "</td>"
       });
       trstr2 += "<td>" + add_time + "</td>"
       trstr2 += "</tr>";
    }

    var trstr3 = "";
    var tmp3 = item.top_3.split(" ");
    if( tmp3 ){
        trstr3 = "<tr class='linux-ps-detail pointer' data-pid='" + tmp3[0] + "'>";
        $.each(tmp3, function(index){
            trstr3 += "<td>" +tmp3[index]+ "</td>"
        });
        trstr3 += "<td>" + add_time + "</td>"
        trstr3 += "</tr>";
    }
    return trstr1 + trstr2 + trstr3;

});

smarty.register_modifier( 'format_float', function( val ) {
    return val.toFixed(2);
} );

function get_machine_status_str(result){
    if( result ){
        var size = result.length;
        var cpu_core = 0;
        var sum_load = 0;
        var max_load = 0;
        var max_load_date = "";
        var total_memory = 0;
        for(var i = 0; i < size; i++ ){
            cpu_core = result[i].processor;
            total_memory = result[i].memory_total;
            sum_load += result[i].load_average;
            if( result[i].load_average > max_load ){
                max_load = result[i].load_average;
                max_load_date =  result[i].date;
            }
        }
        var avg_load = (sum_load/size).toFixed(2);
    }

    var healthy = "";
    if( max_load > cpu_core/2 ){
        healthy = "<span class='label label-danger'>Machine bad</span>"
    }else if( max_load > cpu_core/3 ){
        healthy = "<span class='label label-warn'>Machine warn</span>"
    }else{
        healthy = "<span class='label label-success'>Machine healthy</span>"
    }
    var str_avg_load = "";
    if( avg_load > cpu_core/2 ){
        str_avg_load = "avg-load-average: <span class='danger'>" + avg_load + "</span>"
    }else{
        str_avg_load = "avg-load-average: <span class='success'>" + avg_load + "</span>"
    }

    var str_max_load = "";
    if( max_load > cpu_core/2 ){
        str_max_load = "max-load-average: <span class='danger'>" + max_load.toFixed(2) + " (in " + max_load_date + ")" + "</span>"
    }else{
        str_max_load = "max-load-average: <span class='success'>" + max_load.toFixed(2) + " (in " + max_load_date + ")" + "</span>"
    }
    return healthy + " cpu-processor: " + cpu_core.toFixed(0) + " | total_memory: " + total_memory + " | " + str_avg_load + " | " + str_max_load;
}