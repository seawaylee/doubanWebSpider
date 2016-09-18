package cn.edu.ncut.doubanWebSpider.spider;

import cn.edu.ncut.doubanWebSpider.dao.BookCommentMapper;
import cn.edu.ncut.doubanWebSpider.dao.UserInfoMapper;
import cn.edu.ncut.doubanWebSpider.model.UserInfo;
import cn.edu.ncut.doubanWebSpider.spider.downloader.HttpClientDownloader;
import cn.edu.ncut.doubanWebSpider.spider.pipeline.UserInfoPipeline;
import cn.edu.ncut.doubanWebSpider.spider.process.UserInfoProcess;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import java.io.*;
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
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    public void crawl()
    {
        //List<String> userUrls = commentMapper.findAllCommentersNo();
        //File f = new File("E:\\IdeaProjects\\doubanWebSpider\\src\\main\\resources\\user_403.txt");
        File f = new File("E:\\IdeaProjects\\doubanWebSpider\\src\\main\\resources\\user_403.txt");
        // 排除已经下载过的地址
        //List<String> userUrls = null;
        //try
        //{
        //    userUrls = FileUtils.readLines(f);
        //} catch (IOException e)
        //{
        //    e.printStackTrace();
        //}
        //List<UserInfo> userInfos = userInfoMapper.selectAll();
        //int ci = 1;
        //for (UserInfo userInfo : userInfos)
        //{
        //    String currentUrl = "https://www.douban.com/people/"+userInfo.getUserno()+"/";
        //    System.out.println("正在检测第" + ci++ + "个地址");
        //    if (userUrls.contains(currentUrl))
        //    {
        //        userUrls.remove(currentUrl);
        //    }
        //}
        //System.out.println("数量:" + userUrls.size());
        //String[] urls = new String[userUrls.size()];
        //for (int i = 0; i < userUrls.size(); i++)
        //{
        //    urls[i] = userUrls.get(i);
        //}

        Spider.create(new UserInfoProcess())
                //.addUrl(urls)
                .addUrl("https://www.douban.com/people/4400922/")
                .addPipeline(userInfoPipeline)
                .setDownloader(new HttpClientDownloader())
                .thread(1).run();
    }
    public static void main(String[] args)
    {
        applicationContext.getBean(UserInfoSpider.class).crawl();
    }
}
