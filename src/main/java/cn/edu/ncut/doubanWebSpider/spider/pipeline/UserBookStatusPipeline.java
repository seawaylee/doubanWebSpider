package cn.edu.ncut.doubanWebSpider.spider.pipeline;

import cn.edu.ncut.doubanWebSpider.dao.UserBookStatusMapper;
import cn.edu.ncut.doubanWebSpider.dao.UserInfoMapper;
import cn.edu.ncut.doubanWebSpider.model.UserBookStatus;
import cn.edu.ncut.doubanWebSpider.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

/**
 * Created by lixiwei on 2016/8/29.
 */
@Component
public class UserBookStatusPipeline implements Pipeline
{
    @Autowired
    private UserBookStatusMapper userBookStatusMapper;

    public void process(ResultItems resultItems, Task task)
    {
        for(Map.Entry<String, Object> entry : resultItems.getAll().entrySet())
        {
            try
            {
                UserBookStatus user = (UserBookStatus) entry.getValue();
                userBookStatusMapper.insert(user);
            }
            catch(Exception e)
            {
                System.out.println("插入用户-图书数据异常:" + e.getCause() );
            }
        }
    }
}
