<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
	<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
	%>
	<base href="<%=basePath%>"/>
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

	<script type="text/javascript">

		/*---加载activity主窗口---*/
		$(function(){

			//页面加载之后直接执行刷新页面
			pageList(1,5);

			//主页面调用时间日期插件方法
			dateTime();



			//点击打开添加模态窗口
			$("#addBtn").click(function (){
				$.ajax({
					url:"workbench/activity/getUserList.do",
					type:"get",
					data:{},
					dataType:"json",
					success:function(data){
						var html = "<option>-</option>";
						var userID = "${sessionScope.user.id}";

						$.each(data, function(i, e){
							if(e.id == userID){
								html+= "<option value='" + e.id + "' selected>" + e.name + "</option>";
							}else{
								html+= "<option value='" + e.id + "'>" + e.name + "</option>";
							}
						})

						$("#create-owner").html(html);


						$("#createActivityModal").modal("show");

					}
				})
				//时间日期插件方法调用
				dateTime();
			})



			//点击打开修改模态窗口
			$("#editBtn").click(function (){

				var $xz = $(".xz:checked");

				if($xz.length==0){
					alert("请选择要修改的活动！");

				}else if($xz.length==1){
					var id = $xz.val();
					$.ajax({
						url:"workbench/activity/getActivityUserListByid.do",
						type:"get",
						data:{"id":id},
						dataType:"json",
						success:function(data){
							var html = "<option>-</option>";


							$.each(data.userList, function(i, e){
								if(e.id == data.activity.owner){
									html+= "<option value='" + e.id + "' selected>" + e.name + "</option>";
								}else{
									html+= "<option value='" + e.id + "'>" + e.name + "</option>";
								}
							})

							$("#edit-marketActivityOwner").html(html);

							$("#edit-id").val(data.activity.id);
							$("#edit-marketActivityName").val(data.activity.name);
							$("#edit-startTime").val(data.activity.startDate);
							$("#edit-endTime").val(data.activity.endDate);
							$("#edit-cost").val(data.activity.cost);
							$("#edit-describe").val(data.activity.description);


							$("#editActivityModal").modal("show");

						}
					})
				}else{
					alert("请注意，每次只能选择一个活动进行修改！");
				}

				//时间日期插件方法调用
				dateTime();
			})



			//点击创建活动信息
			$("#saveActivity").click(function(){
					$.ajax({
						url:"workbench/activity/saveActivity.do",
						type:"post",
						data:{
							"owner":$.trim($("#create-owner").val()),
							"name":$.trim($("#create-name").val()),
							"startDate":$.trim($("#create-startDate").val()),
							"endDate":$.trim($("#create-endDate").val()),
							"cost":$.trim($("#create-cost").val()),
							"description":$.trim($("#create-description").val())
						},
						dataType:"json",
						success:function(data){
							if(data.success){
								alert("恭喜您，市场活动保存成功！");
								$("#createActivityModal").modal("hide");
								$("#createActivity")[0].reset();
								pageList(1,5);
							}else{
								alert("对不起，活动信息添加失败！");
							}
						}
					})
			})



			//点击删除指定活动信息
			$("#deleteBtn").click(function(){

				if($(".xz:checked").length == 0){
					alert("请选择需要删除的活动！");
				}else{
					//拼接获取id值
					var id = '';

					for(var i=0; i < $(".xz:checked").length; i++){

						id += $($(".xz:checked")[i]).val();
						if(i != $(".xz:checked").length -1){
							id += "&id=";
						}
					}

					if(confirm("确认删除？")){
						$.ajax({
							url:"workbench/activity/deleteList.do",
							type:"get",
							data:{"id":id},
							dataType:"json",
							success:function(data){
								if(data.success){
									alert("恭喜您，数据信息删除成功！");
									pageList(1,5);
								}else{
									alert("对不起，数据信息删除失败！");
								}
							}
						})
					}
				}

			})



			//点击更新指定活动信息
			$("#updateBtn").click(function(){
				$.ajax({
					url:"workbench/activity/updateActivity.do",
					type:"post",
					data:{
						"id":$("#edit-id").val(),
						"owner":$.trim($("#edit-marketActivityOwner").val()),
						"name":$.trim($("#edit-marketActivityName").val()),
						"startDate":$.trim($("#edit-startTime").val()),
						"endDate":$.trim($("#edit-endTime").val()),
						"cost":$.trim($("#edit-cost").val()),
						"description":$.trim($("#edit-describe").val())
					},
					dataType:"json",
					success:function(data){
						if(data.success){
							alert("恭喜您，活动信息修改成功！");
							$("#editActivityModal").modal("hide");
							//$("#createActivity")[0].reset();
							//pageList(1,5);
							pageList($("#activityPage").bs_pagination('getOption', 'currentPage'),$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

						}else{
							alert("对不起，活动信息修改失败！");
						}
					}
				})
			})



			//点击条件查询并刷新页面
			$("#select-Btn").click(function(){
				$("#hid-name").val($.trim($("#select-name").val()));
				$("#hid-owner").val($.trim($("#select-owner").val()));
				$("#hid-startTime").val($.trim($("#startTime").val()));
				$("#hid-endTime").val($.trim($("#endTime").val()));

				pageList(1,5);
			})



			//点击全选与全不选
			$("#checkedAll").click(function(){
				$(".xz").prop("checked", this.checked);
			})

			$("#activityBody").on('click', $(".xz"), function(){
				$("#checkedAll").prop("checked", $(".xz").length==$(".xz:checked").length);
			})






		});


		/*---自定义时间插件方法---*/
		function dateTime(){
			/*时间插件*/
			$(".time").datetimepicker({
				minView: "month",
				language:  'zh-CN',
				format: 'yyyy-mm-dd',
				autoclose: true,
				todayBtn: true,
				pickerPosition: "bottom-left"
			});
		}


		/*---自定义页面加载数据列表的方法---*/
		function pageList(pageNo, pageSize){
			$.ajax({
				url:"workbench/activity/pageList.do",
				type:"get",
				data:{
					name:$("#hid-name").val(),
					owner:$("#hid-owner").val(),
					startDate:$("#hid-startTime").val(),
					endDate:$("#hid-endTime").val(),
					pageNo:pageNo,
					pageSize:pageSize
				},
				dataType:"json",
				success:function(data){
					var html = "";
					/*循环获取数据列表信息*/
					$.each(data.dataList, function(i,e){
						html+='<tr class="active">';
						html+='	<td><input type="checkbox" value="'+ e.id +'"  class="xz" /></td>';
						html+='	<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/activity/detail.do?id='+ e.id +'\';">'+ e.name +'</a></td>';
						html+='	<td>' + e.owner + '</td>';
						html+='	<td>'+ e.startDate +'</td>';
						html+='	<td>'+ e.endDate +'</td>';
						html+='</tr>';
					})

					$("#activityBody").html(html);

					var totalPages = Math.ceil(parseInt(data.total)/parseInt(pageSize));
					$("#activityPage").bs_pagination({
						currentPage: pageNo,			// 页码
						rowsPerPage: pageSize, 			// 每页显示的记录条数
						maxRowsPerPage: 20, 			// 每页最多显示的记录条数
						totalPages: totalPages, 		// 总页数
						totalRows: data.total, 			// 总记录条数
						visiblePageLinks: 3, 			// 显示几个卡片

						showGoToPage: true,
						showRowsPerPage: true,
						showRowsInfo: true,
						showRowsDefaultInfo: true,

						onChangePage : function(event, data){
							pageList(data.currentPage , data.rowsPerPage);
						}
					});
				}
			})

			//默认全选按钮不选
			$("#checkedAll").prop("checked", false);
		}



	</script>
</head>
<body>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" id="createActivity" role="form">

						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 290px;">
								<select class="form-control" id="create-owner">

								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 290px;">
                                <input type="text" class="form-control" id="create-name">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 290px;">
								<input type="text" class="form-control time" id="create-startDate">
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 290px;">
								<input type="text" class="form-control time" id="create-endDate">
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 290px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveActivity">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">

					<form class="form-horizontal" role="form">
						<input type="hidden" id="edit-id">
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 290px;">
								<select class="form-control" id="edit-marketActivityOwner">

								<%--
								  <option>zhangsan</option>
								  <option>lisi</option>
								  <option>wangwu</option>
								--%>

								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 290px;">
                                <input type="text" class="form-control" id="edit-marketActivityName" value="发传单">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 290px;">
								<input type="text" class="form-control time" id="edit-startTime" value="2020-10-10">
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 290px;">
								<input type="text" class="form-control time" id="edit-endTime" value="2020-10-20">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 290px;">
								<input type="text" class="form-control" id="edit-cost" value="5,000">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label" >描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe" >市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="updateBtn">更新</button>
				</div>
			</div>
		</div>
	</div>
	

	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>



	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">

<%--条件查询列表信息--%>
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text"  id="select-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon" >所有者</div>
				      <input class="form-control" type="text" id="select-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon " >开始日期</div>
					  <input class="form-control time" type="text" id="startTime" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon " >结束日期</div>
					  <input class="form-control time" type="text" id="endTime">
				    </div>
				  </div>
				  
				  <button type="button" id="select-Btn" class="btn btn-default">查询</button>

					<input type="hidden" id="hid-name">
					<input type="hidden" id="hid-owner">
					<input type="hidden" id="hid-startTime">
					<input type="hidden" id="hid-endTime">

				</form>
			</div>

			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="addBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>



<%--展示数据列表--%>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="checkedAll" /></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="activityBody">

					<%--
						<tr class="active">
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
                            <td>2020-10-10</td>
                            <td>2020-10-20</td>
                        </tr>
                     --%>

					</tbody>
				</table>
			</div>


<%--分页插件使用--%>
			<div style="height: 50px; position: relative;top: 30px;">
				<div id="activityPage"></div>
			</div>

			<%--<div style="height: 50px; position: relative;top: 30px;">
				<div>
					<button type="button" class="btn btn-default" style="cursor: default;">共<b>50</b>条记录</button>
				</div>
				<div class="btn-group" style="position: relative;top: -34px; left: 110px;">
					<button type="button" class="btn btn-default" style="cursor: default;">显示</button>
					<div class="btn-group">
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							10
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">20</a></li>
							<li><a href="#">30</a></li>
						</ul>
					</div>
					<button type="button" class="btn btn-default" style="cursor: default;">条/页</button>
				</div>
				<div style="position: relative;top: -88px; left: 285px;">
					<nav>
						<ul class="pagination">
							<li class="disabled"><a href="#">首页</a></li>
							<li class="disabled"><a href="#">上一页</a></li>
							<li class="active"><a href="#">1</a></li>
							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">4</a></li>
							<li><a href="#">5</a></li>
							<li><a href="#">下一页</a></li>
							<li class="disabled"><a href="#">末页</a></li>
						</ul>
					</nav>
				</div>
			</div>--%>
			
		</div>
		
	</div>
</body>
</html>