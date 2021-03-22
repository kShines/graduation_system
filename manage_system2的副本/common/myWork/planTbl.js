
var storage = window.localStorage;

layui.use(["table", "element", "form", "laydate", "upload", "util"], function () {
  var form = layui.form
    , table = layui.table
    , element = layui.element
    , laydate = layui.laydate
    , util = layui.util
    , $ = layui.jquery
    , classId = 1;

  var nowDate = new Date();
  var getQuarter = function (month) {
    if (month == 1 || month == 2 || month == 3) {
      return 1;
    } else if (month == 4 || month == 5 || month == 6) {
      return 2;
    } else if (month == 7 || month == 8 || month == 9) {
      return 3;
    } else if (month == 10 || month == 11 || month == 12) {
      return 4;
    }
  }

  var deleteItem = function (plan_id, index) {
    $.ajax({
      url: 'http://localhost:8090/api/deletePlanById',
      method: 'POST',
      crossDomain: true,
      xhrFields: {
        withCredentials: true
      },
      contentType: "application/json;charset=utf-8",
      dataType: "json",
      data: JSON.stringify({
        'plan_id': plan_id
      }),
      success: function (data) {
      }
    });
  }

  var allPlans = table.render({
    elem: '#allPlans',
    url: 'http://127.0.0.1:8090/api/getAllPlans',
    method: 'POST',
    contentType: "application/json;charset=utf-8",
    dataType: "json",
    where: {
      'user_id': parseInt(storage.getItem("user_id"))
    },
    page: {
      layout: ['count', 'prev', 'page', 'next']
      , groups: 3
      , first: true
      , last: true
    },
    title: "用户表",
    cols: [[
      { field: 'index', title: '序号', width: 50, type: 'numbers' }
      , { field: 'id', title: 'plan_id', hide: true }
      , { field: 'name', hide: true}
      , { field: 'title', minWidth: 180, title: '计划标题', event: 'detail', align: 'center' }
      , { fixed: 'right', title: '操作', width: 70, templet: '#thisplanOpt', align: 'center' }
    ]],
    parseData: function (res) {
      return {
        "code": 0,
        "msg": res.header.message,
        "count": res.body.count,
        "data": res.body.plans
      };
    },
    done: function (res, curr, count) { }
  });


  var thisQuarterPlan = table.render({
    elem: "#thisQuarterPlan",
    skin: 'line',
    url: 'http://127.0.0.1:8090/api/getPlansByQuarter',
    method: 'POST',
    contentType: "application/json;charset=utf-8",
    dataType: "json",
    where: {
      'user_id': parseInt(storage.getItem("user_id")),
      'year': parseInt(nowDate.getFullYear()),
      'quarter': getQuarter(parseInt(nowDate.getMonth()))
    },
    page: {
      layout: ['count', 'prev', 'page', 'next']
      , groups: 3
      , first: true
      , last: true
    },
    cols: [[
      { title: '序号', width: 50, type: 'numbers' }
      , { field: 'id', title: 'plan_id', hide: true }
      , { field: 'name', hide: true}
      , { field: 'title', minWidth: 180, title: '计划标题', event: 'detail', align: 'center' }
      , { fixed: 'right', title: '操作', width: 70, templet: '#thisplanOpt', align: 'center' }
    ]],
    parseData: function (res) {
      return {
        "code": 0,
        "msg": res.header.message,
        "count": res.body.count,
        "data": res.body.plans
      };
    },
    done: function (res, curr, count) { }
  });

  var otherPlan = table.render({
    elem: "#otherPlan",
    skin: 'line',
    url: 'http://127.0.0.1:8090/api/getPlanByGroupId',
    method: 'POST',
    contentType: "application/json;charset=utf-8",
    dataType: "json",
    where: {
      'user_id': parseInt(storage.getItem("user_id")),
    },
    page: {
      layout: ['count', 'prev', 'page', 'next']
      , groups: 3
      , first: true
      , last: true
    },
    cols: [[
      { field: 'index', title: '序号', width: 50, type: 'numbers' }
      , { field: 'plan_id', title: 'plan_id', hide: true }
      , { field: 'name', title: '姓名', width: 100, align: 'center' }
      , { field: 'title', title: '计划标题', minWidth: 180, align: 'center' }
    ]],
    parseData: function (res) {
      return {
        "code": 0,
        "msg": res.header.message,
        "count": res.body.count,
        "data": res.body.plans
      };
    },
    done: function (res, curr, count) { }
  });

  table.on('tool(allPlans)', function (obj) {
    var data = obj.data;
    if (obj.event === 'del') {
      layer.confirm('确定删除该计划吗', function (index) {
        deleteItem(obj.data.plan_id, index);
        obj.del();
        layer.close(index);
      });
    }
    else if (obj.event === 'detail') {
      storage.setItem("plan_id", obj.data.plan_id);
      window.location.href = "/common/myWork/editWork/otherPlan.html";
    }
  });

  table.on('tool(thisQuarterPlan)', function (obj) {
    var data = obj.data;
    if (obj.event === 'del') {
      layer.confirm('确定删除该计划吗', function (index) {
        deleteItem(obj.data.plan_id, index);
        obj.del();
        layer.close(index);
      });
    }
    else if (obj.event === 'detail') {
      storage.setItem("plan_id", obj.data.plan_id);
      storage.setItem("creator_name", obj.data.name);
      window.location.href = "/common/myWork/editWork/otherPlan.html";
    }
  });


  table.on('row(otherPlan)', function (obj) {
    // console.log(obj.data);
    storage.setItem("plan_id", obj.data.plan_id);
    window.location.href = "/common/myWork/editWork/otherPlan.html";
    obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
  });

  $("#plan_search_button").click(function(){
      table.render({
        elem: "#otherPlan",
        skin: 'line',
        url: 'http://127.0.0.1:8090/api/getPlanByName',
        method: 'POST',
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        where: {
          'user_id': parseInt(storage.getItem("user_id")),
          'name': $("#plan_search").val()
        },
        page: {
          layout: ['count', 'prev', 'page', 'next']
          , groups: 3
          , first: true
          , last: true
        },
        cols: [[
          { field: 'index', title: '序号', width: 50, type: 'numbers' }
          , { field: 'plan_id', title: 'plan_id', hide: true }
          , { field: 'name', title: '姓名', width: 100, align: 'center' }
          , { field: 'title', title: '计划标题', minWidth: 180, align: 'center' }
        ]],
        parseData: function (res) {
          return {
            "code": 0,
            "msg": res.header.message,
            "count": res.body.count,
            "data": res.body.plans
          };
        },
        done: function (res, curr, count) { }
      });
  });
});
