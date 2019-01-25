package io.github.ssy.cache.switchs;

import io.github.ssy.cache.switchs.Listener.RedisNodeCacheListener;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.stereotype.Component;


@Component
public class SwitchFactory {

  //全局缓存
  private static final ConcurrentMap<String, Integer> switchCache = new ConcurrentHashMap<>();

  CuratorFramework client = null;

  NodeCache cache = null;

  public SwitchFactory() {
    //设置client
    client = CuratorFrameworkFactory
        .newClient("192.168.60.10:2181", new ExponentialBackoffRetry(3000, 3));
    cache = new NodeCache(client, SwitchEnum.CDN_REDIS1_ENUM.getRelativePath(), false);
    cache.getListenable()
        .addListener(new RedisNodeCacheListener(client, SwitchEnum.CDN_REDIS1_ENUM, this));
    client.start();
    try {
      cache.start();
    } catch (Exception e) {
      e.printStackTrace();
    }


    //还需要一个初始化的感觉
    switchCache.put(SwitchEnum.CDN_REDIS1_ENUM.getRelativePath(), 0);

  }


  public void putSwitch(String path, Integer value) {
    switchCache.put(path, value);
  }


  public Integer getSwitch(String path) {
    return switchCache.get(path);
  }


}
