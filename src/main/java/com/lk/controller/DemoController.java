package com.lk.controller;

import com.lk.bean.Constant;
import com.lk.service.UserService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Controller
@RequestMapping("/user")
public class DemoController {
	private static Logger logger = LoggerFactory.getLogger(DemoController.class);

	@Autowired
	private UserService userService;
	
	@RequestMapping("/hello")
	public String Hello(ModelMap model) {
		logger.debug("-------------进入方法成功!-----------------------");
		model.addAttribute("name", "dashuaibi");
		return "hello";
	}

	/**
	 * 表单提交
	 * @date 2018-01-04
	 * @author likun
	 * @param request
	 * @throws FileUploadException
	 * @throws IOException
	 */
	@RequestMapping("/addCostInfo")
	@ResponseBody
	public ModelMap addUser(HttpServletRequest request) throws IOException, ServletException {
		ModelMap map = new ModelMap();
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(1024 * 100);
			//得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
			String savePath = request.getServletContext().getRealPath("/WEB-INF/upload");
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("utf-8");
			upload.setFileSizeMax(1024 * 1024);
			upload.setSizeMax(1024 * 1024 * 10);
			List<FileItem> fileList = upload.parseRequest(request);
			for (FileItem fileItem : fileList) {
				if (fileItem.isFormField()) {
					logger.debug(fileItem.getFieldName() + "=" + fileItem.getString("utf-8"));
				} else {
					String fileName = fileItem.getName();
					long now = System.currentTimeMillis();
					InputStream inputStream = fileItem.getInputStream();
					File file = new File(savePath + now+fileName);
					file.createNewFile();
					FileOutputStream fos = new FileOutputStream(file);
					byte temp[] = new byte[1024];
					int size;
					while ((size = inputStream.read(temp)) != -1) { // 每次读取1KB，直至读完
						fos.write(temp, 0, size);
					}
					logger.info("File load success.");
					inputStream.close();
					fos.close();
					map.put(Constant.DATA_CODE,Constant.SUCCESS_CODE);
					map.put(Constant.DATA_MSG,Constant.UPLOAD_FILE_SUCCESS);
					return map;
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			map.put(Constant.DATA_CODE,Constant.FAIL_CODE);
			map.put(Constant.DATA_MSG,Constant.UPLOAD_FILE_FAIL);
			return map;
		}
		return null;
	}
}
