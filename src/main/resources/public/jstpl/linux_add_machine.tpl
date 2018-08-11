<div class="row" style="margin: 10px">
  <div class="col-sm-12" style="padding-left:40px;padding-right:40px" id="linux-add-machine-form">
      <div class="form-group">
          <label>IP</label>
          <input name="ip" data-relation="ip" value="{{$ip}}"  data-check-fields="password" data-server-check="/check/check_ip" type="text" class="form-control" placeholder="127.0.0.1" data-require="1" data-len="5-20" data-len-msg="length range 5-20" data-format="[0-9]+[\.][0-9]+[\.][0-9]+[\.][0-9]+" data-format-msg="error format(127.0.0.1)" >
          <div id="ip_tip"></div>
      </div>
      <div class="form-group">
          <label>Username</label>
          <input type="text" name="username"  value="{{$username}}" data-check-fields="password" class="form-control" data-require="1" data-len="3-20" data-len-msg="length range 3-20" data-format="[a-zA-Z0-9_]+" data-format-msg="only support letter and number or _" placeholder="username" />
          <div id="username_tip"></div>
      </div>
      <div class="form-group">
          <label>Password</label>
          <input type="text" name="password" value="{{$password}}" class="form-control" data-relation="ip,username" data-server-check="/check/check_ip_password" data-require="1" data-len="3-20" data-len-msg="length range 5-20" placeholder="password"  />
          <div id="password_tip"></div>
      </div>
      <div class="form-group">
          <label>Groups</label>
          <input type="text" name="groups" value="{{$groups}}" class="form-control" data-require="0" data-len="3-20" data-len-msg="length range 3-20" data-format="[a-zA-Z0-9_,]+" data-format-msg="only support letter and number or _ ,"  placeholder="group1,group2" />
          <div id="groups_tip"></div>
      </div>
      <div class="form-group">
          <button type="button" class="btn form-control btn-default second-selected btn-sm" id="linux-add-machine-form-button" data-isedit="{{$isedit}}"> Submit </button>
      </div>
  </div>
</div>