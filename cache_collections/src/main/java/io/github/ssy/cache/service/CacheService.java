package io.github.ssy.cache.service;

import io.github.ssy.cache.redis.JedisPoolSentinelUtil;
import io.github.ssy.cache.redis.JedisPoolUtil;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.ZAddParams;


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

  public double zincrby(String key,double incr,String member){
    Jedis redis = jedisPoolSentinelUtil.getResource();
    try {
      return redis.zincrby(key, incr, member);
    } finally {
      redis.close();
    }
  }

  public Set<String> top(String key){
    Jedis redis = jedisPoolSentinelUtil.getResource();
    try {
      return redis.zrevrange(key, 0, 10);
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