<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../common/header.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>商品添加</title>
</head>
<body>
	<div>
		<form  id="form_add" class="layui-form layui-form-pane" action="" method="post" enctype="multipart/form-data">
			<div class="layui-form-item">
				<label class="layui-form-label">商品名称</label>
				<div class="layui-input-block">
					<input type="text" name="name" required lay-verify="required"
						placeholder="请输入商品名称" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">商品副标题</label>
				<div class="layui-input-block">
					<input type="text" name="subtitle" required lay-verify="required"
						placeholder="请输入商品副标题" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">商品分类</label>
				<div class="layui-input-inline">
					<select name="quiz1" id="topCategory" lay-filter="topCategoryFilter">
						<option value="">请选择一级分类</option>
					</select>
				</div>
				<div class="layui-input-inline">
					<select name="categoryId" id="secondCategory">
						<option value="">请选择二级分类</option>
					</select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">商品价格</label>
				<div class="layui-input-block">
					<input type="text" name="price" placeholder="请输入商品价格"
						autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">商品库存</label>
				<div class="layui-input-block">
					<input type="text" name="stock" placeholder="请输入商品库存"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item" pane="">
				<label class="layui-form-label">商品状态</label>
				<div class="layui-input-block">
					<input type="radio" name="status" value="1" title="上架" checked="">
					<input type="radio" name="status" value="2" title="下架">
				</div>
			</div>
			<div class="layui-form-item" pane="">
				<label class="layui-form-label">商品主图</label>
				<div class="layui-input-block">
					<input type="hidden" id="mainImage" name="mainImage"/>
					<img alt="" src="" id="imgId" width="100" height="100"/></br>
					<input type="file" name="pictureFile" onchange="uploadPic()"/>
				</div>
			</div>
			<div class="layui-form-item" pane="">
				<label class="layui-form-label">商品图片</label>
				<div class="layui-input-block">				
					<a href="javascript:void(0)" id="multPicUpload" class="multPicUpload">上传图片</a>
					<input type="hidden" id="subImages" name="subImages" />
					<div id="subImagesDiv"></div>
				</div>
			</div>
			<div class="layui-form-item layui-form-text">
				<label class="layui-form-label">文本域</label>
				<div class="layui-input-block">
					<textarea name="detail" placeholder="请输入内容" class="layui-textarea"></textarea>
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
	<script type="text/javascript" src="${ctx}/static/lib/kindeditor/kindeditor-all-min.js"></script>
	<script type="text/javascript">
		layui.use([ 'form' ], function() {
			var form = layui.form;
			form.render('select');//刷新select选择框渲染，不然不显示
			form.on('select(topCategoryFilter)',function(data){
				console.log(data.elem); //得到select原始DOM对象
				console.log(data.value); //得到被选中的值
				console.log(data.othis); //得到美化后的DOM对象
				$.ajax({
					url:'${ctx}/catergory/selectSecondCategory.action',
					data : 'topCategoryId=' + data.value,
					dataType:'json',
					type:'POST',
					success:function(data){
						console.log(data.code);
						if(data.code==util.SUCCESS){
							 var html ='<option value="">请选二级分类</option>';
							 var data = data.data;
							 for(var i=0;i<data.length;i++){
								 html +='<option value="'+data[i].id+'">'+data[i].name+'</option>';
							 }
							 $('#secondCategory').html(html);
							 form.render('select'); //刷新select选择框渲染,不然不显示
						}else{
							mylayer.errorMsg(jsonObj.msg);
						}
					}
				})
			})
		});
		

		$(function(){
			//加载一级菜单
			$.ajax({
				url:"${ctx}/catergory/selectTopCategory.action",
				dataType:'json',
				success:function(data){
					console.log(data);
					if(data.code==util.SUCCESS){
						 var html ='<option value="">请选一级分类</option>';
						 var data = data.data;
						 for(var i=0;i<data.length;i++){
							 html +='<option value="'+data[i].id+'">'+data[i].name+'</option>';
						 }
						 $('#topCategory').html(html);
					}else{
						
					}
				}
			})
		});
		
		//图片上传
		function uploadPic(){
			//if($('#inputFile').val()==$('#inputFile').defaultValue
			//		||$('#inputFile').val()==""){
			//	alert("取消选择");
			//	return;
			//}
			$('#form_add').ajaxSubmit({
				url:'${ctx}/upload/uploadPic.action',
				type:'POST',
				dataType:'json',
				success:function(data){
					$('#imgId').attr('src',data.url);
					$('#mainImage').val(data.fileName);
				}
			});
		};
		function submitForm(){
			$.ajax({
				url:'${ctx}/product/add.action',
				data:$('#form_add').serialize(),
				type:'POST',
				dataType:'json',
				success : function(jsonObj) {
					if(jsonObj.code == util.SUCCESS) {
						
						mylayer.confirm("添加成功，是够跳转到商品列表界面？", "${ctx}/product/getproduct.action");
					} else {
						mylayer.errorMsg(jsonObj.msg);
					}
				}
				})
		};
		
		
		
		
		
		
		
		var myKindEditor ;
        KindEditor.ready(function(K) {
            var kingEditorParams = {
            		  //指定上传文件参数名称
                    filePostName  : "pictureFile",
                    //指定上传文件请求的url。
                    uploadJson : '${ctx}/upload/multPicUpload.action',
                    //上传类型，分别为image、flash、media、file
                    dir : "image",
                    afterBlur: function () { this.sync(); }

                 
                    }
                    var editor = K.editor(kingEditorParams);
                    //多图片上传
                    K('#multPicUpload').click(function() {
                        editor.loadPlugin('multiimage', function() {
                            editor.plugin.multiImageDialog({
                                 clickFn : function(urlList) {
                                     console.log(urlList);
                                     var div = K('#subImagesDiv');
                                     var imgArray = [];
                                     div.html('');
                                     K.each(urlList, function(i, data) {
                                         imgArray.push(data.fileName);
                                         div.append('<img src="' + data.url + '" width="80" height="50">');
                                     });
                                     $("#subImages").val(imgArray.join(","));
                                     editor.hideDialog();
                                 }
                            });
                        });
                    });

            //富文本编辑器
            myKindEditor = KindEditor.create('#form_add[name=detail]', kingEditorParams);
        }); 

	</script>
</body>
</html>