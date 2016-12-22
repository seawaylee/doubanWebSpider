package cn.edu.ncut.doubanWebSpider.spider.process;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.HttpHost;

import cn.edu.ncut.doubanWebSpider.model.BookInfo;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import utils.ConfigUtil;

public class BookInfoProcessor implements PageProcessor
{
	public static final String COOKIE = "bid=\"Nu4sBFrYFrQ\"; gr_user_id=90e8f0bd-c992-49e9-b769-67b7c813c60c; ll=\"108288\"; viewed=\"26414020_10750155_24703171\"; ps=y; dbcl2=\"142128977:lKgaClsIDDI\"; ct=y; ck=\"uNFe\"; ap=1; gr_session_id_22c937bbd8ebd703f2d8e9445f7dfd03=50785b47-22b1-44e3-b7a3-fd135a23cd6e; __utmt=1; push_noty_num=0; push_doumail_num=1; __utma=30149280.679528336.1449906405.1456155378.1456157975.15; __utmb=30149280.15.10.1456157975; __utmc=30149280; __utmz=30149280.1456157975.15.11.utmcsr=douban.com|utmccn=(referral)|utmcmd=referral|utmcct=/tag/%E5%B0%8F%E8%AF%B4/book; __utmv=30149280.14212";
//	public static final String COOKIE = "bid=\"cLFu0ljxKRE\"; gr_user_id=71a8fed9-c730-4e6b-815f-813f99b450f4; ll=\"108288\"; ap=1; viewed=\"3283973_5257905\"; __utmt=1; dbcl2=\"142128977:lKgaClsIDDI\"; ck=\"uNFe\"; gr_session_id_22c937bbd8ebd703f2d8e9445f7dfd03=c78404b1-2e3a-4bb6-afd9-45e6cfdc0d41; __utmt_douban=1; __utma=30149280.1220747617.1453995829.1455611943.1456219113.6; __utmb=30149280.4.10.1456219113; __utmc=30149280; __utmz=30149280.1456219113.6.5.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; __utmv=30149280.14212; push_noty_num=0; push_doumail_num=1";
	public static final String[] AGENTS = new String[]{
			"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2471.2 Safari/537.36",
			"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.109 Safari/537.36",
			"Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.6) Gecko/20091201 Firefox/3.5.6",
			"Mozilla/5.0 (Windows NT 6.2) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.12 Safari/535.11",
			"Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)"}; 
	// TODO:设置代理IP
	private Site site = Site.me().setRetryTimes(3).setCharset("UTF-8")
			.setUserAgent(AGENTS[new Random().nextInt(5)])
//			.addHeader("Accept", "image/webp,image/*,*/*;q=0.8")
//			.addHeader("Accept-Encoding", "gzip, deflate, sdch")
//			.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6")
//			.addHeader("Cache-Control", "max-age=0")
//			.addHeader("Connection", "keep-alive")
//			.addHeader("Host", "www.douban.com")
//			.setHttpProxy(new HttpHost("218.63.208.223",3128))//61.50.101.146   80
//			.setSleepTime(Integer.parseInt(ConfigUtil.getProperty("spider", "sleeptime")))
			.setSleepTime(0)
			.addCookie("Cookie", COOKIE);

	public Site getSite()
	{
		return site;
	}

