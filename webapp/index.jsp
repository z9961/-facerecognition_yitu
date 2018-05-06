<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆界面</title>


<script src="galleria/jquery-2.2.3.min.js" type="text/javascript"></script>
<script src="galleria/galleria-1.4.2.min.js"></script>
<script src="galleria/themes/classic/galleria.classic.min.js"></script>
<style type="text/css">
.picBox {
	width: 100%;
	height: 450px;
	/*background-color: black;*/
	margin: 0;
}

article {
	width: 100%;
	height: 50%;
	background-color: black;
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

	<div>
		<h1 style="color: blue;" align="center">人脸识别系统</h1>
		<hr />
	</div>


	<!--轮播图实现过程  -->
	<article>
	<div class="picBox">
		<img src="phone/1.jpg" alt="" /> <img src="phone/2.jpg" alt="" /> <img
			src="phone/3.jpg" alt="" />
		<!-- <img src="phone/bookImage/3.jpg" alt="" /> -->
	</div>

	<script>
		/*
		 步骤：
		 1、导入jQuery文件
		 2、导入galleria核心js文件
		 3、导入galleria主题js文件
		 4、调用Galleria对象的run方法启动放置图片的div的图片库效 果
		 */

		$(document).ready(function() {
			Galleria.run(".picBox", {
				autoplay : 2000,
				transition : 'fade'
			});
			//transition：过度效果，有 fade, slide, flash, pulse, fadeslide等值
			//autoplay：幻灯片自动播放，可跟true或一个时间（毫秒）

		});
	</script> </article>



	<form action="${contextPath}/existOrNot" method="post">
		<table align="center"
			style="font-family: '宋体'; font-size: 30px; color: blue;">
			<tr>
				<td>请输入手机号：</td>
				<td><input type="text" name="phone" required="required"
					placeholder="请输入手机号"></td>
				<td><button type="submit">确定</button></td>
			</tr>

		</table>





	</form>
</body>
</html>