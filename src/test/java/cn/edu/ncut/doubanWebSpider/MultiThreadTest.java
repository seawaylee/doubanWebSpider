package cn.edu.ncut.doubanWebSpider;

import utils.HttpPoster;

/**
 * 多线程测试类
 *
 * @author lixiwei-mac
 * @create 17:35
 */
public class MultiThreadTest extends Thread
{
    @Override
    public void run()
    {
        HttpPoster.sendPost("http://localhost:8080/douban/multiprocess.do");
    }

    public static void main(String[] args)
    {
        for(int i=0; i<5; i++)
        {
            MultiThreadTest thread = new MultiThreadTest();
            thread.start();
        }
    }
}
