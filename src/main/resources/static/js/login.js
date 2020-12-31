$(document).ready(function () {
    $('#username').focus(function () {
        $('#username').val('');
    })
    $('#password').focus(function () {
        $('#password').val('');
    })
    $('#username').blur(function () {
        if ($('#username').val() =='') {
            $('#username').val('用户名不能为空');
            $('#username').css({'color':'red'});
        }
    })
    $('#username').focus(function () {
        $('#username').css({'color':'#ADADAD'});
    });

    $('#password').blur(function () {
        if ($('#password').val() == '') {
            $('#password').val('密码不能为空');
            $('#password').css({'color':'red'});
        }
    })
    $('#password').focus(function () {
        $('#password').css({'color':'#ADADAD'});
    });
})
