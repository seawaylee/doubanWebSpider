package cn.edu.ncut.doubanWebSpider.spider.process;

import cn.edu.ncut.doubanWebSpider.model.SimpleBookInfo;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Random;

/**
 * @author NikoBelic
 * @create 16/8/18 23:32
 */
public class CommentProcessor implements PageProcessor
{

    public static final String COOKIE = "ALF=1474110579; SCF=AvCcLMkzPiOCDyCM5OhnLMgmb8u4LunvQ2vh0LKnM-_Gj8nA7UXorGtF7cGBxAmQaEtOCetbR84EA8-j_wLXty0.; SUB=_2A256sZi4DeTxGeRG6lUT-SzFyDyIHXVWXTjwrDV6PUJbktBeLW7_kW0LrOpDXkExVmp7pQ-983lfDkFWJw..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WF71.XX7lS20fKjOJ.ruS9V5JpX5o2p5NHD95QE1h2Neo.E1Ke7Ws4DqcjHCgf7dNiXBcv.Ig4rCgREKPLeIg9L; SUHB=0TPkJCdZsrTBM_; SSOLoginState=1471539432; _T_WM=b5d9e61331779055edef9729861b3971; M_WEIBOCN_PARAMS=featurecode%3D20000180%26oid%3D4008240355912803%26luicode%3D20000061%26lfid%3D4008240355912803";
    public static final String[] AGENTS = new String[]{
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2471.2 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.109 Safari/537.36",
            "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.6) Gecko/20091201 Firefox/3.5.6",
            "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.12 Safari/535.11",
            "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)"};
    // TODO:设置代理IP
    public static final String MOBILEAGENT= "Mozilla/5.0 (iPhone; CPU iPhone OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A5376e Safari/8536.25";
    private Site site = Site.me().setRetryTimes(3).setCharset("UTF-8")
            .setUserAgent(MOBILEAGENT)
            .setSleepTime(0)
            .setRetryTimes(3)
            .setCycleRetryTimes(5)
            .addHeader("Host","m.weibo.cn")
            .addHeader("Referer","http://m.weibo.cn/1828817187/4008240355912803")
            .addCookie("Cookie", COOKIE);

    public Site getSite()
    {
        return site;
    }

    public void process(Page page)
    {
        try
        {
            site.setUserAgent(MOBILEAGENT);
//            Thread.sleep(new Random().nextInt(30)*100);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        //获取图书基本信息
        String username = null;
        try {
            username = URLDecoder.decode(page.getHtml().toString(),"");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(username);
//        List<String> comment = page.getHtml().xpath("//div[@class='WB_text']/text()").all();
//        int index = 0;

        // 下一页的Url
//        String currentUrl = page.getUrl().get();
//        String nextPageUrl = currentUrl.substring(0,currentUrl.length()-1) + (Integer.valueOf(String.valueOf(currentUrl.charAt(currentUrl.length()-1)))+1);
        // 把下一页图书列表加入队列
//        page.addTargetRequest(new Request(nextPageUrl).setPriority(1));
    }
}
