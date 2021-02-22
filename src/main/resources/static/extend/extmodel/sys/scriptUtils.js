layui.define(["jqutil"], function (exports) {
    var $ = layui.$ , jqutil = layui.jqutil;
    jqutil.render({
        url:"/dictionary/getLaytpl",
        params: "",
        type: "POST",
        success:function (d) {
            if (d.ok) {
                var laytpl = d.data;
                var laytplHtml = "";
                $.each(laytpl,function(key,values){
                    laytplHtml += "<script type=\"text/html\" id="+key+">";
                    for (var i = 0; i < values.length; i ++){
                        if (i == 0 ){
                            laytplHtml += "{{# if(d."+key+"=='"+values[i].dicKey+"'){ }}";
                            laytplHtml += values[i].dicValue;
                        } else {
                            laytplHtml += "{{# }else if (d."+key+"=='"+values[i].dicKey+"'){ }}";
                            laytplHtml += values[i].dicValue;
                        }

                    }
                    laytplHtml += "{{# } }}"
                    laytplHtml += "</script>";
                });
                $(".layadmin-header").append(laytplHtml);
            }
        }
    });
    jqutil.load();
    $(".layadmin-header").load("/extend/views/sys/scriptView.html");
    exports('scriptUtils', {});
})