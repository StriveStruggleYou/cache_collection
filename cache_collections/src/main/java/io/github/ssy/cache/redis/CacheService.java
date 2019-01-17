package io.github.ssy.cache.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.util.SafeEncoder;


@Service
public class CacheService {

  @Autowired
  JedisPoolUtil jedisPoolUtil;

  public String get(String key) {
    Jedis redis = jedisPoolUtil.getResource();
    try {
      return redis.get(key);
    } finally {
      redis.close();
    }
  }

  public CacheService(JedisPoolUtil jedisPoolUtil) {
    this.jedisPoolUtil = jedisPoolUtil;
  }
}
