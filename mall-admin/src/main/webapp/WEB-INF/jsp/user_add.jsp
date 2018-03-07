<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../common/header.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>用户添加</title>
</head>
<body>
	<div>
		<form  id="form_add" class="layui-form layui-form-pane" action="" method="post" enctype="multipart/form-data">
			<div class="layui-form-item">
				<label class="layui-form-label">用户名</label>
				<div class="layui-input-block">
					<input type="text" name="username" required lay-verify="required"
						placeholder="请输入用户名" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">密码</label>
				<div class="layui-input-block">
					<input type="text" name="password" required lay-verify="required"
						placeholder="请输入用户密码" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">邮箱</label>
				<div class="layui-input-block">
					<input type="text" name="email" required lay-verify="required"
						placeholder="请输入用户email" autocomplete="off" class="layui-input">
				</div>
			</div>
		
			<div class="layui-form-item">
				<label class="layui-form-label">电话号码</label>
				<div class="layui-input-block">
					<input type="text" name="phone" placeholder="请输入电话号码"
						autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">问题</label>
				<div class="layui-input-block">
					<input type="text" name="question" placeholder="请输入问题"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">答案</label>
				<div class="layui-input-block">
					<input type="text" name="answer" placeholder="请输入答案"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item" pane="">
				<label class="layui-form-label">用户类型</label>
				<div class="layui-input-block">
					<input type="radio" name="role" value="1" title="管理员" checked="">
					<input type="radio" name="role" value="0" title="屌丝用户">
					<input type="radio" name="role" value="" title="BOSS" disabled>
				</div>
			</div>
	
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button type="button" class="layui-btn" onclick="submitForm()">立即提交</button>
					<button type="reset" class="layui-btn layui-btn-primary">重置</button>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript" src="${ctx}/static/lib/jquery/jquery.form.js"></script>
	<script type="text/javascript">
	    layui.use([ 'form' ], function() {
		  var form = layui.form;})
		function submitForm(){
			$.ajax({
				url:'${ctx}/user/add.action',
				data:$('#form_add').serialize(),
				type:'POST',
				dataType:'json',
				success : function(jsonObj) {
					console.log(jsonObj.code)
					if(jsonObj.code == util.SUCCESS) {
						
						mylayer.confirm("添加成功，是够跳转到用户列表界面？", "${ctx}/user/getUserpage.action");
					} else {
						mylayer.errorMsg(jsonObj.msg);
					}
				}
				})
		};


	</script>
</body>
</html>