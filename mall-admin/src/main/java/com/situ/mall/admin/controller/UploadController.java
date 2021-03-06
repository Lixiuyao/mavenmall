package com.situ.mall.admin.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.situ.mall.conmon.until.JsonUtils;
import com.situ.mall.conmon.until.QiniuUtil;

@Controller
@RequestMapping("/upload")
public class UploadController {
	
	@RequestMapping(value="/uploadPic",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadPic(MultipartFile pictureFile){
		String fileName ="";
		try {
			fileName = QiniuUtil.uploadImage(pictureFile.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Map<String, Object> map = new HashMap<>();
		map.put("fileName", fileName);
		map.put("url",QiniuUtil.getUrl(fileName));
		return map;
	}
	
	
	private String uploadByLocal(MultipartFile pictureFile) {
		//为了防止文件重名（重名文件会覆盖丢失）,所以生成一个不重复的随机的名字dcf38fd6925c44528d6c402d02c04d7b
		String name = UUID.randomUUID().toString().replace("-", "");
		System.out.println(pictureFile);
		String ext = FilenameUtils.getExtension(pictureFile.getOriginalFilename());
		System.out.println(ext);
		String fileName = name+"."+ext;
		String filePath="D:\\pic\\"+fileName;
		try {
			pictureFile.transferTo(new File(filePath));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}
	@RequestMapping(value="/multPicUpload",method=RequestMethod.POST)
	@ResponseBody
	public String multiPicUpload(MultipartFile pictureFile) {
		System.out.println("UploadManagerController.multiPicUpload()");
		try {
			//为了防止文件重名（重名文件会覆盖丢失）,所以生成一个不重复的随机的名字dcf38fd6925c44528d6c402d02c04d7b
			String name = UUID.randomUUID().toString().replace("-", "");
			//jpg、png
			String ext = FilenameUtils.getExtension(pictureFile.getOriginalFilename());
			String fileName = name + "." + ext;//dcf38fd6925c44528d6c402d02c04d7b.png
			String filePath = "D:\\pic\\" + fileName;
			try {
				pictureFile.transferTo(new File(filePath));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			String url = "/pic/" + fileName;

			Map<String, Object> map = new HashMap<>();
			map.put("error", 0);
			map.put("fileName", fileName);
			map.put("url", url);
			return JsonUtils.objectToJson(map);
		} catch (Exception e) {
			Map<String, Object> map = new HashMap<>();
			//必须写这个
			map.put("error", 1);
			map.put("message", "图片上传失败");
			return JsonUtils.objectToJson(map);
		}
	}
	
	public static void main(String[] args) {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		System.out.println(uuid);
		
	}
}
