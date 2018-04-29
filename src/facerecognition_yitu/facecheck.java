package facerecognition_yitu;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class facecheck {
	// public static void main(String[] args) {
	// run();

	// https://www.cnblogs.com/yyagrt/p/7260586.html
	// }
	 public static int i = 0;
	@Test
	public void run() {

		Rect rect_cut = new Rect();// 裁剪后的
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
		System.loadLibrary("opencv_java310");
		CascadeClassifier faceDetector = new CascadeClassifier("./resault/lbpcascade_frontalface.xml");
		Mat image = Imgcodecs.imread("./resault/2.jpg");
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(image, faceDetections);

		System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
		for (Rect rect : faceDetections.toArray()) {
			// 用绿色框匡助
			
			if (i <=faceDetections.toArray().length) {
				
				Imgproc.rectangle(image, new Point(rect.x, rect.y),
						new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
				
				i++;
				rect_cut.x=rect.x;
				rect_cut.y=rect.y;
				rect_cut.width=rect.width;
				rect_cut.height=rect.height;
			}

		}
		

		// for(size_t i=0;i<faces.size();i++)
		// {
		//
		// image1= image(Rect(faces[i].x, faces[i].y, faces[i].width,
		// faces[i].height));
		// }

		String filename = "./resault/" + f.format(new Date()) + ".png";
		System.out.println(String.format("Writing %s", filename));
		Imgcodecs.imwrite(filename, image);
		String filename1 = "./resault/" + f.format(new Date()) + ".jpg";
		rect_cut=new Rect(rect_cut.x,rect_cut.y,rect_cut.width,rect_cut.height);  
		Mat image2 = Imgcodecs.imread(filename);
        Mat dst=new Mat(image2,rect_cut);  
        Imgcodecs.imwrite(filename1, dst);  
	}
}
