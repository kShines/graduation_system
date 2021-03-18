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
        
        var deleteItem = function(summary_id, index){
          $.ajax({
            url: 'http://localhost:8090/api/deleteSummaryById',
            method: 'POST',
            crossDomain: true,
            xhrFields: {
                withCredentials: true
            },
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            data: JSON.stringify({
                'summary_id': summary_id
            }),
            success: function(data) {
            }
          });
        }



        var allSummary = table.render({
            elem: "#allSummary",
            skin: 'line',
            url: 'http://127.0.0.1:8090/api/getAllSummary',
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
                { field:'index', title: '序号', width: 50, type: 'numbers' }
                , { field:'id', title:'summary_id' ,hide:true}
                , { field: 'title', minWidth: 180, title: '总结标题',event: 'detail', align: 'center' }
                , { fixed: 'right', title: '操作', width: 80, templet: '#allsumOpt', align: 'center' }
            ]],
            parseData: function(res){
              return {
                  "code": 0, 
                  "msg": res.header.message, 
                  "count": res.body.count,
                  "data": res.body.summarys
              };
            },
            done: function (res, curr, count) { }
        });

        var thisQuarterSummary = table.render({
            elem: "#thisQuarterSummary",
            skin: 'line',
            url: 'http://127.0.0.1:8090/api/getSummaryByQuarter',
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
              { field:'index', title: '序号', width: 50, type: 'numbers' }
              , {field: 'id', title:'summary_id' ,hide:true}
              , { field: 'title', minWidth: 180, title: '总结标题', event: 'detail',align: 'center' }
              , { fixed: 'right', title: '操作', width: 80, templet: '#thissumOpt', align: 'center' }
            ]],
            parseData: function(res){ 
              return {
                  "code": 0, 
                  "msg": res.header.message, 
                  "count": res.body.count,
                  "data": res.body.summarys 
              };
            },
            
            done: function (res, curr, count) { }
        });

        var otherSummary = table.render({
          elem: "#otherSummary",
          skin: 'line',
          url: 'http://127.0.0.1:8090/api/getSummaryByGroupId',
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
            , { field: 'id', title:'summary_id' ,hide:true}
            , { field: 'name', title: '姓名', width: 80, align: 'center' }
            , { field: 'title', minWidth: 180, title: '总结标题', align: 'center' }
        ]],
          parseData: function(res){
            return {
                "code": 0, 
                "msg": res.header.message, 
                "count": res.body.count,
                "data": res.body.summarys
            };
          },
          
          done: function (res, curr, count) { }
      });

        table.on('tool(allSummary)', function(obj){
            var data = obj.data;
            if(obj.event === 'del'){
              layer.confirm('确定删除该总结吗？', function(index){
                deleteItem(obj.data.summary_id, index);
                obj.del();
                layer.close(index);
              });
            } 
            else if (obj.event === 'detail') {
              console.log(obj.data);
              storage.setItem("summary_id", obj.data.summary_id);
              window.location.href = "/common/myWork/editWork/otherSummary.html";
            }
          });
        
          table.on('tool(thisQuarterSummary)', function(obj){
            var data = obj.data;
            if(obj.event === 'del'){
              layer.confirm('确定删除该总结吗？', function(index){
                deleteItem(obj.data.summary_id, index);
                obj.del();
                layer.close(index);
              });
            } 
            else if (obj.event === 'detail') {
              // console.log(obj.data);
              storage.setItem("summary_id", obj.data.summary_id);
              window.location.href = "/common/myWork/editWork/otherSummary.html";
            }
          });

          table.on('row(otherSummary)', function(obj){
            // layer.alert(JSON.stringify(data), {
            //   title: '当前行数据：'
            // });
            console.log("###???");
            console.log(obj.data.summary_id);
            
            storage.setItem("summary_id", obj.data.summary_id);
            window.location.href="/common/myWork/editWork/otherSummary.html";
            obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
          });
       
          $("#summary_search_button").click(function(){
            table.render({
              elem: "#otherSummary",
              skin: 'line',
              url: 'http://127.0.0.1:8090/api/getSummaryByName',
              method: 'POST',
              contentType: "application/json;charset=utf-8",
              dataType: "json",
              where: {
                'user_id': parseInt(storage.getItem("user_id")),
                'name': $("#summary_search").val()
              },
              page: {
                layout: ['count', 'prev', 'page', 'next']
                , groups: 3
                , first: true
                , last: true
              },
              cols: [[
                { field: 'index', title: '序号', width: 50, type: 'numbers' }
                , { field: 'summary_id', title: 'summary_id', hide: true }
                , { field: 'name', title: '姓名', width: 100, align: 'center' }
                , { field: 'title', title: '总结标题', minWidth: 180, align: 'center' }
              ]],
              parseData: function (res) {
                return {
                  "code": 0,
                  "msg": res.header.message,
                  "count": res.body.count,
                  "data": res.body.summarys
                };
              },
              done: function (res, curr, count) { }
            });
        });
    });