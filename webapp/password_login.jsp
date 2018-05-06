<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		String contextPath = request.getContextPath();
		// 将contextPath保存到request中
		request.setAttribute("contextPath", contextPath);
	%>
	
	<form action="${contextPath}/passwordLoginCheck" method="post">
	
	
			<table align="center" style="margin-top: 10%;font-family:'宋体'; font-size:30px; ">
			<tr>
				<td>请输入密码：</td>
				<td><input type="text" name="password" required="required" placeholder="请输入密码"></td>
				<td><button type="submit">确定</button></td>
			</tr>
			
			</table>
            
			
	</form>

</body>
</html>