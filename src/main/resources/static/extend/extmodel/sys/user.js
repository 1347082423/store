layui.define(["form", "table", "jqutil"],function (exports) {
    var table = layui.table, form = layui.form, jqutil = layui.jqutil,$ = layui.$,admin = layui.admin,view = layui.view;
    form.render(null, 'app-content-list');
    //第一个实例
    table.render({
        elem: '#LAY-app-content-list',
        url: '/user/index' //数据接口
        , method: "POST"
        , page: true //开启分页
        , cols: [[ //表头
            {width: 70, checkbox: true}
            , {field: 'id', title: 'ID', width: 80, sort: true}
            , {field: 'name', title: '用户名', width: 150, sort: true}
            , {field: 'username', title: '登录名', width: 150}
            , {field: 'phone', title: '电话', width: 120}
            , {field: 'address', title: '地址', width: 200}
            , {field: 'email', title: '邮箱', width: 200}
            , {field: 'groupNames', title: '部门列表', width: 200}
            , {field: 'groupIds', title: '部门id', width: 200,hide:true}
            , {field: 'roleNames', title: '角色列表', width: 200}
            , {field: 'roleIds', title: '角色id', width: 200,hide:true}
            , {field: 'isForbid', title: '禁用', width: 100, templet: '#isForbid'}
            , {field: 'active', title: '操作', width: 150, toolbar: '#table-content-list'}
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
                title: '修改角色'
                , area: ['735px', '650px']
                , id: 'LAY-popup-content-add'
                , success: function (layero, index) {
                    view(this.id).render('sys/user/addUser', data).done(function () {
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
                                url: "/user/insertRole",
                                params: field,
                                type: "POST",
                                success: function (d) {
                                    if (d.ok) {
                                        layer.msg(d.msg);
                                        table.reload('LAY-app-content-list'); //重载表格
                                        layer.close(index);
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
            data.isForbid = 2;
            checkStatus.push(data)
            layer.confirm('确定删除吗?', function (index) {
                jqutil.render({
                    url: "/user/delUser",
                    params: checkStatus,
                    type: "POST",
                    success: function (d,index) {
                        if (d.ok) {
                            layer.msg(d.msg);
                            table.reload('LAY-app-content-list'); //重载表格
                            layer.close(index);
                        }
                    }
                });
                jqutil.loadByList();

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
            for (var i = 0; i < checkData.length; i ++) {
                checkData[i].isForbid = 2;
            }
            if (checkData.length === 0) {
                return layer.msg('请选择数据');
            }
            //checkData = JSON.stringify({"exSysMenus":checkData});
            layer.confirm('确定删除吗？', function (index) {
                //执行 Ajax 后重载
                jqutil.render({
                    url: "/permissions/saveRoles",
                    params: checkData,
                    type: "POST",
                    success: function (d) {
                        if (d.ok) {
                            layer.msg(d.msg);
                            table.reload('LAY-app-content-list'); //重载表格
                            layer.close(index);
                        }
                    }
                });
                jqutil.loadByList();
            });
        }
        //添加
        , add: function (othis) {
            admin.popup({
                title: '添加角色'
                , area: ['735px', '650px']
                , id: 'LAY-popup-content-add'
                , success: function (layero, index) {
                    view(this.id).render('sys/user/addUser').done(function () {
                        //必须加  否则有些表单加载不出来
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
                                url: "/user/insertRole",
                                params: field,
                                type: "POST",
                                success: function (d) {
                                    if (d.ok) {
                                        layer.msg(d.msg);
                                        table.reload('LAY-app-content-list'); //重载表格
                                        layer.close(index);
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

    exports("sysuser", {});
});


layui.define('treeTable',function (exports) {
    var table = layui.table, form = layui.form, jqutil = layui.jqutil,$ = layui.$,admin = layui.admin,view = layui.view,treeTable = layui.treeTable;
    var data = [{"parent_id":0,"name":"广东省","id":22,"device":[]},{"parent_id":22,"name":"深圳市","id":23,"device":[]},{"parent_id":23,"name":"龙岗区","id":24,"device":[]},{"parent_id":23,"name":"福田区","id":25,"device":[]},{"parent_id":23,"name":"南山区","id":26,"device":[]},{"parent_id":0,"name":"广西省","id":27,"device":[]},{"parent_id":27,"name":"南宁市","id":28,"device":[]},{"parent_id":22,"name":"广州市","id":29,"device":[]}];
    var tree = treeTable.render({
        elem: '#tree-table',
        data: data,
        icon_key: 'name',// 必须
        top_value: 0,
        primary_key: 'id',
        parent_key: 'parent_id',
        is_head: false,
        cols: [
            {
                key: 'name',
                title: '省份',
            },
            {
                title: '',
                align: 'right',
                template: function(item){
                    return '<input type="checkbox" value="'+item.id+'"  lay-filter="check" lay-skin="primary"> ';
                }
            }
        ],
        end: function(e){
            form.render()
        }
    });
    form.on('checkbox(check)', function(data){
        treeTable.childs_checkbox(tree, 1, data.value, $(data.elem).prop('checked'));
    });
    $('.layui-select-title').click(function(event){
        var pt = $(this).parents('.layui-form-select'),
            isOpen = pt.hasClass('layui-form-selected');
        $('.layui-form-select').removeClass('layui-form-selected');
        isOpen || pt.addClass('layui-form-selected')
        event.stopPropagation();
    })
    $('#tree-table, #tree-table2, #tree-table3').click(function(event){
        event.stopPropagation();
    });
    $('.choose').click(function(){
        $(this).parents('.layui-anim').prev().find('input').val(treeTable.checked(tree, 1, 0).join(','));
    })
    exports('addSysUser',{});
});