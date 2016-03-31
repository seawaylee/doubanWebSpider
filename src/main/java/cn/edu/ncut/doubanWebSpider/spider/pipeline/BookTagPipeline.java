package cn.edu.ncut.doubanWebSpider.spider.pipeline;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.edu.ncut.doubanWebSpider.dao.BookTagMapper;
import cn.edu.ncut.doubanWebSpider.model.BookTag;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
@Component("BookTagPipeline")
public class BookTagPipeline implements Pipeline
{
	@Autowired
	public BookTagMapper bookTagMapper;

	public void process(ResultItems resultItems, Task task)
	{
		try
		{
			for(Map.Entry<String, Object> entry : resultItems.getAll().entrySet())
			{
				BookTag bookTag = (BookTag) entry.getValue();
				bookTagMapper.insert(bookTag);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("插入标签数据异常");
		}
	}
//	@Test
//	public void test()
//	{
//		ApplicationContext  context = new ClassPathXmlApplicationContext("classpath:ssm/*.xml");
//		BookTagMapper mapper = context.getBean(BookTagMapper.class);
//		BookTag c = new  BookTag();
//		c.setTagname("aaa");
//		c.setUrl("rrrrrrrrrr");
//		mapper.insert(c);
//	}
}
