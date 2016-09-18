package cn.edu.ncut.doubanWebSpider.spider.process;

import cn.edu.ncut.doubanWebSpider.model.SimpleBookInfo;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.Random;

public class SimpleBookInfoProcessor implements PageProcessor
{
    //public static final String COOKIE = "bid=\"Nu4sBFrYFrQ\"; gr_user_id=90e8f0bd-c992-49e9-b769-67b7c813c60c; ll=\"108288\"; viewed=\"26414020_10750155_24703171\"; ps=y; dbcl2=\"142128977:lKgaClsIDDI\"; ct=y; ck=\"uNFe\"; ap=1; gr_session_id_22c937bbd8ebd703f2d8e9445f7dfd03=50785b47-22b1-44e3-b7a3-fd135a23cd6e; __utmt=1; push_noty_num=0; push_doumail_num=1; __utma=30149280.679528336.1449906405.1456155378.1456157975.15; __utmb=30149280.15.10.1456157975; __utmc=30149280; __utmz=30149280.1456157975.15.11.utmcsr=douban.com|utmccn=(referral)|utmcmd=referral|utmcct=/tag/%E5%B0%8F%E8%AF%B4/book; __utmv=30149280.14212";
    public static final String COOKIE = "gr_user_id=f7ce0ee0-4ede-45c8-bca0-890ce7ff2886; bid=\"UlkkzTCvehw\"; ps=y; ll=\"108288\"; viewed=\"4736167_1440226_2020442_3897837_3821157_3590768_2297549_2130190_26414020\"; regfromurl=http://www.douban.com/people/i_m_huangz/; regfromtitle=%E9%BB%84%E5%81%A5%E5%AE%8F; dbcl2=\"142128977:lKgaClsIDDI\"; ck=\"uNFe\"; ap=1; __utmt=1; push_noty_num=0; push_doumail_num=1; __utmt_douban=1; __utma=30149280.699562146.1456221095.1457420579.1457434590.29; __utmb=30149280.3.10.1457434590; __utmc=30149280; __utmz=30149280.1457324439.26.6.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; __utmv=30149280.14212; __utma=81379588.483132861.1456221095.1457420579.1457434598.25; __utmb=81379588.1.10.1457434598; __utmc=81379588; __utmz=81379588.1457400869.23.2.utmcsr=douban.com|utmccn=(referral)|utmcmd=referral|utmcct=/misc/sorry; _pk_ref.100001.3ac3=%5B%22%22%2C%22%22%2C1457434598%2C%22http%3A%2F%2Fwww.douban.com%2Fmisc%2Fsorry%3Foriginal-url%3Dhttps%253A%252F%252Fbook.douban.com%252Ftag%252F%253Fview%253Dtype%2526icn%253Dindex-sorttags-all%22%5D; _pk_id.100001.3ac3=cb9226d49aa4d18e.1456220256.25.1457434598.1457420754.; _pk_ses.100001.3ac3=*";
    public static final String[] AGENTS = new String[]{
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2471.2 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.109 Safari/537.36",
            "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.6) Gecko/20091201 Firefox/3.5.6",
            "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.12 Safari/535.11",
            "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)"};
    // TODO:设置代理IP
    private Site site = Site.me().setRetryTimes(3).setCharset("UTF-8")
            .setUserAgent(AGENTS[new Random().nextInt(5)])
            .setSleepTime(0)
            .setRetryTimes(3)
            .setCycleRetryTimes(1000)
            .addCookie("Cookie", COOKIE);

    public Site getSite()
    {
        return site;
    }

