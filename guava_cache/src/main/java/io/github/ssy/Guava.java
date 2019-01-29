package io.github.ssy;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.Weigher;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Guava {

  public static void main(String args[]) throws ExecutionException {

    Cache<Integer, String> cache = CacheBuilder.newBuilder()
        //设置cache的初始大小为10，要合理设置该值
        .initialCapacity(10)
        .maximumSize(15)
        //设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
        .concurrencyLevel(5)
        //设置cache中的数据在写入之后的存活时间为10分钟
        .expireAfterWrite(10, TimeUnit.MINUTES)
        //构建cache实例
        .build();

    //设置遍历设置缓存基础数据信息
    for (int i = 0; i < 18; i++) {
      cache.put(i, "ok" + i);
    }

    //遍历查询
    for (Integer key : cache.asMap().keySet()) {
      String value = cache.get(key, new Callable<String>() {
        public String call() throws Exception {
          return "no";
        }
      });
      System.out.println(value);
    }
    //
  }

  /**
   * guava提供了三种缓存回收的策略：基于容量回收、定时回收和基于引用回收。
   *
   * 基于容量回收的时候，还可以设置一个权重，就可以搞定缓存的整个大小了。
   *
   * code = 1基于容量的key 数量回收 code = 2基于容量的key 权重回收
   *
   * code = 3基于时间多少秒没有get 访问回收 code =4 基于时间 多少秒没有覆盖写 回收,一般会组合使用和容量不会单独使用
   */
  Cache buildCache(int code) {
    Cache<Integer, String> cache = null;
    if (code == 1) {
      cache = CacheBuilder.newBuilder()
          //设置cache的初始大小为10，要合理设置该值
          .initialCapacity(10)
          .maximumSize(15)
          //设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
          .concurrencyLevel(5)
          //设置cache中的数据在写入之后的存活时间为10分钟
          .expireAfterWrite(10, TimeUnit.MINUTES)
          //构建cache实例
          .build();
    } else if (code == 2) {
      cache = CacheBuilder.newBuilder()
          //设置cache的初始大小为10，要合理设置该值
          .initialCapacity(10)
          .maximumWeight(1000000L).weigher(new Weigher<Integer, String>() {
            @Override
            public int weigh(Integer key, String value) {
              return value.length();
            }
          })
          //设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
          .concurrencyLevel(5)
          //设置cache中的数据在写入之后的存活时间为10分钟
          .expireAfterWrite(10, TimeUnit.MINUTES)
          //构建cache实例
          .build();
    } else if (code == 3) {
      cache = CacheBuilder.newBuilder()
          //设置cache的初始大小为10，要合理设置该值
          .initialCapacity(10)
          //设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
          .concurrencyLevel(5)
          //设置cache中的数据在写入之后的存活时间为10分钟
          .expireAfterAccess(10, TimeUnit.MINUTES)
          //构建cache实例
          .build();
    } else if (code == 4) {
      cache = CacheBuilder.newBuilder()
          //设置cache的初始大小为10，要合理设置该值
          .initialCapacity(10)
          //设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
          .concurrencyLevel(5)
          //设置cache中的数据在写入之后的存活时间为10分钟
          .expireAfterWrite(10, TimeUnit.MINUTES)
          //构建cache实例
          .build();

    }

    return cache;
  }

}
