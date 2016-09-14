package cn.edu.ncut.doubanWebSpider.spider;

import cn.edu.ncut.doubanWebSpider.dao.UserInfoMapper;
import cn.edu.ncut.doubanWebSpider.spider.downloader.HttpClientDownloader;
import cn.edu.ncut.doubanWebSpider.spider.pipeline.UserBookStatusPipeline;
import cn.edu.ncut.doubanWebSpider.spider.process.UserBookStatusProcessor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixiwei on 2016/9/5.
 */
@Component
public class UserBookStatusSpider implements Crawler
{
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserBookStatusPipeline userBookStatusPipeline;
    @Override
    public void crawl()
    {
        File f = new File("E:\\IdeaProjects\\doubanWebSpider\\src\\main\\resources\\userurls.txt");
        try
        {
            List<String> userUrls = FileUtils.readLines(f);
            List<String> userBookStatusUrl = new ArrayList<>();
            for (String url : userUrls)
            {
                userBookStatusUrl.add(url.replaceFirst("www","book") + "collect?start=0&sort=time&rating=all&filter=all&mode=list");
            }
            System.out.println("数量:" + userBookStatusUrl.size());
            String[] urls = new String[userBookStatusUrl.size()];
            for (int i = 0; i < userBookStatusUrl.size(); i++)
            {
                urls[i] = userBookStatusUrl.get(i);
            }

            Spider.create(new UserBookStatusProcessor())
                    .addUrl(urls)
                    //.addUrl("https://book.douban.com/people/silenthill2006/do?start=0&sort=time&rating=all&filter=all&mode=list")
                    .addPipeline(userBookStatusPipeline)
                    .setDownloader(new HttpClientDownloader())
                    .thread(8).run();
        } catch (IOException e)
        {
            e.printStackTrace();
        }


    }
    public static void main(String[] args)
    {
        applicationContext.getBean(UserBookStatusSpider.class).crawl();
    }

}
