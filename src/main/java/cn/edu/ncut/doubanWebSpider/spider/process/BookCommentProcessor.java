package cn.edu.ncut.doubanWebSpider.spider.process;

import cn.edu.ncut.doubanWebSpider.model.BookComment;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class BookCommentProcessor implements PageProcessor
{
	public static final String COOKIE = "gr_user_id=f7ce0ee0-4ede-45c8-bca0-890ce7ff2886; bid=\"UlkkzTCvehw\"; ps=y; ll=\"108288\"; viewed=\"4736167_1440226_2020442_3897837_3821157_3590768_2297549_2130190_26414020\"; regfromurl=http://www.douban.com/people/i_m_huangz/; regfromtitle=%E9%BB%84%E5%81%A5%E5%AE%8F; dbcl2=\"142128977:lKgaClsIDDI\"; ck=\"uNFe\"; ap=1; __utmt=1; push_noty_num=0; push_doumail_num=1; __utmt_douban=1; __utma=30149280.699562146.1456221095.1457420579.1457434590.29; __utmb=30149280.3.10.1457434590; __utmc=30149280; __utmz=30149280.1457324439.26.6.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; __utmv=30149280.14212; __utma=81379588.483132861.1456221095.1457420579.1457434598.25; __utmb=81379588.1.10.1457434598; __utmc=81379588; __utmz=81379588.1457400869.23.2.utmcsr=douban.com|utmccn=(referral)|utmcmd=referral|utmcct=/misc/sorry; _pk_ref.100001.3ac3=%5B%22%22%2C%22%22%2C1457434598%2C%22http%3A%2F%2Fwww.douban.com%2Fmisc%2Fsorry%3Foriginal-url%3Dhttps%253A%252F%252Fbook.douban.com%252Ftag%252F%253Fview%253Dtype%2526icn%253Dindex-sorttags-all%22%5D; _pk_id.100001.3ac3=cb9226d49aa4d18e.1456220256.25.1457434598.1457420754.; _pk_ses.100001.3ac3=*";
	public static final String[] AGENTS = new String[]{
			"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2471.2 Safari/537.36",
			"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.109 Safari/537.36",
			"Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.6) Gecko/20091201 Firefox/3.5.6",
			"Mozilla/5.0 (Windows NT 6.2) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.12 Safari/535.11",
			"Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)",
			"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36"}; 
	// TODO:设置代理IP
	private Site site = Site.me().setRetryTimes(3).setCharset("UTF-8")
			.setUserAgent(AGENTS[new Random().nextInt(5)])
			.setSleepTime(0)
			.setRetryTimes(3)
			.setCycleRetryTimes(1000)
			.addCookie("Cookie", COOKIE);

	public Site getSite()
	{
		return site;
	}

	public void process(Page page)
	{
 		if(page.getUrl().regex("http(s)?://book.douban.com/subject/\\S+/comments/*").match())
		{
 			try
 			{
 				site.setUserAgent(AGENTS[new Random().nextInt(6)]);
// 				String ip = RedisUtil.sget("proxy_ip");
// 				if(ip !=null && !ip.equals(""))
// 					site.setHttpProxy(new HttpHost(ip));
// 				Thread.sleep(new Random().nextInt(20)*100);
 			} catch (Exception e)
 			{
 				e.printStackTrace();
 			}
			try
			{
				String bookno = page.getUrl().toString().split("/")[4];
				String bookName = page.getHtml().xpath("//div[@id='wrapper']/div[@id='content']/h1/text()").toString().split("短评")[0].trim();
				int commentsNum = page.getHtml().xpath("//div[@class='comment-list new']/ul/li[@class='comment-item']").all().size();
				List<String> userNames = page.getHtml().xpath("//div[@class='comment-list new']/ul/li[@class='comment-item']/h3/span[@class='comment-info']/a/text()").all();
				String totalRatingStr = page.getHtml().xpath("//div[@class='comment-list new']/ul/li[@class='comment-item']/h3/span[@class='comment-info']/span/@title").all().toString();
				String totalDD = page.getHtml().xpath("//div[@class='comment-list new']/ul/li[@class='comment-item']/h3/span[@class='comment-info']/span/@title").all().toString();
				//掐头去尾  中间有俩空格就加一个null
				String ratingStr = totalRatingStr.substring(1, totalRatingStr.length()-1);
				List<String> ratings2 = new ArrayList<String>();
				String[] ratingsStr = ratingStr.split(",");
				boolean first = true;
				first = true;
				if(ratingsStr[0].trim().equals(""))
				{
					ratings2.add(null);
					first = false;//第一个是空的
				}
				else
				{
					ratings2.add(ratingsStr[0].trim());
					first = true;
				}
				for(int i=1;i<ratingsStr.length-1;i++)
				{
					if(first == false && ratingsStr[i].trim().equals(""))//从第一个开始到第i个都是空
						ratings2.add(null);
					else
					{
						first = true;
						if (ratingsStr[i].equals(" ") && ratingsStr[i + 1].equals(" "))
							ratings2.add(null);
						else if (!ratingsStr[i].equals(" "))
						{
							ratings2.add(ratingsStr[i].trim());
						}
					}
				}
				List<String> commentTime = page.getHtml().xpath("//div[@class='comment-list new']/ul/li[@class='comment-item']/h3/span[@class='comment-info']/span/text()").regex("\\w+-\\w+-\\w+").all();
				List<String> contents = page.getHtml().xpath("//div[@class='comment-list new']/ul/li[@class='comment-item']/p/text()").all();
				List<String> usefulNum = page.getHtml().xpath("//div[@class='comment-list new']/ul/li[@class='comment-item']/h3/span[@class='comment-vote']/span/text()").all();
				List<String> userUrls = page.getHtml().xpath("//div[@class='comment-list new']/ul/li[@class='comment-item']/div[@class='avatar']/a/@href").all();
				String commentUrls = page.getUrl().toString();
				
				for(int i=0; i<commentsNum; i++)
				{
					BookComment comment = new BookComment();
					comment.setBookno(bookno);
					comment.setBookname(bookName);
					comment.setUsername(removeFourChar(userNames.get(i)));
					comment.setRating(getRatingByString(ratings2.get(i)));
					comment.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(commentTime.get(i)));
					comment.setContent(removeFourChar(contents.get(i)));
					comment.setUsefulnum(Integer.parseInt(usefulNum.get(i)));
					comment.setUserurl(userUrls.get(i));
					comment.setCommenturl(commentUrls);
					comment.setUserno(userUrls.get(i).split("/")[4]);
					comment.setIsbn(null);
					page.putField("comment" + i, comment);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("书评Xpath解析失败");
			}
			//下一页Url
			int currentPageNo = Integer.parseInt(page.getUrl().toString().split("new\\?p=")[1]);
			String nextPageXpath = "//div[@class='paginator-wrapper']/ul[@class='comment-paginator']/li/a/@href";
			String nextPageUrl = page.getHtml().xpath(nextPageXpath).regex(".*new\\?p=" + (currentPageNo+1)).get();
			if(!(nextPageUrl == null || nextPageUrl.equals("")))
			{
				page.addTargetRequest(new Request(nextPageUrl).putExtra("pageNo",currentPageNo+1).setPriority(0));
			}
		}
		else
		{
			System.out.println("书评地址错误:"+ page.getUrl());
		}
	}
	// 根据用户评级字符串返回评级数字
	private int getRatingByString(String rating)
	{
		if(rating == null)
			return - 1;
		if(rating.equals("力荐"))
			return 5;
		if(rating.equals("推荐"))
			return 4;
		if(rating.equals("还行"))
			return 3;
		if(rating.equals("较差"))
			return 2;
		return 1;
	}
	/**
	 * 替换四个字节的字符 '\xF0\x9F\x98\x84\xF0\x9F）的解决方案 😁
	 * @author ChenGuiYong
	 * @data 2015年8月11日 上午10:31:50
	 * @param content
	 * @return
	 */
	public static String removeFourChar(String content) {
		byte[] conbyte = content.getBytes();
		for (int i = 0; i < conbyte.length; i++) {
			if ((conbyte[i] & 0xF8) == 0xF0) {
				for (int j = 0; j < 4; j++) {
					conbyte[i+j]=0x30;
				}
				i += 4;
			}
		}
		content = new String(conbyte);
		return content.replaceAll("0000", "");
	}
}
