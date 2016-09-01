package cn.edu.ncut.doubanWebSpider.spider;

import cn.edu.ncut.doubanWebSpider.dao.BookCommentMapper;
import cn.edu.ncut.doubanWebSpider.spider.downloader.HttpClientDownloader;
import cn.edu.ncut.doubanWebSpider.spider.pipeline.UserInfoPipeline;
import cn.edu.ncut.doubanWebSpider.spider.process.UserInfoProcess;
import cn.edu.ncut.doubanWebSpider.spider.schedule.QueueNameConstant;
import cn.edu.ncut.doubanWebSpider.spider.schedule.RedisScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import java.util.List;

/**
 * Created by lixiwei on 2016/8/29.
 */
@Component
public class UserInfoSpider implements Crawler
{
    @Autowired
    private BookCommentMapper commentMapper;
    @Autowired
    private UserInfoPipeline userInfoPipeline;
    @Override
    public void crawl()
    {
        //List<String> userUrls = commentMapper.findAllCommentersNo();
        Spider.create(new UserInfoProcess())
                //.addUrl((String[]) userUrls.toArray())
                .addUrl("http://www.douban.com/people/linyouchen/")
                .addPipeline(userInfoPipeline)
                .setDownloader(new HttpClientDownloader())
                //.scheduler(new RedisScheduler(pool,0, QueueNameConstant.QUEUE_USER_INFO))
                .thread(1).run();
    }
    public static void main(String[] args)
    {
        applicationContext.getBean(UserInfoSpider.class).crawl();
    }
}
