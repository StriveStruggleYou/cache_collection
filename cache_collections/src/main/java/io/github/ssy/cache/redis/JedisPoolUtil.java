package io.github.ssy.cache.redis;

import io.github.ssy.cache.switchs.RedisSwitch;
import io.github.ssy.cache.switchs.Switch;
import io.github.ssy.cache.switchs.SwitchFactory;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class JedisPoolUtil {

  @Autowired
  SwitchFactory switchFactory;

  //设置master
  private JedisPool masterPool;

  //设置host
  private String masterHost="192.168.60.10";
  private String masterPwd="chen123";
  private int masterPort=6379;
  private int masterTimeout = 3000;


  //设置backup
  private JedisPool slavePool;
  //设置host
  private String slaveHost="192.168.60.10";
  private String slavePwd="chen123";
  private int slavePort=7379;
  private int slaveTimeout = 3000;

  //设置
  private Switch salveSwitch;


  private int defaultDatabase = 0;

  private String applicationName = "cdn_web";



  @PostConstruct
  public void init() throws Exception {
//    (final GenericObjectPoolConfig poolConfig, final String host, int port,
//    int timeout, final String password, final int database, final String clientName)

    //初始化一下
    salveSwitch=new RedisSwitch(switchFactory);

    if (StringUtils.isNotBlank(masterHost)) {
      masterPool = new JedisPool(new GenericObjectPoolConfig(), masterHost, masterPort,
          masterTimeout,masterPwd, defaultDatabase, "cdn_web");
    }

    if (StringUtils.isNotBlank(slaveHost)) {
      slavePool = new JedisPool(new GenericObjectPoolConfig(), slaveHost, slavePort, slaveTimeout,
          slavePwd, defaultDatabase, "cdn_web");
    }

    //设置
    if ((slavePool == null) && (masterPool == null)) {
      throw new Exception("JedisPoolUtil init error");
    }

  }


  public Jedis getResource() {
    if (salveSwitch.isOn() && slavePool != null) {
      return slavePool.getResource();
    }
    return masterPool.getResource();
  }


  public static void main(String args[]) throws Exception {
    JedisPoolUtil jedisPoolUtil=new JedisPoolUtil();

    jedisPoolUtil.init();

    Jedis jedis=jedisPoolUtil.getResource();

    CacheService cacheService=new CacheService(jedisPoolUtil);

    System.out.println(cacheService.get("name"));
  }

}
