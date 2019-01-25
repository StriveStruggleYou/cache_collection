package io.github.ssy.lru;

import java.util.LinkedHashMap;
import java.util.Map;

public class LruCache {


  Map<String, String> cache;

  private final int maxMemorySize;


  public LruCache(int capacity) {
    if (capacity <= 0) {
      throw new IllegalArgumentException("capacity <= 0");
    }
    //这样不用怎么扩容
    this.cache = new LinkedHashMap(capacity + 10, 0.99F, true);
    maxMemorySize = capacity;
  }


  String get(String key) {
    synchronized (this) {
      return cache.get(key);
    }
  }


  String put(String key, String value) {
    synchronized (this) {
      String result = cache.put(key, value);
      int maxNum = cache.size();
      if (maxNum > maxMemorySize) {
        //删除排头的第一个
        Map.Entry<String, String> toRemove = cache.entrySet().iterator().next();
        cache.remove(toRemove.getKey());
      }
      return result;
    }
  }

  public String remove(String key) {
    synchronized (this) {
      return cache.remove(key);
    }
  }


  void clear() {
    synchronized (this) {
      cache.clear();
    }
  }

  int getMaxMemorySize() {
    return maxMemorySize;
  }


}
