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
            , {field: 'groupname', title: '部门列表', width: 200}
            , {field: 'groupid', title: '部门id', width: 200,hide:true}
            , {field: 'rolename', title: '角色列表', width: 200}
            , {field: 'roleid', title: '角色id', width: 200,hide:true}
            , {field: 'isForbid', title: '禁用', width: 100, templet: '#isForbid'}
            , {field: 'active', title: '操作', width: 150, toolbar: '#table-content-list'}
        ]],
    });

    exports("user", {});
});