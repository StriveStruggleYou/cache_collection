package io.github.ssy;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalListeners;
import com.google.common.cache.RemovalNotification;
import com.google.common.cache.Weigher;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Guava {

  public static void main(String args[]) throws ExecutionException {

    try {
      refesh();
      Thread.sleep(100000);
    } catch (Exception e) {

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

  /**
   * 移除监听器
   */
  static Cache<Integer, String> removeCache() throws InterruptedException {

    RemovalListener<Integer, String> removalListener = new RemovalListener<Integer, String>() {
      public void onRemoval(RemovalNotification<Integer, String> removal) {
        System.out.println("remove key:" + removal.getKey() + " value:" + removal.getValue());
      }
    };

    Cache<Integer, String> cache = CacheBuilder.newBuilder()
        //设置cache的初始大小为10，要合理设置该值
        .initialCapacity(5)
        //设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
        .concurrencyLevel(5)
        .maximumSize(3)
        //设置cache中的数据在写入之后的存活时间为10分钟
        .expireAfterWrite(1, TimeUnit.SECONDS)
        .removalListener(removalListener)
        //构建cache实例
        .build();

    cache.put(1, "nihao");

    Thread.sleep(10000);
    return cache;

  }

  /**
   * 移除异步监听器
   */

  public Cache<Integer, String> removeAyncCache() throws Exception {
    RemovalListener<Integer, String> removalListener = RemovalListeners
        .asynchronous(new RemovalListener<Integer, String>() {
          public void onRemoval(RemovalNotification<Integer, String> removal) {
            System.out.println("remove key:" + removal.getKey() + " value:" + removal.getValue());
          }
        }, Executors.newSingleThreadExecutor());

    Cache<Integer, String> cache = CacheBuilder.newBuilder()
        //设置cache的初始大小为10，要合理设置该值
        .initialCapacity(5)
        //设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
        .concurrencyLevel(5)
        .maximumSize(3)
        //设置cache中的数据在写入之后的存活时间为10分钟
        .expireAfterWrite(1, TimeUnit.SECONDS)

        .removalListener(removalListener)
        //构建cache实例
        .build();
    cache.put(1, "nihao");
    Thread.sleep(10000);
    return cache;
  }

  public static void refesh() throws ExecutionException, InterruptedException {
    final Cache<Integer, String> cache = CacheBuilder.newBuilder()
        .refreshAfterWrite(5, TimeUnit.SECONDS)
        .build(new CacheLoader<Integer, String>() {
          @Override
          public String load(Integer integer) throws Exception {
            return "2222";
          }

          @Override
          public ListenableFuture<String> reload(Integer key, String oldValue) throws Exception {
            ListenableFuture<String> listenableFuture = Futures.immediateFuture("test");
            return listenableFuture;
          }
        });

    cache.put(1, "nihao");

    System.out.println(cache.getIfPresent(1));

    System.out.println(cache.getIfPresent(2));

    Thread.sleep(6000);

    System.out.println(cache.getIfPresent(1));

    System.out.println(cache.getIfPresent(1));


  }


}
