package cn.edu.ncut.doubanWebSpider.spider.process;

import cn.edu.ncut.doubanWebSpider.model.UserInfo;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * Created by lixiwei on 2016/8/29.
 */
public class UserInfoProcess implements PageProcessor
{
    public static final String COOKIE = "bid=1yxWIEOOd5Y; ps=y; ct=y; gr_user_id=a8864a30-9043-4f46-9496-673388b9d633; regfromurl=https://www.douban.com/people/miloyip/; regfromtitle=Milo; ll=\"108288\"; viewed=\"6781808_3004255\"; _vwo_uuid_v2=50DDF2B082D88A71BFAE1586A5D7C2C7|721b719f53994e67f55bebcf0581b77c; ap=1; ue=\"931185214@qq.com\"; dbcl2=\"150773536:/50e1rbedZc\"; ck=XcrJ; _pk_ref.100001.8cb4=%5B%22%22%2C%22%22%2C1474190845%2C%22https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3D2jfFOu1P-zFpzNmRnZi15Umv74MibVG6qHHlAv5Rs0gyA40czLxsSRhHiAJm2JZxBiAUbP7goHailuE287_7j_%26wd%3D%26eqid%3Dfa49e04a000030b90000000257dd3637%22%5D; push_noty_num=0; push_doumail_num=0; _pk_id.100001.8cb4=d906a100e893f1e9.1472807814.10.1474191328.1474187302.; _pk_ses.100001.8cb4=*";
    public static final String[] COOKIES = {
            "bid=1yxWIEOOd5Y; ps=y; ct=y; ap=1; gr_user_id=a8864a30-9043-4f46-9496-673388b9d633; viewed=\"3004255\"; regfromurl=https://www.douban.com/people/miloyip/; regfromtitle=Milo; _vwo_uuid_v2=50DDF2B082D88A71BFAE1586A5D7C2C7|721b719f53994e67f55bebcf0581b77c; _pk_ref.100001.3ac3=%5B%22%22%2C%22%22%2C1473840966%2C%22https%3A%2F%2Fwww.douban.com%2Fmisc%2Fsorry%3Foriginal-url%3Dhttps%253A%252F%252Fbook.douban.com%252Fpeople%252F139539280%252Fcollect%22%5D; ue=\"931185214@qq.com\"; dbcl2=\"150773536:ZDw4AsjXzPs\"; ck=Znig; push_noty_num=0; push_doumail_num=0; _pk_id.100001.3ac3=6f0b23f692748f2b.1471917101.16.1473840982.1473222482.; _pk_ses.100001.3ac3=*"};
            //,"bid=1yxWIEOOd5Y; ps=y; ct=y; gr_user_id=a8864a30-9043-4f46-9496-673388b9d633; viewed=\"3004255\"; regfromurl=https://www.douban.com/people/miloyip/; regfromtitle=Milo; _vwo_uuid_v2=50DDF2B082D88A71BFAE1586A5D7C2C7|721b719f53994e67f55bebcf0581b77c; _pk_ref.100001.3ac3=%5B%22%22%2C%22%22%2C1473840966%2C%22https%3A%2F%2Fwww.douban.com%2Fmisc%2Fsorry%3Foriginal-url%3Dhttps%253A%252F%252Fbook.douban.com%252Fpeople%252F139539280%252Fcollect%22%5D; push_noty_num=0; push_doumail_num=0; ap=1; _pk_id.100001.3ac3=6f0b23f692748f2b.1471917101.16.1473841102.1473222482.; _pk_ses.100001.3ac3=*; ue=\"13522781970@163.com\"; dbcl2=\"150649926:qKRFJp2v0no\""
            //,"bid=1yxWIEOOd5Y; ps=y; ct=y; gr_user_id=a8864a30-9043-4f46-9496-673388b9d633; viewed=\"3004255\"; regfromurl=https://www.douban.com/people/miloyip/; regfromtitle=Milo; _vwo_uuid_v2=50DDF2B082D88A71BFAE1586A5D7C2C7|721b719f53994e67f55bebcf0581b77c; _pk_ref.100001.3ac3=%5B%22%22%2C%22%22%2C1473840966%2C%22https%3A%2F%2Fwww.douban.com%2Fmisc%2Fsorry%3Foriginal-url%3Dhttps%253A%252F%252Fbook.douban.com%252Fpeople%252F139539280%252Fcollect%22%5D; ue=\"13522781970@163.com\"; ap=1; dbcl2=\"142128977:VsKuspOvjaY\"; ck=0yps; push_noty_num=0; push_doumail_num=0; _pk_id.100001.3ac3=6f0b23f692748f2b.1471917101.16.1473841161.1473222482.; _pk_ses.100001.3ac3=*"};

