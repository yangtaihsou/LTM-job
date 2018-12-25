<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<form class="form-inline" role="form" id="queryThreadPoolPropertiesForm">
    <table class="table  table-hover" style="width: 40%">
        <tr>


            <td>
                <select name="taskType" class="form-control">
                    <option value="">请选择任务类型</option>
                    <c:forEach var="item" items="${taskTypeMap}">
                        <option value="${item.key}"
                                <c:if test="${parameterMap.taskType[0]==item.key}"> selected="" </c:if>
                        >${item.value}</option>
                    </c:forEach>
                </select>

            </td>


            <%--
                       <td><input type="text" name="corePoolSize"   placeholder="核心线程数量"   class="form-control" value="${parameterMap.corePoolSize[0]}"/></td>



                       <td><input type="text" name="maxPoolSize"   placeholder="最大线程数量"   class="form-control" value="${parameterMap.maxPoolSize[0]}"/></td>



                       <td><input type="text" name="keepAliveSeconds"   placeholder="线程空闲时间"   class="form-control" value="${parameterMap.keepAliveSeconds[0]}"/></td>



                       <td><input type="text" name="allowCoreThreadTimeOut"   placeholder="核心线程空闲退出"   class="form-control" value="${parameterMap.allowCoreThreadTimeOut[0]}"/></td>



                       <td><input type="text" name="queueCapacity"   placeholder="任务队列容量"   class="form-control" value="${parameterMap.queueCapacity[0]}"/></td>



                       <td><input type="text" name="lastUpdate"   placeholder="最后修改时间"   class="form-control" value="${parameterMap.lastUpdate[0]}"/></td>


                              --%>
            <td>
                <div class="checkbox">
                    <label>
                        <input type="checkbox" name="threadPoolRefreshTimer" id="queryThreadPoolRefreshCheck"
                               method="threadPoolRefreshTimer" value="1000"> 刷新
                    </label>
                </div>
            </td>
            <td><input type="button" class="btn  btn-warning" value="查询" action='get' id="queryThreadPoolRefresh"
                       requestUrl='/threadPoolProperties/list' method="submit" handler="queryThreadPoolProperties"/>
            </td>
        </tr>
    </table>
    <table></table>
</form>
共<c:out value="${count}"/>条数据
<br>
<table class="table table-bordered table-hover">
    <thead>
    <tr>

        <th>任务类型</th>
        <th>核心线程数量</th>
        <th>最大线程数量</th>
        <th>线程空闲时间</th>
        <th>核心线程空闲退出</th>
        <th>任务队列容量</th>
        <th>实时参数</th>
        <th>最后修改时间</th>
        <th>操作</th>
    </tr>
    </tr>
    </thead>
    <tbody>

    <c:forEach var="result" items="${list}" varStatus="status">
        <tr>

            <td class="col-lg-1">
                    ${result.taskType}-${taskTypeMap[result.taskType]}

            </td>
            <td class="col-lg-1">
                设置数量 ${result.corePoolSize} <font color="#FF0000"> 建议数量:${result.warnningCorePoolSize}</font>

            </td>
            <td class="col-lg-1">
                    ${result.maxPoolSize}

            </td>
            <td class="col-lg-1">
                    ${result.keepAliveSeconds}秒

            </td>
            <td class="col-lg-1">
                    ${result.allowCoreThreadTimeOut}

            </td>
            <td class="col-lg-1">
                    ${result.queueCapacity}

            </td>
            <td class="col-lg-1 ">
                调度的任务数=${result.taskCount}</br>
                已完成的任务数=${result.completedTaskCount}</br>
                队列里待处理任务=${result.queueSize}</br>
                建过的最大线程数=${result.largestPoolSize}</br>
                线程池的线程数=${result.getPoolSize}</br>
                活动线程数=${result.activeCount}
            </td>
            <td class="col-lg-1">

                <fmt:formatDate value="${result.lastUpdate}" pattern="yyyy-MM-dd HH:mm:ss" type="date"
                                dateStyle="long"/>
            </td>


            <td class="col-lg-1">
                <input type="button" class="btn  btn-warning" value="编辑" action='get'
                       requestUrl='/threadPoolProperties/toEdit?id=${result.id}' method="submit"
                       handler="toEditThreadPoolProperties"/>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>


<jsp:include page="../common/page.jsp"></jsp:include>