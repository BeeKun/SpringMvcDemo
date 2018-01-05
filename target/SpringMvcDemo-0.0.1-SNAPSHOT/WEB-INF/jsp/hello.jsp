<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<html>
<link rel="stylesheet" href="../css/bootstrap.min.css" />
<link rel="stylesheet" href="../css/bootstrap-theme.min.css">
<link rel="stylesheet" href="../css/bootstrap-datetimepicker.min.css">
<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="../js/bootstrap-datetimepicker.zh-CN.js"></script>
<body>
<form class="" action="/addCostInfo" enctype="multipart/form-data" method="post">
    <div class="form-inline">
        消费类型:<input type="text" class="form-control" id="costType" name="costType">
        消费金额:
        <input type="text" class="form-control" id="costMoney" name="costMoney">
        消费时间:
        <input type="text" class="form-control form_datetime" id="costTime" name="costTime" readonly>
    </div>
    <div class="form-group">
        爆照:<input type="file" id="costFile" name="costFile">
        <button type="submit" class="btn btn-default">Submit</button>
    </div>

</form>
</body>
<script type="text/javascript">
    $(function () {
        $(".form_datetime").datetimepicker({format: 'yyyy-mm-dd hh:ii'});
    });
</script>
</html>
