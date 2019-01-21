package io.github.ssy.cache.redis;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.util.SafeEncoder;


@Service
public class CacheService {

  public CacheService() {
  }


  @Autowired
  JedisPoolUtil jedisPoolUtil;

  @Autowired
  JedisPoolSentinelUtil jedisPoolSentinelUtil;

  public String get(String key) {
    Jedis redis = jedisPoolUtil.getResource();
    try {
      return redis.get(key);
    } finally {
      redis.close();
    }
  }


  public String getFromSentinel(String key) {
    Jedis redis = jedisPoolSentinelUtil.getResource();
    try {
      return redis.get(key);
    } finally {
      redis.close();
    }
  }

  public long lPushList(String key, String value) {
    Jedis redis = jedisPoolSentinelUtil.getResource();
    try {
      return redis.lpush(key, value);
    } finally {
      redis.close();
    }
  }


  public String lTrimList(String key, int start, int end) {
    Jedis redis = jedisPoolSentinelUtil.getResource();
    try {
      return redis.ltrim(key, start, end);
    } finally {
      redis.close();
    }
  }


  public List<String> lRange(String key, int start, int end) {
    Jedis redis = jedisPoolSentinelUtil.getResource();
    try {
      return redis.lrange(key, start, end);
    } finally {
      redis.close();
    }
  }

  public CacheService(JedisPoolUtil jedisPoolUtil) {
    this.jedisPoolUtil = jedisPoolUtil;
  }

  public CacheService(JedisPoolSentinelUtil jedisPoolSentinelUtil) {
    this.jedisPoolSentinelUtil = jedisPoolSentinelUtil;
  }
}
