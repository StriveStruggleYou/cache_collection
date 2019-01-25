package io.github.ssy.lru;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LfuCache {

  Map<String, KeyHint> cache;

  private final int maxMemorySize;

  List<KeyHint> keyHintList;

  LfuCache(int capacity) {
    cache = new ConcurrentHashMap(capacity + 10, 0.99F);
    keyHintList = new LinkedList<KeyHint>();
    maxMemorySize = capacity;
  }


  String get(String key) {
    synchronized (this) {
      //排序,
      KeyHint oldKeyHint = cache.get(key);
      KeyHint newKeyHint = new KeyHint();
      newKeyHint.setKey(oldKeyHint.key);
      newKeyHint.setValue(oldKeyHint.value);
      newKeyHint.setHit(oldKeyHint.hit);
      keyHintList.remove(oldKeyHint);
      keyHintList.add(newKeyHint);
      Collections.sort(keyHintList, new Comparator<KeyHint>() {
        @Override
        public int compare(KeyHint o1, KeyHint o2) {
          return o1.getHit() - o2.getHit();
        }
      });

      return cache.get(key).getValue();
    }
  }


  String put(String key, String value) {
    synchronized (this) {
      //先移除
      int maxNum = cache.size();
      if (maxNum > maxMemorySize) {
        //移除头部的
        keyHintList.remove(0);
      }
      KeyHint keyHint = new KeyHint();
      keyHint.setKey(key);
      keyHint.setValue(value);
      keyHint.setHit(1);
      cache.put(key, keyHint);
      keyHintList.add(keyHint);
      return "";
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
      keyHintList.clear();
    }
  }

  int getMaxMemorySize() {
    return maxMemorySize;
  }


  class KeyHint {

    private String key;

    private String value;

    private Integer hit;

    public String getKey() {
      return key;
    }

    public void setKey(String key) {
      this.key = key;
    }

    public Integer getHit() {
      return hit;
    }

    public void setHit(Integer hit) {
      this.hit = hit;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }
  }

}
