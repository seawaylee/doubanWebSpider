package cn.edu.ncut.doubanWebSpider.spider.pipeline;


import cn.edu.ncut.doubanWebSpider.dao.CommentMapper;
import cn.edu.ncut.doubanWebSpider.model.SimpleBookInfo;
import cn.edu.ncut.doubanWebSpider.model.weibo.Comment;
import cn.edu.ncut.doubanWebSpider.spider.schedule.QueueNameConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import utils.RedisUtil;

import java.util.Map;

/**
 * @author NikoBelic
 * @create 16/8/18 23:26
 */
@Component
public class CommentPipeline implements Pipeline
{
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public void process(ResultItems resultItems, Task task)
    {
        for(Map.Entry<String, Object> entry : resultItems.getAll().entrySet())
        {
            try
            {
                Comment comment = (Comment) entry.getValue();
                commentMapper.insert(comment);
            }
            catch(Exception e)
            {
                System.out.println("插入简要微博评论数据异常:" + e.getCause() );
            }
        }
    }
}
