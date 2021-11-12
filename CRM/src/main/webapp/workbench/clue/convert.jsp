<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
	<%--自动获取并设置基地址--%>
	<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
	%>
	<base href="<%=basePath%>"/>
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>


<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

<script type="text/javascript">

/*---打开页面加载完毕后执行...---*/
	$(function(){
		dateTime();
		keydown13();

		/*打开交易页中活动列表选项并打开模态窗口*/
		$("#activitySource").click(function(){
			showAcivityList();
			$("#searchActivityModal").modal("show");
		})

		/*选择活动并关闭模态窗口*/
		$("#submit").click(function(){
			$("#activity").val($(".xz:checked").attr("name"));
			$("#activityID").val($(".xz:checked").val());

			$("#searchActivityModal").modal("hide");
		})


		$("#isCreateTransaction").click(function(){
			if(this.checked){
				$("#create-transaction2").show(200);
			}else{
				$("#create-transaction2").hide(200);
			}
		});



		$("#transaction").click(function(){
			if($("#isCreateTransaction").prop("checked")){
				//alert("创建交易！");
				$("#formSubmit")[0].submit();
			}else{
				//alert("不创建交易！");
				window.location = "workbench/clue/convert.do?clueID=${param.clueID}";
			}
		})


	});


/*---自动刷新临时交易页面市场活动列表---*/
	function showAcivityList(){
		$.ajax({
			url:"workbench/clue/getAcivityListByName.do",
			type:"get",
			data:{
				"aName":$.trim($("#selectName").val())
			},
			dataType:"json",
			success:function(data){
				var html = "";

				$.each(data, function(i, e){
					html += '<tr>',
							html += '	<td><input type="radio" name="'+ e.name+'" class="xz" value="'+ e.id +'" /></td>',
							html += '	<td>'+ e.name +'</td>',
							html += '	<td>'+ e.startDate +'</td>',
							html += '	<td>'+ e.endDate +'</td>',
							html += '	<td>'+ e.owner +'</td>',
							html += '</tr>'
				})

				$("#showActivityList").html(html);

			}
		})
	}

/*---键盘回车事件---*/
function keydown13(){
	$("#selectName").keydown(function(event){
		if(event.keyCode==13){
			showAcivityList();
			return false;
		}
	})
}

/*---自定义时间插件方法---*/
function dateTime(){
	/*时间插件*/
	$(".time").datetimepicker({
		minView: "month",
		language:  'zh-CN',
		format: 'yyyy-mm-dd',
		autoclose: true,
		todayBtn: true,
		pickerPosition: "top-left"
	});
}







</script>

</head>
<body>
	
	<!-- 搜索市场活动的模态窗口 -->
	<div class="modal fade" id="searchActivityModal" role="dialog" >
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">搜索市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" class="form-control" id="selectName" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>开始日期</td>
								<td>结束日期</td>
								<td>所有者</td>
								<td></td>
							</tr>
						</thead>
						<tbody id="showActivityList">

							<%--<tr>
								<td><input type="radio" name="activity"/></td>
								<td>发传单</td>
								<td>2020-10-10</td>
								<td>2020-10-20</td>
								<td>zhangsan</td>
							</tr>--%>

						</tbody>

					</table>
					<div align="right">
						<input type="button" id="submit"  value="选择" />
					</div>

				</div>
			</div>
		</div>
	</div>


	<div id="title" class="page-header" style="position: relative; left: 20px;">
		<h4>转换线索 <small>${param.fullname}-${param.company}</small></h4>
	</div>
	<div id="create-customer" style="position: relative; left: 40px; height: 35px;">
		新建客户：${param.company}
	</div>
	<div id="create-contact" style="position: relative; left: 40px; height: 35px;">
		新建联系人：${param.fullname}
	</div>


	<div id="create-transaction1" style="position: relative; left: 40px; height: 35px; top: 25px;">
		<input type="checkbox" id="isCreateTransaction"/>
		为客户创建交易
	</div>

<%--创建临时交易列表--%>
	<div id="create-transaction2" style="position: relative; left: 40px; top: 20px; width: 80%; background-color: #F7F7F7; display: none;" >
	
		<form action="workbench/clue/convert.do" id="formSubmit" method="post">
		  <div class="form-group" style="width: 400px; position: relative; left: 20px;">
		    <label for="amountOfMoney">金额</label>
		    <input type="text" class="form-control" name="amountOfMoney" id="amountOfMoney">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="tradeName">交易名称</label>
		    <input type="text" class="form-control" id="tradeName" name="tradeName" value="${param.company}-">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="expectedClosingDate">预计成交日期</label>
		    <input type="text" class="form-control time" id="expectedClosingDate" name="expectedClosingDate">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="stage">阶段</label>
		    <select id="stage"  name="stage" class="form-control">
		    	<c:forEach items="${stage}" var="s">
					<option value="${s.value}">${s.text}</option>
				</c:forEach>
		    </select>
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="activity">市场活动源&nbsp;&nbsp;<a href="javascript:void(0);" id="activitySource" style="text-decoration: none;"><span class="glyphicon glyphicon-search"></span></a></label>
		    <input type="text" class="form-control" name="activity" id="activity" placeholder="点击上面搜索" readonly>
		  </div>

			<input type="hidden" name="activityID" >
			<input type="hidden" id="clueID" name="clueID" value="${param.clueID}">
			<input type="hidden" name="flag" value="1">
		</form>
		
	</div>
	
	<div id="owner" style="position: relative; left: 40px; height: 35px; top: 50px;">
		记录的所有者：<br>
		<b>${param.owner}</b>
	</div>
	<div id="operation" style="position: relative; left: 40px; height: 35px; top: 100px;">
		<input class="btn btn-primary" id="transaction" type="button" value="转换">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input class="btn btn-default" type="button" value="取消">
	</div>
</body>
</html>