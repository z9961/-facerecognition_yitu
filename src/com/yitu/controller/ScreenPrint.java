package com.yitu.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class ScreenPrint {
	// public static void main(String[] args) {
	// run();

	// https://www.cnblogs.com/yyagrt/p/7260586.html
	// }

	// String t =
	// Thread.currentThread().getContextClassLoader().getResource("").getPath();
	// int num = t.indexOf(".metadata");
	// String pathabsolute = t.substring(1, num).replace('/', '\\');

	public boolean screenPrint(String startpath, String uuid, String phone, String type) throws IOException {
		String t = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		int num = t.indexOf(".metadata");

		// 配置文件路径
		String path = t.substring(1, num).replace('/', '\\')
				+ "facerecognition_yitu\\webapp\\resault\\lbpcascade_frontalface.xml";
		// 库文件
		String libpath = t.substring(1, num).replace('/', '\\')
				+ "facerecognition_yitu\\webapp\\WEB-INF\\lib\\opencv_java310.dll";
		// 库文件x64
		String libpath2 = t.substring(1, num).replace('/', '\\')
				+ "facerecognition_yitu\\webapp\\WEB-INF\\lib\\opencv_java310-64.dll";

		// 裁剪后的人脸
		String path3 = t.substring(1, num).replace('/', '\\') + "facerecognition_yitu\\webapp\\img_temp\\" + phone + "\\scan\\"
				+ uuid + ".png";
		// 裁剪后的人脸文件夹
		String path4 = t.substring(1, num).replace('/', '\\') + "facerecognition_yitu\\webapp\\img_temp\\" + phone
				+ "\\scan\\";
		String path7 = t.substring(1, num).replace('/', '\\')
				+ "facerecognition_yitu";
		// 符合的人脸
		String okImgPath = path7 + "\\webapp\\upload_user_img\\" + phone + "\\" + uuid + ".png";

	
		String strPath = path4;
		File file = new File(strPath);
		if (!file.exists()) {
			file.mkdirs();
		}

		System.load(libpath);

		// System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Rect rect_cut = new Rect();// 裁剪后的

		CascadeClassifier faceDetector = new CascadeClassifier(path);
		// System.out.println("daiculiededizhi" + startpath);
		Mat image = Imgcodecs.imread(startpath);
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(image, faceDetections);

		System.out.println("原始图片" + startpath);
		System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
		for (Rect rect : faceDetections.toArray()) {
			// 用绿色框匡助

			int x, y, width, height;
			x = rect_cut.x;
			x = (int) (x * 0.6);
			y = rect_cut.y;
			y = (int) (y * 0.8);
			width = rect_cut.width;
			width = (int) (width * 1.4);
			height = rect_cut.height;
			height = (int) (height * 1.4);

			int i = 0;
			if (i <= faceDetections.toArray().length) {

				// Imgproc.rectangle(image, new Point(x, y), new Point(x +
				// width, y + height), new Scalar(0, 255, 0));

				i++;
				rect_cut.x = rect.x;
				rect_cut.y = rect.y;
				rect_cut.width = rect.width;
				rect_cut.height = rect.height;
			}

		}
		Imgcodecs.imwrite(startpath, image);
		// 有绿框的
		// System.out.println(" 生成有绿框的图片");

		rect_cut = new Rect((int) (rect_cut.x * 1), (int) (rect_cut.y * 1), (int) (rect_cut.width * 1),
				(int) (rect_cut.height * 1));

		Mat image22 = Imgcodecs.imread(startpath);
		Mat dst = new Mat(image22, rect_cut);

		Imgcodecs.imwrite(path3, dst);
		System.out.println("path3" + path3);

		// 检测是否裁剪成功
		File f = new File(path3);
		if (f.exists() && f.isFile() && f.length() > 2000) {
			System.out.println("f.length=" + f.length());
			FileUtils.copyFile(new File(path3), new File(okImgPath));
			return true;
		} else {
			return false;
		}
	}
}
