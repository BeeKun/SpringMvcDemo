<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<html>
<link rel="stylesheet" href="../css/bootstrap.min.css" />
<link rel="stylesheet" href="../css/bootstrap-theme.min.css">
<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
    <head>
        <title>用户登录</title>
    </head>
<body>
<div style="text-align: center; ">登录(没有账号?<a href="/user/signUp">注册</a>)</div>
<div class="form-horizontal" >
    <div class="form-group">
        <label class="col-sm-2 control-label">用户名:</label>
        <div class="col-sm-5">
            <input type="text" class="form-control" id="account" name="account">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">密码:</label>
        <div class="col-sm-5">
            <input type="text" class="form-control" id="password" name="password">
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-default" onclick="login();">登录</button>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    function login() {
        var account = $('#account').val();
        var password = $('#password').val();
        if (account == ""){
            alert("请填写正确的账号!");
            return;
        }
        if (password == ""){
            alert("请填写正确的密码!");
            return;
        }
        $.ajax({
            url:"/user/loginByUser",
            data:{"account":account,"password":password},
            type:"post",
            dataType : "json",
            success:function (data) {
                if (data.code == "00"){
                    window.location.href='';
                }
            }
        });
    }
</script>
</html>
