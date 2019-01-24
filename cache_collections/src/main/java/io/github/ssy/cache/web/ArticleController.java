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
   * set 直接可以去重，根据返回值确定是否存在
   */
  @RequestMapping("uniq")
  @ResponseBody
  public Object uniq(String key) {
    Long dateTime = System.currentTimeMillis();
    return cacheService.uniq("uniq", key);
  }

  /**
   * 简单的对列，左进右出
   */
  @RequestMapping("listPush")
  @ResponseBody
  public Object listPush(String key) {
    Long dateTime = System.currentTimeMillis();
    return cacheService.listPush("listPush", key);
  }

  /**
   * 简单的对列，左进右出
   */
  @RequestMapping("listPop")
  @ResponseBody
  public Object listPop() {
    Long dateTime = System.currentTimeMillis();
    return cacheService.listPop("listPush");
  }


  /**
   * 设置bit位数据信息
   */
  @RequestMapping("sitBit")
  @ResponseBody
  public Object sitBit() {
    Long dateTime = System.currentTimeMillis();
    return cacheService.sitbit("listPush", 1L, true);
  }

  /**
   * 设置bit位数据信息
   */
  @RequestMapping("getBit")
  @ResponseBody
  public Object getBit() {
    Long dateTime = System.currentTimeMillis();
    return cacheService.getbit("getBit", 1L);
  }


  /**
   * 获取set交集
   */
  @RequestMapping("sinter")
  @ResponseBody
  public Object sinter() {
    return cacheService.sinter("setkey1", "setkey2");
  }


  /**
   * Redis 实现自动补全功能， 1.sort 插入数据会自动排序 2.利用zrank 获取前面的索引 3.zrange key start end  步骤2改成索引改成3的start
   * 4.end范围内数据数据还要自己过滤一下
   */
  @RequestMapping("auto")
  @ResponseBody
  public Object auto() {
    Long index = cacheService.zrank("setkey1", "setkey2");
    return cacheService.zrange("setkey1", index, index + 10);
  }


  /**
   * 可靠队列数据信息,处理key结束之后在移除
   */
  @RequestMapping("rpoplpush")
  @ResponseBody
  public Object rpoplpush() {
    String member = cacheService.rpoplpush("setkey1", "setkey2");
    return cacheService.srem("setkey1", member);
  }


}
