package com.lk.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lk.bean.Constant;
import com.lk.bean.CostInfoDO;
import com.lk.service.CostInfoService;
import com.lk.service.UserService;
import net.sf.json.JSONArray;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 消费情况控制层
 * @author likun
 * @date 2018-01-06
 *                             _ooOoo_
 *                            o8888888o
 *                            88" . "88
 *                            (| -_- |)
 *                            O\  =  /O
 *                         ____/`---'\____
 *                       .'  \\|     |//  `.
 *                      /  \\|||  :  |||//  \
 *                     /  _||||| -:- |||||-  \
 *                     |   | \\\  -  /// |   |
 *                     | \_|  ''\---/''  |   |
 *                     \  .-\__  `-`  ___/-. /
 *                   ___`. .'  /--.--\  `. . __
 *                ."" '<  `.___\_<|>_/___.'  >'"".
 *               | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *               \  \ `-.   \_ __\ /__ _/   .-` /  /
 *          ======`-.____`-.___\_____/___.-`____.-'======
 *                             `=---='
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *                     佛祖保佑        永无BUG
 *            佛曰:
 *                   写字楼里写字间，写字间里程序员；
 *                   程序人员写程序，又拿程序换酒钱。
 *                   酒醒只在网上坐，酒醉还来网下眠；
 *                   酒醉酒醒日复日，网上网下年复年。
 *                   但愿老死电脑间，不愿鞠躬老板前；
 *                   奔驰宝马贵者趣，公交自行程序员。
 *                   别人笑我忒疯癫，我笑自己命太贱；
 *                   不见满街漂亮妹，哪个归得程序员？
 */
