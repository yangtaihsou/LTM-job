<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
共<c:out value="${taskCount}"/>条数据<c:out value="${resetCount}"/>
<br>
<table class="table table-bordered table-hover">
    <thead>
    <tr>
        <th> 类型</th>
        <th> 任务状态</th>
        <th> 任务数据</th>
        <th> 失败原因</th>
        <th> 重试</th>
        <th> 业务号</th>
        <th> 订单号</th>
        <th> 更新时间</th>
        <th style="display: none"> uuid</th>
        <th> 开始时间</th>
        <th> 执行</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="result" items="${taskList}" varStatus="status">
        <td class="col-lg-1"> ${taskTypeConfig[result.taskType].taskTypeDesc}<c:out value="${result.taskType}"/></td>
        <td class="col-lg-1">
            <c:if test="${result.taskStatus=='0'}">
                <button type="button" class="btn btn-info">未执行</button>
            </c:if>
            <c:if test="${result.taskStatus=='1'}">
                <button type="button" class="btn  btn-warning">锁定</button>
            </c:if>
            <c:if test="${result.taskStatus=='2'}">
                <button type="button" class="btn btn-success">完成</button>
            </c:if>
            <c:if test="${result.taskStatus=='3'}">
                <button type="button" class="btn btn-danger">暂停（需要重发）</button>
            </c:if>
        </td>
        <td class="col-lg-4" style="word-wrap:break-word;word-break:break-all"><c:out value="${result.taskData}"/></td>
        <td class="col-lg-1" style="word-wrap:break-word;word-break:break-all"><c:out value="${result.errorMsg}"/></td>
        <td class="col-lg-1">
            <c:if test="${result.retryCount>=5}">
                <button type="button" class="btn btn-danger"><c:out value="${result.retryCount}"/></button>
            </c:if>
            <c:if test="${result.retryCount<5}"><c:out value="${result.retryCount}"/></c:if>
        </td>
        <td class="col-lg-1"> <%--<c:out value="${result.businessId}" />--%></td>
        <td class="col-lg-1"><%-- ${result.orderId}--%> </td>

        <td class="col-lg-1"><fmt:formatDate value="${result.lastUpdate}" pattern="yyyy-MM-dd HH:mm:ss" type="date"
                                             dateStyle="long"/></td>
        <td style="display: none"><c:out value="${result.uuid}"/></td>
        <td class="col-lg-1"><fmt:formatDate value="${result.bizTime}" pattern="yyyy-MM-dd HH:mm:ss" type="date"
                                             dateStyle="long"/></td>
        <td class="col-lg-1">
            <input type="button" class="btn btn-info" value="重发" name="resetTask"
                   method="submit"
                   action="post" requestUrl="/task/resetTask" handler="resetSubTask"
                   taskId='<c:out value="${result.id}" />'/>
        </td>
        </tr> </c:forEach>
    </tbody>
</table>
<div id="pageStr">
    <c:out value="${pagestr}"/>
</div>


<script>
    $(document).ready(function () {
        $('#pageStr').html($('#pageStr').text());
    });


</script>