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
  
  zincrby key incr member 增减score的分数
  
  zrank key member 获取当前对象的排名，从小到大
  
  zrevrank key member 获取当前对象的排名，从大到小
  
  zrange key start end 获取排行榜，有序结果从小到大
  
  zrevrange key start end 获取排行榜，有序结果从大到小
  
  zrangebyscore key min max 获取指定分数间的所有元素
  
  zcount key min max 统计指定分数间元素数量
  
  zscore key element 返回指定key的对象的分数
  
  zunionstore 评分聚合
  
  hset key field value 设置key的hash值
  
  hsetnx 如果key不存在先创建
 
  hget key field 获取hash值
  
  hmget key field1 field2 一次获取多个hash的 value
  
  hincrby key field integr 指定hash field 自增
  
  hexistis key field 判断hash key值是否存在
  
  hdel key field 删除hash域
  
  hlen key 获取hash 的长度
  
  hkeys key 获取所有的key
  
  hvals key 获取所有的value
  
  hgetall 获取hash key and value
```
HyperLogLog大数据非精准计数
```
PFADD key element 基数估算者

PFCOUNT key 基数估算值

PFMERGE destkey sourcekey 合并多个HyperLogLog
```
排序功能
```
sort key [BY pattern] [LIMIT start count] [GET pattern] [ASC|DESC] [ALPHA] [STORE dstk ey] 
1. [ASC|DESC][ALPHA]:sort默认的排序方式(asc)是从小到大排的,当然也可以按照逆序 或者按字符顺序排。
2. [BYpattern]:除了可以按集合元素自身值排序外，还可以将集合元素内容按照给定 pattern组合成新的key，并按照新key中对应的内容进行排序。例如:
3. 127.0.0.1:6379sortwatch:letobysevertity:*desc
4. [GETpattern]:可以通过get选项去获取指定pattern作为新key对应的值，get选项可以有
多个。例如:127.0.0.1:6379sort watch:leto by severtity: get severtity:。 对于Hash的引
用，采用->，例如:sort watch:leto get # get bug:*->priority。
5. [LIMITstartcount]限定返回结果的数量。
6. [STOREdstkey]把排序结果缓存起来
```
事务功能
```
```
流水线(pipeline)
```
批量set改成pipeline 可以提升很高的效率
```

发布订阅
```
redis作为一个pub/sub server，在订阅者和发布者之间起到了消息路由的功能。
订阅者可以通 过subscribe和psubscribe命令向redis server订阅自己感兴趣的消息类型，redis将消息类型称 为频道(channel)。
当发布者通过publish命令向redis server发送特定类型的消息时。订阅该消 息类型的全部client都会收到此消息。这里消息的传递是多对多的。
一个client可以订阅多个 channel,也可以向多个channel发送消息。
```
Key设计
```
key的一个格式约定: object-type:id:field 。用":"分隔域，用"."作为单词间的连接，如" comment:12345:reply.to "。不推荐含义不清的key和特别长的key。

从业务需求逻辑和内存的角度，尽可能的设置key存活时间。

程序应该处理如果redis数据丢失时的清理redis内存和重新加载的过程。
```

内存考虑
```
1. 只要有可能的话，就尽量使用散列键而不是字符串键来储存键值对数据，因为散列键管
  理方便、能够避免键名冲突、并且还能够节约内存。
具体实例: 节约内存:Instagram的Redis实践 blog.nosqlfan.com/html/3379.html
2. 如果将redis作为cache进行频繁读写和超时删除等，此时应该避免设置较大的k-v，因为 这样会导致redis的 内存碎片增加，导致rss占用较大，最后被操作系统OOM killer干掉。 一个很具体的issue例子请见:https://github.com/antirez/redis/issues/2136
3. 如果采用序列化考虑通用性，请采用json相关的库进行处理，如果对内存大小和速度都很 关注的，推荐使用messagepack进行序列化和反序列化
4. 如果需要计数器，请将计数器的key通过天或者小时分割，采用hash.

```
延迟考虑
```
1. 尽可能使用批量操作:mget、hmget而不是get和hget，对于set也是如此。 lpush向一个list一次性导入多个元素，而不用lset一个个添加 LRANGE 一次取出一个范围的元素，也不用LINDEX一个个取出。
2. 尽可能的把redis和APP SERVER部署在一个网段甚至一台机器。
3. 对于数据量较大的集合，不要轻易进行删除操作，这样会阻塞 服务器，一般采用重命名+批量删除的策略：

排序集合:
# Rename the key
newkey = "gc:hashes:" + redis.INCR("gc:index")
redis.RENAME("my.zset.key", newkey)
# Delete members from the sorted set in batche of 100s
while redis.ZCARD(newkey) > 0
  redis.ZREMRANGEBYRANK(newkey, 0, 99)
