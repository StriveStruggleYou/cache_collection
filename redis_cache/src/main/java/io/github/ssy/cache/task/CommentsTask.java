package io.github.ssy.cache.task;

import io.github.ssy.cache.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CommentsTask {

  @Autowired
  CacheService cacheService;

  /**
   * 每分钟执行一次缩容
   */
  @Scheduled(fixedDelay = 60 * 1000)
  public void trimTask() {
    System.out.println("--------lTrimList--------");
    cacheService.lTrimList("articleKey", 0, 10);
  }






}
