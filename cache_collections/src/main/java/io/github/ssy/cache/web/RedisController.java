package io.github.ssy.cache.web;


import io.github.ssy.cache.redis.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/redis")
public class RedisController {

  @Autowired
  CacheService cacheService;


  @RequestMapping("/get")
  @ResponseBody
  public Object get() {
    return cacheService.get("name");
  }

  @RequestMapping("/get1")
  @ResponseBody
  public Object get1() {
    return cacheService.getFromSentinel("name");
  }
}
