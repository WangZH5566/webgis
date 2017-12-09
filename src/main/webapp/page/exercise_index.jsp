<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/page/comm/tags.jsp"%>
<html lang='en'>
<head>
  <meta charset='UTF-8'>
  <meta http-equiv='X-UA-Compatible' content='IE=edge'>
  <meta name='renderer' content='webkit' />
  <meta name='viewport' content='initial-scale=1,minimum-scale=1' />
  <%@ include file="/page/comm/css.jsp"%>
</head>
<body>
<div class='page'>
  <div class='main-header'>
      <nav class='navbar navbar-static-top'>
          <ul class='nav navbar-nav'>
              <li class='active'><a href='${ctx}/page/exercise_index.jsp'>推演管理</a></li>
              <li><a href='${ctx}/page/telegram_index.jsp'>电文模板</a></li>
              <li><a href='${ctx}/page/chart_index.jsp'>海图编辑</a></li>
              <li><a href='${ctx}/page/icon_index.jsp'>图标管理</a></li>
          </ul>

          <ul class='nav navbar-nav pull-right'>
              <li><a href='javascript:void(0);'>欢迎您，管理员</a></li>
              <li><a href='javascript:void(0);' onclick="javascript:window.location='${ctx}/page/user_login.jsp';">登出</a></li>
          </ul>
      </nav>
  </div>
  <div class="row">
    <div class='box box-success'>
      <div class='box-header'>
        <h3 class='box-title' id='table01_title'></h3>
        <div class='box-tools pull-right' id='paginator11-1'></div>
      </div>
      <div class='box-body'>
        <table id='list01'></table>
      </div>
    </div>
  </div>
</div>
  <%@ include file="/page/comm/js.jsp"%>
  <script>
    $(document).ready(function(){

      //列表处理
      var $mmGrid = $('#list01').mmGrid({
        url: '${ctx}/data/e_list.json?' + new Date().getTime(),
        cols: [
          {title: '推演名称', name: 'name', width: 25},
          {title: '创建时间', name: 'createTime', width: 100},
          {title: '开始时间', name: 'startTime', width: 100},
          {title: '结束时间', name: 'endTime', width: 100},
          {title: '参与人员', name: 'attendee', width: 300, renderder:function(val,item,rowIndex){
              if(val == undefined) {
                  return;
              }
              debugger;
              var attendee = new Array();
              for(var i = 0; i<val.length; i++) {
                  attendee.push(val[i].username);
              }
              return attendee.join(',');
          }}
        ],
        method: 'post',
        params: {},
        indexCol: true,
        indexColWidth: 25,
        showBackboard: false,
        fullWidthRows: true,
        height: '355px',
        plugins: [
          $('#paginator11-1').mmPaginator({
            totalCountName: 'total',
            pageParamName: 'pageNum',
            limitParamName: "pageSize"
          })
        ]
      });
    });
  </script>
</body>
</html>
