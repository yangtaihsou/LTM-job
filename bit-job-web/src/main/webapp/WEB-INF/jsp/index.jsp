<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <title>job平台</title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <script src="/static/js/jquery.min.js"></script>
    <link href="/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/bootstrap/blog.css" rel="stylesheet">
    <script src="/static/js/jquery.extend.js"></script>
    <script src="/static/js/worker.js"></script>
    <script src="/static/js/quartz.js"></script>
    <script src="/static/js/scheduler.js"></script>
    <script src="/static/js/map.js"></script>
    <script src="/static/bootstrap/js/bootstrap.min.js"></script>
    <script src="/static/js/My97DatePicker/WdatePicker.js"></script>
</head>

<body>


<div class="blog-masthead">
    <div class="blog_container ">
        <nav class="blog-nav" id="topSidebar">
        </nav>
    </div>
</div>

<div class="container-fluid">

    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar" id="leftSidebar">
            </ul>

        </div>
        <br>
        <div class="col-xs-12 col-sm-9" id="content">


        </div>


    </div>
</div>
</body>
</html>
<script>

    function initTabResource(main) {
        var topMenuHtml = "";
        <c:forEach var="topLab" items="${tabs[systemCode]}" varStatus="status">
        topMenuHtml = topMenuHtml + '<c:out value="${topLab}" escapeXml="false"/>';
        </c:forEach>
        main.topMenu.html = topMenuHtml;

        <c:forEach var="item" items="${tabs}" varStatus="status">
        var subTabHtml = "";
        <c:forEach var="subTab" items="${tabs[item.key]}" varStatus="status">
        subTabHtml = subTabHtml + '<c:out value="${subTab}" escapeXml="false"/>';
        </c:forEach>
        main.map.put("<c:out value="${item.key}"/>", subTabHtml);
        </c:forEach>
    }

    $(document).ready(function () {
        var main = Worker.getInstance();

        //initTabResource(main);

        main.loadDefaultSidebar();
        $('#topSidebar>a').live('click', function () {//点击top
            $(this).addClass("blog-active").siblings().removeClass("blog-active");
            var leftId = $(this).attr('id');
            $('#leftSidebar').html(main.getHTML(leftId));
        });

        $('#leftSidebar>li').live('click', function () {//点击左侧菜单
            $(this).css('background-color', '#f8f8f9').siblings().css('background-color', '');
            var a = $(this).find("a")[0];
            var requestUrl = $(a).attr('src');
            if (typeof(requestUrl) != 'undefined') {
                $('.theme-popover-mask').fadeIn(100);
                main.ajaxRequestForcontent(requestUrl, '', 'content');
            }
        });
        $('#pageStr li>a').live('click', function () { //翻页
            var requestUrl = $(this).attr('src');
            if (!requestUrl) return;
            main.ajaxRequestForcontent(requestUrl, '', 'content');
            //main.ajaxRequestForcontent(requestUrl, '', 'content');
        })
        main.loadDefaultPage();


        $(':input[method]').live('click', function () {
            var method = $(this).attr('method');
            if (typeof(method) != 'undefined') {
                var event = new Event();
                event.setSource(this);
                main[method].request(event.getSource());
            }
        })


        var quartz = Quartz.getInstance();

        $('input[name=resetScheduler]').live('click', function () {//关启定时任务
            var resetType = $(this).val();
            var schedulerName = $(this).attr('schedulerName');
            var data = "schedulerName=" + schedulerName;
            if (resetType == '启动') {
                quartz.startTask.request(data, this);
            } else if (resetType == '停止') {
                quartz.stopTask.request(data, this);
            }
        })

        $('input[name=resetBaseScheduler]').live('click', function () {//关启定时任务
            var resetType = $(this).val();
            if (resetType == '启动') {
                quartz.startBaseTask.request("");
            } else if (resetType == '停止') {
                quartz.stopBaseTask.request("");
            }
        })

        $('input[name=resetBizScheduler]').live('click', function () {//关启定时任务
            var resetType = $(this).val();
            if (resetType == '启动') {
                quartz.startBizTask.request("");
            } else if (resetType == '停止') {
                quartz.stopBizTask.request("");
            }
        })


        var scheduler = Scheduler.getInstance();


        $('input[name=resetAllScheduler]').live('click', function () {//关启定时任务
            var resetType = $(this).val();
            if (resetType == '启动') {
                scheduler.startAllScheduler.request("");
            } else if (resetType == '停止') {
                scheduler.stopAllScheduler.request("");
            }
        })

        $('input[name=resetAScheduler]').live('click', function () {//关启定时任务

            var resetType = $(this).val();
            var schedulerName = $(this).attr('schedulerName');
            var data = "schedulerName=" + schedulerName;
            if (resetType == '启动') {
                scheduler.startScheduler.request(data, this);
            } else if (resetType == '停止') {
                scheduler.stopScheduler.request(data, this);
            }
        })
    });
    //禁止后退键 作用于Firefox、Opera
    document.onkeypress = $.banBackSpace;
    //禁止后退键  作用于IE、Chrome
    document.onkeydown = $.banBackSpace;

</script>