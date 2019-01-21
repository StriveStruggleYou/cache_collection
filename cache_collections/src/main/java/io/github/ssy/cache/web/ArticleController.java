package io.github.ssy.cache.web;

import io.github.ssy.cache.redis.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/article")
public class ArticleController {

  private String articleKey = "articleKey";

  @Autowired
  CacheService cacheService;

  /**
   * 如果超过100之后的数据，就从mysql中取，
   */
  @RequestMapping("listComments")
  @ResponseBody
  public Object listComments() {
    //
    return cacheService.lRange(articleKey, 0, 10);
  }

  @RequestMapping("addComment")
  @ResponseBody
  public Object addComment(String comment) {
    cacheService.lPushList(articleKey, comment);
    return "success";
  }


}
