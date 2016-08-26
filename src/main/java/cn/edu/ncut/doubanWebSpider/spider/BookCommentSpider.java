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
//		List<SimpleBookInfo> bookInfos = simpleBookMapper.selectByTagName(new String[]{
//				"经济","成长","文化",""小说","商业","社会","中国文学","经典","编程",
//				"鲁迅"
//		});
		List<SimpleBookInfo> bookInfos = simpleBookMapper.selectAllUrls();
		String[] urls = new String[bookInfos.size()];
		int i = 0;
		for(SimpleBookInfo book : bookInfos)
		{
			urls[i++] = book.getUrl() + "comments/hot?p=1";
		}
		
		Spider.create(new BookCommentProcessor())
		.addUrl(urls)
		//.addUrl("https://book.douban.com/subject/2567698/comments/hot?p=18")
		.addPipeline(bookCommentPipeline)
		.scheduler(new RedisScheduler(pool,0, QueueNameConstant.QUEUE_BOOK_COMMNENT))
		.setDownloader(new HttpClientDownloader())
		.thread(1).run();
	}
	public static void main(String[] args)
	{
		applicationContext.getBean(BookCommentSpider.class).crawl();
	}
	
	
}
