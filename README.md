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
  
  llen key 如果key不存在就是0 如果不是list类型就报错
  
  lindex mylist index 返回list 索引的位置
  
  lrange key start end 查看范围的 list 0 -1 表示返回全部（-1 是倒数最后一个的意思）
  
  ltrim key start end 保留指定区间的元素，返回的n是被移除的元素个数
  
  lrem key count value 从list 中删除count个的值
  
  lpop key 从头部删除
  
  rpop 从尾部删除
  
  lset key index value 设置list指定index 的值
  
  blpop key key1 keyn timeout 阻塞队列 从头部删除 
  
  brpop 从尾部删除
  
  rpoplpush srckey destkey 从一个队列尾部移动到一个队列的头部返回值的移除的数据，两个队列交换数据。安全队列使用 RPOPLPUSH 命令(或者它的阻塞版本 BRPOPLPUSH )可以解决这个问题：因为它不仅返回一个消息，同时还将这个消息添加到另一个备份列表当中，如果一切正常的话，当一个客户端完成某个消息的处理之后，可以用 LREM 命令将这个消息从备份表删除。另一个应用场景是循环链表。
  
  sadd key member 成功返回1 如果在集合中返回0，添加的key不是set就返回错误
  
  srem key member 成功返回1 如果 集合不存在 member不存在返回0 key 不是set返回错误
  
  spop key 如果set为空或者不存在返回nil
  
  srandommember key 随机取一个set的值但是不删除
  
  smove srckey destkey member 从srckey 移除 member 到destkey
  
  scard key 查看集合大小
  
  sismember key member 判断 member是否在set中
  
  sinter key key1 返回指定 set的交集
  
  sinterstore destkey key key1 交集存到destkey
  
  sunion key key1 并集
  
  sunionstrore destkey key key1 并集存到destkey
  
  sdiff key key1 差集
  
  sdiffstore destkey key key1 差集存储到destkey
  
  smembers key 返回set所有元素，set过多会阻塞，生产禁止使用
  
  zadd key score member  有序集合的评分
  
  zrem key member 删除有序集合的key
  
  zremrankgebyrank 删除排名在指定区间的范围
  
  zremrenkgescore 删除得分在指定区间的范围
  
  2.5.3 待续
  

```

### 高可用redis

#### 主从模式master／salve 模式
```
salve 的机器需要在redis.conf文件中
添加
slaveof master host port

slave-read-only yes 表示slave 只读不写，这样做主从切换的时候要把slave单独切换成 master。

切换slave命令
slaveof no one 这样就变成一个独立的master了，就可以写入了。
```
一主多slave部署
```
1.不推荐拓扑结构,这样切换master要修改很多个slave的redis的情况

2.推荐链状结构，master---->slave1------>slave2当我们变slave1成master的时候不需要修改slave2
```
应用层面如何去实现主从切换
```
1.zk实现的开关

2.开关是否全部生效的监控问题

3.为防止丢失数据，切勿将redis保存重要的数据信息

java代码实现,参考cache_collections里面的具体实现

实现缺陷，如果不是人为主动去切换，那么master挂掉就会有一段时间不能进行使用，那么看一下redis 的哨兵模式是怎么解决这个问题的。
```

#### sentinel模式主动切换
sentinel 配置文件
```
port 26379
bind 192.168.60.10
daemonize no
pidfile "/var/run/redis-sentinel1.pid"
logfile ""
dir "/private/tmp"
sentinel myid 6c677bfb2642e1b873e1ad248b9de71972d4d1c2
sentinel deny-scripts-reconfig yes
sentinel monitor mymaster 192.168.60.10 7379 2
#redis 有密码的时候需要加上
sentinel auth-pass mymaster chen123
protected-mode no
sentinel config-epoch mymaster 2
sentinel leader-epoch mymaster 2
```
应用层面实现
```
参考 cache_collection 里面的具体实现
1.注意，旧master起来的时候因为已经有一个新的master了，如果要满足主从复制，那么我们需要在原来旧master上配置
masterauth "chen123" 原来他自己是master 可能就没有配，但是主从一切换他就变从了。所以理论上所有的都需要配上
这个东西。

2.sentinel 故障转移也有一个短暂时间不可用，需要非get请求记录到日志里面，后续可以把这段时间的请求，通过日志重新写入。
```



## memeroy
