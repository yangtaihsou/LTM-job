<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<form class="form-inline" role="form" id="queryTaskServiceConfigForm">
    <table class="table  table-hover" style="width: 40%">
        <tr>
            <td><input type="text" name="taskType" placeholder="任务类型" class="form-control"
                       value="${parameterMap.taskType[0]}"/></td>
            <td><input type="text" name="taskTypeKey" placeholder="任务类型key" class="form-control"
                       value="${parameterMap.taskTypeKey[0]}"/></td>

            <td>
                <select class="form-control" name="taskService">
                    <option value="">请选择service类型</option>
                    <c:forEach var="item1" items="${serviceMap}">
                        <option value="${item1.key}"
                                <c:if test="${parameterMap.taskService[0]==item1.key}">
                                    selected=""
                                </c:if>
                        >${item1.value}</option>
                    </c:forEach>
                </select>
            </td>
            <td>
                <select class="form-control" name="taskParentType">
                    <option value="">请选择父任务</option>
                    <c:forEach var="item1" items="${taskServiceConfigMap}">
                        <option value="${item1.key}"
                                <c:if test="${parameterMap.taskParentType[0]==item1.value.taskParentType}">
                                    selected=""
                                </c:if>
                        >${item1.value.taskParentTypeDesc}</option>
                    </c:forEach>
                </select>
            </td>
            <%--
                            <td><input type="text" name="status"   placeholder=""   class="form-control" value="${parameterMap.status[0]}"/></td>--%>
            <td>
                <input type="button" class="btn  btn-warning" value="查询" action='get'
                       requestUrl='/taskServiceConfig/list' method="submit" handler="queryTaskServiceConfig"/>
            </td>

            <td>
                <input type="button" class="btn btn-primary" value="启动全部"
                       method="submit"
                       action="post" requestUrl="/taskServiceConfig/updateAllStatus?status=1"
                       handler="resetTaskServiceConfigStatus"
                /></td>
            <td>
                <input type="button" class="btn btn-warning" value="停止全部"
                       method="submit"
                       action="post" requestUrl="/taskServiceConfig/updateAllStatus?status=0"
                       handler="resetTaskServiceConfigStatus"
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

        <th>任务类型</th>
        <th>任务类型key</th>
        <th>任务类型描述</th>
        <th>serviceId</th>
        <th>父任务类型</th>
        <th>父任务类型描述</th>
        <th>任务状态</th>
        <th>一次查询任务数量</th>
        <th>单个线程处理量</th>
        <th>锁处理方式</th>
        <th>任务执行频率</th>

        <th>消息应用</th>
        <th>消息主题</th>
        <th>操作</th>
    </tr>
    </tr>
    </thead>
    <tbody>

    <c:forEach var="result" items="${list}" varStatus="status">
        <tr>

            <td class="col-lg-1">${result.taskType} </td>
            <td class="col-lg-1">${result.taskTypeKey} </td>
            <td class="col-lg-1">${result.taskTypeDesc} </td>
            <td class="col-lg-1">${serviceMap[result.taskService]} </td>
            <td class="col-lg-1">${result.taskParentType} </td>
            <td class="col-lg-1">${result.taskParentTypeDesc} </td>
            <td class="col-lg-1">

                <c:if test="${result.status==0}">
                    <input type="button" name="resetTaskServiceConfigStatus" method="submit"
                           action="post" requestUrl="/taskServiceConfig/updateStatus"
                           handler="resetTaskServiceConfigStatus" dataid="${result.id}" class="btn btn-primary"
                           value="启动">
                </c:if>
                <c:if test="${result.status==1}">
                    <input type="button" name="resetTaskServiceConfigStatus" method="submit"
                           action="post" requestUrl="/taskServiceConfig/updateStatus"
                           handler="resetTaskServiceConfigStatus" dataid="${result.id}" class="btn btn-warning"
                           value="停止">
                </c:if>
            </td>


            <td class="col-lg-1">${result.taskTotalSize} </td>
            <td class="col-lg-1">${result.taskSizePerThread} </td>
            <td class="col-lg-1">
                <c:if test='${result.lockType==0}'> 单次处理 </c:if>
                <c:if test='${result.lockType==1}'> 批量处理 </c:if>
            </td>
            <td class="col-lg-1">${result.repeatInterval}秒</td>
            <td class="col-lg-1">${result.jmqApp}</td>
            <td class="col-lg-1">${result.jmqTopic}</td>
            <td class="col-lg-1">
                <input type="button" class="btn  btn-warning" value="编辑" action='get'
                       requestUrl='/taskServiceConfig/toEdit?id=${result.id}' method="submit"
                       handler="toEditTaskServiceConfig"/>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>


<jsp:include page="../common/page.jsp"></jsp:include>
