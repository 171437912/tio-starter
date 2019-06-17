package com.yingke.tiostarter.websocket.server;

import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.tio.utils.jfinal.P;

/**
 * @author lyk 2019/6/6 11:21
 * ApplicationRunner和CommandLineRunner。这两个接口是Springboot给我们提供了两种“开机启动”的方式
 * 相同点：这两种方法提供的目的是为了满足，在项目启动的时候立刻执行某些方法。我们可以通过实现ApplicationRunner和CommandLineRunner，来实现
 * ，他们都是在SpringApplication 执行之后开始执行的。
 * 不同点：CommandLineRunner接口可以用来接收字符串数组的命令行参数，ApplicationRunner 是使用ApplicationArguments 用来接收参数的
 *
 * order是优先级   值越小，越优先执行
 */
@Component
@Order(value = 1)
public class ShowcaseServerRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        //设置P使用哪个配置文件
        P.use("app.properties");

        //启动websocket server
        ShowcaseWebsocketStarter.start();

        //刷新 redis保存的在线用户信息，和在线总人数
        RedissonClient client = RedissonConfig.newInstance().getRedissonClient();
        client.getKeys().deleteByPattern(Const.WS_USER_PREFIX+"*");
        client.getKeys().delete(Const.ALL_ONLINE_USER);
    }
}
