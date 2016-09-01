package cn.edu.ncut.doubanWebSpider.spider.pipeline;

import cn.edu.ncut.doubanWebSpider.dao.BookCommentMapper;
import cn.edu.ncut.doubanWebSpider.model.BookComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;
@Component
@Transactional
public class BookCommentPipeline implements Pipeline
{
	@Autowired
	private BookCommentMapper bookCommentMapper;
	
	public void process(ResultItems resultItems, Task task)
	{
		for(Map.Entry<String, Object> entry : resultItems.getAll().entrySet())
		{
			try
			{
				BookComment comment = (BookComment) entry.getValue();
				bookCommentMapper.insert(comment);
			}
			catch(Exception e)
			{
				System.out.println("插入书评数据异常:" + e.getCause() );
				//RedisUtil.push(QueueNameConstant.QUEUE_COMMENT_ERROR,resultItems.getRequest().getUrl());
			}
		}
	}
}