@Controller
@RequestMapping("/cost")
public class CostController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	/**
	 * 将常量数据用spring从配置文件中读取出来
	 */
	@Value("${pageSize}")
	private int pageSize;
	@Value("${cacheTime}")
	private long cacheTime;

	/**
	 * 使用java 8 以上支持的time包中的转换方式,弃用simpleDateFormat
	 */
	private static final DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private static final DateTimeFormatter sdfH = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private CostInfoService costInfoService;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 新增消费信息页面
	 * @return
	 */
	@RequestMapping("/costAddPage")
	public String Hello() {
		return "costAddInit";
	}

	/**
	 * 消费情况展示页面
	 * @date 2018-01-06
	 * @author likun
	 * @param model
	 * @param pageNum
	 * @return
	 */
	@RequestMapping("/costInfoPage")
	public String showCostInfoPage(ModelMap model, @RequestParam(defaultValue="1") int pageNum){
		//开始分页,默认初始页数为1,size为自己定义
		PageHelper.startPage(pageNum,pageSize);
		Map<String,Object> reqMap = new HashMap<>(2);
		//TODO  此处有bug,后期需要修改
		//从redis中取出登录用户
		String account = redisTemplate.opsForValue().get("account").toString();
		reqMap.put("account",account);
		//查询所有消费信息明细
		List<CostInfoDO> costInfoDOList = costInfoService.selectCostInfoByMap(reqMap);
		// 将转换好的时间存入临时temp中
		for (CostInfoDO infoDO : costInfoDOList) {
			infoDO.setDateTemp(infoDO.getCostTime().format(sdfH));
		}
		//查询当天消费信息金额总和
		List<CostInfoDO> dailyCostList = costInfoService.selectDailyCostMoney(account);
		JSONArray array = getDailyCost(dailyCostList);
		PageInfo<CostInfoDO> pageInfo = new PageInfo<>(costInfoDOList);
		model.addAttribute("costInfoDOList",costInfoDOList);
		model.addAttribute("pageInfo",pageInfo);
		model.addAttribute("array",array);
		return "costInfo";
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
	public ModelMap addCostInfo(HttpServletRequest request) throws IOException, ServletException {
		ModelMap map = new ModelMap();
		Map<String,Object> reqMap = new HashMap<>();
		String costTime = request.getParameter("costTime");
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(1024 * 100);
			//得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
			String savePath = "E:\\uploadPicture";
			File fileOrigin = new File(savePath);
			// 如果文件夹不存在则创建
			if (!fileOrigin.isDirectory()){
				fileOrigin.mkdir();
			}
			System.out.println(savePath);
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("utf-8");
			upload.setFileSizeMax(1024 * 1024);
			upload.setSizeMax(1024 * 1024 * 10);
			List<FileItem> fileList = upload.parseRequest(request);
			for (FileItem fileItem : fileList) {
				if (fileItem.isFormField()) {
					logger.debug(fileItem.getFieldName() + "=" + fileItem.getString("utf-8"));
					reqMap.put(fileItem.getFieldName(),fileItem.getString("utf-8"));
				} else {
					String fileName = fileItem.getName();
					InputStream inputStream = fileItem.getInputStream();
					String pictureName = System.currentTimeMillis()+ fileName;
					reqMap.put("costPictureName",pictureName);
					File file = new File(savePath,pictureName);
					FileOutputStream fos = new FileOutputStream(file);
					byte[] temp = new byte[1024];
					int size;
					// 每次读取1KB，直至读完
					while ((size = inputStream.read(temp)) != -1) {
						fos.write(temp, 0, size);
					}
					logger.info("File load success.");
					inputStream.close();
					fos.close();
				}
			}
			if(insertCostInfo(reqMap)){
				map.put(Constant.DATA_CODE,Constant.SUCCESS_CODE);
				map.put(Constant.DATA_MSG,Constant.UPLOAD_FILE_SUCCESS);
				return map;
			}
			map.put(Constant.DATA_CODE,Constant.FAIL_CODE);
			map.put(Constant.DATA_MSG,Constant.UPLOAD_FILE_FAIL);
			return map;
		}catch (Exception e){
			e.printStackTrace();
			map.put(Constant.DATA_CODE,Constant.FAIL_CODE);
			map.put(Constant.DATA_MSG,Constant.UPLOAD_FILE_FAIL);
			return map;
		}
	}

	/**
	 * 查询该数据是否有上传文件
	 * @return
	 */
	@RequestMapping("/checkPicture")
	@ResponseBody
	public ModelMap checkUploadPictureOrNot(String id) throws IOException {
		ModelMap map = new ModelMap();
		Map<String,Object> reqMap = new HashMap<>(1);
		reqMap.put("id",id);
		//查询该id是否有上传图片
		List<CostInfoDO> costList = costInfoService.checkUploadPictureOrNot(reqMap);
		if (Objects.isNull(costList)){
			map.put(Constant.DATA_CODE,Constant.FAIL_CODE);
			map.put(Constant.DATA_MSG,Constant.SEARCH_MESSAGE_FAIL);
			return map;
		}
		String costPictureName = costList.get(0).getCostPictureName();
		if (StringUtils.isBlank(costPictureName)){
			map.put(Constant.DATA_CODE,Constant.FAIL_CODE);
			map.put(Constant.DATA_MSG,Constant.SEARCH_MESSAGE_FAIL);
			return map;
		}
		//将对应id的图片名称存进redis中,设置缓存时间为10s
		redisTemplate.opsForValue().set(id,costPictureName,cacheTime,TimeUnit.SECONDS);
		map.put(Constant.DATA_CODE,Constant.SUCCESS_CODE);
		return map;
	}

	/**
	 * 下载图片详情
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/downLoadPicture")
	public void downLoadPicture(HttpServletRequest request, HttpServletResponse response)throws IOException {
		//从redis中获取指定对象的图片名称
		String fileName = redisTemplate.opsForValue().get(request.getParameter("id").toString()).toString();
		//设置文件MIME类型
		response.setContentType(request.getServletContext().getMimeType(fileName));
		//设置Content-Disposition
		response.setHeader("Content-Disposition", "attachment;filename="+fileName);
		String fullFilePathName = "E:\\uploadPicture\\"+fileName;
		InputStream in = new FileInputStream(fullFilePathName);
		OutputStream out = response.getOutputStream();
		//写文件
		try {
			int b;
			while((b=in.read())!= -1) {
				out.write(b);
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			in.close();
			out.close();
		}
	}

	/**
	 * 消费信息存入数据库中
	 * @date 2018-01-07
	 * @param reqMap
	 * @return
	 */
	private boolean insertCostInfo(Map<String,Object> reqMap){
		reqMap.put("account",redisTemplate.opsForValue().get("account"));
		int count = costInfoService.addCostInfo(reqMap);
		if (count > 0) {
			return true;
		}
		return false;
	}

	private JSONArray getDailyCost(List<CostInfoDO> dailyCostList){
		List<Integer> costMoneyList = new ArrayList<>();
		List<String> costTimeList = new ArrayList<>();
		for (CostInfoDO costInfoDO : dailyCostList){
			costMoneyList.add(costInfoDO.getCostMoney());
			costTimeList.add(sdf.format(costInfoDO.getCostTime()));
		}
		JSONArray array = new JSONArray();
		array.add(costMoneyList);
		array.add(costTimeList);
		System.out.println(array.toString());
		return array;
	}
}
