<div class="panel-body" style="overflow: auto">
  <div class="row">{{machine_load_average_format result=$result.list}}</div><!-- .row -->
  <div class="row">
      <div id="chartdiv_table" class="col-md-12" style="height: 450px"></div>
  </div>
</div>
<div class="panel-body" style="overflow: auto;max-height:400px">
    <table class="table table-bordered" id="command-table">
        <thead>
        <tr id="linux-field-title">
            <th data-field="date">date</th>
            <th data-field="load_average" class="selected">load_average</th>
            <th data-field="memory_available">memory_available</th>
            <th data-field="memory_free">memory_free</th>
            <th data-field="memory_use">memory_use</th>
            <th data-field="netstat">connection</th>
            <th data-field="ps_num">ps_num</th>
            <th data-field="thread_num">thread_num</th>
        </tr>
        </thead>
        <tbody>
            {{foreach from=$result.list item=res}}
                <tr>
                    <td class="linux-date-detail" data-add-time="{{$res.add_time}}">{{$res.date}}</td>
                    <td>{{$res.load_average|format_float}}</td>
                    <td>{{$res.memory_available}}</td>
                    <td>{{$res.memory_free}}</td>
                    <td>{{$res.memory_use}}</td>
                    <td>{{$res.netstat}}</td>
                    <td>{{$res.ps_num}}</td>
                    <td>{{$res.thread_num}}</td>
                </tr>
            {{/foreach}}
        </tbody>
    </table>
</div>