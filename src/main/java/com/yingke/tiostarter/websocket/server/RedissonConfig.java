package com.yingke.tiostarter.websocket.server;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.tio.utils.hutool.StrUtil;
import org.tio.utils.jfinal.P;

/**
 * @author lyk 2019/6/6 13:49
 * redisson 配置类
 */
@Slf4j
public  class RedissonConfig {

    private static RedissonConfig redissonConfig;
    private static RedissonClient redissonClient;

    public static RedissonConfig newInstance(){
        if (redissonConfig == null) {
            synchronized (RedissonConfig.class){
                if(redissonConfig == null){
                    init();
                }
            }
        }
        return redissonConfig;
    }

    private static void init() {
        if(redissonConfig == null){
            String host = P.get("redis.host");
            if(StrUtil.isEmpty(host)) {
                log.error("redis服务器IP不能为空");
            }
            Integer port = P.getInt("redis.port");
            if(port == null) {
                log.error("redis端口不能为空");
            }
            Config config = new Config();
            SingleServerConfig singleServerConfig = config.useSingleServer();
            String password = P.get("redis.password");
            if (!StrUtil.isEmpty(password)) {
                singleServerConfig.setAddress("redis://"+host+":"+port)
                        .setPassword(password)
                        .setTimeout(P.getInt("redis.timeout"));
            } else {
                singleServerConfig.setAddress("redis://"+host+":"+port)
                        .setTimeout(P.getInt("redis.timeout"));
            }
            try {
                redissonClient = Redisson.create(config);
                redissonConfig = new RedissonConfig();
            } catch (Exception e) {
                log.error("redis连接失败");
            }
        }
    }

    public RedissonClient getRedissonClient(){
        return redissonClient;
    }

}
