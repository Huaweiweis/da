package com.zcf.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class FileUploadUtils {

	/**
	 * 
	 * @param filedata
	 * @param request
	 * @return 返回图片的储存路径 前端代码
	 *         <!--上传图片、文件的代码--> <form action="upload" method="post" enctype=
	 *         "multipart/form-data"> <input type="file" name="file"> <input
	 *         type="submit"> </form> <!--接收--> <img src="${img}" />
	 */
	public static String saveFile(MultipartFile filedata, HttpServletRequest request) {
		long startTime = System.currentTimeMillis();// 用来检测程序运行时间
		String pathval = request.getSession().getServletContext().getRealPath("/") + "WEB-INF/";
		// 根据配置文件获取服务器图片存放路径
		String newFileName = String.valueOf(System.currentTimeMillis());
		String saveFilePath = "img/";
		/* 构建文件目录 */
		File fileDir = new File(pathval + saveFilePath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		// 上传的文件名
		String filename = filedata.getOriginalFilename();
		// 文件的扩张名
		String extensionName = filename.substring(filename.lastIndexOf(".") + 1);
		String imgPath = saveFilePath + newFileName + "." + extensionName;
		try {

			System.out.println(pathval + imgPath);// 打印图片位置
			FileOutputStream out = new FileOutputStream(pathval + imgPath);
			// 写入文件
			out.write(filedata.getBytes());
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("方法二的运行时间：" + String.valueOf(endTime - startTime) + "ms");
		return imgPath;
	}

	/**
	 * 采用file.Transto 来保存上传的文件 将name=file控件得到的文件封装成CommonsMultipartFile 对象
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 *             前端代码 <form name="Form2" action="fileUpload2"
	 *             method="post"enctype="multipart/form-data">
	 *             <h1>采用multipart提供的file.transfer方法上传文件</h1>
	 *             <input type="file" name="file"> <input
	 *             type="submit"value="upload" /> </form>
	 */
	public static String fileUpload(CommonsMultipartFile file, HttpServletRequest request) throws IOException {

		long startTime = System.currentTimeMillis();// 用来检测程序运行时间

		String pathval = request.getSession().getServletContext().getRealPath("/") + "WEB-INF/";
		// 根据配置文件获取服务器图片存放路径
		String saveFilePath = "imgs/";
		/* 构建文件目录 */
		File fileDir = new File(pathval + saveFilePath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		String filename = file.getOriginalFilename();// 获取上传文件的全名
		String ExtensionName = filename.substring(filename.lastIndexOf(".") + 1);// 获取文件的扩展名如:mp4
		String Millisecond = String.valueOf(System.currentTimeMillis());// 获取当前时间的毫秒数
		String NewFileName = Millisecond + "." + ExtensionName;// 该文件在系统中的名字
		String FileName = saveFilePath + NewFileName;// 文件存储的路径
		String path = pathval + saveFilePath + NewFileName; // 文件储存绝对路径
		System.out.println(path);
		File newFile = new File(path);
		// 通过CommonsMultipartFile的方法直接写文件（注意这个时候）
		file.transferTo(newFile);
		long endTime = System.currentTimeMillis();
		System.out.println("方法二的运行时间：" + String.valueOf(endTime - startTime) + "ms");
		return FileName;
	}

	/**
	 * 采用spring提供的上传文件的方法
	 * 
	 * @param request
	 * @return 文件在服务器的路径
	 * @throws IllegalStateException
	 * @throws IOException
	 *
	 *             前端代码 <form name="Form2" action="springUpload"
	 *             method="post"enctype="multipart/form-data">
	 *             <h1>使用spring mvc提供的类的方法上传文件</h1>
	 *             <input type="file" name="file"> <input type="submit" value=
	 *             "upload"/> </form>
	 */
	public static String springUpload(HttpServletRequest request) throws IllegalStateException, IOException {
		long startTime = System.currentTimeMillis();// 用来检测程序运行时间
		// 项目在容器中的实际发布运行的根路径
		String pathval = request.getSession().getServletContext().getRealPath("/") + "WEB-INF/";
		String saveFilePath = "video/";// 上传文件所在的目录

		/* 构建文件目录 */
		File fileDir = new File(pathval + saveFilePath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator<String> iter = multiRequest.getFileNames();

			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				String filename = file.getOriginalFilename();// 获取上传文件的全名
				String ExtensionName = filename.substring(filename.lastIndexOf(".") + 1);// 获取文件的扩展名如:mp4
				String Millisecond = String.valueOf(System.currentTimeMillis());// 获取当前时间的毫秒数
				String NewFileName = "M" + Millisecond + "." + ExtensionName;// 该文件在系统中的名字
				String FileName = saveFilePath + NewFileName;// 文件存储的路径
				// System.out.println(NewFileName);
				if (file != null) {
					String path = pathval + saveFilePath + NewFileName;
					System.out.println(path);
					// 上传
					file.transferTo(new File(path));
					  return FileName;
				}
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("方法三的运行时间：" + String.valueOf(endTime - startTime) + "ms");
		return null;
	}

	/**
	 * 多文件上传
	 * 
	 * @param file
	 * @param request
	 * @param gid
	 * @return fileName 
	 * <form name="Form2" action="fileUpload2" method="post" enctype=
	 *         "multipart/form-data">
	 *         <h1>采用multipart提供的file.transfer方法上传文件</h1>
	 *         <input type="text" name="gid"> <input type="file" name="file"
	 *         multiple="multiple"> <input type="submit"value="upload"
	 *         /> </form>
	 */

	public static String saveFile(CommonsMultipartFile[] file, HttpServletRequest request, Long gid) {
		long startTime = System.currentTimeMillis();// 用来检测程序运行时间
		String pathval = request.getSession().getServletContext().getRealPath("/") + "WEB-INF/";
		// 根据配置文件获取服务器图片存放路径
		String saveFilePath = "imgs/" + gid + "/";
		/* 构建文件目录 */
		File fileDir = new File(pathval + saveFilePath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		List<String> fileName = new ArrayList<String>();
		if (file != null && file.length > 0) {
			try {
				for (int i = 0; i < file.length; i++) {
					if (!file[i].isEmpty()) {
						String filename = file[i].getOriginalFilename();
						String ExtensionName = filename.substring(filename.lastIndexOf(".") + 1);// 获取文件的扩展名如:mp4
						String Millisecond = String.valueOf(System.currentTimeMillis());// 获取当前时间的毫秒数
						String NewFileName = Millisecond + "." + ExtensionName;// 该文件在系统中的名字
						String FileName = saveFilePath + NewFileName;// 文件存储的路径
						String path = pathval + saveFilePath + NewFileName; // 文件储存绝对路径
						File newFile = new File(path);
						fileName.add(FileName);
						// 通过CommonsMultipartFile的方法直接写文件（注意这个时候）
						file[i].transferTo(newFile);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("方法的运行时间：" + String.valueOf(endTime - startTime) + "ms");
		
		return StringUtils.strip(fileName.toString(),"[]");
	}
}