<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Node List</h3>
                <button class="pull-right btn btn-default btn-sm" id="add-machine" style="margin-top:-24px"><span class="glyphicon glyphicon-plus"></span> Add Machine</button>
            </div>
            <div class="panel-body" style="overflow: auto;max-height:700px">
                <table class="table table-bordered" id="command-table">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>ip</th>
                        <th>username</th>
                        <th>password</th>
                        <th>groups</th>
                        <th>add_time</th>
                        <th>operate</th>
                    </tr>
                    </thead>
                    <tbody>
                        {{foreach from=$result item=res}}
                            <tr>
                                <td>{{$res.id}}</td>
                                <td>{{$res.ip}}</td>
                                <td>{{$res.username}}</td>
                                <td>{{$res.password}}</td>
                                <td>{{$res.groups}}</td>
                                <td>{{$res.add_time|date}}</td>
                                <td>
                                    <a href="javascript:void(0)" data-ip="{{$res.ip}}" class="linux-remove-machine-button">delete</a> | <a href="javascript:void(0)" data-detail="{{$res|msgpack}}" class="linux-edit-machine-button">edit</a>
                                </td>
                            </tr>
                        {{/foreach}}
                    </tbody>
                </table>
            </div>
        </div><!-- .panel -->
    </div><!-- .col-md-12 -->
</div>