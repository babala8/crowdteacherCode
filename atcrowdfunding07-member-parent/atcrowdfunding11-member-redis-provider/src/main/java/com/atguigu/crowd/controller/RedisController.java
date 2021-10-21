package com.atguigu.crowd.controller;

import com.atguigu.crowd.util.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author ldy
 * @version 1.0
 */
@RestController
@Slf4j
public class RedisController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * redis设置字符串键值
     * @param key
     * @param value
     * @return
     */
    @RequestMapping("/set/redis/key/value")
    ResultEntity<String> setRedisKeyValueRemote(@RequestParam("key") String key, @RequestParam("value") String value){

        try {
            ValueOperations<String, String> ops = redisTemplate.opsForValue();
            ops.set(key, value);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }

    }

    /**
     * redis设置字符串超时时间
     * @param key
     * @param value
     * @param time
     * @param timeUnit
     * @return
     */
    @RequestMapping("/set/redis/key/value/with/timeout")
    ResultEntity<String> setRedisKeyValueWithTimeoutRemote(@RequestParam("key") String key,
                                                           @RequestParam("value") String value,
                                                           @RequestParam("time") long time,
                                                           @RequestParam("timeUnit") TimeUnit timeUnit){

        try {
            ValueOperations<String, String> ops = redisTemplate.opsForValue();
            ops.set(key, value,time,timeUnit);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }

    }

    /**
     * redis根据键获取值
     * @param key
     * @return
     */
    @RequestMapping("/get/string/value/by/key")
    ResultEntity<String> getStringValueByKeyRemote(@RequestParam("key") String key){

        try {
            ValueOperations<String, String> ops = redisTemplate.opsForValue();
            String value = ops.get(key);
            return ResultEntity.successWithData(value);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * redis根据键删除值
     * @param key
     * @return
     */
    @RequestMapping("/remove/redis/key")
    ResultEntity<String> removeRedisKeyRemote(@RequestParam("key") String key){

        try {
            redisTemplate.delete(key);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }

    }
}
