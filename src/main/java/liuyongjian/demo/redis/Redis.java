package liuyongjian.demo.redis;

import java.io.IOException;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.Response;

public class Redis {
	public Jedis conn() {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxTotal(20);
		
		JedisPool pool = new JedisPool(config, "192.168.91.131", 6379, Protocol.DEFAULT_TIMEOUT, "111111", 0);
		Jedis jedis = pool.getResource();
		pool.close();
		return jedis;
	}
	
	public static void main(String[] args) {
		Jedis jedis = new Redis().conn(); 
		
		Pipeline pipeline = jedis.pipelined();
		pipeline.multi();
		pipeline.incr("test");
		pipeline.incr("test");
		pipeline.incrBy("test", 10);
		Response<List<Object>> response = pipeline.exec();
		try {
			pipeline.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		response.get().forEach(l -> {
			System.err.println(l.toString());
		});
		
		// 保存快照
		String string = jedis.bgsave();
		System.err.println(string);
		
		jedis.close();
	}
}
