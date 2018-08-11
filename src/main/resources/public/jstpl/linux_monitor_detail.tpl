<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">Detail</h3>
    <div class="pull-right" style="margin-top:-26px">
        <form class="form-inline">
            <div class="form-group">
                <label class="radio-inline">
                    <input type="radio" class="linux-monitor-request" name="date" value="day" /> 7 day
                </label>
                <label class="radio-inline">
                    <input type="radio" class="linux-monitor-request" name="date" value="hour" /> 24 hour
                </label>
                <label class="radio-inline">
                    <input type="radio" class="linux-monitor-request" name="date" checked="true" value="minute" /> 60 minute
                </label>
            </div>
            <div class="form-group" style="margin-left:10px">
                <input size="16" type="text"  name="start_time" placeholder="start_time" class="form-control form_datetime linux-monitor-date-request" />
            </div><!-- .form-group -->
            <div class="form-group" style="margin-left:10px">
                <input size="16" type="text"  name="end_time" placeholder="end_time" class="form-control form_datetime linux-monitor-date-request" />
            </div><!-- .form-group -->
            <div class="form-group" style="margin-left:10px">
                <select name="type" class="linux-monitor-request monitor-type form-control">
                    <option selected="selected" value="avg">avg</option>
                    <option value="min">min</option>
                    <option value="max">max</option>
                    <option value="sum">sum</option>
                </select>
            </div>
        </form>
    </div>
  </div>
  <div id="linux-monitor-right-div-content">
    {{include file="linux_monitor_detail_content.tpl"}}
  </div>
</div>
