<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../common/header.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>二级分类</title>
</head>
<body>
	<label class="layui-form-label">二级分类</label>
	<c:forEach items="${list}" var="second" >
		
		<input type="hidden" name="id" value="${second.id}" />
			<div class="layui-form-item">
			
				
				<div class="layui-input-block">
					<input type="text" name="name" required lay-verify="required"
						value="${second.name}" autocomplete="off" class="layui-input">
				</div>
			</div>
	
	</c:forEach>

	<div>
	</div>
	<script type="text/javascript" src="${ctx}/static/lib/jquery/jquery.form.js"></script>
</body>
</html>