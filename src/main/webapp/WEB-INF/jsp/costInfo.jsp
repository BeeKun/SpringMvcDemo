<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<link rel="stylesheet" href="../css/bootstrap.min.css" />
<link rel="stylesheet" href="../css/bootstrap-theme.min.css">
<link rel="stylesheet" href="../css/bootstrap-datetimepicker.min.css">
<script type="text/javascript" src="../js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="../js/jquery.form.js"></script>
<script type="text/javascript" src="../js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="../js/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
<script type="text/javascript" src="../js/echarts.js"></script>
<%
    pageContext.setAttribute("path", request.getContextPath());
%>
<body>
<div class="cntainer">
    <div class="row">
        <div class="col-md-12">
            <h1>消费列表</h1>
            <p>${costInfoDOList.get(0).account}</p>
        </div>
    </div>
    <div class="row">
        <div class="col-md-10">
            <table class="table table-bordered table-hover">
                <tr>
                    <th>消费金额</th>
                    <th>消费明细</th>
                    <th>消费地址</th>
                    <th>消费时间</th>
                    <th>明细照片</th>
                </tr>
                <c:forEach items="${costInfoDOList}" var="costInfo">
                    <tr>
                        <td>${costInfo.costMoney }元</td>
                        <td>${costInfo.costDescription }</td>
                        <td>${costInfo.costAddress }</td>
                        <td> ${costInfo.dateTemp}</td>
                        <td>
                            <c:if test="${costInfo.costPictureName} !=null">
                                <button type="button" class="btn btn-primary" onclick="downLoadPicture('${costInfo.id}')">下载</button>
                            </c:if>
                        </td>
                        <td style="display: none;">${costInfo.id }</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>

    <!-- 分页 -->
    <div class="row">
        <div class="col-md-6">
            当前${pageInfo.pageNum }页，总共${pageInfo.pages }页，总共${pageInfo.total }条记录
        </div>
        <div class="col-md-6">
            <nav aria-lable="Page navigation">
                <ul class="pagination">

                    <li><a href="${path }/cost/costInfoPage?pageNum=1">首页</a></li>

                    <c:if test="${pageInfo.hasPreviousPage  }">
                        <li>
                            <a href="${path }/cost/costInfoPage?pageNum=${pageInfo.pageNum-1}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                    </c:if>

                    <c:forEach items="${pageInfo.navigatepageNums  }" var="page">
                        <c:if test="${page==pageInfo.pageNum }">
                            <li class="active"><a href="${path }/user/costInfoPage?pageNum=${page}">${page}</a></li>
                        </c:if>
                        <c:if test="${page!=pageInfo.pageNum }">
                            <li><a href="${path }/cost/costInfoPage?pageNum=${page}">${page}</a></li>
                        </c:if>
                    </c:forEach>

                    <c:if test="${pageInfo.hasNextPage }">
                        <li>
                            <a href="${path }/cost/costInfoPage?pageNum=${pageInfo.pageNum+1 }" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </c:if>

                    <li><a href="${path }/cost/costInfoPage?pageNum=${pageInfo.pages}">末页</a></li>

                </ul>
            </nav>
        </div>
    </div>
    <div id="showAxis" style="width: 83.33%;height: 500px;"></div>

</div>
<script type="text/javascript">
    $(document).ready(function() {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('showAxis'));
        option = {
            title: {
                text: '消费折线图'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data:['消费明细']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data:${array[1]}
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name:'消费明细',
                    type:'line',
                    stack: '总量',
                    data:${array[0]}
                }
            ]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    });
    
    function getEchartsData() {
        //showAxis
    }

    //照片下载方法
    function downLoadPicture(id){
        $.post('checkPicture',{"id":id},function(data){
            if (data.code == "00"){
                window.location.href='downLoadPicture?id='+id;
            }else {
                alert(data.msg);
            }

        });


    }
</script>
</body>
</html>
