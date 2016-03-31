package cn.edu.ncut.doubanWebSpider.spider;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.edu.ncut.doubanWebSpider.dao.BookTagMapper;
import cn.edu.ncut.doubanWebSpider.model.BookTag;
import cn.edu.ncut.doubanWebSpider.spider.downloader.Downloader;
import cn.edu.ncut.doubanWebSpider.spider.downloader.HttpClientDownloader;
import cn.edu.ncut.doubanWebSpider.spider.pipeline.BookInfoPipeline;
import cn.edu.ncut.doubanWebSpider.spider.process.BookInfoProcessor;
import cn.edu.ncut.doubanWebSpider.spider.schedule.QueueNameConstant;
import cn.edu.ncut.doubanWebSpider.spider.schedule.RedisScheduler;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import utils.ConfigUtil;
import utils.RedisUtil;
@Component
public class BookInfoSpider implements Crawler
{
	@Autowired
	private BookTagMapper bookTagMapper;
	@Autowired
	private BookInfoPipeline bookInfoPipeline;
	public void crawl()
	{
		RedisUtil.init();
		List<BookTag> bookTag = bookTagMapper.selectAll();
		Request[] requests = new Request[bookTag.size()];
		int i = 0;
		for(BookTag tag : bookTag)
		{
			requests[i++] = new Request(tag.getUrl()).setPriority(0);
		}
		Spider.create(new BookInfoProcessor())
		.addRequest(requests)
//		.addUrl("http://www.ip138.com/")
		.addPipeline(bookInfoPipeline)
		.scheduler(new RedisScheduler(pool,Integer.parseInt(ConfigUtil.getProperty("redis", "redis.index")),QueueNameConstant.QUEUE_BOOK_INFO))
		.thread(1).run();
	}
	public static void main(String[] args)
	{
		applicationContext.getBean(BookInfoSpider.class).crawl();
	}
}
