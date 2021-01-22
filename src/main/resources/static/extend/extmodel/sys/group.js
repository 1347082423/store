layui.define(["form", "table", "jqutil", "treeTable"], function (exports) {
    var treeTable = layui.treeTable, form = layui.form, jqutil = layui.jqutil, $ = layui.$, admin = layui.admin,
        view = layui.view;
    // 直接下载后url: './data/table-tree.json',这个配置可能看不到数据，改为data:[],获取自己的实际链接返回json数组
    var re = treeTable.render({
        elem: '#tree-table',
        url:'group/index',
        type:'POST',
        params:'',
        icon_key: 'name',
        is_checkbox: true,
        end: function (e) {
            form.render();
        },
        cols: [
            {
                key: 'name',
                title: '名称',
            },
            {
                key: 'maxuser',
                title: '最大人数',
                align: 'center',
                template: 'maxuser'
            },
            {
                key: 'minuser',
                title: '最小人数',
                align: 'center',
                template: 'minuser'
            },
            {
                title: '类别',
                align: 'center',
                template: function (item) {
                    var value = "未定义";
                    if (item.type == 1) {
                        value = "部门";
                    } else if (item.type == 2) {
                        value = "职位";
                    }
                    return '<p>' + value + '</p>';
                }
            },
            {
                title: '序号',
                key: 'sort',
            },
            {
                title: '禁用',
                align: 'center',
                template: 'isForbid',
            },
            {
                key: 'groupfunction',
                title: '职责',
                align: 'center',
            },
            {
                title: '操作',
                align: 'left',
                width:'200px',
                template: "table-content-list",
            }
        ]
    });
    //监听搜索
    form.on('submit(LAY-app-contlist-search)', function (data) {
        var field = data.field;
        //条件（名字、禁用、职位）
        if (field.name != '' || field.type == 2 || field.isForbid == 2) {
            re.isList=true;
        }
        //执行重载
        treeTable.reload(re, {
            where: field
        });
    });

    $('.layuiadmin-btn-list').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    var active = {
        batchdel: function () {
            var checkStatus = treeTable.checkStatus(re,0)
                , checkData = checkStatus.data; //得到选中的数据
            if (checkData.length === 0) {
                return layer.msg('请选择数据');
            }
            //checkData = JSON.stringify({"exSysMenus":checkData});
            layer.confirm('确定删除吗？', function (index) {
                //执行 Ajax 后重载
                for (var i = 0; i < checkData.length; i ++) {
                    checkData[i].isForbid = 2;
                }
                jqutil.render({
                    url: "/group/updateGroup",
                    params: checkData,
                    type: "POST",
                    success: function (d) {
                        if (d.ok) {
                            layer.msg(d.msg);
                            treeTable.reload(re); //重载表格
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
                title: '添加组信息'
                , area: ['735px', '650px']
                , id: 'LAY-popup-content-add'
                , success: function (layero, index) {
                    view(this.id).render('sys/group/addgroup').done(function () {
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
                                url: "/group/insertGroup",
                                params: field,
                                type: "POST",
                                success: function (d) {
                                    if (d.ok) {
                                        layer.msg(d.msg);
                                        treeTable.reload(re); //重载表格
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


    treeTable.on('tree(add)',function(obj){
        var data = obj.item;
        data = {pid:data.id}
        admin.popup({
            title: '添加组信息'
            , area: ['735px', '650px']
            , id: 'LAY-popup-content-add'
            , success: function (layero, index) {
                view(this.id).render('sys/group/addgroup', data).done(function () {
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
                            url: "/group/insertGroup",
                            params: field,
                            type: "POST",
                            success: function (d) {
                                if (d.ok) {
                                    layer.msg(d.msg);
                                    treeTable.reload(re); //重载表格
                                    layer.close(index);
                                }
                            }
                        });
                        jqutil.load();
                    });
                });
            }
        });
    });
    treeTable.on('tree(edit)',function(obj){
        var data = obj.item;
        admin.popup({
            title: '修改组信息'
            , area: ['735px', '650px']
            , id: 'LAY-popup-content-add'
            , success: function (layero, index) {
                view(this.id).render('sys/group/addgroup', data).done(function () {
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
                            url: "/group/updateGroup",
                            params: checkStatus,
                            type: "POST",
                            success: function (d) {
                                if (d.ok) {
                                    layer.msg(d.msg);
                                    treeTable.reload(re); //重载表格
                                    layer.close(index);
                                }
                            }
                        });
                        jqutil.loadByList();
                    });
                });
            }
        });
    });
    treeTable.on('tree(del)',function(obj){
        var data = obj.item; //获得当前行数据
        var checkStatus = [];
        data.isForbid = 2;
        checkStatus.push(data)
        layer.confirm('确定删除吗?', function (index) {
            jqutil.render({
                url: "/group/updateGroup",
                params: checkStatus,
                type: "POST",
                success: function (d,index) {
                    if (d.ok) {
                        layer.msg(d.msg);
                        treeTable.reload(re); //重载表格
                        layer.close(index);
                    }
                }
            });
            jqutil.loadByList();

        });
    });
    exports('group', {})
});

layui.define(['treeSelect', 'form'], function (exports) {
    var treeSelect = layui.treeSelect, form = layui.form;
    var $ = layui.$;
    form.render(null, 'layuiadmin-app-form-list');
    treeSelect.render({
        // 选择器
        elem: '#pTree',
        // 数据
        data: '/group/groupTree',
        // 异步加载方式：get/post，默认get
        type: 'post',
        params:{type:'1'},
        // 占位符
        placeholder: '上级组织',
        // 是否开启搜索功能：true/false，默认false
        // search: true,
        // 点击回调
        click: function (d) {
            $("#pid").val(d.current.id)
        },
        // 加载完成后的回调函数
        success: function (d) {
            var pid = $("#pid").val();
            if (pid != null && pid.length > 0 && pid !=0 ) {
                treeSelect.checkNode("pTree", pid);
            }
        }
    });
    layui.data.sendParams = function (params) {
    }

    exports('addgroup', {})
});