    //	public static final String COOKIE = "bid=\"cLFu0ljxKRE\"; gr_user_id=71a8fed9-c730-4e6b-815f-813f99b450f4; ll=\"108288\"; ap=1; viewed=\"3283973_5257905\"; __utmt=1; dbcl2=\"142128977:lKgaClsIDDI\"; ck=\"uNFe\"; gr_session_id_22c937bbd8ebd703f2d8e9445f7dfd03=c78404b1-2e3a-4bb6-afd9-45e6cfdc0d41; __utmt_douban=1; __utma=30149280.1220747617.1453995829.1455611943.1456219113.6; __utmb=30149280.4.10.1456219113; __utmc=30149280; __utmz=30149280.1456219113.6.5.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; __utmv=30149280.14212; push_noty_num=0; push_doumail_num=1";
    public static final String[] AGENTS = new String[]{
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2471.2 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.109 Safari/537.36",
            "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.6) Gecko/20091201 Firefox/3.5.6",
            "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.12 Safari/535.11",
            "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)",
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36"};
    // TODO:设置代理IP
    private Site site = Site.me().setRetryTimes(3).setCharset("UTF-8")
            .setUserAgent(AGENTS[new Random().nextInt(5)])
            .setSleepTime(0)
            .setRetryTimes(3)
            .setCycleRetryTimes(100)
            //.addCookie("Cookie", COOKIES[new Random().nextInt(3)] + System.currentTimeMillis());
            .addCookie("Cookie", COOKIE);


    @Override
    public Site getSite()
    {
        return site;
    }

    @Override
    public void process(Page page)
    {
        try
        {
            site.setUserAgent(AGENTS[new Random().nextInt(5)])
            //.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    //.addHeader("Accept-Encoding",":gzip, deflate, sdch, br")
                    //.addHeader("Accept-Language",":zh-CN,zh;q=0.8,en;q=0.6")
                    //.addHeader("Cache-Control","max-age=0")
                    //.addHeader("Connection",":keep-alive")
                    //.addHeader("Cookie",COOKIES[new Random().nextInt(3)] + System.currentTimeMillis())
                    .addHeader("Host","www.douban.com");
                    //.addHeader("Upgrade-Insecure-Requests","1")
                    //.addHeader("Referer","https://book.douban.com/subject/26414020/comments/");
            Thread.sleep(new Random().nextInt(30)*100);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            String userno = page.getUrl().toString().split("/")[4];
            String name = page.getHtml().xpath("//div[@class='info']/h1/text()").get().trim();
            String signature = page.getHtml().xpath("//div[@class='info']/h1/div[@class='signature_display pl']/text()").get();
            String introduction = page.getHtml().xpath("//span[@id='intro_display']/text()").get();
            String location = page.getHtml().xpath("//div[@class='user-info']/a/text()").get();
            String joinTime = page.getHtml().xpath("//div[@class='user-info']/div[@class='pl']/text()").regex("\\d+-\\d+-\\d+").get();
            String pic = page.getHtml().xpath("//div[@class='basic-info']/img/@src").get();
            String reading = page.getHtml().xpath("//div[@class='article']/div[@id='book']/h2/span[@class='pl']/a/text()").regex("\\d+本在读").get();
            String hasread = page.getHtml().xpath("//div[@class='article']/div[@id='book']/h2/span[@class='pl']/a/text()").regex("\\d+本读过").get();
            String wantread = page.getHtml().xpath("//div[@class='article']/div[@id='book']/h2/span[@class='pl']/a/text()").regex("\\d+本想读").get();
            String follwees = page.getHtml().xpath(("//div[@id='friend']/h2/span[@class='pl']/a/text()")).regex("\\d+").get();
            String followers = page.getHtml().xpath("//div[@class='aside']/p[@class='rev-link']/a/text()").regex("\\d+").get();
            //
            UserInfo user = new UserInfo();
            user.setUserno(userno);
            user.setName(name);
            user.setSignature(signature);
            user.setIntroduction(introduction);
            user.setLocation(location);
            try
            {
                user.setTime(joinTime == null ? null : new SimpleDateFormat("yyyy-MM-dd").parse(joinTime));
            }catch (Exception e)
            {
                user.setTime(null);
            }
            user.setPic(pic);
            user.setReading(reading == null?0:Integer.parseInt(reading.split("本在读")[0]));
            user.setHasread(hasread == null?0:Integer.parseInt(hasread.split("本读过")[0]));
            user.setWantread(wantread == null?0:Integer.parseInt(wantread.split("本想读")[0]));
            user.setFollowees(follwees == null?0:Integer.parseInt(follwees));
            user.setFollowers(followers == null?0:Integer.parseInt(followers));
            page.putField("user",user);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("用户信息Xpath解析失败");
            //FileUtils.writeStrToFile(page.getUrl().toString() + "\r\n","E:\\IdeaProjects\\doubanWebSpider\\src\\main\\resources\\user_error.txt");
        }
    }
}
