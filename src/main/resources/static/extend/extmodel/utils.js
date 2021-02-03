layui.define(['jquery'], function (exports) {
    var $ = layui.jquery, basicUtil = function () {
        this.v = '1.0.0';

    };
    basicUtil.prototype.removeItemByList = function(data,key){
        if (data == null || data.length == 0){
            return data
        }
        for (var i = 0; i < data.length; i ++){
            delete data[i][key];
        }
        return data;
    };

    basicUtil.prototype.andItemByList = function(data,key,value){
        if (data == null || data.length == 0){
            return data
        }
        for (var i = 0; i < data.length; i ++){
            data[i][key] = value;
        }
        return data;
    };

    var mod = new basicUtil();
    exports("basicUtil", mod)
});