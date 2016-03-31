package cn.edu.ncut.doubanWebSpider.spider.pipeline;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.edu.ncut.doubanWebSpider.dao.SimpleBookInfoMapper;
import cn.edu.ncut.doubanWebSpider.model.BookComment;
import cn.edu.ncut.doubanWebSpider.model.SimpleBookInfo;
import cn.edu.ncut.doubanWebSpider.spider.schedule.QueueNameConstant;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import utils.RedisUtil;
@Component
public class SimpleBookInfoPipeline implements Pipeline
{
	@Autowired
	private SimpleBookInfoMapper simpleBookInfoMapper;

	public void process(ResultItems resultItems, Task task)
	{
		for(Map.Entry<String, Object> entry : resultItems.getAll().entrySet())
		{
			try
			{
				SimpleBookInfo sbi = (SimpleBookInfo) entry.getValue();
				simpleBookInfoMapper.insert(sbi);
			}
			catch(Exception e)
			{
				System.out.println("插入简要图书数据异常:" + e.getCause() );
				RedisUtil.push(QueueNameConstant.QUEUE_SIMPLE_BOOK_ERROR,resultItems.getRequest().getUrl());
			}
		}
	}
}
