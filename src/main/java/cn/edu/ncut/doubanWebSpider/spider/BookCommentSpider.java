package cn.edu.ncut.doubanWebSpider.spider;

import cn.edu.ncut.doubanWebSpider.dao.SimpleBookInfoMapper;
import cn.edu.ncut.doubanWebSpider.model.SimpleBookInfo;
import cn.edu.ncut.doubanWebSpider.spider.downloader.HttpClientDownloader;
import cn.edu.ncut.doubanWebSpider.spider.pipeline.BookCommentPipeline;
import cn.edu.ncut.doubanWebSpider.spider.process.BookCommentProcessor;
import cn.edu.ncut.doubanWebSpider.spider.schedule.QueueNameConstant;
import cn.edu.ncut.doubanWebSpider.spider.schedule.RedisScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import utils.RedisUtil;

import java.util.List;

@Component
public class BookCommentSpider implements Crawler
{
	@Autowired
	private SimpleBookInfoMapper simpleBookMapper;
	@Autowired
	private BookCommentPipeline bookCommentPipeline;
	public void crawl()
	{
		//RedisUtil.init();
		List<SimpleBookInfo> bookInfos = simpleBookMapper.selectAllUrls();
		String[] urls = new String[bookInfos.size()];
		int i = 0;
		for(SimpleBookInfo book : bookInfos)
		{
			urls[i++] = book.getUrl() + "comments/new?p=1";
		}
		System.out.println("Total:" + urls.length);
		Spider.create(new BookCommentProcessor())
		.addUrl(urls)
		//.addUrl("https://book.douban.com/subject/25862578/comments/new?p=30")
		.addPipeline(bookCommentPipeline)
		//.setScheduler(new FileCacheQueueScheduler("C:\\Users\\51195\\Desktop\\filePath.txt"))
		//.scheduler(new RedisScheduler(pool,0, QueueNameConstant.QUEUE_BOOK_COMMNENT))
		.setDownloader(new HttpClientDownloader())
		.thread(8).run();
	}
	public static void main(String[] args)
	{
		applicationContext.getBean(BookCommentSpider.class).crawl();
	}
	
	
}
