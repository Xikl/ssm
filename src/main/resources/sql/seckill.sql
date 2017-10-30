-- 秒杀执行存贮过程
DELIMITER $$ -- console ; 转换为$$
-- 定义存贮过程
-- 参数 in: 输入参数 out:输出参数 为结果赋值
-- row_count() 返回修改上一条类型sql(delete, insert update)的影响行数
-- row_count 0:表示影响行数为0,  >0表示修改的行数，  <0: sql错误或者未执行
CREATE  PROCEDURE `seckill`.`execute_seckill`(
    IN v_seckill_id BIGINT,
    IN v_phone BIGINT,
    IN v_kill_time TIMESTAMP,
    OUT r_result INT
)
BEGIN
  DECLARE insert_count INT DEFAULT 0;
  START TRANSACTION;
  INSERT IGNORE INTO success_killed(seckill_id, user_phone, state)
    VALUES (v_seckill_id, v_phone, 0);
  SELECT row_count() INTO insert_count;
  IF (insert_count = 0) THEN
    ROLLBACK ;
    SET r_result = -1;/*重复秒杀*/
  ELSEIF (insert_count < 0) THEN
    ROLLBACK ;
    SET r_result = -2;/*系统异常*/
  ELSE
    UPDATE seckill
      SET  number = number - 1
    WHERE seckill_id = v_seckill_id
          AND end_time > v_kill_time
          AND start_time < v_kill_time
          AND number > 0;
    SELECT row_count() INTO insert_count;
    IF (insert_count = 0) THEN
      ROLLBACK ;
      SET r_result = 0;/*秒杀结束*/
    ELSEIF (insert_count < 0) THEN
      ROLLBACK ;
      SET r_result = -2;/*系统异常*/
    ELSE
      COMMIT ;
      SET r_result = 1;/*秒杀成功*/
    END IF;

  END IF;
END $$/*存储过程定义结束*/

-- 定义换行符号为 ;
DELIMITER ;
SET @r_result = -3;

-- 执行存储过程
CALL execute_seckill(1003, 15088664613,now(), @r_result);
-- 获得结果
SELECT @r_result;
-- 存储过程是优化：事务行级锁持有的时间
-- 2：不要过多依赖于存储过程
-- 3：在此秒杀系统中有一定的可操作性， 简单的逻辑