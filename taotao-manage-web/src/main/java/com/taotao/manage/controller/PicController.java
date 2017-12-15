package com.taotao.manage.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.ServerInfo;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.omg.CORBA.StringHolder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.manage.vo.PicUploadResult;

@RequestMapping("/pic")
@Controller
public class PicController {

	private static final String[] IMAGE_TYPES = { ".jpg", ".png", ".bmp", ".jpeg", ".gif" };
	
	private static final ObjectMapper MAPPER = new ObjectMapper();

	@RequestMapping(value="/upload",produces=MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String upload(@RequestParam("uploadFile") MultipartFile multipartFile) throws JsonProcessingException {

		// 放回结果
		PicUploadResult picUploadResult = new PicUploadResult();
		picUploadResult.setError(1);

		// 设置文件是否合法
		Boolean flag = false;

		// 验证后缀
		for (String type : IMAGE_TYPES) {

			if (multipartFile.getOriginalFilename().lastIndexOf(type) > 0) {
				flag = true;
				break;
			}
		}
		if (flag) {
			try {
				// 验证内容
				BufferedImage read = ImageIO.read(multipartFile.getInputStream());
				// 得到图片服务器地址
				String trackerConfig = this.getClass().getClassLoader().getResource("tracker.conf").getPath();
				// 初始化图片服务器
				ClientGlobal.init(trackerConfig);
				// 创建客户端
				TrackerClient trackerClient = new TrackerClient();
				// 创建服务端
				TrackerServer trackerServer = trackerClient.getConnection();
				// 创建StorageServer
				StorageServer storageServer = null;
				// 创建StorageClient
				StorageClient storageClient = new StorageClient(trackerServer, storageServer);

				// 上传文件
				// 获取文件后缀
				String file_ext_name = StringUtils.substringAfterLast(multipartFile.getOriginalFilename(), ".");
				// 参数:文件url,文件后缀,文件信息(null)
				String[] upload_file = storageClient.upload_file(multipartFile.getBytes(), file_ext_name, null);
				// 第一个值是组名，第二个是相对路径；两个字符串拼接后是完整图片路径
				String url = "";
				if (upload_file != null && upload_file.length > 0) {

					String groupName = upload_file[0];
					String fileName = upload_file[1];
					// 获取存储服务器信息
					ServerInfo[] serverInfos = trackerClient.getFetchStorages(trackerServer, groupName, fileName);
					url = "http://" + serverInfos[0].getIpAddr() + "/" + groupName + "/" + fileName;
					
					picUploadResult.setError(0);
					picUploadResult.setUrl(url);
					
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return MAPPER.writeValueAsString(picUploadResult);
	}
}
