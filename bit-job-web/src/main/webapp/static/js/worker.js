var Worker = (function () {
    var instance;
    var _ = function () {
        var me = this;
        // 事件对象

        me.handler = {
            callbacks: {
                queryJmqConfig: function (data) {
                    $('#content').html(data);
                },
                toEditJmqConfig: function (data) {
                    $('#content').html(data);
                },
                queryMarketing: function (data) {
                    $('#content').html(data);
                },
                toEditMarketing: function (data) {
                    $('#content').html(data);
                },
                queryTask: function (data) {
                    $('#content').html(data);
                },
                toEditTask: function (data) {
                    $('#content').html(data);
                },
                queryTaskServiceConfig: function (data) {
                    $('#content').html(data);
                },
                toEditTaskServiceConfig: function (data) {
                    $('#content').html(data);
                },
                queryTimerTaskConfig: function (data) {
                    $('#content').html(data);
                },
                toEditTimerTaskConfig: function (data) {
                    $('#content').html(data);
                },
                resetTimerTaskConfigStatus: function (data, obj) {
                    var updataStatus = data.status;
                    var resetType = $(obj.target).val();
                    if (updataStatus == true) {
                        if (resetType == '启动') {
                            alert("启动任务成功");
                            $(obj.target).val('停止');
                            $(obj.target).removeClass();
                            $(obj.target).addClass('btn btn-warning');
                        } else {
                            alert("停止任务成功");
                            $(obj.target).val('启动');
                            $(obj.target).removeClass();
                            $(obj.target).addClass('btn  btn-primary');
                        }
                    } else {
                        alert("启动任务失败");
                    }
                },
                resetTaskServiceConfigStatus: function (data, obj) {
                    var updataStatus = data.status;
                    var resetType = $(obj.target).val();
                    if (updataStatus == true) {
                        if (resetType == '启动') {
                            alert("启动任务成功");
                            $(obj.target).val('停止');
                            $(obj.target).removeClass();
                            $(obj.target).addClass('btn btn-warning');
                        } else {
                            alert("停止任务成功");
                            $(obj.target).val('启动');
                            $(obj.target).removeClass();
                            $(obj.target).addClass('btn  btn-primary');
                        }
                    } else {
                        alert("启动任务失败");
                    }
                },
                resetAllTimerTaskConfigStatus: function (data, obj) {
                    var updataStatus = data.status;
                    var resetType = $(obj.target).val();
                    if (updataStatus == true) {
                        if (resetType == '启动全部') {
                            alert("启动任务成功");
                            $('input[name="resetTimerTaskConfigStatus"]').val("停止");
                            $('input[name="resetTimerTaskConfigStatus"]').removeClass();
                            $('input[name="resetTimerTaskConfigStatus"]').addClass('btn btn-warning');
                        } else {
                            alert("停止任务成功");
                            $('input[name="resetTimerTaskConfigStatus"]').val("启动");
                            $('input[name="resetTimerTaskConfigStatus"]').removeClass();
                            $('input[name="resetTimerTaskConfigStatus"]').addClass('btn btn-primary');
                        }
                    } else {
                        alert("重启任务失败");
                    }
                },
                resetAllTaskServiceConfigStatus: function (data, obj) {
                    var updataStatus = data.status;
                    var resetType = $(obj.target).val();
                    if (updataStatus == true) {
                        if (resetType == '启动') {
                            alert("启动任务成功");
                            $('input[name="resetTaskServiceConfigStatus"]').val("停止");
                            $('input[name="resetTaskServiceConfigStatus"]').removeClass();
                            $('input[name="resetTaskServiceConfigStatus"]').addClass('btn btn-warning');
                        } else {
                            alert("停止任务成功");
                            $('input[name="resetTaskServiceConfigStatus"]').val("启动");
                            $('input[name="resetTaskServiceConfigStatus"]').removeClass();
                            $('input[name="resetTaskServiceConfigStatus"]').addClass('btn btn-primary');
                        }
                    } else {
                        alert("启动任务失败");
                    }
                },
                subTaskQuery: function (data, obj) {
                    $('.theme-popover-mask').fadeOut(1);
                    $('#subTaskQueryResult').html(data);
                },
                restTaskByQuery: function (data, obj) {
                    $('.theme-popover-mask').fadeOut(1);
                    $('#subTaskQueryResult').html(data);
                },

                resetSubTask: function (data, obj) {//重置子任务
                    var updataStatus = data.status;
                    if (updataStatus == true) {
                        alert("更新成功，准备重发");
                        var tr = $(obj.target).parent().parent();//当前行
                        var td = $(tr).find('td')[1];//第三列
                        $(td).html(me.button.taskStatusBtn.info);
                    } else {
                        alert("重发失败");
                    }
                },
                queryThreadPoolProperties: function (data) {
                    $('#content').html(data);
                    var status = me.threadPoolRefreshTimer.status;
                    if (status == 1) {
                        $('#queryThreadPoolRefreshCheck').attr("checked", "checked");
                    }
                },
                toEditThreadPoolProperties: function (data) {
                    $('#content').html(data);
                },
                updateTaskInterval: function (data) {
                    var updataStatus = data.status;
                    if (updataStatus == "true") {
                        alert("修改频率成功");
                    } else {
                        alert("修改频率失败");
                    }
                },
                queryServerInstanceInfo: function (data) {
                    $('#content').html(data);
                }
            },
            callPara: {
                queryJmqConfig: function () {
                    var paraData = $('#queryJmqConfigForm').serialize();
                    return paraData;
                },
                toEditJmqConfig: function () {
                    return "";
                },
                queryMarketing: function () {
                    var paraData = $('#queryMarketingForm').serialize();
                    return paraData;
                },
                toEditMarketing: function () {
                    return "";
                },
                queryTask: function () {
                    var paraData = $('#queryTaskForm').serialize();
                    return paraData;
                },
                toEditTask: function () {
                    return "";
                },
                queryTaskServiceConfig: function () {
                    var paraData = $('#queryTaskServiceConfigForm').serialize();
                    return paraData;
                },
                toEditTaskServiceConfig: function () {
                    return "";
                },
                queryTimerTaskConfig: function () {
                    var paraData = $('#queryTimerTaskConfigForm').serialize();
                    return paraData;
                },
                toEditTimerTaskConfig: function () {
                    return "";
                },
                resetTimerTaskConfigStatus: function (obj) {
                    var resetType = $(obj.target).val();
                    var dataid = $(obj.target).attr('dataid');
                    var data = "id=" + dataid;
                    if (resetType == '启动') {
                        data = data + "&status=1";
                    } else if (resetType == '停止') {
                        data = data + "&status=0";
                    }
                    return data;
                },
                resetTaskServiceConfigStatus: function (obj) {
                    var resetType = $(obj.target).val();
                    var dataid = $(obj.target).attr('dataid');
                    var data = "id=" + dataid;
                    if (resetType == '启动') {
                        data = data + "&status=1";
                    } else if (resetType == '停止') {
                        data = data + "&status=0";
                    }
                    return data;
                },
                resetAllTimerTaskConfigStatus: function (obj) {
                    return "";
                },
                resetAllTaskServiceConfigStatus: function (obj) {
                    return "";
                },
                subTaskQuery: function (obj) {
                    var paraData = $('#subTaskQueryForm').serialize();
                    return paraData;
                },
                restTaskByQuery: function (obj) {
                    if (!window.confirm("要重置任务吗？")) {
                        return;
                    }
                    var paraData = $('#subTaskQueryForm').serialize();
                    return paraData;
                },
                resetSubTask: function (obj) {//重置子任务
                    var taskId = $(obj.target).attr('taskId');
                    return "taskId=" + taskId;
                },
                queryThreadPoolProperties: function () {
                    var paraData = $('#queryThreadPoolPropertiesForm').serialize();
                    return paraData;
                },
                toEditThreadPoolProperties: function () {
                    return "";
                },
                updateTaskInterval: function (obj) {
                    var triggerName = obj['triggerName'].value;
                    var schedulerName = obj['schedulerName'].value;
                    var tr = $(obj.target).parent().parent();//当前行
                    var td = $(tr).find('td')[0];//第二列
                    var repeatInterval = $(td).find('input')[0].value;
                    var data = "triggerName=" + triggerName + "&schedulerName=" + schedulerName + "&repeatInterval=" + repeatInterval;
                    return data;
                },
                queryServerInstanceInfo: function () {
                    var paraData = $('#queryServerInstanceInfoForm').serialize();
                    return paraData;
                }
            }
        }

        me.showEnableTip = {
            "生效": {"text": "失效", "class": "btn btn-info", "tip": "生效成功"},
            "失效": {"text": "生效", "class": "btn btn-warning", "tip": "失效成功"}
        }


        /*        me.createForm=function()
         {
         var form = jQuery('<form   name="roleResoucesTreeForm"></form>');
         var oldElement = $('input[name="saveTree"]');
         var newElement = jQuery(oldElement).clone();
         jQuery(newElement).appendTo(form);
         return form;
         }*/
        me.formRequest = {//数据来源于form
            this_me: this,
            event: '',
            callbackUrl: '',
            content: 'content',
            request: function (event) {
                this.event = event;
                var paraData = $('#' + event['form'].value).serialize();
                var action = event["action"].value;
                if (action == 'new') {
                    window.open(event['requestUrl'].value);
                } else {

                    if (event['handler']) {
                        var handler = event['handler'].value;
                        me.handler.callPara[handler](event);
                        me.ajax(action, event['requestUrl'].value, paraData, me.handler.callbacks[handler], this, event);
                    } else {
                        me.ajax(action, event['requestUrl'].value, paraData, this.callback, this);
                    }
                }
            },
            callback: function (data) {
                var status = data.status;
                if (status == true) {
                    alert("成功");
                } else {
                    alert(data.reason);
                }
            }
        }


        me.submit = {
            request: function (event) {
                var action = event["action"].value;
                if (action == "delete") {
                    if (!window.confirm("要删除吗？")) {
                        return;
                    }
                }
                var handler = event['handler'].value;
                var paraData = me.handler.callPara[handler](event);
                me.ajax(action, event['requestUrl'].value, paraData, me.handler.callbacks[handler], this, event);
            }
        }
        me.ajax = function (type, url, paraData, fnMethod, fnObj, event) {
            me.releaseLastAjax();
            $.ajax({
                type: type,
                url: url,
                data: paraData,
                cache: false,
                async: true,
                success: function (result) {
                    fnMethod.call(fnObj, result, event);
                }
            })
        }

        me.updateAjax = function (paraData, url, fn, fnP1) {
            $.ajax({
                type: "post",
                url: url,
                cache: false,
                async: true,
                data: paraData + "&_t=" + new Date().getTime(),
                success: function (result) {
                    fn.call(null, result, fnP1);//回调
                }
            })
        }

        me.ajaxRequestForcontent = function (url, paraData, domId) {
            me.releaseLastAjax();
            var ajaxRequest = $.ajax({
                type: "get",
                url: url,
                async: true,
                data: paraData + "&_t=" + new Date().getTime(),
                complete: function (data) {
                    $('.theme-popover-mask').fadeOut(1);
                    $('#' + domId).html(data.responseText);

                },
                success: function () {
                }
            })
            me.ajaxRequesArr.push(ajaxRequest);//临时存放ajax请求对象
        }


        /*        $.ajax.beforeSend(function(){
         me.releaseLastAjax();
         })*/

        me.ajaxRequesArr = [];

        me.releaseLastAjax = function () {
            for (var i = 0; i < me.ajaxRequesArr.length; i++) {
                var status = me.ajaxRequesArr[i].status;
                if (status != 200) {
                    me.ajaxRequesArr[i].abort();
                }
            }
            me.ajaxRequesArr = [];
        }


        var me = this;
        me.map = new Map();
        me.initTopMenusHTML = function () {
            /* me.map.put("workList",'   <li class="active"><a href="javascript:void(0)" src="/work/baseList">系统quartz列表</a></li>' +
                 ' <li><a href="javascript:void(0)"  src="/work/bizList">业务quartz列表</a></li>');*/
            me.map.put("schedulerList", '   <li class="active"><a href="javascript:void(0)" src="/scheduler/list">调度列表</a></li>');
            me.map.put("subTaskList", '   <li class="active" style="background-color: #f9f9f9" ><a href="javascript:void(0)" src="/task/list">子任务列表</a></li>');

            me.map.put("timerTaskConfig", '   <li class="active"><a href="javascript:void(0)" src="/timerTaskConfig/list">定时任务列表</a></li>' +
                ' <li><a href="javascript:void(0)"  src="/timerTaskConfig/toSave">定时任务添加</a></li>');

            me.map.put("taskServiceConfig", '   <li class="active"><a href="javascript:void(0)" src="/taskServiceConfig/list">任务配置列表</a></li>' +
                ' <li><a href="javascript:void(0)"  src="/taskServiceConfig/toSave">任务配置添加</a></li>' +
                '   <li class="active"><a href="javascript:void(0)" src="/threadPoolProperties/list">任务线程池配置列表</a></li>' +
                ' <li><a href="javascript:void(0)"  src="/threadPoolProperties/toSave">任务线程池配置添加</a></li>' +
                ' <li><a href="javascript:void(0)"  src="/serverinstanceinfo/list">服务器实例列表</a></li>' +
                '<li class="active"><a href="javascript:void(0)" src="/jmqConfig/list">mq配置列表</a></li>' +
                ' <li><a href="javascript:void(0)"  src="/jmqConfig/toSave">mq配置添加</a></li>');


        }
        me.topMenu = {
            html: '<a class="blog-nav-item blog-active" href="javascript:void(0)" id="subTaskList">子任务管理</a>'
                /*   +'<a class="blog-nav-item " href="javascript:void(0)" id="workList">quartz管理</a>'*/
                + '<a class="blog-nav-item " href="javascript:void(0)" id="schedulerList">调用管理</a>'
                + '<a class="blog-nav-item " href="javascript:void(0)" id="timerTaskConfig">定时任务管理</a>'
                + '<a class="blog-nav-item " href="javascript:void(0)" id="taskServiceConfig">任务配置管理</a>'
        }
        me.getHTML = function (key) {
            return me.map.get(key);
        }
        me.loadDefaultSidebar = function () {//加载菜单
            me.initTopMenusHTML();
            $('#topSidebar').html(me.topMenu.html);//top menu
            $('#leftSidebar').html(me.getHTML('resourcesList'));//左侧菜单
        }
        me.loadDefaultPage = function () {//加载默认显示的页面
            var a = $('#topSidebar').find("a")[0];
            $(a).click();
            var li = $('#leftSidebar').find("li")[0];
            $(li).click();
        }

        me.button = {
            taskStatusBtn: {//任务状态按钮类型
                info: '<button type="button" class="btn btn-info">未执行</button>',
                warning: '<button type="button" class="btn btn-warning">锁定</button>',
                success: '<button type="button" class="btn btn-success">完成</button>'
            }
        }

        me.querySubTaskTimer = {
            intevalId: '',
            request: function (obj) {//刷新子任务列表
                if ($(obj.target).attr("checked")) {
                    var excuteFunc = "$('#subTaskQuery').click()";
                    me.excuteInterval.startInterval(excuteFunc, $(obj.target).val());
                } else {
                    this.stop();
                }
            },
            stop: function () {//停止
                me.excuteInterval.stopInterval();
            }
        }
        me.threadPoolRefreshTimer = {
            intevalId: '',
            request: function (obj) {
                if ($(obj.target).attr("checked")) {
                    this.status = 1;
                    var excuteFunc = "$('#queryThreadPoolRefresh').click()";
                    me.excuteInterval.startInterval(excuteFunc, $(obj.target).val());
                } else {
                    this.status = 0;
                    this.stop();
                }
            },
            stop: function () {//停止
                me.excuteInterval.stopInterval();
            },
            status: 0
        }
        me.excuteInterval = {//执行定时任务
            intevalId: '0',
            startInterval: function (excuteFunc, interval) {//停止定时任务
                this.intevalId = window.setInterval(excuteFunc, interval);
            },

            stopInterval: function () {//停止定时任务
                window.clearInterval(this.intevalId);
            }
        }


    }
    return {
        getInstance: function () {
            if (!instance) {
                instance = new _();
            }
            return instance;
        }
    }


})()

var Event = function () {
    this.target;
    this.obj = {};
    this.setSource = function (objs) {
        this.obj = objs.attributes;
        this.target = objs;
        this.obj.target = objs;
    }
    this.getSource = function () {
        return this.obj;
    }
    this.getTarget = function () {
        return this.target;
    }
}