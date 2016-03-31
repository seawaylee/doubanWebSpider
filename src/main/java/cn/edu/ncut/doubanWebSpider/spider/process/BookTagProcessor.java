package cn.edu.ncut.doubanWebSpider.spider.process;

import java.util.ArrayList;
import java.util.List;

import cn.edu.ncut.doubanWebSpider.model.BookTag;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class BookTagProcessor implements PageProcessor
{
	public static final String COOKIE = "bid='Nu4sBFrYFrQ'; gr_user_id=90e8f0bd-c992-49e9-b769-67b7c813c60c; ll='108288'; viewed='26414020_10750155_24703171'; ps=y; dbcl2='142128977:lKgaClsIDDI'; ct=y; ck='uNFe'; ap=1; push_noty_num=0; push_doumail_num=1; _pk_ref.100001.8cb4=%5B%22%22%2C%22%22%2C1456134097%2C%22http%3A%2F%2Fbook.douban.com%2Ftag%2F%3Fview%3Dtype%26icn%3Dindex-sorttags-all%22%5D; _pk_id.100001.8cb4=70ff0815eaa92b53.1449906405.12.1456134097.1456127961.; _pk_ses.100001.8cb4=*; __utma=30149280.679528336.1449906405.1456120751.1456133798.12; __utmb=30149280.4.10.1456133798; __utmc=30149280; __utmz=30149280.1455892682.10.10.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; __utmv=30149280.14212";
	//TODO:设置代理IP
	private Site site = Site.me()
			.setRetryTimes(3)
			.setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2471.2 Safari/537.36")
			.addCookie("Cookie", COOKIE);
	public Site getSite()
	{
		return site;
	}

	public void process(Page page)
	{
		
		//Xpath表达式
		String tagCategoryXpath = "//div/div/a[@class='tag-title-wrapper']/@name";
		String tagUrlXpath = "//a[@class='tag']/@href";
		String tagNameXpath = "//a[@class='tag']/text()";
		String tableXpath = "//table[@class='tagCol']";
		//通过Xpath得到需要的属性
		List<String> tagNames = page.getHtml().xpath(tagNameXpath).all();
		List<String> tagUrls =  page.getHtml().xpath(tagUrlXpath).all();
		List<String> tagCategory = page.getHtml().xpath(tagCategoryXpath).all();
		List<String> tables = page.getHtml().xpath(tableXpath).all();//看看有多少个种类
		int count = 0;
		for(int c = 0; c < tables.size(); c++)
		{
			int tagsNum = tables.get(c).split("<td>").length - 1;
			for(int i=0; i<tagsNum;i++)
			{
				BookTag bookTag = new BookTag();
				bookTag.setTagname(tagNames.get(count));
				bookTag.setUrl(tagUrls.get(count).replaceAll("\\?focus=", ""));
				bookTag.setCategory(tagCategory.get(c));
				page.putField("bookTag"+count++, bookTag);
			}
		}
	}
}
