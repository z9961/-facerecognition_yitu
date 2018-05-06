<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="js/jquery-1.11.2.js"></script>
<title>Insert title here</title>
</head>
<body>
	<div id="support"></div>
	<div id="contentHolder">
		<video id="video" width="640" height="480"
			style="border:1px solid red" autoplay></video>
		<button id="snap" onclick="getMedia()">打开摄像头</button>

		<canvas style="border:1px solid red" id="canvas1" width="640"
			height="480"></canvas>
		<button id="snap" onclick="getPhoto()">拍照</button>

		

	</div>


	<script type="text/javascript">
		var video = document.querySelector('video');
		var audio, audioType;

		var canvas1 = document.getElementById('canvas1');
		var context1 = canvas1.getContext('2d');

		navigator.getUserMedia = navigator.getUserMedia
				|| navigator.webkitGetUserMedia || navigator.mozGetUserMedia
				|| navigator.msGetUserMedia;
		window.URL = window.URL || window.webkitURL || window.mozURL
				|| window.msURL;

		var exArray = []; //存储设备源ID  

		navigator.mediaDevices.enumerateDevices().then(function(sourceInfos) {
			for (var i = 0; i != sourceInfos.length; ++i) {
				var sourceInfo = sourceInfos[i];
				//这里会遍历audio,video，所以要加以区分  
				if (sourceInfo.kind === 'video') {
					exArray.push(sourceInfo.id);
				}
			}

		});

		function getMedia() {
			if (navigator.getUserMedia) {
				navigator.getUserMedia({
					'video' : {
						'optional' : [ {
							'sourceId' : exArray[0]
						//0为前置摄像头，1为后置  
						} ]
					},
					'audio' : true
				}, successFunc, errorFunc); //success是获取成功的回调函数  
			} else {
				alert('Native device media streaming (getUserMedia) not supported in this browser.');
			}
		}

		/**
		 * 关闭连接
		 */

		//发送消息  
		// function send() {  
		// var message = document.getElementById('text').value;  
		// websocket.send(message);  
		// } 
		function successFunc(stream) {
			//alert('Succeed to get media!');  
			if (video.mozSrcObject !== undefined) {
				//Firefox中，video.mozSrcObject最初为null，而不是未定义的，我们可以靠这个来检测Firefox的支持  
				video.mozSrcObject = stream;
			} else {
				video.src = window.URL && window.URL.createObjectURL(stream)
						|| stream;
			}

		}
		function errorFunc(e) {
			alert('Error！' + e);
		}

		// 将视频帧绘制到Canvas对象上,Canvas每60ms切换帧，形成肉眼视频效果  
		function drawVideoAtCanvas(video, context) {
			window.setInterval(function() {
				context.drawImage(video, 0, 0, 640, 480);
			}, 60);

		}

		// vedio播放时触发，绘制vedio帧图像到canvas  
		//        video.addEventListener('play', function () {  
		//            drawVideoAtCanvas(video, context2);  
		//        }, false);  

		//拍照  
		function getPhoto() {

			context1.drawImage(video, 0, 0, 640, 480);
			var data = canvas1.toDataURL("image/png")
		

			$.ajax({

				method : 'POST',
				url : 'uploadFace',
				async : true,

				data : {
					scanData : canvas1.toDataURL()
				},
				success : function(res) {
					alert(res);
					if (res == "success") {
						window.location.href = "registersuccess.jsp";
					} else {
						alert("未识别到人脸!请重新拍照.");
					}
				}
			})

		}

		//视频  
		function getVedio() {
			drawVideoAtCanvas(video, context2);

		}
	</script>
</body>
</html>