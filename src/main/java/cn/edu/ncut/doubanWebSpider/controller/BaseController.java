package cn.edu.ncut.doubanWebSpider.controller;

import cn.edu.ncut.doubanWebSpider.dao.BookCommentMapper;
import cn.edu.ncut.doubanWebSpider.dao.SimpleBookInfoMapper;
import cn.edu.ncut.doubanWebSpider.model.BookComment;
import cn.edu.ncut.doubanWebSpider.model.SimpleBookInfo;
import cn.edu.ncut.doubanWebSpider.model.extend.BarInfoData;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import us.codecraft.webmagic.selector.Json;
import utils.HttpPoster;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BaseController
{

	@Autowired
	private SimpleBookInfoMapper simpleBookInfoMapper;
	@Autowired
	private BookCommentMapper bookCommentMapper;

	/*
	 * 可以做的统计图
	 * 1.图书不同标签下的图书所占比例（查数量即可）
	 * 2.根据标签对图书评分进行排序
	 * 3.评论数最多的10本书
	 */
	@RequestMapping("douban")
	public String showPage(ModelMap model)
	{
		System.out.println("----------");
		model.put("test", "test");
		return "douban";
	}

	/**
	 * 根据书名查询信息
	 *
	 * @author 李熙伟 2016年3月4日
	 */
	@RequestMapping(value = "searchByName")
	public @ResponseBody String searchByName(
			@RequestParam(value = "name", required = true) String name)
	{
		System.out.println("查询关键字:" + name);
		String title = '%' + name.toLowerCase() + '%';
		List<SimpleBookInfo> books = simpleBookInfoMapper.selectByName(title);
		String result = "";
		for (SimpleBookInfo book : books)
		{
			result += book.toString();
		}
		return result;
	}

	/**
	 * 查看基本信息数量
	 *
	 * @author 李熙伟 2016年3月4日
	 */
	@RequestMapping(value = "getAmount/{type}")
	public @ResponseBody Integer getAmountByType(@PathVariable String type,HttpServletRequest request)
	{
		if (request.getHeader("x-forwarded-for") == null)
		{
			String ip = request.getRemoteAddr()+":" + request.getRemotePort();
			System.out.println(ip);
//			RedisUtil.sadd("proxy_ip", ip);
		}
		else
		{
			System.out.println(request.getHeader("x-forwarded-for"));
		}
		Integer result = 0;
		if (type.equals("book"))
		{
			SimpleBookInfo record = new SimpleBookInfo();
			result = simpleBookInfoMapper.selectCount(record);
			// System.out.println("图书信息数 "+result );
		} else
		{
			BookComment record2 = new BookComment();
			result = bookCommentMapper.selectCount(record2);
			// System.out.println("书评信息数 "+result);
		}
		return result;
	}

	/**
	 * 热门标签图书数量分布
	 *
	 * @author 李熙伟 2016年3月4日
	 */
	@RequestMapping("getBookCount")
	public @ResponseBody BarInfoData getBookCount()
	{
		List<SimpleBookInfo> result = simpleBookInfoMapper
				.getBookNumByTagStatic(10);
		System.out.println("热门标签图书数量分布" + JSON.toJSONString(result));
		String[] xAxis = new String[result.size()];
		String[] yAxis = new String[result.size()];
		for (int i = 0; i < result.size(); i++)
		{
			xAxis[i] = result.get(i).getTag();
			yAxis[i] = result.get(i).getItemResultAmount().toString();
		}
		BarInfoData data = new BarInfoData(yAxis, xAxis);
		data.setxAxis(xAxis);
		data.setyAxis(yAxis);
		return data;
	}
	/**
	 * 图书评分排序
	 * @author 李熙伟
	 * 2016年3月23日
	 */
	@RequestMapping("getBookRatingSort")
	public @ResponseBody BarInfoData getBookRatingSort()
	{
		List<SimpleBookInfo> result = simpleBookInfoMapper
				.getBookRatingSort(10);
		System.out.println("图书评分排序" + JSON.toJSONString(result));
		String[] xAxis = new String[result.size()];
		String[] yAxis = new String[result.size()];
		for (int i = 0; i < result.size(); i++)
		{
			xAxis[i] = result.get(i).getTitle();
			yAxis[i] = result.get(i).getRating().toString();
		}
		BarInfoData ratingData = new BarInfoData(yAxis, xAxis);
		ratingData.setxAxis(xAxis);
		ratingData.setyAxis(yAxis);
		return ratingData;
	}
	/**
	 * 评论数最多的用户
	 * @author 李熙伟
	 * 2016年3月23日
	 */
	@RequestMapping("getCommentSort")
	public @ResponseBody BarInfoData getCommentSort()
	{
		List<BookComment> result = bookCommentMapper.getCommentNumByUserNoStatistic(10);
		System.out.println("用户评论数分布"+JSON.toJSONString(result));
 		String[] xAxis = new String[result.size()];
 		String[] yAxis = new String[result.size()];
 		for(int i=0;i<result.size();i++)
		{
 			xAxis[i] = result.get(i).getUserno();
 			yAxis[i]  = result.get(i).getItemResultAmount().toString();
 		}
 		BarInfoData commentData = new BarInfoData(yAxis,xAxis);
		commentData.setxAxis(xAxis);
 		commentData.setyAxis(yAxis);
 		return commentData;
	}

	/**
	 * 获取评分值分布
	 * @return
     */
	@RequestMapping("getRatingStatistic")
	public @ResponseBody BarInfoData getRatingStatistic()
	{
		List<BookComment> result = bookCommentMapper.getRatingStatistic();
		System.out.println("评分值分布"+JSON.toJSONString(result));
 		String[] xAxis = new String[result.size()];
 		String[] yAxis = new String[result.size()];
 		for(int i=0;i<result.size();i++)
		{
 			xAxis[i] = result.get(i).getRating().toString();
 			yAxis[i]  = result.get(i).getItemResultAmount().toString();
 		}
		BarInfoData commentData = new BarInfoData(yAxis,xAxis);
 		commentData.setxAxis(xAxis);
 		commentData.setyAxis(yAxis);
 		return commentData;
	}
	/**
	 * 获取评论时间分布
	 */
	@RequestMapping("getCommentTimeStatistic")
	public @ResponseBody BarInfoData getCommentTimeStatistic()
	{
		List<BookComment> result = bookCommentMapper.getCommentTimeStatistic();
		String[] xAxis = new String[result.size()];
		String[] yAxis = new String[result.size()];
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		for (int i = 0; i < result.size(); i++)
		{
			xAxis[i] = df.format(result.get(i).getTime()).substring(0,4);
			yAxis[i] = result.get(i).getItemResultAmount().toString();
		}
		BarInfoData timeData = new BarInfoData(yAxis,xAxis);
		System.out.println("书评时间分布" + JSON.toJSONString(timeData));
		return timeData;
	}
	/**
	 * 接收前台UserNo,向推荐服务发送请求
	 */
	@RequestMapping("getRecommend")
	public @ResponseBody Object getRecommend(@RequestParam(value = "userno", required = true) String userno)
	{
		String url = "http://localhost:5000/getRecommend";
		String response = HttpPoster.sendJsonToRecommend("userno",userno,url);
		String[] booknos = response.substring(1,response.length()-1).split(",");
		List<String> urls = new ArrayList<String>();
		for (int i = 0; i < booknos.length; i++)
		{
			urls.add("http://book.douban.com/subject/" + booknos[i].trim() + "/?from=tag_all");
		}
		List<SimpleBookInfo> books = simpleBookInfoMapper.selectAllByUrl(urls);
		String html = "";
		int count = 0;
		for (SimpleBookInfo book : books)
		{
			html += "<img layer-pid=\"" + book.getId() +  "\" layer-src=\""+book.getImg() + "\" src=\"" + book.getImg() + "\" alt=\""+book.getTitle()+"\""  + " width=\"" + 200 + "\" height=\"" + 300 +"\">";
			count++;
			html +="&nbsp";
			if(count % 5 == 0)
				html += "<br>";
		}
		return html;
	}
}
