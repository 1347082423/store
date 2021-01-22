layui.define(['form', 'jquery'], function (exports) {
    var $ = layui.jquery, JQUtil = function () {
        this.v = '1.0.0';

    };

    JQUtil.prototype.render = function (options) {
        var layer = layui.layer, admin = layui.admin;
        //请求地址
        this.url = options.url;
        // 请求参数
        this.data = options.params;
        // 请求方式
        this.type = options.type === undefined ? 'GET' : options.type;
        // 渲染成功后的回调函数
        this.success = typeof options.success != "function" ? function (d) {
            layer.ready(function () {
                admin.popup({
                    content: '未配置请求成功后的响应函数'
                    , area: '300px'
                    , shade: false
                });
            });
        } : options.success;
        this.error = typeof options.error != "function" ? function (jqXHR) {
            console.log(jqXHR)
            layer.ready(function () {
                admin.popup({
                    content: '请求错误'
                    , area: '300px'
                    , shade: false
                });
            });
        } : options.error;
    };
    JQUtil.prototype.load = function () {
        $.ajax({
            type: this.type,
            url: this.url,
            dataType: "JSON",
            data: this.data,
            success: this.success,
            error: this.error
        });
    };
    JQUtil.prototype.loadByList = function () {
        $.ajax({
            type: this.type,
            url: this.url,
            dataType: "JSON",
            data: JSON.stringify(this.data),
            contentType:"application/json",
            success: this.success,
            error: this.error
        });
    }

    JQUtil.prototype.genID = function(length){
        return Number(Math.random().toString().substr(3,length) + Date.now()).toString(36);
    }
    var mod = new JQUtil();
    exports("jqutil", mod)
});