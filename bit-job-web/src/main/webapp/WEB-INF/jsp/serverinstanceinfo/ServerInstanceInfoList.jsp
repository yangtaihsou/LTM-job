<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<form class="form-inline" role="form" id="queryServerInstanceInfoForm">
    <table class="table  table-hover" style="width: 40%">
        <tr>


            <td><input type="text" name="serverIp" placeholder="服务实例IP" class="form-control"
                       value="${parameterMap.serverIp[0]}"/></td>


            <%--
                    <td><input type="text" name="serverPath"   placeholder="服务实例路径"   class="form-control" value="${parameterMap.serverPath[0]}"/></td>



                    <td><input type="text" name="uuid"   placeholder="UUID"   class="form-control" value="${parameterMap.uuid[0]}"/></td>--%>


            <td><%--<input type="text" name="lastUpdate"   placeholder="最后修改时间"   class="form-control" value="${parameterMap.lastUpdate[0]}"/>--%>


                <input type="text" name="lastUpdate" class="form-control"


                        <c:if test="${lastUpdate!=null}">

                            value="<fmt:formatDate value="${lastUpdate}" pattern="yyyy-MM-dd HH:mm:ss" type="date"
                                                   dateStyle="long"/>"
                        </c:if>
                        <c:if test="${lastUpdate==null}">

                            value="${parameterMap.lastUpdate[0]}"
                        </c:if>

                       placeholder="最后修改时间" onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
            </td>


            <td>
                <input type="button" class="btn  btn-warning" value="查询" action='get'
                       requestUrl='/serverinstanceinfo/list' method="submit" handler="queryServerInstanceInfo"/>
            </td>
        </tr>
    </table>
</form>
共<c:out value="${count}"/>条数据。默认查询最近5分钟数据
<br>
<table class="table table-bordered table-hover">
    <thead>
    <tr>

        <th>服务实例IP</th>
        <th>服务实例路径</th>
        <th>UUID</th>
        <th>最后修改时间</th>
        <th>操作</th>
    </tr>
    </tr>
    </thead>
    <tbody>

    <c:forEach var="result" items="${list}" varStatus="status">
        <tr>

            <td class="col-lg-1">
                    ${result.serverIp}

            </td>
            <td class="col-lg-1">
                    ${result.serverPath}

            </td>
            <td class="col-lg-1">
                    ${result.uuid}

            </td>
            <td class="col-lg-1">
                    <%--   ${result.lastUpdate}--%>

                <fmt:formatDate value="${result.lastUpdate}" pattern="yyyy-MM-dd HH:mm:ss" type="date"
                                dateStyle="long"/>
            </td>


            <td class="col-lg-1">
                    <%-- <input type="button"   class="btn  btn-warning" value="编辑"   action='get'
                            requestUrl='/serverinstanceinfo/toEdit?id=${result.id}'  method="submit" handler="toEditServerInstanceInfo"/>--%>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<jsp:include page="../common/page.jsp"></jsp:include>
