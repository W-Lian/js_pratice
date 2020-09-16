package com.michael.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
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
@WebServlet("/remote")
public class RemotePicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String imgStr = test();
		
		String imgStr =readImg("http://tzwjw.gaj.taizhou.gov.cn/files/103216/1710/y_25b2452411.png");
		request.setAttribute("imgStr", imgStr);
		request.getRequestDispatcher("/pic.jsp").forward(request, response);
	}
	
	private static void strigToPic(String imgStr) {
		String path = "F:\\picture\\a.png";

		if (imgStr == null)
			System.out.println("imgStr is null");
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
		// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		String imgFile = "C:\\Users\\WL\\Desktop\\微信图片_20200219193645.jpg";// 待处理的图片
		// 地址也有写成"F:/deskBG/86619-107.jpg"形式的
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
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

	public static String readImg(String urlOrPath){
		
        InputStream in = null;
        try {
          byte[] b ;
       //加载https途径的图片（要避开信任证书的验证）
         if(urlOrPath.toLowerCase().startsWith("https")){
            b=HttpsUtils.doGet(urlOrPath);
         }else if(urlOrPath.toLowerCase().startsWith("http")){ 
          //加载http途径的图片
            	URL url = new URL(urlOrPath);
//            	URLConnection openConnection = url.openConnection();
//            	openConnection.connect();
//    			in = openConnection.getInputStream();
    			in = url.openStream();    			           	
            }else{ //加载本地路径的图片
                File file = new File(urlOrPath);
                if(!file.isFile() || !file.exists() || !file.canRead()){
//                    log.info("图片不存在或文件错误");
                	System.out.println("图片不存在或文件错误");
                    return "error";
                }                
                in = new FileInputStream(file);
            }
            b = getByte(in); //调用方法，得到输出流的字节数组
			return base64ToStr(b);    //调用方法，为防止异常 ，得到编码后的结果
 
        } catch (Exception e) {
//        	log.error("读取图片发生异常:"+ e);
        	System.out.println("读取图片发生异常:"+ e);
        	return "error";
        }
    }
	
	public static byte[] getByte(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();		
		try {
			byte[] buf=new byte[1024]; //缓存数组
			while(in.read(buf)!=-1){ //读取输入流中的数据放入缓存，如果读取完则循环条件为false;
				out.write(buf); //将缓存数组中的数据写入out输出流，如果需要写到文件，使用输出流的其他方法
				}
			out.flush();
			return out.toByteArray();	//将输出流的结果转换为字节数组的形式返回	（先执行finally再执行return	）
		} finally{
			if(in!=null){
					in.close();
			}
			if(out!=null){
				out.close();
			}			
		}
	}
	
	/*
	 * 编码
	 * Base64被定义为：Base64内容传送编码被设计用来把任意序列的8位字节描述为一种不易被人直接识别的形式
	 */
	public static String base64ToStr(byte[] bytes) throws IOException {
		String content = "";
		content = new BASE64Encoder().encode(bytes);
//		return content;
		return content.trim().replaceAll("\n", "").replaceAll("\r", ""); //消除回车和换行
	}
	
}
