<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<html>
<link rel="stylesheet" href="../css/bootstrap.min.css" />
<link rel="stylesheet" href="../css/bootstrap-theme.min.css">
<script type="text/javascript" src="../js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
<style>
    #circleContent{
        width:100%;
        height:800px;
    }
</style>
    <head>
        <title>用户登录</title>
    </head>
<body>
<div style="width: 100%;">
    <ul class="nav nav-tabs nav-justified">
        <li class="active"><a href="##">首页</a></li>
        <li><a href="##">用户登录</a></li>
        <li><a href="##">用户注册</a></li>
        <li><a href="##">消费录入</a></li>
        <li><a href="##">消费统计</a></li>
    </ul>
</div>

<div id="circleContent" class="carousel slide" data-ride="carousel" data-interval="2000">
    <ol class="carousel-indicators">
        <li data-target="#circleContent" data-slide-to="0" class="active"></li>
        <li data-target="#circleContent" data-slide-to="1"></li>
        <li data-target="#circleContent" data-slide-to="2"></li>
    </ol>
    <div class="carousel-inner">
        <div class="item active">
            <img src="../img/1.jpg">
            <div class="carousel-caption"></div>
        </div>
        <div class="item">
            <img src="../img/2.jpg">
            <div class="carousel-caption"></div>
        </div>
        <div class="item">
            <img src="../img/3.jpg">
            <div class="carousel-caption"></div>
        </div>
    </div>
    <a class="carousel-control left" href="#circleContent" data-slide="prevent">‹</a>
    <a class="carousel-control right" href="#circleContent" data-slide="next">›</a>
</div>






</body>
<script type="text/javascript">
//    $(document).ready(function(){
//        $('#circleContent').carousel({interval:5000});//每隔5秒自动轮播
//    });
</script>
</html>
