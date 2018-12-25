<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<form class="form-inline" role="form" id="timerTaskConfigForm">
    <table class="table   table-hover">


        <tr>
            <td class="col-lg-1"><font color="#FF0000">*&nbsp;</font>任务类型</td>
            <td>
                <input type="text" name="taskTimerType" class="form-control"/>
            </td>

        </tr>
        <tr>
            <td class="col-lg-1"><font color="#FF0000">*&nbsp;</font>任务类型标识</td>
            <td>
                <input type="text" name="taskTimerKey" class="form-control"/>
            </td>

        </tr>
        <tr>
            <td class="col-lg-1"><font color="#FF0000">*&nbsp;</font>任务类型描述</td>
            <td>
                <input type="text" name="taskTimerDesc" class="form-control"/>
            </td>

        </tr>
        <tr>
            <td class="col-lg-1"><font color="#FF0000">*&nbsp;</font>数据类型</td>
            <td>
                <select class="form-control" name="dataType">
                    <option value="">请选择时间类型</option>
                    <c:forEach var="item1" items="${timerTypeConfig}">
                        <option value="${item1.key}">${item1.value}</option>
                    </c:forEach>
                </select>

            </td>

        </tr>
        <tr>
            <td class="col-lg-1">执行频率</td>
            <td>

                <input type="text" name="month" class="form-control"/>月

                <input type="text" name="day" class="form-control"/>日

                <input type="text" name="hour" class="form-control"/>时

                <input type="text" name="minute" class="form-control"/>分

                <input type="text" name="second" class="form-control"/>秒
            </td>

        </tr>
        <tr>
            <td class="col-lg-1"><font color="#FF0000">*&nbsp;</font>执行时间</td>
            <td>
                <input type="text" name="biztime" class="form-control"
                       onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
            </td>

        </tr>


        <tr>
            <td class="col-lg-1" colspan="2">
                <input type="button" class="btn  btn-warning" value="增加" form="timerTaskConfigForm" action='post'
                       requestUrl='/timerTaskConfig/save' method="formRequest"/>
            </td>
        </tr>
    </table>
</form>