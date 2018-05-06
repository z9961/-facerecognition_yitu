package com.yitu.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.yitu.entity.User;

import sun.misc.BASE64Decoder;

/**
 * Servlet implementation class UploadFace
 */
@WebServlet("/uploadFace")
public class UploadFace extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ScreenPrint screenprint = new ScreenPrint();

	public UploadFace() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// 获取web的上下文路径
		String t = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		int num = t.indexOf(".metadata");
		String path = t.substring(1, num).replace('/', '\\') + "facerecognition_yitu\\webapp\\img_temp\\";
		String path_end = t.substring(1, num).replace('/', '\\') + "facerecognition_yitu\\webapp\\upload_user_img\\";
		// 給文件起名字
		String phone = ((User) request.getSession().getAttribute("user")).getPhone();
		// 获取前段截取的图片
		response.setContentType("text/html");
		String dataurl = request.getParameter("scanData");
		String img = dataurl.substring(22, dataurl.length());
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		// 在文件夹生成标签
		System.out.println(path + phone + "\\" + uuid + ".png");
		base64StrToImage(img, path + phone + "\\" + uuid + ".png");
		boolean isok = screenprint.screenPrint(path + phone + "\\" + uuid + ".png", uuid, phone, "0");
		System.out.println(isok);
		if (isok) {
			response.setCharacterEncoding("UTF-8");// 编码
			response.getWriter().write("success");// 传值
		} else {
			response.setCharacterEncoding("UTF-8");// 编码
			response.getWriter().write("failure");// 传值
		}

	}

	public static boolean base64StrToImage(String imgStr, String path) {
		if (imgStr == null)
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// 解密
			byte[] b = decoder.decodeBuffer(imgStr);
			// 处理数据
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			// 文件夹不存在则自动创建
			File tempFile = new File(path);
			if (!tempFile.getParentFile().exists()) {
				tempFile.getParentFile().mkdirs();
			}
			OutputStream out = new FileOutputStream(tempFile);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}

	}

}
