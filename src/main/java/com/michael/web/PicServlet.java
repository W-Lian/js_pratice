package com.michael.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdk.nashorn.internal.ir.RuntimeNode.Request;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Servlet implementation class PicServlet
 */
@WebServlet("/java")
public class PicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String imgStr = test();
//		Map<String, String> strMap = new HashMap<>();
//		strMap.put("img", imgStr);
//		System.out.println(imgStr);
		request.setAttribute("imgStr", imgStr);
		request.getRequestDispatcher("/pic.jsp").forward(request, response);
	}
	
	private static void strigToPic(String imgStr) {
		String path = "F:\\picture\\a.png";

		if (imgStr == null)
			System.out.println("imgStr is null");
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// ����
			byte[] b = decoder.decodeBuffer(imgStr);
			// ��������
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(path);
			out.write(b);
			out.flush();
			out.close();
			System.out.println("success");
		} catch (Exception e) {

			System.out.println(e);
		}

	}
	
	public static String picChangeString() {
		// ��ͼƬ�ļ�ת��Ϊ�ֽ������ַ��������������Base64���봦��
		String imgFile = "C:\\Users\\WL\\Desktop\\΢��ͼƬ_20200219193645.jpg";// �������ͼƬ
		// ��ַҲ��д��"F:/deskBG/86619-107.jpg"��ʽ��
		InputStream in = null;
		byte[] data = null;
		// ��ȡͼƬ�ֽ�����
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// ���ֽ�����Base64����
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// ����Base64��������ֽ������ַ���
	}
	
	public static String test() {
		BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		File f = new File("D:\\zjsy\\code\\jsgaosp-manage\\jsgaosp-manage-web\\src\\main\\webapp\\app\\img\\lg.jpg");
		BufferedImage bi;
		try {
			bi = ImageIO.read(f);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bi, "jpg", baos);
			byte[] bytes = baos.toByteArray();
			return encoder.encodeBuffer(bytes).trim();
		} catch (IOException e) {
			e.printStackTrace();
			return "test fail";

		}
	}

}
