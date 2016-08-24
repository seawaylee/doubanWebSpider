package cn.edu.ncut.doubanWebSpider.spider.pipeline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.edu.ncut.doubanWebSpider.dao.BookInfoMapper;
import cn.edu.ncut.doubanWebSpider.model.BookInfo;
import cn.edu.ncut.doubanWebSpider.spider.schedule.QueueNameConstant;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import utils.RedisUtil;
@Component
public class BookInfoPipeline implements Pipeline
{
	@Autowired
	private BookInfoMapper bookInfoMapper;

	public void process(ResultItems resultItems, Task task)
	{
		try
		{
			BookInfo bookInfo = resultItems.get("bookInfo");
			if(bookInfo != null)
				bookInfoMapper.insert(bookInfo);
		} catch (Exception e)
		{
			System.out.println("插入图书信息数据异常:" + e.getCause() );
			RedisUtil.push(QueueNameConstant.QUEUE_BOOK_ERROR,resultItems.getRequest().getUrl());
		}
	}
}
