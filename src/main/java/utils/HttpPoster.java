package utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpPoster
{
	public static String sendJsonToRecommend(String jsonName,String json,String url)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put( jsonName, json);
		String responseContent = "";
		try
		{
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost( url); // 创建HttpPost
			List<NameValuePair> formParams = new ArrayList<NameValuePair>(); // 构建POST请求的表单参数
			for (Map.Entry<String, String> entry : map.entrySet())
			{
				formParams.add( new BasicNameValuePair(entry.getKey(),
						entry.getValue()));
			}
			httpPost.setEntity( new UrlEncodedFormEntity(formParams, "UTF-8" ));
			HttpResponse response = null;
			response = client.execute( httpPost); // 执行POST请求
			HttpEntity entity = response.getEntity(); // 获取响应实体
			if ( null != entity)
			{
				responseContent = EntityUtils. toString(entity, "ISO8859-1");
				EntityUtils. consume(entity); // Consume response content
			}
			client. getConnectionManager(). shutdown();
		} catch (ConnectException c)
		{
			responseContent = "主机无法请求到地址：" + url ;
		} catch (Exception e)
		{
			responseContent = "NoSendSucceed " + e.getMessage();
			e.printStackTrace();
		}
		return responseContent;
	}
	public static String sendPost(String url)
	{
		String responseContent = "";
		try
		{
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost( url); // 创建HttpPost
			HttpResponse response = null;
			response = client.execute( httpPost); // 执行POST请求
			HttpEntity entity = response.getEntity(); // 获取响应实体
			if ( null != entity)
			{
				responseContent = EntityUtils. toString(entity, "ISO8859-1");
				EntityUtils. consume(entity); // Consume response content
			}
			client. getConnectionManager(). shutdown();
		} catch (ConnectException c)
		{
			responseContent = "主机无法请求到地址：" + url ;
		} catch (Exception e)
		{
			responseContent = "NoSendSucceed " + e.getMessage();
			e.printStackTrace();
		}
		return responseContent;
	}
}
