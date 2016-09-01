package cn.edu.ncut.doubanWebSpider.spider.schedule;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.DuplicateRemovedScheduler;
import us.codecraft.webmagic.scheduler.MonitorableScheduler;
import us.codecraft.webmagic.scheduler.component.DuplicateRemover;

public class RedisScheduler extends DuplicateRemovedScheduler implements
		MonitorableScheduler, DuplicateRemover {

	private JedisPool pool;

	private Integer index;

	//在插入redis的队列中时，对url进行处理
	private String queueName = null;
	
	private static final String QUEUE_PREFIX = "queue_";

	private static final String SET_PREFIX = "set_";

	private static final String ITEM_PREFIX = "item_";
	
	public RedisScheduler(JedisPool pool, Integer index,String queueName) {
		this.pool = pool;
		this.index = index;
		this.queueName = queueName;
		setDuplicateRemover(this);
	}

	public RedisScheduler(JedisPool pool, Integer index ) {
		this.pool = pool;
		this.index = index;
		setDuplicateRemover(this);
	}
	
	public Jedis getJedis() {
		for (int i = 0; i < 50; i++)
		{
			try
			{
				Jedis jedis = pool.getResource();
				jedis.select(index);
				return jedis;
			}catch(Exception e)
			{
				continue;
			}
		}
		return null;
	}

	public void resetDuplicateCheck(Task task) {
		Jedis jedis = getJedis();
		try {
			jedis.del(getSetKey(task));
		} 
		catch(Exception e)
		{
			e.getMessage();
			System.out.println("resetDuplicateCheck Error");
		}
		finally {
			pool.returnResource(jedis);
		}
	}
	// URL放到 Set里进行查重
	public boolean isDuplicate(Request request, Task task) {
		Jedis jedis = null;
		for (int i = 0; i < 50; i++)
		{
			try
			{
				jedis = getJedis();
				boolean isDuplicate = jedis.sismember(getSetKey(task),
						request.getUrl());
				if (!isDuplicate)
				{
					jedis.sadd(getSetKey(task), request.getUrl());
				}
				return isDuplicate;
			} catch (Exception e)
			{
				e.getMessage();
				try{
					Thread.sleep(5000);
				} catch (InterruptedException e1)
				{
					e1.printStackTrace();
				}
				System.out.println("isDuplicate Error");
				continue;
			} finally
			{
				pool.returnResource(jedis);
			}
		}
		return false;
	}
	// 经过Set查重，如果不重复，加入待爬取队列
	// 将待爬取url加入list的同时，会把链接的优先级、extra等信息加入hash列表
	@Override
	protected void pushWhenNoDuplicate(Request request, Task task) {
		Jedis jedis = null;
		for (int i = 0; i < 50; i++)
		{

			try
			{
				jedis = getJedis();
				jedis.rpush(getQueueKey(task), request.getUrl());
				if (request.getExtras() != null)
				{
					String field = DigestUtils.shaHex(request.getUrl());
					String value = JSON.toJSONString(request);
					jedis.hset((ITEM_PREFIX + task.getUUID()), field, value);
				}
				break;
			} catch (Exception e)
			{
				e.getMessage();
				System.out.println("pushWhenNoDuplicate Error");
				try
				{
					Thread.sleep(5000);
				} catch (InterruptedException e1)
				{
					e1.printStackTrace();
				}
				continue;
			} finally
			{
				pool.returnResource(jedis);
			}
		}
	}
	// 从队列中提取Url
	public synchronized Request poll(Task task) {
		Jedis jedis = null;
		for (int i = 0; i < 50; i++)
		{

			try
			{
				jedis = getJedis();
				String url = jedis.lpop(getQueueKey(task));
				if (url == null)
				{
					return null;
				}
				String key = ITEM_PREFIX + task.getUUID();
				String field = DigestUtils.shaHex(url);
				byte[] bytes = jedis.hget(key.getBytes(), field.getBytes());
				if (bytes != null)
				{
					Request o = JSON.parseObject(new String(bytes), Request.class);
					return o;
				}
				Request request = new Request(url);
				return request;
			} catch (Exception e)
			{
				e.getMessage();
				System.out.println("poll Error");
				try
				{
					Thread.sleep(5000);
				} catch (InterruptedException e1)
				{
					e1.printStackTrace();
				}
				continue;
			} finally
			{
				pool.returnResource(jedis);
			}
		}
		return null;
	}

	protected String getSetKey(Task task) {
		return SET_PREFIX + task.getUUID();
	}

	protected String getQueueKey(Task task) {
//		if(StringUtils.isNotBlank(queueName))
//			return queueName + task.getUUID();
//		else
		return QUEUE_PREFIX + task.getUUID();
	}
	
	public int getLeftRequestsCount(Task task) {
		Jedis jedis = getJedis();
		try {
			Long size = jedis.llen(getQueueKey(task));
			return size.intValue();
		} finally {
			pool.returnResource(jedis);
		}
	}

	public int getTotalRequestsCount(Task task) {
		Jedis jedis = getJedis();
		try {
			Long size = jedis.scard(getQueueKey(task));
			return size.intValue();
		} finally {
			pool.returnResource(jedis);
		}
	}

}
