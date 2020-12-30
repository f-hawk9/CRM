// 表单提交验证

function loginUser() {
    var userName = $('#userName').val();
    var password = $('#password').val();
    var code=$("#code").val();
    if (userName == '') {
        alert('请输入用户名！');
        return false;
    }
    if (password == '') {
        alert('请输入密码！');
        return false;
    }
    if (code == '') {
        alert('请输入验证码！');
        return false;
    }
    $.ajax({
        type: "post",
        url: "user/login",
        data: {
            userName: userName,
            password: password,
            code:code
        },
        dataType: "json",
        success : function(data) {
            console.log(data.result);
            if(data.result){
                alert("登录成功");
                window.location.href="user/getAllUser";
            } else if(data.result.false=0){
                alert("请输入正确的账号和密码");
                window.location.reload();
            }else{
                alert("验证码不正确");
                window.location.reload();
            }
        },
        error : function() {
            alert("出错啦！！！")
        }
    });
}