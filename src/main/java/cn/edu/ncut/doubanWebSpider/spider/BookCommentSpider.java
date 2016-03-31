package cn.edu.ncut.doubanWebSpider.spider;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.edu.ncut.doubanWebSpider.dao.SimpleBookInfoMapper;
import cn.edu.ncut.doubanWebSpider.model.SimpleBookInfo;
import cn.edu.ncut.doubanWebSpider.spider.pipeline.BookCommentPipeline;
import cn.edu.ncut.doubanWebSpider.spider.process.BookCommentProcessor;
import cn.edu.ncut.doubanWebSpider.spider.schedule.QueueNameConstant;
import cn.edu.ncut.doubanWebSpider.spider.schedule.RedisScheduler;
import tk.mybatis.mapper.entity.Example;
import us.codecraft.webmagic.Spider;
import utils.RedisUtil;
@Component
public class BookCommentSpider implements Crawler
{
	@Autowired
	private SimpleBookInfoMapper simpleBookMapper;
	@Autowired
	private BookCommentPipeline bookCommentPipeline;
	public void crawl()
	{
		RedisUtil.init();
//		List<SimpleBookInfo> bookInfos = simpleBookMapper.selectByTagName(new String[]{
//				"经济","成长","文化",""小说","商业","社会","中国文学","经典","编程",
//				"鲁迅"
//		});
		List<SimpleBookInfo> bookInfos = simpleBookMapper.selectByExample(new Example(SimpleBookInfo.class).selectProperties("url"));
		String[] urls = new String[bookInfos.size()];
		int i = 0;
		for(SimpleBookInfo book : bookInfos)
		{
			urls[i++] = book.getUrl().replace("/?from=tag_all", "/comments/hot?p=10");
		}
		
		Spider.create(new BookCommentProcessor())
		.addUrl(urls)
		.addPipeline(bookCommentPipeline)
		.scheduler(new RedisScheduler(pool,0,QueueNameConstant.QUEUE_BOOK_COMMNENT))
		.thread(2).run();
	}
	public static void main(String[] args)
	{
		applicationContext.getBean(BookCommentSpider.class).crawl();
	}
	
	
}
