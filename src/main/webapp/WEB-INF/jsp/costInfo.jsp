<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<html>
<link rel="stylesheet" href="../css/bootstrap.min.css" />
<link rel="stylesheet" href="../css/bootstrap-theme.min.css">
<link rel="stylesheet" href="../css/bootstrap-datetimepicker.min.css">
<script type="text/javascript" src="../js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="../js/jquery.form.js"></script>
<script type="text/javascript" src="../js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="../js/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
<%
    pageContext.setAttribute("path", request.getContextPath());
%>
<body>
<div class="cntainer">
    <div class="row">
        <div class="col-md-12">
            <h1>人员列表</h1>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <table class="table table-honver">
                <tr>
                    <th>id</th>
                    <th>名字</th>
                    <th>年龄</th>
                    <th>级别</th>
                </tr>
                <c:forEach items="${listUser}" var="emp">
                    <tr>
                        <td>${emp.id }</td>
                        <td>${emp.name }</td>
                        <td>${emp.age }</td>
                        <td>${emp.grade }</td>
                        <td><button class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-pencil"></span>增加</button></td>
                        <td><button class="btn btn-warning btn-sm"><span class="glyphicon glyphicon-trash"></span>删除</button></td>
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

                    <li><a href="${path }/user/getUserList.do?pageNum=1">首页</a></li>

                    <c:if test="${pageInfo.hasPreviousPage  }">
                        <li>
                            <a href="${path }/user/getUserList.do?pageNum=${pageInfo.pageNum-1}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                    </c:if>

                    <c:forEach items="${pageInfo.navigatepageNums  }" var="page">
                        <c:if test="${page==pageInfo.pageNum }">
                            <li class="active"><a href="${path }/user/getUserList.do?pageNum=${page}">${page}</a></li>
                        </c:if>
                        <c:if test="${page!=pageInfo.pageNum }">
                            <li><a href="${path }/user/getUserList.do?pageNum=${page}">${page}</a></li>
                        </c:if>
                    </c:forEach>

                    <c:if test="${pageInfo.hasNextPage }">
                        <li>
                            <a href="${path }/user/getUserList.do?pageNum=${pageInfo.pageNum+1 }" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </c:if>

                    <li><a href="${path }/user/getUserList.do?pageNum=${pageInfo.pages}">末页</a></li>

                </ul>
            </nav>
        </div>
    </div>
</div>
</body>
</html>
