<form role="form" id="form" action="#" method="post" enctype="multipart/form-data" onsubmit="return false">
    <div>
        <h3 style="position: center">帐号登录</h3>
    </div>
    <div class="form-group has-feedback">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
        <input type="text" class="form-control" name="userId" id="phoneForL" placeholder="请输入手机号码" value="${userId1}" required/>
    </div>
    <div class="form-group has-feedback">
        <span class=" glyphicon glyphicon-lock form-control-feedback"></span>
        <input type="password" class="form-control" name="password" id="pswForL" placeholder="请输入密码" value="${password}" required/>
    </div>
    <div class="form-group has-feedback">
        <input type="text" class="form-control" id="captchaForL1" placeholder="请输入验证码" style="width: 70%;display: inline;">
        <img id="loginform:vCode1" src="/getCode" style="display:inline;float: right;" title="看不清？点击换一张试试" onclick="javascript:document.getElementById('loginform:vCode1').src='/getCode?'+Math.random();"/>
    </div>
    <div class="checkbox">
        <label><input type="checkbox" name="check" checked title="选中后可保存密码，七天内直接登录"/>记住密码</label>
        <a data-toggle="modal" data-target="#register" href="" style="margin-left:145px">立即注册</a>
    </div>
    <input type="hidden" name="type" value="1">
    <button type="button" class="btn btn-default btn-success btn-block" onclick="login('phoneForL','pswForL','captchaForL1',1)">登录</button>
</form>