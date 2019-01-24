package io.github.ssy.cache.web;

import io.github.ssy.cache.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/article")
public class ArticleController {

  private String articleKey = "articleKey";

  private String likeKey = "articleLike";

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

  /**
   * 排行榜应用，取 TOP N 操作,点赞上升排名
   */
  @RequestMapping("doLike")
  @ResponseBody
  public Object doLike(String article) {
    cacheService.zincrby(likeKey, 1.0, article);
    return "success";
  }

  /**
   * 排行榜应用，取 TOP N 操作
   */
  @RequestMapping("topLike")
  @ResponseBody
  public Object topLike() {
    return cacheService.top(likeKey);
  }

  /**
   * 需要精准设定过期时间的应用 通过nx xx 两种参数可以适应不同的场景
   */
  @RequestMapping("addExpirationDate")
  @ResponseBody
  public Object addExpirationDate(String key) {
    Long dateTime = System.currentTimeMillis();
    return cacheService.zadd("addExpirationDate", dateTime, key);
  }

  /**
   * set 直接可以去重
   */
  @RequestMapping("uniq")
  @ResponseBody
  public Object uniq(String key) {
    Long dateTime = System.currentTimeMillis();
    return cacheService.uniq("uniq", key);
  }


}
