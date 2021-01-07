layui.define(['form'], function (exports) {
    var $ = layui.jquery, jqutil = function () {
        this.v = '1.0.0';
    };

    jqutil.prototype.render = function (options) {
        var layer = layui.layer, admin = layui.admin;
        //请求地址
        var url = options.url,
            // 请求参数
            data = options.params,
            // 请求方式
            type = options.type === undefined ? 'GET' : options.type,
            // 渲染成功后的回调函数
            success = typeof options.success != "function" ? function (d) {
                layer.ready(function () {
                    admin.popup({
                        content: '未配置请求成功后的响应函数'
                        , area: '300px'
                        , shade: false
                    });
                });
            } : options.success;
        error = typeof options.error != "function" ? function (jqXHR) {
            console.log(jqXHR)
            layer.ready(function () {
                admin.popup({
                    content: '请求错误'
                    , area: '300px'
                    , shade: false
                });
            });
        }:options.error
    };
    jqutil.prototype.load = function () {
        var thant = this;
        $.ajax({
            type: this.type,
            url: this.url,
            dataType: "json",
            data: this.data,
            success: this.success,
            error: this.error
        })
    }
    exports("jqutil", core)
});