
layui.use(["table", "element", "form", "laydate", "upload", "util"], function () {
  var form = layui.form
    , table = layui.table
    , element = layui.element
    , laydate = layui.laydate
    , util = layui.util
    , $ = layui.jquery
    , classId = 1;

  var storage = window.localStorage;
  var nowTbl = function(){
    table.render({
      elem: "#nowTbl",
      skin: 'line',
      url: 'http://127.0.0.1:8090/api/getLatestGrade',
      method: 'POST',
      contentType: "application/json;charset=utf-8",
      dataType: "json",
      where: {
        'user_id': parseInt(storage.getItem("user_id")),
      },
      page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
        layout: ['count', 'prev', 'page', 'next'] //自定义分页布局
        , groups: 3 //只显示 1 个连续页码
        , first: true //不显示首页
        , last: true //不显示尾页
        , limit: 8
      },
      cols: [[
        { field: 'index', title: '序号', width: 50, type: 'numbers' }
        , { field: 'grade_id', title:'grade_id', hide:true}
        , { field: 'name', minWidth: 100, title: '姓名', align: 'center' }
        , { field: 'title', minWidth: 200, title: '标题', align: 'center' }
        , { field: 'grade_rank', minWidth: 90, title: '成绩', align: 'center', fixed: 'right' }
      ]],
      parseData: function (res) {
        return {
          "code": 0,
          "msg": res.header.message,
          "count": res.body.count,
          "data": res.body.results
        };
      },
      done: function (res, curr, count) { }
    });
  };

  var historyTbl = function(){
    table.render({
      elem: "#historyTbl",
      skin: 'line',
      url: 'http://127.0.0.1:8090/api/getAllAssessBatchByGroupId',
      method: 'POST',
      contentType: "application/json;charset=utf-8",
      dataType: "json",
      where: {
        'user_id': parseInt(storage.getItem("user_id")),
      },
      page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
        layout: ['count', 'prev', 'page', 'next'] //自定义分页布局
        , groups: 3 //只显示 1 个连续页码
        , first: true //不显示首页
        , last: true //不显示尾页
        , limit: 8
      },
      cols: [[
        { field:'index', title: '序号', width: 50, type: 'numbers' }
        , { field: 'assess_batch_id', title:'id', hide:true}
        , { field: 'title', minWidth: 150, title: '标题', align: 'center' }
        , { fixed: 'right', title: '操作', minWidth: 70, templet: '#more', align: 'center' }
      ]],
      parseData: function (res) {
        return {
          "code": 0,
          "msg": res.header.message,
          "count": res.body.count,
          "data": res.body.results
        };
      },
      done: function (res, curr, count) { }
    });
  };

  $("#latestSearch").click(function(){
    table.render({
      elem: "#nowTbl",
      skin: 'line',
      url: 'http://127.0.0.1:8090/api/getLatestGradeByName',
      method: 'POST',
      contentType: "application/json;charset=utf-8",
      dataType: "json",
      where: {
        'user_id': parseInt(storage.getItem("user_id")),
        'name': $("#latestSearchInput").val()
      },
      page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
        layout: ['count', 'prev', 'page', 'next'] //自定义分页布局
        , groups: 3 //只显示 1 个连续页码
        , first: true //不显示首页
        , last: true //不显示尾页
        , limit: 8
      },
      cols: [[
        { field: 'index', title: '序号', width: 50, type: 'numbers' }
        , { field: 'grade_id', title:'grade_id', hide:true}
        , { field: 'name', minWidth: 100, title: '姓名', align: 'center' }
        , { field: 'title', minWidth: 200, title: '标题', align: 'center' }
        , { field: 'grade_rank', minWidth: 90, title: '成绩', align: 'center', fixed: 'right' }
      ]],
      parseData: function (res) {
        return {
          "code": 0,
          "msg": res.header.message,
          "count": res.body.count,
          "data": res.body.grades
        };
      },
      done: function (res, curr, count) { }
    });
  });

  $("#historySearch").click(function(){
    table.render({
      elem: "#historyTbl",
      skin: 'line',
      url: 'http://127.0.0.1:8090/api/getAssessBatchByTitle',
      method: 'POST',
      contentType: "application/json;charset=utf-8",
      dataType: "json",
      where: {
        'user_id': parseInt(storage.getItem("user_id")),
        'title': $("#historySearchInput").val()
      },
      page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
        layout: ['count', 'prev', 'page', 'next'] //自定义分页布局
        , groups: 3 //只显示 1 个连续页码
        , first: true //不显示首页
        , last: true //不显示尾页
        , limit: 8
      },
      cols: [[
        { field:'index', title: '序号', width: 50, type: 'numbers' }
        , { field: 'assess_batch_id', title:'id', hide:true}
        , { field: 'title', minWidth: 150, title: '标题', align: 'center' }
        , { fixed: 'right', title: '操作', minWidth: 70, templet: '#more', align: 'center' }
      ]],
      parseData: function (res) {
        return {
          "code": 0,
          "msg": res.header.message,
          "count": res.body.count,
          "data": res.body.assess_batches
        };
      },
      done: function (res, curr, count) { }
    });
  });
  nowTbl();
  historyTbl();
  $("#latest").click(function(){
    nowTbl();
    $("#latestSearchInput").val("");
  });
  $("#history").click(function(){
    historyTbl();
    $("#historySearchInput").val("");
  });

  table.on('tool(historyTbl)', function (obj) {
    var data = obj.data;
    if (obj.event === 'detail') {
      table.render({
        elem: "#detailHistoryTbl",
        skin: 'line',
        url: 'http://127.0.0.1:8090/api/getUserGradeByAssessBatchId',
        method: 'POST',
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        where: {
          'assess_batch_id': parseInt(obj.data.assess_batch_id)
        },
        page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
          layout: ['count', 'prev', 'page', 'next'] //自定义分页布局
          , groups: 3 //只显示 1 个连续页码
          , first: true //不显示首页
          , last: true //不显示尾页
          , limit: 8
        },
        cols: [[
          { field: 'index', title: '序号', width: 50, type: 'numbers' }
          , { field: 'name', minWidth: 100, title: '姓名', align: 'center' }
          , { field: 'grade_rank', minWidth: 90, title: '成绩', align: 'center', fixed: 'right' }
        ]],
        parseData: function (res) {
          return {
            "code": 0,
            "msg": res.header.message,
            "count": res.body.count,
            "data": res.body.grades
          };
        },
        done: function (res, curr, count) { }
      });
      layer.open({
        type: 1,
        area: ['320px', '500px'],
        title: '考核 ' + obj.data.title,
        content: $("#detailHistory"),
        shade: 0,
        btn: ['确认', '取消']
        , btn1: function (index, layero) {
          // alert(加入成功);
          layer.closeAll();
        },
        btn2: function (index, layero) {
          layer.closeAll();
        },

      })
      table.resize('detailHistoryTbl');
    }
  });


});
