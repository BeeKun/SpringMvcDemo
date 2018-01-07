<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<html>
<link rel="stylesheet" href="../css/bootstrap.min.css" />
<link rel="stylesheet" href="../css/bootstrap-theme.min.css">
<link rel="stylesheet" href="../css/bootstrap-datetimepicker.min.css">
<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/jquery.form.js"></script>
<script type="text/javascript" src="../js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="../js/bootstrap-datetimepicker.zh-CN.js"></script>
<body>
<form class="" action="/addCostInfo" enctype="multipart/form-data" method="post" id="commitForm">
    <div class="form-inline">
        消费金额:<input type="text" class="form-control" id="costMoney" name="costMoney">
        消费类型:<input type="text" class="form-control" id="costDescription" name="costDescription">
        消费地点:<input type="text" class="form-control" id="costAddress" name="costAddress">
        消费时间:<input type="text" class="form-control form_datetime" id="costTime" name="costTime" readonly>
    </div>
    <div class="form-group">
        爆照:<input type="file" id="costFile" name="costFile">
    </div>
</form>
<button class="btn btn-default" onclick="submit()">Submit</button>
</body>
<script type="text/javascript">
    $(function () {
        $(".form_datetime").datetimepicker({format: 'yyyy-mm-dd hh:ii'});

        //ajaxSubmit 实现Form提交后回调
        var options = {
            type: 'POST',
            url: 'addCostInfo',
            success:showResponse,
            dataType: 'json',
            error : function(xhr, status, err) {
                alert("操作失败");
            }
        };
        $("#commitForm").submit(function(){
            $(this).ajaxSubmit(options);
            return false;   //防止表单自动提交
        });
    });

    function submit() {
        $("#commitForm").submit();
    }

    /**
     * 保存后，执行回调
     * @param responseText
     * @param statusText
     * @param xhr
     * @param $form
     */
    function showResponse(responseText, statusText, xhr, $form){
        if(responseText.code == "00"){
            alert(responseText.msg);
        } else {
            alert(responseText.msg);
        }
    }

</script>
</html>
