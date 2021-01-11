layui.define(["form", "table", "jqutil"], function (exports) {
    var table = layui.table, form = layui.form, jqutil = layui.jqutil,$ = layui.$,admin = layui.admin,view = layui.view;
    form.render(null, 'app-content-list');
    //第一个实例
    table.render({
        elem: '#LAY-app-content-list',
        url: '/permissions/index' //数据接口
        , method: "POST"
        , page: true //开启分页
        , cols: [[ //表头
            {width: 80, checkbox: true}
            , {field: 'id', title: 'ID', width: 80, sort: true}
            , {field: 'name', title: '角色名称', width: 200, sort: true}
            , {field: 'rolename', title: '英文名称', width: 200}
            , {field: 'isForbid', title: '禁用', width: 100, templet: '#isForbid'}
            , {field: 'type', title: '打开类型', width: 120, sort: true, templet: '#type'}
            , {field: 'roles', title: '资源列表', width: 200}
            , {field: 'active', title: '操作', width: 200, toolbar: '#table-content-list'}
        ]],
    });
    //监听搜索
    form.on('submit(LAY-app-contlist-search)', function (data) {
        var field = data.field;
        //执行重载
        table.reload('LAY-app-content-list', {
            where: field
        });
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
                    view(this.id).render('sys/permissions/addPermissions', data).done(function () {
                        //必须加  否则有些表单项加载不出来
                        form.render(null, 'layuiadmin-app-form-list');
                        //监听提交
                        form.on('submit(layuiadmin-app-form-submit)', function (data) {
                            var field = data.field; //获取提交的字段
                            //提交 Ajax 成功后，关闭当前弹层并重载表格
                            //400请求参数出错
                            /*jqutil.render({
                                url: "/menu/saveMenu",
                                params: field,
                                type: "POST",
                                success: function (d) {
                                    if (d.isOk) {
                                        layer.msg(d.msg);
                                        layui.table.reload('LAY-app-content-list'); //重载表格
                                    }
                                }
                            });
                            jqutil.load();*/
                            layer.close(index); //执行关闭
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
                        }
                    }
                });
                jqutil.loadByList();
                layer.close(index);
                //向服务端发送删除指令
            });
        }
    });

    $('.layuiadmin-btn-list').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
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
                        }
                    }
                });
                jqutil.loadByList();
                layer.close(index); //执行关闭
            });
        }
        //添加
        , add: function (othis) {
            admin.popup({
                title: '添加菜单'
                , area: ['735px', '650px']
                , id: 'LAY-popup-content-add'
                , success: function (layero, index) {
                    view(this.id).render('sys/permissions/addPermissions').done(function () {
                        //必须加  否则有些表单加载不出来
                        form.render(null, 'layuiadmin-app-form-list');
                        //监听提交
                        form.on('submit(layuiadmin-app-form-submit)', function (data) {
                            var field = data.field; //获取提交的字段
                            //提交 Ajax 成功后，关闭当前弹层并重载表格
                            //400请求参数出错
                            jqutil.render({
                                url: "/menu/saveMenu",
                                params: field,
                                type: "POST",
                                success: function (d) {
                                    if (d.isOk) {
                                        layer.msg(d.msg);
                                        layui.table.reload('LAY-app-content-list'); //重载表格
                                    }
                                }
                            });
                            jqutil.load();
                            layer.close(index); //执行关闭
                        });
                    });
                }
            });
        }
    };

    exports("permissions", {});

});


layui.define('tableSelect',function (exports) {
    var MOD_NAME = 'addPermissions',tableSelect = layui.tableSelect;
    tableSelect.render({
        elem: '#resource',	//定义输入框input对象
        checkedKey: 'LAY-app-content-list', //表格的唯一建值，非常重要，影响到选中状态 必填
        searchKey: 'keyword',	//搜索输入框的name值 默认keyword
        searchPlaceholder: '关键词搜索',	//搜索输入框的提示文字 默认关键词搜索
        table: {	//定义表格参数，与LAYUI的TABLE模块一致，只是无需再定义表格elem
            url:'/permissions/index',
            cols: [[ //表头
                {width: 80, checkbox: true}
                , {field: 'id', title: 'ID', width: 80, sort: true}
                , {field: 'name', title: '角色名称', width: 200, sort: true}
                , {field: 'rolename', title: '英文名称', width: 200}
                , {field: 'isForbid', title: '禁用', width: 100, templet: '#isForbid'}
                , {field: 'type', title: '打开类型', width: 120, sort: true, templet: '#type'}
                , {field: 'roles', title: '资源列表', width: 200}
                , {field: 'active', title: '操作', width: 200, toolbar: '#table-content-list'}
            ]]
        },
        done: function (elem, data) {
            //选择完后的回调，包含2个返回值 elem:返回之前input对象；data:表格返回的选中的数据 []
            //拿到data[]后 就按照业务需求做想做的事情啦~比如加个隐藏域放ID...
        }
    })
    exports(MOD_NAME, {});
})