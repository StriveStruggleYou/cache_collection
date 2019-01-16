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
  
  SET key value [EX seconds] [PX milliseconds] [NX|XX]  NX 不存在才执行，XX 存在才执行 NX 可以用于构建分布式锁，执行成功的，对 key 的时间不断追加时间
  
  setnx key value 不存在才设置成功
  
  get key
  
  getset key value 返回旧的值，设置新的值进去
  
  incr key number number 不存在加1 ，存在就指定步长
  
  decr key number number 不存在就减1 存在就减去步长
  
  incrby key number number 必须存在
  
  decrby key nymber number 必须存在
  
  incrbyfloat key number 自增浮点数
  
  append key value 追加key 的值
  
  substr key start end 截取字符串
  
  setrange key offset value 片段修改字符串
  
  getrange key start end 截取字符串
  
  strlen key 获取key的长度
  
  getbit key offset 获取bit 偏移
  
  setbit key offset value 设置bit偏移量
  
  bitcount key start end 统计总共的偏移
  
  bitop 位于操作会阻塞比较长的时间，不建议在master上操作
  
  lpush key string  江湖规矩 lpush rpop 1表是成功 0表示key存在但不是list类型
  
  linsert list after／before key newkey 在list指定位置之前或者是之后插入 key
  
  
  
  

```

## memeroy
