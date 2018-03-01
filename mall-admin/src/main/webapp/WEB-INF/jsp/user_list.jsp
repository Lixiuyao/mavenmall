<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="../common/header.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <title> - 用户列表</title>
</head>
<body>
  <div class="demoTable">
		  用户名：
		   <div class="layui-inline">
		    <input class="layui-input" name="id" id="SearchName" autocomplete="off">
		  </div>
		    电话号码：
		   <div class="layui-inline">
		    <input class="layui-input" name="id" id="SearchSubtitle" autocomplete="off">
		  </div>
		      邮箱：
		   <div class="layui-inline">
		    <input class="layui-input" name="id" id="SearchSubtitle" autocomplete="off">
		  </div>
	     <button class="layui-btn" data-type="search">搜索</button>
	     <button class="layui-btn layui-btn-danger" data-type="deleteAll">批量删除</button>
	      <button class="layui-btn layui-btn-block" data-type="add">添加用户</button>
	 </div>
	<table class="layui-hide" id="datagrid"></table>
	
	 <table class="layui-hide" id="datagrid" lay-filter="datagrid"></table>
	 <script type="text/html" id="toolbar">
     	<a class="layui-btn layui-btn-primary  layui-btn-xs" lay-event="detail">查看</a>
     	<a class="layui-btn layui-btn-primary  layui-btn-xs" lay-event="edit">编辑</a>
     	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
	</script>
	 <script>
		layui.use('table', function(){
		  var table = layui.table;
		  table.render({
		    elem: '#datagrid', //要渲染哪个表格
		    url:'${ctx}/manager/user/pageList.action', //异步数据接口
		    cellMinWidth: 50, //列宽自动分配，全局定义常规单元格的最小宽度
		    cols: [[
		            {type:'checkbox'},
		      {field:'id', title: 'ID', sort: true},
		      {field:'username', title: '用户名', sort: true},
		      {field:'password', title: '密码', sort: true},
		      {field:'email', title: '邮箱', sort: true},
		      {field:'phone', title: '电话', sort: true},
		      {field:'question', title: '提示问题', sort: true},
		      {field:'answer', title: '答案', sort: true},
		      {field:'role', title: '角色', sort: true},
		    ]],
		    page: true,
		    id:"listReload" //设定容器唯一ID，id值是对表格的数据操作方法上是必要的传递条件，它是表格容器的索引
		  });
		});
	</script> 
	
</body>
</html>