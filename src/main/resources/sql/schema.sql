-- 数据库初始化脚本
-- 创建数据库

CREATE DATABASE seckill;

-- 使用数据库
USE seckill;

-- 创建表
CREATE TABLE seckill(

  `seckill_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '商品库存',
  `name` VARCHAR(128) NOT NULL COMMENT '商品名称',
  `number` INT NOT NULL COMMENT '库存数量',
  `start_time` TIMESTAMP NOT NULL COMMENT '秒杀开始时间',
  `end_time` TIMESTAMP NOT NULL COMMENT '秒杀结束时间',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`seckill_id`),
  KEY idx_start_time(start_time),
  KEY idx_end_time(end_time),
  KEY idx_create_time(create_time)

)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=UTF8 COMMENT='秒杀存库存表';

-- 插入操作
INSERT INTO
  seckill(name, number, start_time, end_time)
VALUES
  ('1000元秒杀iphone8', 100, '2017-10-05 00:00:00', '2017-10-06 00:00:00'),
  ('500元秒杀iPad2', 200, '2017-10-05 00:00:00', '2017-10-06 00:00:00'),
  ('300元秒杀小米5x', 400, '2017-10-05 00:00:00', '2017-10-06 00:00:00'),
  ('1元秒杀SpringCLoud全套课程', 500, '2017-10-05 00:00:00', '2017-10-06 00:00:00');



-- 秒杀功能明细表
-- 用户登录认证信息表
CREATE TABLE success_killed(

  `seckill_id` BIGINT NOT NULL COMMENT '秒杀商品id',
  `user_phone` BIGINT NOT NULL COMMENT '用户手机号',
  `state` TINYINT NOT NULL DEFAULT -1 COMMENT '状态： -1 无效， 0 成功， 1 已付款， 2 已发货',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (seckill_id, user_phone), /*联合主键*/
  KEY idx_create_time(create_time)

)ENGINE =InnoDB DEFAULT CHARSET=utf8 COMMENT '秒杀成功明细表'
