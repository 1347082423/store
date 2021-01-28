layui.define(["form", "table", "jqutil"], function (exports) {
    var $ = layui.$
        , admin = layui.admin
        , view = layui.view
        , table = layui.table
        , form = layui.form
        , jqutil = layui.jqutil;
    form.render(null, 'app-content-list');
    $('.layuiadmin-btn-list').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

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
        url: '/resource/index' //数据接口
        , method: "POST"
        , page: true //开启分页
        , cols: [[ //表头
            {width: 80, checkbox: true}
            , {field: 'id', title: 'ID', width: 100, sort: true}
            , {field: 'name', title: '资源名字', width: 200}
            , {field: 'category', title: '公开资源', width: 120, sort: true, templet: '#category'}
            , {field: 'url', title: '资源路径', width: 200}
            , {field: 'isForbid', title: '禁用', width: 100, templet: '#isForbid'}
            , {field: 'model', title: '所属model',hide:true, width: 120, sort: true}
            , {field: 'type', title: '操作类型', width: 120, sort: true, templet: '#type'}
            , {field: 'pid', title: 'pid',hide:true, width: 100}
            , {field: 'desc', title: 'desc', width: 200}
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
                title: '修改资源信息'
                , area: ['735px', '650px']
                , id: 'LAY-popup-content-add'
                , success: function (layero, index) {
                    view(this.id).render('sys/resource/addresource',data).done(function () {
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
                            var checkStatus = [];
                            checkStatus.push(field)
                            jqutil.render({
                                url: "/resource/updateResource",
                                params: checkStatus,
                                type: "POST",
                                success: function (d) {
                                    if (d.ok) {
                                        layer.msg(d.msg);
                                        layui.table.reload('LAY-app-content-list'); //重载表格
                                        layer.close(index); //执行关闭
                                    }
                                }
                            });
                            jqutil.loadByList();
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

    var active = {
        batchdel: function () {
            var checkStatus = table.checkStatus('LAY-app-content-list')
                , checkData = checkStatus.data; //得到选中的数据
            if (checkData.length === 0) {
                return layer.msg('请选择数据');
            }
            //checkData = JSON.stringify({"exSysMenus":checkData});
            layer.confirm('确定删除吗？', function (index) {
                //执行 Ajax 后重载
                jqutil.render({
                    url: "/menu/isForbid",
                    params: checkData,
                    type: "POST",
                    success: function (d) {
                        if (d.ok) {
                            layer.msg(d.msg);
                            layui.table.reload('LAY-app-content-list'); //重载表格
                            layer.close(index); //执行关闭
                        }
                    }
                });
                jqutil.loadByList();
            });
        }
        //添加
        , add: function (othis) {
            admin.popup({
                title: '添加资源'
                , area: ['735px', '650px']
                , id: 'LAY-popup-content-add'
                , success: function (layero, index) {
                    view(this.id).render('sys/resource/addresource').done(function () {
                        //必须加  否则有些表单加载不出来
                        form.render(null, 'layuiadmin-app-form-list');
                        //监听提交
                        form.on('submit(layuiadmin-app-form-submit)', function (data) {
                            var field = data.field; //获取提交的字段
                            //提交 Ajax 成功后，关闭当前弹层并重载表格
                            //400请求参数出错
                            if (field.isForbid == "on") {
                                field.isForbid = "1";
                            } else {
                                field.isForbid = "2";
                            }
                            jqutil.render({
                                url: "/resource/saveResource",
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
        }
    };

    exports('resource', {})
});

layui.define(['treeSelect', 'form'],function (exports) {
    var treeSelect = layui.treeSelect, form = layui.form;
    var $ = layui.$;
    form.render(null, 'layuiadmin-app-form-list');
    var icon = "";
    treeSelect.render({
        // 选择器
        elem: '#pTree',
        // 数据
        data: '/menu/getAllMenu',
        // 异步加载方式：get/post，默认get
        type: 'get',
        // 占位符
        placeholder: '上级菜单',
        // 是否开启搜索功能：true/false，默认false
        search: true,
        // 点击回调
        click: function (d) {
            $("#pid").val(d.current.id)
        },
        // 加载完成后的回调函数
        success: function (d) {
            var pid = $("#pid").val();
            if (pid != null && pid.length > 0) {
                treeSelect.checkNode("pTree", pid);
            }
        }
    });
    exports('addresource',{})
});