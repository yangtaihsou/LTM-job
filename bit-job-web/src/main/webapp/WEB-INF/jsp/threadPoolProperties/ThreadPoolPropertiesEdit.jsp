<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<form class="form-inline" role="form" id="threadPoolPropertiesForm">
    <table class="table   table-hover">


        <tr>
            <td class="col-lg-1">任务类型</td>
            <td>


                <select name="taskType" class="form-control">
                    <option value="">请选择任务类型</option>
                    <c:forEach var="item" items="${taskTypeMap}">
                        <option value="${item.key}"
                                <c:if test="${threadPoolProperties.taskType==item.key}"> selected="" </c:if>
                        >${item.value}</option>
                    </c:forEach>
                </select>

            </td>

        </tr>
        <tr>
            <td class="col-lg-1">核心线程数量</td>
            <td>
                <input type="text" name="corePoolSize" value="${threadPoolProperties.corePoolSize}"
                       class="form-control"/>
                建议数量:${warnningCorePoolSize}
            </td>

        </tr>
        <tr>
            <td class="col-lg-1">最大线程数量</td>
            <td>
                <input type="text" name="maxPoolSize" value="${threadPoolProperties.maxPoolSize}" class="form-control"/>
            </td>

        </tr>
        <tr>
            <td class="col-lg-1">线程空闲时间</td>
            <td>
                <input type="text" name="keepAliveSeconds" value="${threadPoolProperties.keepAliveSeconds}"
                       class="form-control"/>秒
            </td>

        </tr>
        <tr>
            <td class="col-lg-1">核心线程空闲退出</td>
            <td><%--
                             <input type="text" name="allowCoreThreadTimeOut"  value="${threadPoolProperties.allowCoreThreadTimeOut}" class="form-control"/>--%>

                <input type="radio" name="allowCoreThreadTimeOut" value="0" class="form-control"
                <c:if test="${threadPoolProperties.allowCoreThreadTimeOut==false}"> checked="" </c:if>
                >否
                <input type="radio" name="allowCoreThreadTimeOut" value="1" class="form-control"
                <c:if test="${threadPoolProperties.allowCoreThreadTimeOut==true}"> checked="" </c:if>
                >是
            </td>

        </tr>
        <tr>
            <td class="col-lg-1">任务队列容量</td>
            <td>
                <input type="text" name="queueCapacity" value="${threadPoolProperties.queueCapacity}"
                       class="form-control"/>
            </td>

        </tr>
        <%--                   <tr>
             <td class="col-lg-1">最后修改时间</td>
             <td>
                                     <input type="text" name="lastUpdate"  value="${threadPoolProperties.lastUpdate}" class="form-control"/>
                         </td>

          </tr>	    --%>


        <tr>
            <td class="col-lg-1" colspan="2">
                <input type="button" class="btn  btn-warning" value="更新" form="threadPoolPropertiesForm" action='post'
                       requestUrl='/threadPoolProperties/edit' method="formRequest"/>
            </td>
            <input type="hidden" value="${threadPoolProperties.id}" name="id">
        </tr>
    </table>
</form>