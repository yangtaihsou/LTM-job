<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<form class="form-inline" role="form" id="queryTimerTaskConfigForm">
    <table class="table  table-hover" style="width: 40%">
        <tr>
            <td><input type="text" name="taskTimerType" placeholder="执行任务的类型" class="form-control"
                       value="${parameterMap.taskTimerType[0]}"/></td>
            <td><input type="text" name="taskTimerKey" placeholder="定时任务标识" class="form-control"
                       value="${parameterMap.taskTimerKey[0]}"/></td>
            <td>
                <select class="form-control" name="dataType">
                    <option value="">请选择时间类型</option>
                    <c:forEach var="item1" items="${timerTypeConfig}">
                        <option value="${item1.key}"
                                <c:if test="${item1.key==parameterMap.dataType[0]}">
                                    selected=""
                                </c:if>
                        >${item1.value}</option>
                    </c:forEach>
                </select>
            </td>

            <td><input type="text" name="status" placeholder="任务状态" class="form-control"
                       value="${parameterMap.status[0]}"/></td>

            <td>
                <input type="button" class="btn  btn-warning" value="查询" action='get'
                       requestUrl='/timerTaskConfig/list' method="submit" handler="queryTimerTaskConfig"/>
            </td>
            <td>
                <input type="button" name="" class="btn btn-primary" value="启动全部"
                       method="submit"
                       action="post" requestUrl="/timerTaskConfig/updateAllStatus?status=1"
                       handler="resetAllTimerTaskConfigStatus"
                /></td>
            <td>
                <input type="button" name="" class="btn btn-warning" value="停止全部"
                       method="submit"
                       action="post" requestUrl="/timerTaskConfig/updateAllStatus?status=0"
                       handler="resetAllTimerTaskConfigStatus"
                />
            </td>
        </tr>
    </table>
</form>
共<c:out value="${count}"/>条数据
<br>
<table class="table table-bordered table-hover">
    <thead>
    <tr>

        <th>类型</th>
        <th>定时任务标识</th>
        <th>定时任务描述</th>
        <th>数据类型</th>
        <th>月</th>
        <th>日</th>
        <th>时</th>
        <th>分</th>
        <th>秒</th>
        <th>执行时间</th>
        <th colspan="3">状态</th>
        <th>操作</th>
    </tr>
    </tr>
    </thead>
    <tbody>

    <c:forEach var="result" items="${list}" varStatus="status">
        <tr>

            <td class="col-lg-1">${result.taskTimerType} </td>
            <td class="col-lg-1">${result.taskTimerKey} </td>
            <td class="col-lg-1">${result.taskTimerDesc} </td>
            <td class="col-lg-1">${timerTypeConfig[result.dataType]} </td>
            <td class="col-lg-1">${result.month} </td>
            <td class="col-lg-1">${result.day} </td>
            <td class="col-lg-1">${result.hour} </td>
            <td class="col-lg-1">${result.minute} </td>
            <td class="col-lg-1">${result.second} </td>
            <td class="col-lg-1">
                <fmt:formatDate value="${result.biztime}" pattern="yyyy-MM-dd HH:mm:ss" type="date" dateStyle="long"/>
            </td>
            <td class="col-lg-1" colspan="3">
                <c:if test="${result.status==0}">
                    <input type="button" name="resetTimerTaskConfigStatus" method="submit"
                           action="post" requestUrl="/timerTaskConfig/updateStatus" handler="resetTimerTaskConfigStatus"
                           dataid="${result.id}" class="btn btn-primary" value="启动">
                </c:if>
                <c:if test="${result.status==1}">
                    <input type="button" name="resetTimerTaskConfigStatus" method="submit"
                           action="post" requestUrl="/timerTaskConfig/updateStatus" handler="resetTimerTaskConfigStatus"
                           dataid="${result.id}" class="btn btn-warning" value="停止">
                </c:if>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </td>


            <td class="col-lg-1">
                <input type="button" class="btn  btn-warning" value="编辑" action='get'
                       requestUrl='/timerTaskConfig/toEdit?id=${result.id}' method="submit"
                       handler="toEditTimerTaskConfig"/>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<jsp:include page="../common/page.jsp"></jsp:include>