var Quartz = (function () {
    var instance;
    var _ = function () {
        var me = this;

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
        me.startTask = {
            request: function (paraData, targetObj) {//启动任务
                return me.updateAjax(paraData, "/work/start", this.callback, targetObj);
            },
            callback: function (data, obj) {
                var updataStatus = data.status;
                if (updataStatus == "true") {
                    alert("启动任务成功");
                    $(obj).val('停止');
                    $(obj).removeClass();
                    $(obj).addClass('btn btn-warning');
                } else {
                    alert("启动任务失败");
                }
            }
        }

        me.stopTask = {
            request: function (paraData, targetObj) {//启动任务
                return me.updateAjax(paraData, "/work/stop", this.callback, targetObj);
            },
            callback: function (data, obj) {
                var updataStatus = data.status;
                if (updataStatus == "true") {
                    alert("停止任务成功");
                    $(obj).val('启动');
                    $(obj).removeClass();
                    $(obj).addClass('btn btn-primary');
                } else {
                    alert("停止任务失败");
                }
            }
        }

        me.startBaseTask = {
            request: function (paraData) {//启动所有基础任务
                return me.updateAjax(paraData, "/work/startBaseAll", this.callback);
            },
            callback: function (data) {
                var updataStatus = data.status;
                if (updataStatus == "true") {
                    alert("启动任务成功");
                    $("input[name=resetScheduler]").each(function () {
                        $(this).val('停止');
                        $(this).removeClass();
                        $(this).addClass('btn btn-warning');
                    });
                } else {
                    alert("启动任务失败");
                }
            }
        }


        me.stopBaseTask = {
            request: function (paraData) {//停止所有基础任务
                return me.updateAjax(paraData, "/work/stopBaseAll", this.callback);
            },
            callback: function (data) {
                var updataStatus = data.status;
                if (updataStatus == "true") {
                    alert("停止任务成功");
                    $("input[name=resetScheduler]").each(function () {
                        $(this).val('启动');
                        $(this).removeClass();
                        $(this).addClass('btn btn-primary');
                    });

                } else {
                    alert("停止任务失败");
                }
            }
        }

        me.startBizTask = {
            request: function (paraData) {//启动所有业务任务
                return me.updateAjax(paraData, "/work/startBizAll", this.callback);
            },
            callback: function (data) {
                var updataStatus = data.status;
                if (updataStatus == "true") {
                    alert("启动任务成功");
                    $("input[name=resetScheduler]").each(function () {
                        $(this).val('停止');
                        $(this).removeClass();
                        $(this).addClass('btn btn-warning');
                    });
                } else {
                    alert("启动任务失败");
                }
            }
        }
        me.stopBizTask = {
            request: function (paraData) {//停止所有基础任务
                return me.updateAjax(paraData, "/work/stopBizAll", this.callback);
            },
            callback: function (data) {
                var updataStatus = data.status;
                if (updataStatus == "true") {
                    alert("停止任务成功");
                    $("input[name=resetScheduler]").each(function () {
                        $(this).val('启动');
                        $(this).removeClass();
                        $(this).addClass('btn btn-primary');
                    });
                } else {
                    alert("停止任务失败");
                }
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