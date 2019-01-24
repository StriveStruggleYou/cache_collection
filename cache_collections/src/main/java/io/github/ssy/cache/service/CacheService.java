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

  public double zincrby(String key, double incr, String member) {
    Jedis redis = jedisPoolSentinelUtil.getResource();
    try {
      return redis.zincrby(key, incr, member);
    } finally {
      redis.close();
    }
  }

  public Set<String> top(String key) {
    Jedis redis = jedisPoolSentinelUtil.getResource();
    try {
      return redis.zrevrange(key, 0, 10);
    } finally {
      redis.close();
    }
  }


  public double zadd(String key, double score, String member) {
    Jedis redis = jedisPoolSentinelUtil.getResource();
    try {
      ZAddParams params = new ZAddParams();
      //不存在才执行
      params.nx();
      return redis.zadd(key, score, member, params);
    } finally {
      redis.close();
    }
  }


  public double uniq(String key, String member) {
    Jedis redis = jedisPoolSentinelUtil.getResource();
    try {
      ZAddParams params = new ZAddParams();
      //不存在才执行
      params.nx();
      return redis.sadd(key, member);
    } finally {
      redis.close();
    }
  }

  /**
   * 求两个set的交集
   */
  public Set<String> sinter(String setKey1, String setKey2) {
    Jedis redis = jedisPoolSentinelUtil.getResource();
    try {
      return redis.sinter(setKey1, setKey2);
    } finally {
      redis.close();
    }
  }


  //左边推数据，右边拉数据
  public double listPush(String key, String member) {
    Jedis redis = jedisPoolSentinelUtil.getResource();
    try {

      return redis.lpush(key, member);
    } finally {
      redis.close();
    }
  }

  public String listPop(String key) {
    Jedis redis = jedisPoolSentinelUtil.getResource();
    try {
      return redis.rpop(key);
    } finally {
      redis.close();
    }
  }

  public Boolean sitbit(String key, Long offset, Boolean value) {
    Jedis redis = jedisPoolSentinelUtil.getResource();
    try {
      return redis.setbit(key, offset, value);
    } finally {
      redis.close();
    }
  }


  public Boolean getbit(String key, Long offset) {
    Jedis redis = jedisPoolSentinelUtil.getResource();
    try {
      return redis.getbit(key, offset);
    } finally {
      redis.close();
    }
  }

  public Long zrank(String key, String member) {
    Jedis redis = jedisPoolSentinelUtil.getResource();
    try {
      return redis.zrank(key, member);
    } finally {
      redis.close();
    }
  }

  public Set<String> zrange(String key, Long start, Long end) {
    Jedis redis = jedisPoolSentinelUtil.getResource();
    try {
      return redis.zrange(key, start, end);
    } finally {
      redis.close();
    }
  }


  public String rpoplpush(String key1, String key2) {
    Jedis redis = jedisPoolSentinelUtil.getResource();
    try {
      return redis.rpoplpush(key1, key2);
    } finally {
      redis.close();
    }
  }

  /**
   *移除队列中的一个元素
   */
  public Long srem(String key1, String key2) {
    Jedis redis = jedisPoolSentinelUtil.getResource();
    try {
      return redis.srem(key1, key2);
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
