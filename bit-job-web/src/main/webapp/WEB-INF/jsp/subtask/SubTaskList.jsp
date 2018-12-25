<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="form-inline" role="form" id="subTaskQueryForm">
    <div class="form-group">
        <select class="form-control" name="taskType">
            <option value="">请选择任务类型</option>
            <c:forEach var="item1" items="${taskTypeConfig}">
                <option value="${item1.key}">${item1.value.taskTypeDesc}${item1.value.taskType}</option>
            </c:forEach>
        </select>
    </div>
    <div class="form-group">
        <%--<label class="sr-only" for="exampleInputEmail2">Email address</label>--%>
        <select class="form-control" name="taskStatus">
            <option value="">请选择任务状态</option>
            <option value="0">任务未执行</option>
            <option value="1">任务锁定</option>
            <option value="2">任务完成</option>
            <option value="3">任务暂停</option>
        </select>
    </div>
    <div class="form-group">
        <div class="input-group">
            <div class="input-group-addon">uuid</div>
            <input type="text" name="uuid" class="form-control" placeholder="uuid"/>
        </div>
    </div>
    -

    <div class="input-group">
        <div class="input-group-addon">业务单号</div>
        <input type="text" name="bizOrderId" class="form-control" placeholder="业务单号"/>
    </div>
    </div>-
    <%--       <div class="input-group">
               <div class="input-group-addon">订单id</div>
               <input type="text" name="venderId" class="form-control" placeholder="商家id"  />
           </div>
       </div>--%>

    <%--<div class="form-group">
        <div class="input-group">
            <div class="input-group-addon">业务id</div>
            <input type="text" name="businessId" class="form-control" placeholder="业务id"  />
        </div>
    </div>
  &lt;%&ndash;  <div class="form-group">
        <div class="input-group">
            <div class="input-group-addon">业务id</div>
            <input type="text" name="businessId" class="form-control" placeholder="业务id"  />
        </div>
    </div>&ndash;%&gt;
    <div class="form-group">
        <div class="input-group">
            <div class="input-group-addon">订单号</div>
            <input type="text" name="orderId" class="form-control" placeholder="订单号"  />
        </div>
    </div>--%>
    <div class="form-group">
        <div class="input-group">
            <div class="input-group-addon">时间</div>
            <input type="text" name="createTime" class="form-control" placeholder="开始时间"
                   onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>

        </div>
    </div>
    <div class="form-group">
        <input type="text" name="lastUpdate" class="form-control" placeholder="结束时间"
               onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
    </div>

    <div class="form-group">
        <select class="form-control" name="resultStatus">
            <option value="" selected="">请选择执行结果</option>
            <option value="0">初始</option>
            <option value="1">成功</option>
            <option value="2">失败</option>
        </select>
    </div>
    <button type="button" class="btn  btn-primary" id="subTaskQuery"
            method="submit"
            action="get" requestUrl="/task/list?type=query" handler="subTaskQuery"
    >查询
    </button>

    <div class="checkbox">
        <label>
            <input type="checkbox" name="querySubTaskTimer" method="querySubTaskTimer" value="1000"> 刷新
        </label>
    </div>

    <div class="checkbox">
        <label>
            <button type="button" class="btn  btn-primary"
                    method="submit"
                    action="get" requestUrl="/task/restTaskByQuery?type=init" handler="restTaskByQuery"
            >重发任务
            </button>
            &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
            <button type="button" class="btn  btn-primary"
                    method="submit"
                    action="get" requestUrl="/task/restTaskByQuery?type=stop" handler="restTaskByQuery"
            >暂停任务
            </button>

        </label>
    </div>
</form>

<br>
<div id="subTaskQueryResult">
    <jsp:include page="SubTaskResult.jsp" flush="true"/>
</div>

<script>
    $(document).ready(function () {
        var main = Worker.getInstance();
        main.excuteInterval.stopInterval();//页面重新加载时，清除定时任务
    });


</script>

