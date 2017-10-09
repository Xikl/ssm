# ssm
ssm框架整合初体验
使用声明式事务的优点
     * 1.开发团队的达成一致约定，明确标注事务方法的编程风格
     * 2.保证事务方法的执行时间要尽可能短， 同时不要穿插网络操作 比如http和tcp，如果非要的话可以剥离到事务方法之外
     * 3.不是所得方法都需要事务，如只有一条写入操作或者只读

````
GET /seckill/list 秒杀列表 > 秒杀列表
GET /seckill/{id}/detail > 详情页
GET /seckill/time/now > 获得系统时间
POST /seckill/{id}/exposer > 暴露秒杀
POST /seckill/{id}/{md5}/execution > 执行秒杀
````