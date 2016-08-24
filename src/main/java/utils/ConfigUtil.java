package utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil
{
	//从配置文件获取图书标签
		public  static String getProperty(String fileName,String propName)
		{
			try
			{
				Properties props = null;
				InputStream in = RedisUtil.class.getClassLoader().getResourceAsStream(fileName+".properties");
				props = new Properties();
				props.load(in);
				return props.getProperty(propName);
			} catch (Exception e)
			{
				System.out.println("从配置文件获取路径失败");
				e.printStackTrace();
				return null;
			}
		}
}
