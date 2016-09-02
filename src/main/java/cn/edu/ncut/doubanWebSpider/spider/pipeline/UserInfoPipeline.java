package cn.edu.ncut.doubanWebSpider.spider.pipeline;

import cn.edu.ncut.doubanWebSpider.dao.UserInfoMapper;
import cn.edu.ncut.doubanWebSpider.model.UserInfo;
import cn.edu.ncut.doubanWebSpider.spider.schedule.QueueNameConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import utils.RedisUtil;

import java.util.Map;

/**
 * Created by lixiwei on 2016/8/29.
 */
@Component
public class UserInfoPipeline implements Pipeline
{
    @Autowired
    private UserInfoMapper userInfoMapper;

    public void process(ResultItems resultItems, Task task)
    {
        for(Map.Entry<String, Object> entry : resultItems.getAll().entrySet())
        {
            try
            {
                UserInfo user = (UserInfo) entry.getValue();
                if (userInfoMapper.selectByUserno(user.getUserno()).size() <= 0)
                    userInfoMapper.insert(user);
            }
            catch(Exception e)
            {
                System.out.println("插入用户数据异常:" + e.getCause() );
                //RedisUtil.push(QueueNameConstant.QUEUE_USER_ERROR,resultItems.getRequest().getUrl());
            }
        }
    }
}
