;layui.define(["form","table"],function (opt) {
    var view = layui.view,
        table = layui.table,
        form = layui.form;
    var $ = layui.$;
    //监听搜索
    form.on('submit(LAY-app-contlist-search)', function(data){
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
        ,page: true //开启分页
        ,cols: [[ //表头
            {field: 'id', title: 'ID', width:80, sort: true, fixed: 'left'}
            ,{field: 'title', title: '菜单名字', width: 200, sort: true}
            ,{field: 'name', title: '菜单name', width:200}
            ,{field: 'category', title: '类别', width:100, sort: true}
            ,{field: 'url', title: '跳转地址', width:200}
            ,{field: 'isForbid', title: '禁用', width: 100}
            ,{field: 'model', title: '所属model', width: 100, sort: true}
            ,{field: 'type', title: '打开类型', width: 100, sort: true}
            ,{field: 'sort', title: '排序', width: 100}
            ,{field: 'active', title: '操作', width: 200,toolbar: '#table-content-list'}
        ]],
        done: function (res, curr, count) {
            //如果是异步请求数据方式，res即为你接口返回的信息。
            //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
            $("[data-field='category']").children().each(function () {
                if ($(this).text() == '1') {
                    $(this).text('系统资源');
                } else if ($(this).text() == '2') {
                    $(this).text('非系统资源');
                }else{
                    $(this).text('未定义');
                }
            });

            $("[data-field='isForbid']").children().each(function () {
                if ($(this).text() == '1') {
                    $(this).text('未禁用');
                } else if ($(this).text() == '2') {
                    $(this).text('禁用');
                }else{
                    $(this).text('未定义');
                }
            });

            $("[data-field='type']").children().each(function () {
                if ($(this).text() == '1') {
                    $(this).text('内部打开');
                } else if ($(this).text() == '2') {
                    $(this).text('ifream');
                }else{
                    $(this).text('未定义');
                }
            });
        }
    });
    opt("menu",{});
})