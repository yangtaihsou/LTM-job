<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<form class="form-inline" role="form" id="jmqConfigForm">
    <table class="table   table-hover">


        <tr>
            <td class="col-lg-1">应用名字</td>
            <td>


                <input type="text" name="jmqApp" value="${jmqConfig.jmqApp}" class="form-control"/>
            </td>

        </tr>
        <tr>
            <td class="col-lg-1">jmq密码</td>
            <td>
                <input type="text" name="jmqPass" value="${jmqConfig.jmqPass}" class="form-control"/>
            </td>

        </tr>
        <tr>
            <td class="col-lg-1">jmq地址</td>
            <td>
                <input type="text" name="jmqAddress" value="${jmqConfig.jmqAddress}" class="form-control"/>
            </td>

        </tr>
        <tr>
            <td class="col-lg-1">状态</td>
            <td>
                <input type="text" name="status" value="${jmqConfig.status}" class="form-control"/>0无效1有效
            </td>

        </tr>


        <tr>
            <td class="col-lg-1" colspan="2">
                <input type="button" class="btn  btn-warning" value="更新" form="jmqConfigForm" action='post'
                       requestUrl='/jmqConfig/edit' method="formRequest"/>
            </td>
            <input type="hidden" value="${jmqConfig.id}" name="id">
        </tr>
    </table>
</form>