<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<form class="form-inline" role="form" id="taskServiceConfigForm">
    <table class="table   table-hover">


        <tr>
            <td class="col-lg-1">任务类型</td>
            <td>
                <input type="text" name="taskType" value="${taskServiceConfig.taskType}" class="form-control"/>
            </td>

        </tr>
        <tr>
            <td class="col-lg-1">任务类型key</td>
            <td>
                <input type="text" name="taskTypeKey" value="${taskServiceConfig.taskTypeKey}" class="form-control"/>
            </td>

        </tr>
        <tr>
            <td class="col-lg-1">任务类型描述</td>
            <td>
                <input type="text" name="taskTypeDesc" value="${taskServiceConfig.taskTypeDesc}" class="form-control"/>
            </td>

        </tr>
        <tr>
            <td class="col-lg-1">serviceId</td>
            <td>
                <select class="form-control" name="taskService">
                    <option value="">请选择service类型</option>
                    <c:forEach var="item1" items="${serviceMap}">
                        <option value="${item1.key}"
                                <c:if test="${taskServiceConfig.taskService==item1.key}">
                                    selected=""
                                </c:if>
                        >${item1.value}</option>
                    </c:forEach>
                </select>
            </td>

        </tr>
        <tr>
            <td class="col-lg-1">父任务类型</td>
            <td>
                <select class="form-control" name="taskParentType">
                    <option value="">请选择父任务</option>
                    <c:forEach var="item1" items="${taskServiceConfigMap}">
                        <option value="${item1.key}"
                                <c:if test="${taskServiceConfig.taskParentType!=null&&taskServiceConfig.taskParentType==item1.value.taskType}">
                                    selected=""
                                </c:if>
                        >${item1.value.taskTypeDesc}</option>
                    </c:forEach>
                </select>
            </td>

        </tr>

        <tr>
            <td class="col-lg-1">任务状态</td>
            <td>
                <input type="text" name="status" value="${taskServiceConfig.status}" class="form-control"/>
            </td>

        </tr>
        <tr>
            <td class="col-lg-1">一次查询任务数量</td>
            <td>
                <input type="text" name="taskTotalSize" value="${taskServiceConfig.taskTotalSize}"
                       class="form-control"/>默认100条
            </td>
        </tr>
        <tr>
            <td class="col-lg-1">单个线程处理量</td>
            <td>
                <input type="text" name="taskSizePerThread" value="${taskServiceConfig.taskSizePerThread}"
                       class="form-control"/>默认10条
            </td>
        </tr>
        <tr>
            <td class="col-lg-1">锁处理方式</td>
            <td>
                <select class="form-control" name="lockType">
                    <option value="">请选择锁方式</option>
                    <option value="0" <c:if test='${taskServiceConfig.lockType==0}'> selected="" </c:if>>单次处理</option>
                    <option value="1"  <c:if test='${taskServiceConfig.lockType==1}'> selected="" </c:if> >批量处理</option>
                </select>
            </td>
        </tr>
        <tr>
            <td class="col-lg-1">任务执行频率</td>
            <td>
                <input type="text" name="repeatInterval" value="${taskServiceConfig.repeatInterval}"
                       class="form-control"/>秒(默认10秒)
            </td>
        </tr>
        <tr>
            <td class="col-lg-1">消息应用名字</td>
            <td>
                <select name="jmqApp" class="form-control">
                    <option value="">请选择mq应用名字</option>
                    <c:forEach var="item" items="${jmqAppMap}">
                        <option value="${item.key}"
                                <c:if test="${parameterMap.jmqApp[0]==item.key}"> selected="" </c:if>
                        >${item.value.jmqApp}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td class="col-lg-1">消息主题</td>
            <td>
                <input type="text" name="jmqTopic" value="${taskServiceConfig.jmqTopic}" class="form-control"/>秒(默认10秒)
            </td>
        </tr>
        <tr>
            <td class="col-lg-1" colspan="2">如果不进行新加资源线程池，系统将默认分配</td>
        </tr>
        <tr>
            <td class="col-lg-1" colspan="2">
                <input type="button" class="btn  btn-warning" value="更新" form="taskServiceConfigForm" action='post'
                       requestUrl='/taskServiceConfig/edit' method="formRequest"/>
            </td>
            <input type="hidden" value="${taskServiceConfig.id}" name="id">
        </tr>
    </table>
</form>