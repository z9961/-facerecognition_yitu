package com.yitu.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import com.yitu.dao.IUserDao;
import com.yitu.dao.UserDaoImp;
import com.yitu.entity.User;

import sun.misc.BASE64Decoder;

/**
 * Servlet implementation class FaceCheckServlet 判断调用哪个方式进行人脸检测比较两张图片的相似度
 * 第一次用人脸识别登录登录
 */
@WebServlet("/faceCheckChoose")
public class FaceCheckChoose extends HttpServlet {
	// 通過图片相似度来进行 //光照等很多条件会影响
	ScreenPrint screenprint = new ScreenPrint();

	String t = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	int num = t.indexOf(".metadata");
	String path = t.substring(1, num).replace('/', '\\') + "facerecognition_yitu";

	private static final long serialVersionUID = 1L;

	BPImage bp = new BPImage();

	public FaceCheckChoose() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("==================start====================");
		/**
		 * 获取状态信息后 以下方法选择人脸识别技术
		 * 
		 */
		response.setCharacterEncoding("UTF-8");// 编码
		// 給文件起名字
		String phone = ((User) request.getSession().getAttribute("user")).getPhone();

		// 获取前段截取的图片
		response.setContentType("text/html");
		String dataurl = request.getParameter("scanData");
		String img = dataurl.substring(22, dataurl.length());
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		// 在文件夹生成标签
		base64StrToImage(img, path + "\\webapp\\img_temp\\" + phone + "\\" + uuid + ".png");
		// 获取用户已经的登录次数
		IUserDao userdao = UserDaoImp.getInstance();
		int count = userdao.countOfLogin(phone);
		 System.out.println("当前手机号"+phone);
		if (null == getFiles(path + "\\webapp\\upload_user_img\\" + phone)
				|| 0 == getFiles(path + "\\webapp\\upload_user_img\\" + phone).size()) {
			// 如果没有上传图片
			 System.out.println("未上传图片");
		}else{if (count < 4) {
			// 采用相似度匹配登录
			System.out.println("采用相似度匹配登录");
			// 已经上传图片
			boolean isok = loginbyopencv(phone, uuid);
			if (isok) {
				System.out.println("相似度匹配登录ok");
				response.getWriter().write("success");// 传值
			} else {
				System.out.println("相似度匹配登录error");
				response.getWriter().write("failure");// 传值
			}
		} else {
			// 采用学习算法
			boolean isok = cutimg(phone, uuid);
			if (isok) {
				String cutTempImgPath = path + "\\webapp\\img_temp\\" + phone + "\\scan\\" + uuid + ".png";
				String okImgPath = path + "\\webapp\\upload_user_img\\" + phone + "\\";
				double degree = bp.train(cutTempImgPath, okImgPath);
				System.out.println("degree=" + degree);
				if (degree >= 0.99) {
					System.out.println("人脸学习成功");
					// 需要改變用戶狀態
					userdao.changeState(phone);

					// 将符合条件的照片添加到user人脸中
					FileUtils.copyFile(new File(cutTempImgPath), new File(okImgPath + uuid + ".png"));
					response.getWriter().write("success");// 传值
				} else {
					System.out.println("人脸学习失败");
					response.getWriter().write("failure");// 传值
				}
			} else {
				response.getWriter().write("failure");// 传值
			}

		}}
		
		System.out.println("==================end====================");
	}

	// 裁切图片
	public boolean cutimg(String phone, String uuid) throws IOException {

		// 用户注册时上传的照片
		String userImgPath = path + "\\webapp\\upload_user_img\\" + phone + "\\"
				+ getFiles(path + "\\webapp\\upload_user_img\\" + phone).get(0);

		// 拍摄的人脸
		String tempImgPath = path + "\\webapp\\img_temp\\" + phone + "\\" + uuid + ".png";
		// 裁切的人脸
		String cutTempImgPath = path + "\\webapp\\img_temp\\" + phone + "\\scan\\" + uuid + ".png";
		// 符合的人脸
		String okImgPath = path + "\\webapp\\upload_user_img\\" + phone + "\\" + uuid + ".png";

		boolean isok = screenprint.screenPrint(tempImgPath, uuid, phone, "1");
		return isok;
	}

	// 相似度匹配登录
	public boolean loginbyopencv(String phone, String uuid) throws IOException {
		SimilarityDegree similiar = new SimilarityDegree();

		// 用户注册时上传的照片
		String userImgPath = path + "\\webapp\\upload_user_img\\" + phone + "\\"
				+ getFiles(path + "\\webapp\\upload_user_img\\" + phone).get(0);

		// 拍摄的人脸
		String tempImgPath = path + "\\webapp\\img_temp\\" + phone + "\\" + uuid + ".png";
		// 裁切的人脸
		String cutTempImgPath = path + "\\webapp\\img_temp\\" + phone + "\\scan\\" + uuid + ".png";
		// 符合的人脸
		String okImgPath = path + "\\webapp\\upload_user_img\\" + phone + "\\" + uuid + ".png";

		similiar.setUser_img_path(userImgPath);
		boolean isok = cutimg(phone, uuid);
		System.out.println("裁脸是否成功:" + isok);
		if (isok) {
			System.out.println("=========检测到人脸,判断是否是同一个人===========");
			similiar.setUser_temp_img_path(cutTempImgPath);
			double degree;
			try {
				degree = similiar.similarDegree();

				System.out.println("degree" + degree);
				if (degree >= 80) {
					// 需要改變用戶狀態
					IUserDao userdao = UserDaoImp.getInstance();
					userdao.changeState(phone);

					// 将符合条件的照片添加到user人脸中
					// 检测是否裁剪成功
					File f = new File(cutTempImgPath);
					if (f.exists() && f.isFile() && f.length() > 2000) {
						System.out.println("f.length=" + f.length());
						FileUtils.copyFile(new File(cutTempImgPath), new File(okImgPath));
						return true;
					} else {
						return false;
					}

				} else {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	// 转化为图片保存
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
