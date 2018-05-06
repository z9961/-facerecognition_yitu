<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="js/jquery-1.11.2.js"></script>
<title>Insert title here</title>
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
	function code_login() {

		$.ajax({
			method : 'POST',
			url : 'YZMLoginCheck',
			async : true,
			data : {
				code : document.getElementById("code").value
			},

			success : function(res) {

				if (res == "success") {
					window.location.href = "welcome.jsp";
				} else {
					alert("验证码错误!");
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
	%>

	<form>



		<table align="center" style="margin-top: 10%">

			<tr>
				<td>请输入验证码：</td>
				<td><input type="text" name="code" id="code" /></td>
				<td><input type="button" value="获取验证码" onclick="YZM()" /></td>

			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="登陆" onclick="code_login()" /></td>
				<td><input type="reset" value="重置" /></td>
			</tr>

			<tr>
				<lable style="color:red ; visibility:hidden" id="fault_code">验证码错误</lable>
			</tr>
		</table>

	</form>

</body>
</html>