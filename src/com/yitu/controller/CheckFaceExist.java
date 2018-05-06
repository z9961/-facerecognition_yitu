package com.yitu.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CheckFaceExist")
public class CheckFaceExist extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");// 编码

		String t = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		int num = t.indexOf(".metadata");
		String path = t.substring(1, num).replace('/', '\\') + "facerecognition_yitu";

		String phone = (String) request.getSession().getAttribute("phone");

		if (null == getFiles(path + "\\webapp\\upload_user_img\\" + phone)
				|| 0 == getFiles(path + "\\webapp\\upload_user_img\\" + phone).size()) {
			// 如果没有上传图片
			request.getSession().setAttribute("CheckFaceExist", 0);
		} else {
			request.getSession().setAttribute("CheckFaceExist", 1);
		}

		response.sendRedirect("face_login.jsp");
	}
	//

	// 获取本地文件夹
	public static ArrayList<String> getFiles(String path) {
		ArrayList<String> files = new ArrayList<String>();
		File file = new File(path);
		try {
			File[] tempList = file.listFiles();
			for (int i = 0; i < tempList.length; i++) {
				if (tempList[i].isFile()) {
					// System.out.println("文 件：" + tempList[i]);
					files.add(tempList[i].getName());
				}
				if (tempList[i].isDirectory()) {
					// System.out.println("文件夹：" + tempList[i]);
				}
				// 文件名字
				return files;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return files;

	}
}
