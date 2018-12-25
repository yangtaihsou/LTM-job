<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<form class="form-inline" role="form" id="queryJmqConfigForm">
    <table class="table  table-hover" style="width: 40%">
        <tr>


            <td>

            <td><input type="text" name="jmqApp" placeholder="应用名字" class="form-control"
                       value="${parameterMap.status[0]}"/></td>


            </td>


            <td><input type="text" name="status" placeholder="状态" class="form-control"
                       value="${parameterMap.status[0]}"/></td>


            <td>
                <input type="button" class="btn  btn-warning" value="查询" action='get'
                       requestUrl='/jmqConfig/list' method="submit" handler="queryJmqConfig"/>
            </td>
        </tr>
    </table>
</form>
共<c:out value="${count}"/>条数据
<br>
<table class="table table-bordered table-hover">
    <thead>
    <tr>

        <th>应用名字</th>
        <th>jmq密码</th>
        <th>jmq地址</th>

        <th>状态</th>
        <th>操作</th>
    </tr>
    </tr>
    </thead>
    <tbody>

    <c:forEach var="result" items="${jmqConfigList}" varStatus="status">
        <tr>

            <td class="col-lg-1">
                    ${result.jmqApp}

            </td>
            <td class="col-lg-1">
                    ${result.jmqPass}

            </td>
            <td class="col-lg-1">
                    ${result.jmqAddress}

            </td>

            <td class="col-lg-1">
                    ${result.status}

            </td>


            <td class="col-lg-1">
                <input type="button" class="btn  btn-warning" value="编辑" action='get'
                       requestUrl='/jmqConfig/toEdit?id=${result.id}' method="submit" handler="toEditJmqConfig"/>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>


<jsp:include page="../common/page.jsp"></jsp:include>