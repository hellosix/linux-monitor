<div class="container-fluid container-title">
    <div class="container-fluid container-title-content">
        <div class="pull-left"><img src="../image/monitor.png" class="img-circle div-circle content-logo" /></div>
        <div class="pull-left">
            <h1 id="cluster-name">Linux Monitor</h1>
        </div>
        <div id="redis-opt-button" class="pull-right">
            <div class="tab-top-div">
            <div id="node-monitor" data-step-width="100%" class="tab-top-button active pull-right" data-tag="node-monitor" data-group="manager-top" style="margin-left:10px"><span class="glyphicon glyphicon-eye-open"></span> Node Monitor</div>
            <div id="node-manager" data-step-width="256px" class="tab-top-button pull-right" data-tag="node-manager" data-group="manager-top" style="margin-left:10px"><span class="glyphicon glyphicon-cog"></span> Node Manager</div>
            </div>
        </div>
    </div>
</div>
<div class="container-fluid" id="center-content-div">
    {{include file="linux_monitor_content.tpl"}}
</div><!-- .container-fluid -->
