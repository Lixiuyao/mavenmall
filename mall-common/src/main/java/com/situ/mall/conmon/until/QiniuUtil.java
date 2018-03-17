package com.situ.mall.conmon.until;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.poi.openxml4j.opc.internal.unmarshallers.PackagePropertiesUnmarshaller;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

public class QiniuUtil {
	
	private static final String CONFIG_AK = "qiniu.accesskey";
	private static final String CONFIG_SK = "qiniu.secretkey";
	private static final String CONFIG_BUCKET = "qiniu.bucket";
	private static final String CONFIG_SERVER = "qiniu.server";
	
	private static final Auth auth;
	private static final UploadManager uploadManager;
	private static final String buckeName;
	private static final String server;
	
	static{
		//构造一个带指定Zone对象的配置类
		auth =Auth.create(PropertiesUtil.getProperty(CONFIG_AK), 
				PropertiesUtil.getProperty(CONFIG_SK));
		Configuration cfg = new Configuration(Zone.zone0());
		//...其他参数参考类注释
		
		
		uploadManager = new UploadManager(cfg);
		buckeName = PropertiesUtil.getProperty(CONFIG_BUCKET);
		server = PropertiesUtil.getProperty(CONFIG_SERVER);
		}
	/*
	 * 上传单张图片，返回值是图片在七牛云的名字
	 */
	public static String uploadImage(byte[] buffer){
		//默认不指定key的情况下，以文件内容的hash值作为文件名
				String key = null;
				String upToken = auth.uploadToken(buckeName);
				try {
				    Response response = uploadManager.put(buffer, key, upToken);
				    //解析上传成功的结果
				    DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
				    System.out.println(putRet.key);
				    System.out.println(putRet.hash);
				    return putRet.key;
				} catch (QiniuException ex) {
				    Response r = ex.response;
				    System.err.println(r.toString());
				    try {
				        System.err.println(r.bodyString());
				    } catch (QiniuException ex2) {
				        //ignore
				    }
				}
				return "";
		
		
		
		
		
		
		
		
		
		
	
		
	}
	public static Object getUrl(String fileName) {
		
		
		
		try {
			String encodedFileName= URLEncoder.encode(fileName, "utf-8");
			String finalUrl = String.format("%s/%s", server, encodedFileName);
			System.out.println(finalUrl);
			return finalUrl;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
}
