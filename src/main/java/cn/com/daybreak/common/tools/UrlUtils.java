package cn.com.daybreak.common.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class UrlUtils {
	/**
	 * 获取url的path部分中的参数值
	 * @param path
	 * @param paramName
	 * @return
	 */
	public static String getPathParamValue(String path, String paramName) {
		String paramValue = "";
		try {
			path = URLDecoder.decode(path,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String[] paths = path.split("\\/");
		for (int i=0; i<paths.length; ++i) {
			if (paths[i].equalsIgnoreCase(paramName)) {
				if (i < paths.length-1)
					paramValue = paths[i+1];
				break;
			}
		}
		
		return paramValue;
	}
}
