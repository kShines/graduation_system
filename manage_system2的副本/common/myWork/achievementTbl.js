layui.use(["table", "element", "form", "laydate", "upload", "util"], function () {
    var form = layui.form
        , table = layui.table
        , element = layui.element
        , laydate = layui.laydate
        , util = layui.util
        , $ = layui.jquery
        , classId = 1;
    
    var storage = window.localStorage;
    var nowDate = new Date();
    var getQuarter = function(month){
        if(month == 1 || month == 2 || month == 3){
        return 1;
        } else if(month == 4 || month == 5 || month == 6) {
        return 2;
        } else if(month == 7 || month == 8 || month == 9){
        return 3;
        } else if(month == 10 || month == 11 || month == 12){
        return 4;
        }
    }
    
    var deleteItem = function(achievement_id, index){
        $.ajax({
            url: 'http://localhost:8090/api/deleteAchievementById',
            method: 'POST',
            crossDomain: true,
            xhrFields: {
                withCredentials: true
            },
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            data: JSON.stringify({
                'achievement_id': achievement_id
            }),
            success: function(data) {
            }
        });
    }

    var myAchievement = table.render({
        elem: "#myAchievement",
        url: 'http://127.0.0.1:8090/api/getAllAchievements',
        method: 'POST',
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        where: {
            'user_id': parseInt(storage.getItem("user_id"))
        },
        title: "用户表",
        page: {
            layout: ['count', 'prev', 'page', 'next']
            , groups: 3
            , first: true
            , last: true
          },
        cols: [[
            { title: '序号', width: 50, type: 'numbers' }
            , { field:'id',title:'achievement_id' ,hide:true}
            , { field: 'title', minWidth: 180, title: '总结标题', event:'detail',align: 'center' }
            , { fixed: 'right', title: '操作', width: 70, templet: '#myachOpt', align: 'center' }
        ]],
        parseData: function(res){ 
            return {
                "code": 0, 
                "msg": res.header.message, 
                "count": res.body.count,
                "data": res.body.achievements
            };
        },
        
        done: function (res, curr, count) { }
    });

    var otherAchievement = table.render({
        elem: "#otherAchievement",
        skin: 'line',
        url: 'http://127.0.0.1:8090/api/getAchievementByGroupId',
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
            {  field:'index', title: '序号', width: 50, type: 'numbers' }
            , { field:'id',title:'achievement_id' ,hide:true}
            , { field: 'name', title: '姓名', width: 80, align: 'center' }
            , { field: 'title', minWidth: 180, title: '晒绩标题', align: 'center' }
           
        ]],
        parseData: function(res){
            return {
                "code": 0, 
                "msg": res.header.message, 
                "count": res.body.count,
                "data": res.body.achievements
            };
        },
        done: function (res, curr, count) { }
    });


    table.on('tool(myAchievement)', function(obj){
        var data = obj.data;
        if(obj.event === 'del'){
          layer.confirm('真的删除行么', function(index){
            deleteItem(obj.data.achievement_id, index);
            obj.del();
            layer.close(index);
          });
        } 
        else if (obj.event === 'detail') {
            storage.setItem("achievement_id", obj.data.achievement_id);
            storage.setItem("creator_name", obj.data.name);
            window.location.href = "/common/myWork/editWork/otherAchieve.html";
          }
      });
    

      table.on('row(otherAchievement)', function(obj){
        var data = obj.data;
        
        // layer.alert(JSON.stringify(data), {
        //   title: '当前行数据：'
        // });
        console.log(obj.data);
        storage.setItem("achievement_id", obj.data.achievement_id);
        window.location.href="/common/myWork/editWork/otherAchieve.html";
        obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
      });
   
      $("#achievement_search_button").click(function(){
        table.render({
          elem: "#otherAchievement",
          skin: 'line',
          url: 'http://127.0.0.1:8090/api/getAchievementByName',
          method: 'POST',
          contentType: "application/json;charset=utf-8",
          dataType: "json",
          where: {
            'user_id': parseInt(storage.getItem("user_id")),
            'name': $("#achievement_search").val()
          },
          page: {
            layout: ['count', 'prev', 'page', 'next']
            , groups: 3
            , first: true
            , last: true
          },
          cols: [[
            { field: 'index', title: '序号', width: 50, type: 'numbers' }
            , { field: 'achievement_id', title: 'achievement_id', hide: true }
            , { field: 'name', title: '姓名', width: 100, align: 'center' }
            , { field: 'title', title: '计划标题', minWidth: 180, align: 'center' }
          ]],
          parseData: function (res) {
            return {
              "code": 0,
              "msg": res.header.message,
              "count": res.body.count,
              "data": res.body.achievements
            };
          },
          done: function (res, curr, count) { }
        });
    });


});