end

集合：
# Rename the key
newkey = "gc:hashes:" + redis.INCR("gc:index")
redis.RENAME("my.set.key", newkey)
# Delete members from the set in batches of 100
cursor = 0
loop
  cursor, members = redis.SSCAN(newkey, cursor, "COUNT", 100)
  if size of members > 0
redis.SREM(newkey, members)
end
  if cursor == 0
break
end end

列表：
# Rename the key
newkey = "gc:hashes:" + redis.INCR("gc:index")
redis.RENAME("my.list.key", newkey)
# Trim off elements in batche of 100s
while redis.LLEN(newkey) > 0
  redis.LTRIM(newkey, 0, -99)
end

Hash:
# Rename the key
newkey = "gc:hashes:" + redis.INCR( "gc:index" )
redis.RENAME("my.hash.key", newkey)
# Delete fields from the hash in batche of 100s
cursor = 0
loop
  cursor, hash_keys = redis.HSCAN(newkey, cursor, "COUNT", 100)
  if hash_keys count > 0
redis.HDEL(newkey, hash_keys)
end
  if cursor == 0
break
end end

4. 尽可能使用不要超过1M大小的kv。

5. 减少对大数据集的高时间复杂度的操作。

6.尽可能使用pipeline操作:一次性的发送命令比一个个发要减 少网络延迟和单个处理开销。一个性能测试结果为(注意并不是 pipeline越大效率越高，注意最后一个测试结果) 

7.如果出现频繁对string进行append操作，则请使用list进行 push操作，取出时使用pop。这样避免string频繁分配内存导致的延时。

8.如果要sort的集合非常大的话排序就会消耗很长时间。由于 redis单线程的，所以长时间的排序操作会阻塞其他client的 请 求。解决办法是通过主从复制机制将数据复制到多个slave上。 然后我们只在slave上做排序操作。把可能的对排序结果缓存。 另外就是一个方案是就是采用sorted set对需要按某个顺序访问 的集合建立索引。



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

3.sentinel 也可以主备然后靠开关来处理
```
#### 经典场景使用
```
1. 取最新 N 个数据的操作 比如典型的取你网站的最新文章，通过下面方式，我们可以将最新的 5000条评论的ID放在
Redis的List集合中，并将超出集合部分从数据库获取。
使用LPUSH latest.comments命令，向 list集合中插入数据 插入完成后再用 LTRIM latest.comments 0 5000 命令使其永远只保存最近5000个 ID

2. 排行榜应用，取 TOP N 操作
这个需求与上面需求的不同之处在于，前面操作以时间为权重，这个是以某个条件为权重， 比如按顶的次数排序，这时候就需要我们的 sorted set出马了，将你要排序的值设置成 sorted set的score，将具体的数据设置成相应的 value，每次只需要执行一条ZADD命令即可。

3.需要精准设定过期时间的应用
比如你可以把上面说到的 sorted set 的 score 值设置成过期时间的时间戳，那么就可以简单 地通过过期时间排序，定时清除过期数据了，不仅是清除 Redis中的过期数据，你完全可以把 Redis 里这个过期时间当成是对数据库中数据的索引，用 Redis 来找出哪些数据需要过期删 除，然后再精准地从数据库中删除相应的记录。

4.计数器应用
Redis的命令都是原子性的，你可以轻松地利用 INCR，DECR 命令来构建计数器系统。

5.Uniq 操作，获取某段时间所有数据排重值
这个使用Redis的 set数据结构最合适了，只需要不断地将数据往 set中扔就行了，set意为集
合，所以会自动排重。

6.实时系统，反垃圾系统
通过上面说到的 set功能，你可以知道一个终端用户是否进行了某个操作，可以找到其操作的
集合并进行分析统计对比等。

7.Pub/Sub 构建实时消息系统
 Redis 的 Pub/Sub 系统可以构建实时的消息系统，比如很多用 Pub/Sub 构建的实时聊天系统 的例子。
 
 8.构建队列系统
使用list可以构建队列系统，使用 sorted set甚至可以构建有优先级的队列系统。

9.缓存 性能优于Memcached，数据结构更多样化。作为RDBMS的前端挡箭牌，redis可以对一些使用频率极高的sql操作进行cache，比如，我们可以根据sql的hash进行SQL结果的缓存。

10.使用setbit进行统计计数。

11.维护好友关系 使用set进行是否为好友关系，共同好友等操作。

12.使用 Redis 实现自动补全功能。

13. 可靠队列设计。


```


## memeroy
