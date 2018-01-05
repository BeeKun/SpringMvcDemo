<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<html>
<script type="text/javascript" src="../js/jquery.js"></script>
    <head>
        <title>消息提示</title>
    </head>
<body>
    <button id="but" onclick="test()">anniu</button>
</body>
<script type="text/javascript">
    function test() {
        $.ajax({
            url:"test",
            data:{"id":1},
            type:"post",
            dataType : "json",
            success:function (data) {
                alert(data.code);
            }
        });
    }
</script>
</html>
