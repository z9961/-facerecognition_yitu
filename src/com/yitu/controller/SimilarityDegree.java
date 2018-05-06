package com.yitu.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class SimilarityDegree {
	private String user_img_path;// 用户注册时的照片
	private String user_temp_img_path;// 用户拍照用来比较的图片
	private double degree;// 兩張照片相似度
      
	//要調用的方法
	
	public double similarDegree() throws Exception {
		degree = compare(getData(user_img_path), getData(user_temp_img_path));
		return degree;

	}
	public String getUser_img_path() {
		return user_img_path;
	}
	public void setUser_img_path(String user_img_path) {
		this.user_img_path = user_img_path;
	}
	public String getUser_temp_img_path() {
		return user_temp_img_path;
	}
	public void setUser_temp_img_path(String user_temp_img_path) {
		this.user_temp_img_path = user_temp_img_path;
	}
	public double getDegree() {
		return degree;
	}
	public void setDegree(double degree) {
		this.degree = degree;
	}
	public static int[] getData(String name) throws Exception {
		BufferedImage img = ImageIO.read(new File(name));
		BufferedImage slt = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
		slt.getGraphics().drawImage(img, 0, 0, 100, 100, null);
		// ImageIO.write(slt,"jpeg",new File("slt.jpg"));
		int[] data = new int[256];
		for (int x = 0; x < slt.getWidth(); x++) {
			for (int y = 0; y < slt.getHeight(); y++) {
				int rgb = slt.getRGB(x, y);
				Color myColor = new Color(rgb);
				int r = myColor.getRed();
				int g = myColor.getGreen();
				int b = myColor.getBlue();
				data[(r + g + b) / 3]++;
			}
		}
		// data 就是所谓图形学当中的直方图的概念
		return data;
	}

	public static float compare(int[] s, int[] t) {
		float result = 0F;
		for (int i = 0; i < 256; i++) {
			int abs = Math.abs(s[i] - t[i]);
			int max = Math.max(s[i], t[i]);
			result += (1 - ((float) abs / (max == 0 ? 1 : max)));
		}
		return (result / 256) * 100;
	}



}
