package cn.nb.code;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 	配置文件读取类
 *  @author xuyao
 * */
public class PropertiesUtil {

	private static Properties p = new Properties();

	private static Logger logger=LoggerFactory.getLogger(PropertiesUtil.class);
	
	static{
		try {
			InputStream in = PropertiesUtil.class.getResourceAsStream("/config.properties");
			p.load(in);
		} catch (IOException e) {
			logger.error("load app.properties error", e);			
		}
	}

	public static String get(String key) {
		return (String)p.get(key);
	}
	
}
