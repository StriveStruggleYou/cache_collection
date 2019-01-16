# cache_collection

## redis

### 命令总结

key 操作
```
  keys * ？ [] 通配符 列出有关的key 阻塞
  
  scan 0 返回第一行是下一次所带的数字 再次返回0就遍历结束 不会阻塞
  
  exists key 是否存在 1存在 0不存在
  
  del key key1 keyn 1存在 0 不存在
  
  type key 返回key的类型 返回 none 不存在key
  
  randomkey 随机返回key 数据库为空返回空串
  
  rename oldkey newkey 如果newkey 存在就会被覆盖 返回1就成功 0就失败 失败原因有oldkey不存在 oldkey和newkey名称一致
  
  expire key seconds 设置key 的过期时间
  
  pexpire key 毫秒 设置毫秒时间
  
  ttl key 检查key的过期时间 -1 表示没有设置过期时间 -2表示key不存在
  
  pttl key 毫秒的生命周期，其余同上
  
  
  

```

## memeroy
