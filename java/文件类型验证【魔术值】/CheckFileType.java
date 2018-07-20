package plus.ojbk.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

public class CheckFileType {
	
	private static final Logger logger = LoggerFactory.getLogger(CheckFileType.class);
	
	public final static Map<String, String> FILE_TYPE = new HashMap<String, String>();
	
	/**
	 * 魔术值开头相同的 要同时判断 后缀 与魔术对应 方可确定该文件 
	 */
	static {
		FILE_TYPE.put("mp3", "494433");
		FILE_TYPE.put("jpg", "FFD8FF");
		FILE_TYPE.put("png", "89504E47");
		FILE_TYPE.put("gif", "47494638");
		FILE_TYPE.put("xlsx", "504B0304"); // 与zip 相同开头 
		FILE_TYPE.put("xls", "D0CF11E0");
		FILE_TYPE.put("zip", "504B0304");
		FILE_TYPE.put("doc", "D0CF11E0"); //与xls 相同开头
		FILE_TYPE.put("pdf", "255044462D312E"); //pdf文件
		FILE_TYPE.put("docx", "504B0304");//与zip 相同开头 
		FILE_TYPE.put("rar", "52617221");
		//如果以上 文件类型不能满足你 ，问题不大 。  上传你需要 的文件  64行log打印的 魔术值 抬头自己加入map中即可 
	}
	
	/**
	 * 调用示例 
	 *
	 *	JSONObject  obj = CheckFileType.getFileByFile(multipartFile);
	 *		if(!obj.getBoolean("state")) {
	 *			System.err.println("文件不合法");
	 *		}
	 *
	 * 
	 */
	public final static JSONObject getFileByFile(MultipartFile file) throws IOException {
		byte[] b = new byte[50];
		String fileName =file.getOriginalFilename();
		String fileType= fileName.substring(fileName.lastIndexOf(".") + 1);
		InputStream is =file.getInputStream();
		is.read(b);
		JSONObject obj = getFileType(b,fileType);
		is.close();
		return obj;
	}
	
	/**
	 * 验证文件
	 */
	public final static JSONObject getFileType(byte[] b,String fileType) {
		String fileTypeHex = String.valueOf(getFileHexString(b));
		logger.info("文件的魔术值 - {}",fileTypeHex);
		logger.info("未验证的文件后缀  - {}",fileType);
		JSONObject obj = new JSONObject();
		if(fileTypeHex.toUpperCase().startsWith(FILE_TYPE.get(fileType))) {
			obj.put("state", true);
			obj.put("type", fileType);
			obj.put("msg", "文件合法!");
		}else {
			obj.put("state", false);
			obj.put("msg", "未知文件!");
		}
		return obj;
	}
	
	/**
	 * 获取文件的字节码
	 */
	public final static String getFileHexString(byte[] b) {
		StringBuilder stringBuilder = new StringBuilder();
		if (b == null || b.length <= 0) {
			return "";
		}
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
}
