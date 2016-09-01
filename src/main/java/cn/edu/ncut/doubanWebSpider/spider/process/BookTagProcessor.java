package cn.edu.ncut.doubanWebSpider.spider.process;

import cn.edu.ncut.doubanWebSpider.model.BookTag;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

public class BookTagProcessor implements PageProcessor
{
	public static final String COOKIE = "gr_user_id=f7ce0ee0-4ede-45c8-bca0-890ce7ff2886; bid=\"UlkkzTCvehw\"; ps=y; ll=\"108288\"; viewed=\"4736167_1440226_2020442_3897837_3821157_3590768_2297549_2130190_26414020\"; regfromurl=http://www.douban.com/people/i_m_huangz/; regfromtitle=%E9%BB%84%E5%81%A5%E5%AE%8F; dbcl2=\"142128977:lKgaClsIDDI\"; ck=\"uNFe\"; ap=1; __utmt=1; push_noty_num=0; push_doumail_num=1; __utmt_douban=1; __utma=30149280.699562146.1456221095.1457420579.1457434590.29; __utmb=30149280.3.10.1457434590; __utmc=30149280; __utmz=30149280.1457324439.26.6.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; __utmv=30149280.14212; __utma=81379588.483132861.1456221095.1457420579.1457434598.25; __utmb=81379588.1.10.1457434598; __utmc=81379588; __utmz=81379588.1457400869.23.2.utmcsr=douban.com|utmccn=(referral)|utmcmd=referral|utmcct=/misc/sorry; _pk_ref.100001.3ac3=%5B%22%22%2C%22%22%2C1457434598%2C%22http%3A%2F%2Fwww.douban.com%2Fmisc%2Fsorry%3Foriginal-url%3Dhttps%253A%252F%252Fbook.douban.com%252Ftag%252F%253Fview%253Dtype%2526icn%253Dindex-sorttags-all%22%5D; _pk_id.100001.3ac3=cb9226d49aa4d18e.1456220256.25.1457434598.1457420754.; _pk_ses.100001.3ac3=*";
	//TODO:设置代理IP
	private Site site = Site.me()
			.setRetryTimes(3)
			.setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2471.2 Safari/537.36")
			.addCookie("Cookie", COOKIE);
	public Site getSite()
	{//
		return site;
	}

	public void process(Page page)
	{
		
		//通过Xpath得到需要的属性
		List<String> tagCategory = page.getHtml().css("a.tag-title-wrapper").all();
		List<String> tagUrls =  page.getHtml().css("table.tagCol").links().all();
		List<String> tagNames = page.getHtml().xpath("//table[@class='tagCol']//td//a//text()").all();
		int count = 0;
		for(int c = 0; c < tagNames.size(); c++)
		{
			BookTag bookTag = new BookTag();
			bookTag.setTagname(tagNames.get(c));
			bookTag.setUrl(tagUrls.get(count));
			page.putField("bookTag"+count++, bookTag);
		}
	}
}
