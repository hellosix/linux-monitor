<div class="row">
    <div class="col-md-12">
    <div style="width:250px" class="pull-left">
        <div class="panel panel-default" style="padding-top:10px">
          <div class="panel-body" style="padding:0px">
            <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
              <div class="panel-heading left-menu-head">Host List</div>
              {{assign name="i" value=0}}
              {{foreach from=$hostGroups key=team item=res}}
                    {{$i++}}
                   <div class="no-border">
                    <div class="panel-heading" role="tab" id="headingOne{{$i}}" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne{{$i}}" aria-expanded="true" aria-controls="collapseOne{{$i}}">
                      <h4 class="panel-title" style="color:#686d73">
                          <span class="glyphicon glyphicon-chevron-right"></span> {{$team}}
                      </h4>
                    </div>
                    <div id="collapseOne{{$i}}" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne{{$i}}">
                      <div class="panel-body">
                        <ul class="list-group">
                            {{foreach from=$res item=ip}}
                                <li class="list-group-item last-click-button linux-monitor-request" data-ip="{{$ip}}" >{{$ip}}</li>
                            {{/foreach}}
                        </ul>
                      </div>
                    </div>
                  </div>
              {{/foreach}}
            </div>
          </div>
        </div>
      </div>

      <div style="padding-left:265px;margin 0 auto">
        <div id="right-content-div">
            <div class="panel panel-default">
              <div class="panel-heading">
                <h3 class="panel-title">Welcome</h3>
              </div>
              <div class="panel-body">
                <ul class="timeline">
                    <li class="timeline-inverted" style="height:112px">
                      <div class="timeline-badge"><i>what</i></div>
                      <div class="timeline-panel">
                        <div class="timeline-heading">
                          <h4 class="timeline-title">what linux monitor ?</h4>
                        </div>
                        <div class="timeline-body">
                          <p>linux monitor is monitor linux machine</p>
                        </div>
                      </div>
                    </li>
                    <li class="timeline-inverted">
                      <div class="timeline-badge info"><i>how</i></div>
                      <div class="timeline-panel">
                        <div class="timeline-heading">
                          <h4 class="timeline-title">how to use ?</h4>
                        </div>
                        <div class="timeline-body">
                            <p>if you without machine you can add machine node</p>
                            <p><a href="javascript:void(0)" id="skip-node-manager">node-manager</a></p>
                        </div>
                      </div>
                    </li>
                    <li class="timeline-inverted">
                      <div class="timeline-badge success"><i>why</i></div>
                      <div class="timeline-panel">
                        <div class="timeline-heading">
                          <h4 class="timeline-title">why use linux monitor ?</h4>
                        </div>
                        <div class="timeline-body">
                          linux monitor is easy to monitor linux machine
                      </div>
                    </li>
                </ul>
              </div>
            </div>
        </div>
      </div><!-- col-md-9 -->
    </div>
</div><!-- .row -->