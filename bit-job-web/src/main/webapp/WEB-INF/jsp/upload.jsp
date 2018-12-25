<%@ page language="java" pageEncoding="utf-8" %>
<form action="/file/createMigrateTask" method="post" enctype="multipart/form-data">
    <input type="file" name="fileName"/>
    任务类型
    <select name="taskType">
        <option value="">请选择</option>
        <option value="300">迁移其他，请在文件随便只写一个数字</option>
        <option value="301">根据vendorid迁移</option>
        <option value="302">根据vendorid迁移理赔单</option>
        <option value="311">根据vendorid查询保单</option>

    </select>
    <input type="submit" name="提交"/>

    提交后，不要多次提交按钮。结果得十秒左右返回。迁移其他，请保证文件里只有一个数子
</form>