    public void process(Page page)
    {
        try
        {
            site.setUserAgent(AGENTS[new Random().nextInt(5)]);
            Thread.sleep(new Random().nextInt(20) * 100);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        // 当前页面是图书列表页面
        if (page.getUrl().regex("http(s)?://book\\.douban\\.com/tag/\\S+\\?+").match())
        {
            processForBookList(page);
        }
    }

    /**
     * 图书列表 入保存图书简要信息  获取下一页图书列表URL
     *
     * @author 李熙伟 2016年2月23日
     */
    public void processForBookList(Page page)
    {
        //获取图书基本信息
        int bookNum = page.getHtml().xpath("//li[@class='subject-item']").all().size();
        List<String> bookNames = page.getHtml().xpath("//li[@class='subject-item]/div[@class='info']/h2/a/@title").all();
        List<String> bookUrls = page.getHtml().xpath("//li[@class='subject-item]/div[@class='info']/h2/a/@href").all();
        List<String> bookImgs = page.getHtml().xpath("//li[@class='subject-item]/div[@class='pic']/a/img/@src").all();
        List<String> bookRatings = page.getHtml().xpath("//li[@class='subject-item]/div[@class='info']/div[@class='star clearfix']/span[@class='rating_nums']/text()").all();
        List<String> ratingNums = page.getHtml().xpath("//li[@class='subject-item]/div[@class='info']/div[@class='star clearfix']/span[@class='pl']/text()").all();
        List<String> ddStr = page.getHtml().xpath("//li[@class='subject-item]/div[@class='info']").all();
        int index = 0;
        for (String dd : ddStr)
        {
            if (dd.contains("rating_nums"))
            {
                index++;
            } else
                bookRatings.add(index, null);

        }
        List<String> otherInfos = page.getHtml().xpath("//li[@class='subject-item]/div[@class='info']/div[@class='pub']/text()").all();
        for (int i = 0; i < bookNum; i++)
        {
            try
            {
                SimpleBookInfo sbi = new SimpleBookInfo();
                sbi.setTitle(bookNames.get(i));
                sbi.setUrl(bookUrls.get(i));
                sbi.setBookno(bookUrls.get(i).split("/")[4]);
                sbi.setImg(bookImgs.get(i));
                if (bookRatings.get(i) != null)
                    sbi.setRating(Double.parseDouble(bookRatings.get(i)));
                else
                    sbi.setRating(0.0);

                if (ratingNums.get(i) != null)
                {
                    String value = ratingNums.get(i).substring(2, ratingNums.get(i).length() - 5).replaceAll("少于","").replaceAll("目前无","0");
                    sbi.setRatingnum(Integer.valueOf(value));
                }
                sbi.setTag(page.getUrl().toString().split("/")[4].split("\\?")[0]);
                // 抽取替他信息
                String[] otherInfo = otherInfos.get(i).split("/");
                int l = otherInfo.length - 1;
                //[美] 卡勒德·胡赛尼 / 李继宏 / 上海人民出版社 / 2006-5 / 29.00元
                sbi.setPrice(otherInfo[l]);
                sbi.setPublishtime(otherInfo[l - 1]);
                sbi.setPress(otherInfo[l - 2]);
                sbi.setAuthor(otherInfo[0]);
                if (l > 3)
                {
                    String translators = "";
                    for (int k = 1; k <= l - 2; k++)
                    {
                        if (!otherInfo[k].equals(otherInfo[l - 2]))
                            translators += otherInfo[k];
                        else if (k >= l - 2)
                            break;

                    }
                    sbi.setTranslator(translators);
                }
                page.putField("simpleBookInfo" + i, sbi);
            } catch (Exception e)
            {
                System.out.println("解析图书列表异常:" + e.getMessage());
            }
        }


        // 下一页的Url
        String nextPageUrlXpath = "//span[@class='next']/a/@href";
        // 如果当前页有数据 ，不会返回空，否则返回空
        String hasContentXpath = "//div[@class='grid-16-8 clearfix']/div[@class='article']/div[@id='subject_list']/p";
        // 获取标签
        String nextPageUrl;
        if ((page.getHtml().xpath(hasContentXpath).get() == null) || !(page.getHtml().xpath(hasContentXpath).get().toString().trim().contains("没有找到符合条件的图书")))
        {
            // 把下一页图书列表加入队列
            String url = page.getUrl().toString();
            int currentPage = Integer.parseInt(url.substring(url.indexOf("=")+1, url.indexOf('&')));
            nextPageUrl = url.split("\\?")[0] + "?start=" + (currentPage + 20) + "&type=T";
            page.addTargetRequest(new Request(nextPageUrl).setPriority(1));
        }
    }
}
