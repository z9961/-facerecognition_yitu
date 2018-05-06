<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆界面</title>
<style type="text/css">
#as1 {
	width: 20%;
	height: 80px;
	background-color: #121212;
	margin-left: 20%;
	text-align: left;
	float: left;
}

#as2 {
	width: 20%;
	height: 80px;
	background-color: #121212;
	text-align: left;
	float: left;
}

#as3 {
	width: 20%;
	height: 80px;
	background-color: #121212;
	text-align: left;
	float: left;
}
</style>
</head>
<body>


	<%
		String contextPath = request.getContextPath();
		// 将contextPath保存到request中
		request.setAttribute("contextPath", contextPath);
	%>

	<div style="margin: 0 43%;">
		<h1>&nbsp&nbsp&nbsp登录界面</h1>

		&nbsp ${sessionScope.user.phone},欢迎登陆 </br> </br>
		&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp选择以下方式登陆： 
	</div>

	<!-- </br>
	<a href="password_login.jsp">1.密码登陆</a>
	</br>
	<a href="">2.短信验证码登陆 </a>
	</br>
	<a href="face_login.jsp">3.人脸识别登陆</a> -->


</br>

	<article> <aside>
	<div id="as1">
		<h2>
			<!--<a href="video/lanqiu.mp4" target="Myiframe" style="padding-left: 100px;color: #FFF5EE">动作库</a>-->
			<a href="face_login.jsp" target="Myiframe"
				style="padding-left: 100px; color: #FFF5EE">刷脸登录</a>
		</h2>

	</div>
	<div id="as2">
		<h2>
			<a href="password_login.jsp" target="Myiframe"
				style="padding-left: 100px; color: #FFF5EE">账号密码</a>
		</h2>

	</div>
	<div id="as3">
		<h2>
			<a href="message_login.jsp" target="Myiframe"
				style="padding-left: 100px; color: #FFF5EE">短信验证码登陆</a>
		</h2>

	</div>
	</aside> <section>
	<div id="se1">
		<iframe src="" name="Myiframe" width="60%" height="600px"
			style="align-content: center; margin-left: 20%; background-color: gray;">
		</iframe>
	</div>

	</section> </article>





</body>
</html>