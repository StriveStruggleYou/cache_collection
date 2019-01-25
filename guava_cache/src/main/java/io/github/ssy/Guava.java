package io.github.ssy;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
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

}
