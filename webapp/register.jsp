<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>信息注册</title>
<script src="js/jquery-1.11.2.js"></script>
<script type="text/javascript">

	function YZM() {
		
		$.ajax({
		method : 'POST',
		url : 'TelValidateServlet',
		async : true,


		success : function(res) {
			
			if (res == "success") {
			alert("短信验证码生成");
			} else {
				alert("后台崩溃了");
			}
		}
	})
	}
</script>
</head>
<body>
	<%
		String contextPath = request.getContextPath();
		// 将contextPath保存到request中
		request.setAttribute("contextPath", contextPath);
 if("1".equals(request.getAttribute("turn_index"))){
			 
			 request.getRequestDispatcher("index.jsp").forward(request, response);
			 
		 }
		
		
	%>
	
	
	
	
	<h1 style="color: blue;" align="center">注册信息</h1><hr/>
	
	<em style="color: red;text-align: center">${sessionScope.user.phone}</em>
	
	<p style="color:red">${requestScope.code_fault}<p>
		<p style="color:red">${requestScope.exception_sql}<p>
	
	
	<form action="${contextPath}/register" method="post">
			
		<table align="center"> 
			<tr>
				<td>用户名：</td>
				<td><input type="text" name="username" required="required" placeholder="用户名"></td>
			</tr>
			<tr>
				<td>密&emsp;码：</td>
				<td><input type="password" name="password"required="required" placeholder="密码"></td>
				
			</tr>
			<tr>
				<td> <input id="ido1" name="rdo1" type="radio" value="1" checked="checked" />上传人脸	</td>
				<td><input id="ido1" name="rdo1" type="radio" value="0" />不上传人脸
				</td>
			
				
			</tr>
			<tr>
				<td>验证码：</td>
				<td><input type="text" name="messagecode" required="required" placeholder="短信验证码"></td>
				<td><input type="button" value="获取短信验证码" onclick="YZM()"/></td>
			</tr>
			
			<tr>
				<td><input type="submit" value="注册"/></td>
				<td><input type="reset" value="重置"/></td>
			
			
			</tr>
		
		</table>
	
	
	
	
	
	
	
	
	
	
	
	
	
		<!-- <input type="text" name="username" required="required"
			placeholder="用户名"></br> <input type="password" name="password"
			required="required" placeholder="密码"></br> <input type="text"
			name="messagecode" required="required" placeholder="短信验证码"></br>
			 <input id="ido1" name="rdo1" type="radio" value="1" checked="checked" />上传人脸
		<input id="ido1" name="rdo1" type="radio" value="0" />不上传人脸
		<button type="submit">确定</button> -->
	</form>
</body>
</html>