package cn.edu.ncut.doubanWebSpider.spider.pipeline;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.edu.ncut.doubanWebSpider.dao.BookTagMapper;
import cn.edu.ncut.doubanWebSpider.model.BookTag;
import org.springframework.transaction.annotation.Transactional;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
@Component("BookTagPipeline")
@Transactional
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
				BookTag tag = (BookTag) entry.getValue();
				List<BookTag> bookTags = bookTagMapper.selectByName(tag.getTagname());
				if (bookTags.size() <= 0)
					bookTagMapper.insert(tag);
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
