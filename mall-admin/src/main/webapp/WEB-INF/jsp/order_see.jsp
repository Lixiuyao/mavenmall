<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="../common/header.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <title> -订单详情</title>
</head>
<body>

	<table class="layui-hide" id="datagrid" lay-filter="datagrid"></table>
		
	 <script>
		layui.use('table', function(){
		  var table = layui.table;
		  table.render({
		    elem: '#datagrid', //要渲染哪个表格
		    url:'${ctx}/order/OrderItemm.action?orderNo=${orderNo}', //异步数据接口
		    cellMinWidth: 50, //列宽自动分配，全局定义常规单元格的最小宽度
		    cols: [[
		      {field:'productName', title: '商品信息', sort: true},
		      {field:'productImage', title: '商品主图', templet: '#imgTpl'},
		      {field:'currentUnitPrice', title: '单价', sort: true},
		      {field:'quantity', title: '数量', sort: true},
		    ]],
		    id:"listReload" //设定容器唯一ID，id值是对表格的数据操作方法上是必要的传递条件，它是表格容器的索引
		  });
		  var $=layui.$;
		  active = {
				  //这里的表格重载是指对表格重新进行渲染，包括数据请求和基础参数的读取
				    search: function(){
				      //执行重载
				      table.reload('listReload', {
			    	  where: {
			    		  username: $('#SearchName').val(),
				          phone: $('#SearchPhone').val(),
				          email: $('#SearchEmail').val()
				        },
				       // page: {
				       //   curr: 1 //重新从第 1 页开始
				       // },
				      });
				    },
				    deleteAll:function(){
				    	 var checkStatus = table.checkStatus('listReload');
				         var data = checkStatus.data;
				         console.log(checkStatus.data) //获取选中行的数据
					     console.log(checkStatus.data.length) //获取选中行数量，可作为是否有选中行的条件
					     console.log(checkStatus.isAll) //表格是否全选
					     layer.confirm('真的删除'+data.length+'这条数据吗', function(index){
					    	 var ids =util.getSelectedIds(data);
					    	 $.ajax({
					    		 url:'${ctx}/user/deleteAll.action',
					    		 data:{'ids':ids},
					    		 dataType:'json',
					    		 success:function(data){
					    			 if(data.code == util.SUCCESS) {
					       					mylayer.success("删除成功");
					       					active.search();
					       				} else {
					       					mylayer.errorMsg("删除失败");
					       				} 
					    		 }
					    		 
					    	 });
					     });
				    },
				    add:function(){  
				    	if($('#Category').val()=="one"){   
			   		 		 location.href = "${ctx}/category/getAdd.action"
			   			}else if($('#Category').val()=="two"){   
							location.href = "${ctx}/category/getAddTwo.action"
						}else{
							mylayer.errorMsg("请先选择");
						}  
				    
				    }
				  };
				//监听工具条
				  table.on('tool(datagrid)', function(obj){ //注：tool是工具条事件名，datagrid是table原始容器的属性 lay-filter="对应的值"
				    var data = obj.data; //获得当前行数据
				    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
				    var tr = obj.tr; //获得当前行 tr 的DOM对象
				   
				    if(layEvent === 'detail'){ //查看
				    	//编辑
					      layer.msg("查看："+data.orderNo);
					      layer.open({
					    	  type:2,
					    	  title:'订单详情',
					    	  area:['500px','388px'],
					    	  offset : '10px', //只定义top坐标，水平保持居中
					    	  content : '${ctx}/order/toOrderItemm.action?id=' + data.orderNo
					      });
				    } else if(layEvent === 'del'){ //删除
				        layer.confirm('真的删除该商品么', function(index){
				    		$.ajax({
				    			url:'${ctx}/user/deleteById.action',
				    			data:{'id':data.id},
				    			dataType:'json',
				    			success : function(data) {
				       				if(data.code == util.SUCCESS) {
				       					mylayer.success("删除成功");
				       					active.search();
				       				} else {
				       					mylayer.errorMsg("删除失败");
				       				} 
				       				layer.close(index);
				       			}
				    		});
				    	  
				        });
				    } else if(layEvent === 'edit'){ 
				    	//编辑
				      layer.msg("查看："+data.id);
				      layer.open({
				    	  type:2,
				    	  title:'商品编辑',
				    	  area:['500px','388px'],
				    	  offset : '10px', //只定义top坐标，水平保持居中
				    	  content : '${ctx}/category/updatePage.action?id=' + data.id
				      });
				    }
				  });
	
				  //按钮触发事件
				  $('.demoTable .layui-btn').on('click', function(){
				    var type = $(this).data('type');
				    active[type] ? active[type].call(this) : '';
				  });
				});
		</script> 
		<script type="text/html" id="imgTpl">
  			<img src="/pic/{{d.productImage}}">
		</script>
		<script type="text/html" id="rolrTpl">
  			{{#       if(d.role ==1 ){  }}
				   	  <button class="layui-btn layui-btn-danger" >管理员</button>
			{{#       }else{			  }}
					  <button class="layui-btn " >屌丝用户</button>
			{{#		  }					  }}
		</script>
</body>
</html>