package cn.edu.ncut.doubanWebSpider.spider;

import cn.edu.ncut.doubanWebSpider.dao.BookTagMapper;
import cn.edu.ncut.doubanWebSpider.model.BookTag;
import cn.edu.ncut.doubanWebSpider.spider.downloader.HttpClientDownloader;
import cn.edu.ncut.doubanWebSpider.spider.pipeline.SimpleBookInfoPipeline;
import cn.edu.ncut.doubanWebSpider.spider.process.SimpleBookInfoProcessor;
import cn.edu.ncut.doubanWebSpider.spider.schedule.QueueNameConstant;
import cn.edu.ncut.doubanWebSpider.spider.schedule.RedisScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;

import java.util.List;
@Component
public class SimpleBookInfoSpider implements Crawler
{
	@Autowired
	private BookTagMapper bookTagMapper;
	@Autowired
	private SimpleBookInfoPipeline simpleBookInfoPipeline;
	public void crawl()
	{
		//RedisUtil.init();
		List<BookTag> bookTag = bookTagMapper.selectAll();
		Request[] requests = new Request[bookTag.size()];
		int i = 0;
		for(BookTag tag : bookTag)
		{
			requests[i++] = new Request(tag.getUrl() + "?start=0&Type=S").setPriority(0).putExtra("RedisSuffix","www.douban.com");
		}
		Spider.create(new SimpleBookInfoProcessor())
		.addRequest(requests)
		//.addUrl("http://book.douban.com/tag/古龙?start=0&Type=T")
		.addPipeline(simpleBookInfoPipeline)
		.setDownloader(new HttpClientDownloader())
		.scheduler(new RedisScheduler(pool,0, QueueNameConstant.QUEUE_SIMPLE_BOOK_INFO))
		.thread(1).run();
	}
	public static void main(String[] args)
	{
		applicationContext.getBean(SimpleBookInfoSpider.class).crawl();
	}
}
