package com.yitu.controller;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Random;

public class BPImage {
	private int picSize = 19 * 19;// 图像大小
	private int hiddenSize = 12;// 隐藏层节点个数
	private byte[] imageinfor = new byte[374];// 存放图像信息
	private double[] input = new double[picSize + 1];// 归一化后的图像信息
	private double[][] inputWeight = new double[hiddenSize][picSize + 1];// 输入层参数
	private double[] alpha1 = new double[hiddenSize];// 隐藏层调整的梯度
	private double[] hiddenWeight = new double[hiddenSize + 1];// 隐藏层参数
	private double[] hiddenOutput = new double[hiddenSize + 1];// 隐藏层输出
	private double alpha2;// 输出层调整的梯度
	private double output;// 输出层
	private double ci = 0.3;// 学习率
	private double opt;// 期望输出
	Random random = new Random();
	private double[] pro;

	public BPImage() {
	}

	// 初始化 每张图片的每个元素在x属性下的比重
	public void init() {
		for (int i = 0; i < hiddenSize; i++) {
			for (int j = 0; j < picSize + 1; j++)
				inputWeight[i][j] = random.nextDouble() * 2 - 1;
			// inputWeight[i][j] =0;
		}
		// 每个节点的参数
		for (int i = 0; i < hiddenSize + 1; i++) {
			hiddenWeight[i] = random.nextDouble() * 2 - 1;
			// hiddenWeight[i]=0;
		}
	}

	// sigmoid Sigmoid函数是一个在生物学中常见的S型函数，也称为S型生长曲线。
	// [1] 在信息科学中，由于其单增以及反函数单增等性质
	// Sigmoid函数常被用作神经网络的阈值函数，将变量映射到0,1之间。
	private double Sigmoid(double x) {
		return 1.0d / (1.0d + Math.exp(-x));
	}

	// 图像文件读入
	public void PGMReader(String filename) {
		File file = new File(filename);
		try {
			RandomAccessFile in = new RandomAccessFile(file, "r");
			in.read(imageinfor);

			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < picSize; i++) {
			int temp = imageinfor[i + 13];

			input[i] = (double) (temp + 128) / 255;// 将图片归一化 //现在图片非0便是一
		}
		input[picSize] = 1.0;
	}

	public void PGMReader(File file) {
		try {
			RandomAccessFile in = new RandomAccessFile(file, "r");
			in.read(imageinfor);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < picSize; i++) {
			int temp = imageinfor[i + 13];
			input[i] = (double) (temp + 128) / 255;
		}
		input[picSize] = 1.0; // 最后一位
	}

	public void setOpt(double opt) { // 预期输出值
		this.opt = opt;
	}

	private void forward() { // 向前演化
		for (int i = 0; i < hiddenSize; i++) {
			double temp = 0;
			for (int j = 0; j < picSize + 1; j++) {
				temp += input[j] * inputWeight[i][j];
			}
			hiddenOutput[i] = Sigmoid(temp);// 每个像素在该节点（属性）下的权重和
		}
		hiddenOutput[hiddenSize] = 1.0;

		double temp = 0;
		for (int i = 0; i < hiddenSize + 1; i++) {
			temp += hiddenOutput[i] * hiddenWeight[i];
		}
		output = Sigmoid(temp);// 输出
	}

	public void BP() {
		// 计算各层的梯度
		alpha2 = (opt - output) * output * (1 - output);

		for (int i = 0; i < hiddenSize; i++) {

			alpha1[i] = hiddenOutput[i] * (1 - hiddenOutput[i]) * alpha2 * hiddenWeight[i];
		}

		// 反向传播 （调整权重）
		for (int i = 0; i < hiddenSize; i++) {
			hiddenWeight[i] += ((hiddenOutput[i] * alpha2 * ci));
			for (int j = 0; j < picSize + 1; j++) {
				inputWeight[i][j] += ((input[j] * alpha1[i] * ci));
			}
		}
		hiddenWeight[hiddenSize] += (hiddenOutput[hiddenSize] * alpha2 * ci);
	}

	public double train(String path, String upload_user_img_path) {
		double degree = 0.0;
		int length_user_img = 0;
		File file = new File(upload_user_img_path);
		File[] user_img_List = file.listFiles();
		length_user_img = user_img_List.length;
		File temp_img = new File(path);

		init();
		pro = new double[151];

		for (int i = 0; i < 4; i++) {
			int right = 0;
			int facenumber = 0;
			int nonfacenumber = 0;
			// 能遍歷幾次
			for (int j = 0; j < length_user_img; j++) {
				this.setOpt(1.0);
				this.PGMReader(user_img_List[j]);
				this.forward();
				this.BP();
				facenumber++;

			}

			// 正例测试
			this.PGMReader(path);
			this.forward();
			/*
			 * if (output > 0.6) right++;
			 * 
			 * pro[i] = (double) right / 10; degree=pro[i]; if (i % 10 == 0) {
			 * System.out.println("第" + i + "次迭代估算正确率为：" + pro[i]); }
			 * 
			 * if (pro[i] >= 0.95) { System.out.println("第" + i + "次迭代估算正确率为：" +
			 * pro[i]); break; }
			 */

		}

		return output;
	}

	// 获取本地文件夹
	public static ArrayList<String> getFiles(String path) {
		ArrayList<String> files = new ArrayList<String>();
		File file = new File(path);
		File[] tempList = file.listFiles();

		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				// System.out.println("文 件：" + tempList[i]);
				files.add(tempList[i].getName());
			}
			if (tempList[i].isDirectory()) {
				// System.out.println("文件夹：" + tempList[i]);
			}
		}
		// 文件名字
		return files;
	}

}
