package utils;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

public class MatManager {
	
	public static BufferedImage Mat2BufferedImage(Mat m) {
		int type = BufferedImage.TYPE_BYTE_GRAY;
		
		if (m.channels() > 1) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		
		int buffer_size = m.channels() * m.cols() * m.rows();
		
		byte[] b = new byte[buffer_size];
		
		m.get(0, 0, b);
		
		BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
		
		final byte[] target_pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(b, 0, target_pixels, 0, b.length);
		
		return image;
	}
	
	public static Mat BufferedImage2Mat(BufferedImage img) {
		Mat m = new Mat(img.getWidth(), img.getHeight(), CvType.CV_8UC3);
		
		m.put(img.getWidth(), img.getHeight(), ((DataBufferByte) img.getRaster().getDataBuffer()).getData());
		
		return m;
	}
	
	public static List<Mat> divideMat(Mat m, int divider) {
		List<Rect> values = new ArrayList<Rect>();
		
		if (m.cols() % divider != 0 || m.rows() % divider != 0)
			return null;

		int step_x = m.rows() / divider;
		int step_y = m.cols() / divider;
		int x;
		int y = 0;
		
		while (y < m.rows()) {
			x = 0;
			while (x < m.cols()) {
				values.add(new Rect(x, y, step_x, step_y));
				x += step_x;
			}
			y += step_y;
		}
		return cutMat(m, values);
	}
	
	public static List<Mat> cutMat(Mat m, List<Rect> values) {
		List<Mat> list = new ArrayList<Mat>();
		
		for (Rect r : values) {
			list.add(new Mat(m, r));
		}
		
		return list;
	}
	
	public static double[] getDataFromMat(Mat img) {
		double[] res = new double[img.rows() + img.cols()];
		int cnt;
		
		for (int i = 0; i < img.cols(); i++) {
			cnt = 0;
			for (int j = 0; j < img.rows(); j++)
				if (img.get(j, i) != null && img.get(j, i)[0] == 255)
					cnt++;
			res[i] = cnt;
		}
		
		for (int i = img.cols(); i < (img.cols() + img.rows()); i++) {
			cnt = 0;
			for (int j = 0; j < img.cols(); j++)
				if (img.get(i, j) != null && img.get(i, j)[0] == 255)
					cnt++;
			res[i] = cnt;
		}
		
		return res;
	}
	
	public static int[] getLengthFromMat(Mat img) {
		int[] res = new int[img.cols()];
		int cnt;
		
		for (int i = 0; i < img.cols(); i++) {
			cnt = 0;
			for (int j = 0; j < img.rows(); j++)
				if (img.get(j, i) != null && img.get(j, i)[0] == 255)
					cnt++;
			res[i] = cnt;
		}
		
		return res;
	}
	
	public static int[] getWidthFromMat(Mat img) {
		int[] res = new int[img.rows()];
		int cnt;
		
		for (int i = 0; i < img.rows(); i++) {
			cnt = 0;
			for (int j = 0; j < img.cols(); j++)
				if (img.get(i, j) != null && img.get(i, j)[0] == 255)
					cnt++;
			res[i] = cnt;
		}
		
		return res;
	}
}