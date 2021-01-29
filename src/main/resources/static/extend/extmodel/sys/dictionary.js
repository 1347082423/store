//dictionary
layui.define(["form", "table", "jqutil", "treeTable"], function (exports) {

    var treeTable = layui.treeTable, form = layui.form, jqutil = layui.jqutil, $ = layui.$, admin = layui.admin,
        view = layui.view;
    // 直接下载后url: './data/table-tree.json',这个配置可能看不到数据，改为data:[],获取自己的实际链接返回json数组
    var re = treeTable.render({
        elem: '#tree-table',
        url: '/dictionary/index',
        type: 'POST',
        params: '',
        primary_key: 'dicId',//id
        parent_key: 'dicParentid',//父级id
        icon_key: 'dicValue',
        is_checkbox: true,
        end: function (e) {
            form.render();
        },
        cols: [
            {
                key: 'dicValue',
                title: '名称',
            },
            {
                key: 'dicKey',
                title: '字典key',
                align: 'center'
            },
            {
                title: '类别',
                align: 'center',
                template: function (item) {
                    var value = "未定义";
                    if (item.dicCategory == 1) {
                        value = "系统字典";
                    } else if (item.dicCategory == 2) {
                        value = "业务字典";
                    }
                    return '<p>' + value + '</p>';
                }
            },
            {
                title: '序号',
                key: 'dicSort',
            },
            {
                title: '禁用',
                align: 'center',
                template: 'dicForbid',
                key: 'dicForbid',
            },
            {
                title: '操作',
                align: 'left',
                width: '200px',
                template: "table-content-list",
            }
        ]
    });
    //监听搜索
    form.on('submit(LAY-app-contlist-search)', function (data) {
        var field = data.field;
        //条件（名字、禁用、职位）  不展示树形
        if (field.name != '' || field.type == 2 || field.isForbid == 2) {
            re.isList = true;
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
            var checkStatus = treeTable.checkStatus(re, 0)
                , checkData = checkStatus.data; //得到选中的数据
            if (checkData.length === 0) {
                return layer.msg('请选择数据');
            }
            //checkData = JSON.stringify({"exSysMenus":checkData});
            layer.confirm('确定删除吗？', function (index) {
                //执行 Ajax 后重载
                for (var i = 0; i < checkData.length; i++) {
                    checkData[i].isForbid = 2;
                }
                jqutil.render({
                    url: "/dictionary/updateDictionary",
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
                title: '添加字典信息'
                , area: ['735px', '800px']
                , id: 'LAY-popup-content-add'
                , success: function (layero, index) {
                    view(this.id).render('sys/dictionary/adddictionary').done(function () {
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
                                url: "/dictionary/insertDictionary",
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


    exports("dictionary", {})
});


layui.define(['treeSelect', 'form', 'table'], function (exports) {
    var table = layui.table, form = layui.form;
    //头工具栏事件
    table.on('toolbar(LAY-app-content-list)', function (obj) {
        switch (obj.event) {
            case 'add':
                var oldData = table.cache["LAY-app-content-list"];
                var data1={};
                oldData.push(data1);
                table.reload('LAY-app-content-list',{data : oldData,url:''});
                break;
            case 'del':
                layer.confirm('确定删除吗？', function (index) {
                    var oldData = table.cache["LAY-app-content-list"];
                    var data = [];
                    for (var i = 0; i < oldData.length; i ++){
                        if (!oldData[i].LAY_CHECKED){
                            data.push(oldData[i])
                        }
                    }
                    layer.msg("删除成功",{time: 10},function(){
                        table.reload('LAY-app-content-list',{data : data,url:''});
                    });
                });
                break;
        }
    });

    layui.data.sendParams = function (params) {
        var where = {};
        if (params.dicParentid == null){
            where.dicParentid = -1;
        }else{
            where.dicParentid = params.dicParentid;
        }
        loadTable(where);
    }

    function loadTable(where){
        table.render({
            elem: '#LAY-app-content-list'
            , url: '/dictionary/getChilds'
            , method: "POST"
            , where: where
            , toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
            , defaultToolbar: []
            , cols: [[
                {type: 'checkbox', fixed: 'left'}
                , {field: 'id', title: 'ID', width: 80, hide: true,}
                , {field: 'dicKey', title: '字典key', width: 120, edit: 'text'}
                , {field: 'dicValue', title: '字典value', width: 150, edit: 'text'}
                , {field: 'dicSort', title: '排序', width: 80, edit: 'text'}
                , {field: 'dicForbid', title: '是否禁用', width: 150, templet: "#isForbid"}
            ]]
        });
    }
    exports('adddictionary', {});
});