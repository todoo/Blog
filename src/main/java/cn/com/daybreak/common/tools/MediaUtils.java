package cn.com.daybreak.common.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MediaUtils {
	public final static String MEDIA_ROOT_PATH = MediaUtils.class.getClassLoader().getResource("../resources/").getPath();
	public static long lastMediaTime;//最后一次生成媒体名称的时间戳
	
	public static String getUniqueMediaName() {
		long timestamp = System.currentTimeMillis();
		if (timestamp == lastMediaTime) {
			timestamp++;
		}
		lastMediaTime = timestamp;
		return "" + timestamp;
	}
	
	public static boolean writeMediaToLocalPath(InputStream in, String path, String mediaName) {
		try {
			String mediaPath = MEDIA_ROOT_PATH + path + mediaName;
			
			File file = new File(mediaPath);
		
			FileOutputStream out = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			while(in.read(buffer)>0) {
				out.write(buffer);
			}
			
			out.flush();
			out.close();
			
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
