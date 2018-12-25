<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<body>
<table class="table table-bordered table-hover">
    <thead>
    <tr>
        <th>任务名字</th>
        <th>配置文件的名字</th>
        <th>具体内容</th>
        <th>操作

            <input type="button" name="resetAllScheduler" class="btn btn-primary" value="启动">启动全部</input> &nbsp;
            <input type="button" name="resetAllScheduler" class="btn btn-warning" value="停止">停止全部</input>

        </th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="result" items="${schedulerResultList}" varStatus="status">
        <tr>
            <td><c:out value="${result.name}"/></td>
            <td><c:out value="${result.key}"/></td>
            <td>
                <table>
                    <c:forEach var="triggerResult" items="${result.triggerResultList}" varStatus="triggerResultStatus">
                        <tr><%--<td> trigger名字：<c:out value="${triggerResult.triggerName}" />&nbsp;&nbsp;&nbsp;</td>--%>
                            <td> 执行频率：<input type="text" value="${triggerResult.repeatInterval}"/>&nbsp;&nbsp;&nbsp;
                            </td>
                            <td>

                                <input type="button" class="btn  btn-warning" value="修改"
                                       triggerName='<c:out value="${triggerResult.triggerName}" />'
                                       schedulerName='<c:out value="${result.key}" />'
                                       method="submit"
                                       action="post" requestUrl="/scheduler/updateSchedulerRepeatInterval"
                                       handler="updateTaskInterval">
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
            <td>
                <c:if test="${result.status==1}"> <input type="button" name="resetAScheduler"
                                                         schedulerName="${result.key}" class="btn btn-warning"
                                                         value="停止"></c:if>
                <c:if test="${result.status!=1}"> <input type="button" name="resetAScheduler"
                                                         schedulerName="${result.key}" class="btn btn-primary"
                                                         value="启动"></c:if></td>
        </tr>
    </c:forEach>


    </tbody>
</table>


