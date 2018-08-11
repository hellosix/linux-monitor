<div class="panel-body" style="overflow: auto;margin-bottom:0px;padding-bottom:0px;max-height:450px;background-color:#4c4b4b;color:#fff;">
    <table class="table table-hover"  border="0">
        <thead>
        <tr>
            <th>PID</th>
            <th>USER</th>
            <th>PR</th>
            <th>NI</th>
            <th>VIRT</th>
            <th>RES</th>
            <th>SHR</th>
            <th>S</th>
            <th>%CPU</th>
            <th>%MEM</th>
            <th>TIME+</th>
            <th>COMMAND</th>
            <th>DATE</th>
        </tr>
        </thead>
        <tbody id="linux-monitor-detail-tbody">
            {{foreach from=$result item=res}}
                {{linux_top_data_detail_format item=$res}}
            {{/foreach}}
        </tbody>
    </table>
</div>