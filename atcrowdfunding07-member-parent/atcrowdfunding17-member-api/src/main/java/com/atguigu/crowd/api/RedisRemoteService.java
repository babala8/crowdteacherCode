package com.atguigu.crowd.api;

import com.atguigu.crowd.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.TimeUnit;

/**
 * @author ldy
 * @version 1.0
 */
@FeignClient(value = "atguigu-crowd-redis")
public interface RedisRemoteService {

    @RequestMapping("/set/redis/key/value")
    ResultEntity<String> setRedisKeyValueRemote(@RequestParam("key") String key,@RequestParam("value") String value);

    @RequestMapping("/set/redis/key/value/with/timeout")
    ResultEntity<String> setRedisKeyValueWithTimeoutRemote(@RequestParam("key") String key,
                                                           @RequestParam("value") String value,
                                                           @RequestParam("time") long time,
                                                           @RequestParam("timeUnit") TimeUnit timeUnit);


    @RequestMapping("/get/string/value/by/key")
    ResultEntity<String> getStringValueByKeyRemote(@RequestParam("key") String key);


    @RequestMapping("/remove/redis/key")
    ResultEntity<String> removeRedisKeyRemote(@RequestParam("key") String key);

}
