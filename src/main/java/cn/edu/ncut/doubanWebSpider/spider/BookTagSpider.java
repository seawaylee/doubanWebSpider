package cn.edu.ncut.doubanWebSpider.spider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.edu.ncut.doubanWebSpider.spider.pipeline.BookTagPipeline;
import cn.edu.ncut.doubanWebSpider.spider.process.BookTagProcessor;
import us.codecraft.webmagic.Spider;
@Component
public class BookTagSpider implements Crawler
{
	/*
	 * 注意：这里一定要注入pipleline，因为pipeline里面需要注入一个mapper，如果这里不把pipeline交由Spring管理，那么那个Mapper也无法正常获取。
	 */
	@Autowired
	private BookTagPipeline bookTagPipeline; 
	public void crawl()
	{
		Spider.create(new BookTagProcessor()).addUrl("http://book.douban.com/tag/?view=type&icn=index-sorttags-all")
		.addPipeline(bookTagPipeline)
		.thread(1).run();
	}
	
	public static void main(String[] args) 
	{
		applicationContext.getBean(BookTagSpider.class).crawl();
	}
}
