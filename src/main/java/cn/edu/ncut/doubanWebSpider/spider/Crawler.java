package cn.edu.ncut.doubanWebSpider.spider;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.JedisPool;

public interface Crawler 
{
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:ssm/spring-*.xml");
//	JedisPool pool = new JedisPool(new JedisPoolConfig(),ConfigUtil.getProperty("redis", "redis.ip") );
	JedisPool pool = null;//RedisUtil.init();
	public void crawl();
}