	public void process(Page page)
	{
		try
		{
			site.setUserAgent(AGENTS[new Random().nextInt(5)]);
//			System.out.println(site.getUserAgent());
//			Long a = System.currentTimeMillis();
			Thread.sleep(new Random().nextInt(10)*100);
//			System.out.println("Sleep : " + (System.currentTimeMillis() - a)/1000.0);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		// 当前页面是图书列表页面
		if (page.getUrl().regex("http://www\\.douban\\.com/tag/\\S+/book*")
				.match())
		{
			processForBookList(page);
		}
		// 当前页面是图书详细信息页面
		else if (page.getUrl()
				.regex("http://book\\.douban\\.com/subject/\\S+/?from=tag_all")
				.match())
		{
			processForBookInfo(page);
		}
	}

	/**
	 * 图书列表 抽取图书详细信息URL 获取下一页图书列表URL
	 * 
	 * @author seawayLee 2016年2月23日
	 */
	public void processForBookList(Page page)
	{
		// 下一页的Url
		String nextPageUrlXpath = "//span[@class='next']/a/@href";
		// 如果当前页有数据 ，不会返回空，否则返回空
		String hasContentXpath = "//div[@class='grid-16-8 clearfix']/div[@class='article']/div[@class='mod book-list']/dl";
		// 获取图书内容地址
		String bookUrlXpath = "//div[@class='mod book-list']/dl/dd/a/@href";
		// 获取标签
		String tag = page.getUrl().toString().split("/")[4];
		List<String> bookUrls = page.getHtml().xpath(bookUrlXpath).all();
		String nextPageUrl = page.getHtml().xpath(nextPageUrlXpath).toString();
		if( !(page.getHtml().xpath(hasContentXpath).get() == null || page.getHtml().xpath(hasContentXpath).get().toString().trim().equals("")))
		{
			// 把图书详细内容URL加入爬取队列
			for (String bookUrl : bookUrls)
			{
				page.addTargetRequest(new Request(bookUrl).setPriority(1)
						.putExtra("tag", tag));
//				System.out.println("图书详细-入列:" + bookUrl);
			}
			// 把下一页图书列表加入队列
			page.addTargetRequest(new Request(nextPageUrl).setPriority(0));
		}
	}

	/**
	 * 图书详细信息
	 * 
	 * @author seawayLee 2016年2月23日
	 */
	public void processForBookInfo(Page page)
	{
		/*
		 * no; title; authorname; press; orititle; translator; publishtime;
		 * pages; price; binding; series; isbn; rating; comments; contentinfo;
		 * authorinfo; usertags; alsolikeebook; alsolikebook; shortcommentsnum;
		 * bookcommentsnum; readingnotesnum; bookurl; discussnum; readingsnum;
		 * hasreadnum; wantreadnum; tag;
		 */
		try
		{
			BookInfo book = new BookInfo();
			String infoDiv = page.getHtml().xpath("//div[@class='subject clearfix']/div[@id='info']")
					.toString();
			Map<String, String> infoMap = getInfoMap(infoDiv); 

			book.setNo(page.getUrl().toString().split("/")[4]);
			book.setTitle(page.getHtml()
					.xpath("//div[@id='wrapper']/h1/span/text()").toString());

			book.setAuthorname(infoMap.get("作者"));
			book.setPress(infoMap.get("出版社"));
			book.setOrititle(infoMap.get("原作名"));
			book.setTranslator(infoMap.get("译者"));
			book.setBinding(infoMap.get("装帧"));
			book.setSeries(infoMap.get("丛书"));
			book.setIsbn(infoMap.get("ISBN"));
			//出版年份
			try
			{
				String dateStr = infoMap.get("出版年");
				dateStr = dateStr.replace("年", "-").replace("月","-").replace("日", "");
				if(dateStr.endsWith("-"))
					dateStr = dateStr.substring(0,dateStr.length() - 1);
				int len = dateStr.split("-").length;
				if(len == 1)
				{
					SimpleDateFormat df = null;
					if(dateStr.length() == 4)
					{
						 df = new SimpleDateFormat("yyyy");
					}
					else if(dateStr.length() == 6)
					{
						dateStr = dateStr.substring(0,4) + "-" + dateStr.substring(4);
						df = new SimpleDateFormat("yyyy-MM");
					}
					else if(dateStr.length() == 8)
					{
						dateStr = dateStr.substring(0,4) + "-" + dateStr.substring(4,6) + "-" + dateStr.substring(6);
						df = new SimpleDateFormat("yyyy-MM-dd");
					}
					book.setPublishtime(df.parse(dateStr));
				}
				else if(len == 2)
				{
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
					book.setPublishtime(df.parse(dateStr));
				}
				else if(len == 3)
				{
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					book.setPublishtime(df.parse(dateStr));
				}
			}
			catch(Exception e)
			{
				book.setPublishtime(null);
			}
			//页数
			try
			{
				book.setPages(Integer.parseInt(infoMap.get("页数").replace("页", "")));
			}
			catch(Exception e)
			{
				book.setPages(0);
			}
			//价格
			try
			{
				book.setPrice(Double.parseDouble(infoMap.get("定价").replace("元", "").replace("CNY", "").replace("$", "")
						.replace("USD", "").replace("US", "").replace("￥", "").replace("CAD", "").replace("NTD", "")
						.replace("D", "").replace("港币", "").replace("新台币", "").replace("NT", "").replace("TW", "")
						.replace("GBP", "").replace("美元", "").replace("美", "").replace("英镑", "").replace("HK", "")));
			}
			catch(Exception e)
			{
				e.printStackTrace();
				book.setPrice(null);
			}
			//评分
			try
			{
			book.setRating(Double.parseDouble(page.getHtml()
					.xpath("//div[@class='rating_self clearfix']/strong/text()")
					.toString()));
			}
			catch(Exception e)
			{
				book.setRating(null);
			}
			//评论数
			try
			{
				book.setComments(Integer.parseInt(page.getHtml()
						.xpath("//div[@class='rating_sum']/span/a/span/text()")
						.toString()));
			}
			catch(Exception e)
			{
				book.setComments(0);
			}
			// 内容简介
			try
			{
				book.setContentinfo(page.getHtml()
					.xpath("//div[@id='link-report']/div/div[@class='intro']/p/text()").all()
					.toString());
			}
			catch(Exception e)
			{
				try
				{
				book.setContentinfo(page.getHtml()
						.xpath("//div[@id='link-report']/span[@class='short']/div[@class='intro']/p/text()").all()
						.toString());
				}
				catch(Exception e1)
				{
					book.setContentinfo("");
				}
			}
			// 作者简介
			try
			{
				book.setAuthorinfo(page.getHtml()
						.xpath("//div[@class='indent']/div/div/p/text()").all().get(1)
						.toString());
			}
			catch(Exception e)
			{
				try
				{
					book.setAuthorinfo(page.getHtml()
							.xpath("//div[@class='indent']/span[@class='short']/div/p/text()").all().get(2)
							.toString());
				}
				catch(Exception e1)
				{
					book.setAuthorinfo("");
				}
			}
			// 用户使用的标签
			try
			{
				String userTagsStr = "";
				List<String> userTags = page.getHtml()
						.xpath("//a[@class='tag']/text()").all();
				for (String tag : userTags)
				{
					userTagsStr += tag;
					userTagsStr += "&";
				}
				userTagsStr = userTagsStr.substring(0, userTagsStr.lastIndexOf("&"));
				book.setUsertags(userTagsStr);
			}
			catch(Exception e)
			{
				book.setUsertags("");
			}
			// 读书的人也喜欢读这些 电子书
			try
			{
				String alsoLikeEBookStr = ""; 
				List<String> eBooks = page.getHtml()
						.xpath("//div[@class='block5 subject_show']/div[@class='content clearfix']/dl/dd/div[@class='title']/a/text()")
						.all();
				for (String ebook : eBooks)
				{
					alsoLikeEBookStr += ebook;
					alsoLikeEBookStr += "&";
				}
				alsoLikeEBookStr = alsoLikeEBookStr.substring(0,alsoLikeEBookStr.lastIndexOf("&"));
				book.setAlsolikeebook(alsoLikeEBookStr);
			}
			catch(Exception e)
			{
				book.setAlsolikeebook("");
			}
			// 读书的人也喜欢读 这些书
			try
			{
				String alsoLikeBookStr = "";
				List<String> books = page.getHtml()
						.xpath("//div[@class='block5 subject_show knnlike']/div[@class='content clearfix']/dl/dd/a/text()")
						.all();
				for (String book_ : books)
				{
					alsoLikeBookStr += book_;
					alsoLikeBookStr += "&";
				}
				alsoLikeBookStr = alsoLikeBookStr.substring(0,alsoLikeBookStr.lastIndexOf("&"));
				book.setAlsolikebook(alsoLikeBookStr);
			}
			catch(Exception e)
			{
				book.setAlsolikebook("");
			}
			// 书详细信息url
			book.setBookurl(page.getUrl().toString());
			// 短评  书评  读书笔记  正在讨论
			try
			{
				book.setShortcommentsnum(Integer.parseInt(page.getHtml()
						.xpath("//div[@class='mod-hd']/h2/span[@class='pl']/a/text()")
						.toString().replace("全部", "").replace("条", "")
						.replace(" ", "")));
				book.setBookcommentsnum(Integer.parseInt(page.getHtml()
						.xpath("//div[@class='ugc-mod']/h2/span[@class='pl']/a/span/text()")
						.toString()));  
				book.setReadingnotesnum(Integer.parseInt(page.getHtml()
						.xpath("//div[@class='ugc-mod reading-notes']/div[@class='hd']/h2/span[@class='pl']/a/span/text()")
						.toString()));
			}
			catch(Exception e)
			{
				book.setShortcommentsnum(0);
				book.setBookcommentsnum(0);
				book.setReadingnotesnum(0);
			}
			// 正在豆瓣App讨论这本书
			try
			{
				book.setDiscussnum(Integer.parseInt(page.getHtml()
						.xpath("//div[@class='get_douban_app']/h2/text()")
						.toString().split("人正在")[0].replace("+", "")));
			}
			catch(Exception e)
			{
				book.setDiscussnum(0);
			}
			// 在读  读过  想读
			try
			{
				book.setReadingsnum(Integer.parseInt(page.getHtml()
						.xpath("//div[@id='collector']/p/a/text()").all().get(0).replace("人在读","")));
				book.setHasreadnum(Integer.parseInt(page.getHtml()
						.xpath("//div[@id='collector']/p/a/text()").all().get(1).replace("人读过","")));
				book.setWantreadnum(Integer.parseInt(page.getHtml()
						.xpath("//div[@id='collector']/p/a/text()").all().get(2).replace("人想读","")));
			}
			catch(Exception e)
			{
				book.setReadingsnum(0);
				book.setHasreadnum(0);
				book.setWantreadnum(0);
			}
			// 图书标签
			try
			{
				book.setTag(page.getRequest().getExtra("tag").toString());
			}
			catch(Exception e)
			{
				book.setTag("");
			}
			page.putField("bookInfo", book);
			// 书评单爬吧
		} catch (Exception e)
		{
			System.out.println("XPath解析出现异常"+page.getUrl());
		}
	}

	// 处理图书信息中的infoDiv
	private Map<String, String> getInfoMap(String html)
	{
		String result = html
				.replaceAll("<[/]?span>", "")
				.replace("<span class=\"pl\">", "")
				.replaceAll("<a.+?>", "")
				.replace("</a>", "")
				.replace("\n", "")
				.replace("<br />", "\n")
				.replace("<br/>", "\n")
				.replace("<br>", "\n")
				.replaceAll("<div.+?>", "")
				.replace(" ", "")
				.replace("&nbsp;", " ")
				.replace("&middot;", "-")
				.replace("</div>", "");
		
		String[] lines = result.split("\n");
		Map<String, String> map = new HashMap<String, String>();
		for (String line : lines)
		{
			map.put(line.split(":")[0], line.split(":")[1]);
		}
		if (map.get("丛书") == null)
			map.put("丛书", "");
		if (map.get("译者") == null)
			map.put("译者", "");
		if (map.get("原作名") == null)
			map.put("原作名", "");
		if(map.get("页数") == null)
			map.put("页数","0");
		if(map.get("装帧") == null)
			map.put("装帧","");
		
		return map;
	}
}
