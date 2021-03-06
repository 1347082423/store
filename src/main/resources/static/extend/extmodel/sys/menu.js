;layui.define(["form", "table","jqutil"], function (opt) {
    var view = layui.view
        , admin = layui.admin,
        table = layui.table,
        form = layui.form
        , jqutil = layui.jqutil;
    var $ = layui.$;
    //监听搜索
    form.on('submit(LAY-app-contlist-search)', function (data) {
        var field = data.field;
        //执行重载
        table.reload('LAY-app-content-list', {
            where: field
        });
    });
    form.render(null, 'app-content-list');
    //第一个实例
    table.render({
        elem: '#LAY-app-content-list',
        url: '/menu/index' //数据接口
        , method: "POST"
        , page: true //开启分页
        , cols: [[ //表头
            {width: 80, checkbox: true}
            , {field: 'id', title: 'ID', width: 80, sort: true}
            , {field: 'title', title: '菜单名字', width: 200, sort: true}
            , {field: 'name', title: '菜单name', width: 200}
            , {field: 'category', title: '类别', width: 100, sort: true, templet: '#resourceCategory'}
            , {field: 'url', title: '跳转地址', width: 200}
            , {field: 'isForbid', title: '禁用', width: 100, templet: '#isForbid'}
            , {field: 'model', title: '所属model', width: 120, sort: true}
            , {field: 'type', title: '打开类型', width: 120, sort: true, templet: '#openType'}
            , {field: 'sort', title: '排序', width: 100}
            , {field: 'icon', title: '图标', width: 160}
            , {field: 'pid', title: 'pid',hide:true, width: 100}
            , {field: 'active', title: '操作', width: 200, toolbar: '#table-content-list'}
        ]],
    });
    //监听工具条
    table.on('tool(LAY-app-content-list)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）
        if (layEvent === 'edit') { //编辑
            admin.popup({
                title: '修改菜单'
                , area: ['735px', '650px']
                , id: 'LAY-popup-content-add'
                , success: function (layero, index) {
                    view(this.id).render('sys/menu/addmenu',data).done(function () {
                        //必须加  否则有些表单项加载不出来
                        form.render(null, 'layuiadmin-app-form-list');
                        //监听提交
                        form.on('submit(layuiadmin-app-form-submit)', function (data) {
                            var field = data.field; //获取提交的字段
                            //提交 Ajax 成功后，关闭当前弹层并重载表格
                            //400请求参数出错
                            if(field.isForbid == "on") {
                                field.isForbid = "1";
                            } else {
                                field.isForbid = "2";
                            }
                            jqutil.render({
                                url: "/menu/saveMenu",
                                params: field,
                                type: "POST",
                                success: function (d) {
                                    if (d.ok) {
                                        layer.msg(d.msg);
                                        layui.table.reload('LAY-app-content-list'); //重载表格
                                        layer.close(index); //执行关闭
                                    }
                                }
                            });
                            jqutil.load();
                        });
                    });
                }
            });
        } else if (layEvent === 'del') { //删除
            var checkStatus = [];
            checkStatus.push(data)
            layer.confirm('确定删除吗?', function (index) {
                jqutil.render({
                    url: "/menu/isForbid",
                    params: checkStatus,
                    type: "POST",
                    success: function (d) {
                        if (d.ok) {
                            layer.msg(d.msg);
                            layui.table.reload('LAY-app-content-list'); //重载表格
                            layer.close(index);
                        }
                    }
                });
                jqutil.loadByList();

                //向服务端发送删除指令
            });
        }
    });
    opt("menu", {});
})