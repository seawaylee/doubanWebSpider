package cn.edu.ncut.doubanWebSpider.spider;

import cn.edu.ncut.doubanWebSpider.dao.CommentMapper;
import cn.edu.ncut.doubanWebSpider.model.weibo.Comment;
import cn.edu.ncut.doubanWebSpider.spider.downloader.HttpClientDownloader;
import cn.edu.ncut.doubanWebSpider.spider.pipeline.CommentPipeline;
import cn.edu.ncut.doubanWebSpider.spider.process.CommentProcessor;
import cn.edu.ncut.doubanWebSpider.spider.process.SimpleBookInfoProcessor;
import cn.edu.ncut.doubanWebSpider.spider.schedule.QueueNameConstant;
import cn.edu.ncut.doubanWebSpider.spider.schedule.RedisScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.QueueScheduler;

/**
 * @author NikoBelic
 * @create 16/8/18 23:18
 */
@Component
public class WeiboSpider implements Crawler
{
    @Autowired
    CommentMapper commentMapper;


    @Override
    public void crawl()
    {
        Spider.create(new CommentProcessor())
                .addRequest(new Request("http://m.weibo.cn/single/rcList?format=cards&id=4008240355912803&type=comment&hot=0&page=1"))
                //		.addUrl("http://www.douban.com/tag/港台/book?start=480")
                .addPipeline(new CommentPipeline())
                .setDownloader(new HttpClientDownloader())
//                .scheduler(new RedisScheduler(pool,2, QueueNameConstant.QUEUE_SIMPLE_BOOK_INFO))
                .scheduler(new QueueScheduler())
                .thread(1).run();
    }
    public static void main(String[] args)
    {
        applicationContext.getBean(WeiboSpider.class).crawl();
    }

}
