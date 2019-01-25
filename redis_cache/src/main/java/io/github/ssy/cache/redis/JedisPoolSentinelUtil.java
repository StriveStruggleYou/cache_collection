package io.github.ssy.cache.redis;


import io.github.ssy.cache.service.CacheService;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

@Component
public class JedisPoolSentinelUtil {

  JedisSentinelPool jedisSentinelPool;

  private String masterSentinelName;
  private String masterSentinelList;

  public JedisPoolSentinelUtil() {
    masterSentinelName = "mymaster";

    JedisPoolConfig config = new JedisPoolConfig();

    Set sentinelSet = new HashSet(2);
    sentinelSet.add("192.168.60.10:26379");
    sentinelSet.add("192.168.60.10:27379");

    jedisSentinelPool =
        new JedisSentinelPool(masterSentinelName, sentinelSet, config, 30000,
            "chen123", 0);
  }

  public Jedis getResource() {
    return jedisSentinelPool.getResource();
  }


  public static void main(String args[]) {
    JedisPoolSentinelUtil jedisPoolSentinelUtil = new JedisPoolSentinelUtil();
    CacheService cacheService = new CacheService(jedisPoolSentinelUtil);
    String result = cacheService.getFromSentinel("name");
    System.out.println(result);
  }


}
