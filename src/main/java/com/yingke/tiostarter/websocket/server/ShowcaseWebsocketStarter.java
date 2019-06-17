package com.yingke.tiostarter.websocket.server;

import org.tio.cluster.TioClusterConfig;
import org.tio.cluster.redisson.RedissonTioClusterTopic;
import org.tio.server.ServerGroupContext;
import org.tio.utils.jfinal.P;
import org.tio.websocket.server.WsServerStarter;

/**
 * @author lyk 2019/6/6 10:16
 * tio 入口配置类
 */
public class ShowcaseWebsocketStarter {

    private WsServerStarter wsServerStarter;
    private ServerGroupContext serverGroupContext;

    /**
     * @author lyk 2019/6/6 11:12
     * 入口配置 初始化wsServerStarter
     */
    public ShowcaseWebsocketStarter(int port, ShowcaseWsMsgHandler wsMsgHandler) throws Exception {
        wsServerStarter = new WsServerStarter(port, wsMsgHandler);

        serverGroupContext = wsServerStarter.getServerGroupContext();
        serverGroupContext.setName(ShowcaseServerConfig.PROTOCOL_NAME);
        serverGroupContext.setServerAioListener(ShowcaseServerAioListener.me);

        //设置ip监控
        serverGroupContext.setIpStatListener(ShowcaseIpStatListener.me);
        //设置ip统计时间段
        serverGroupContext.ipStats.addDurations(ShowcaseServerConfig.IpStatDuration.IPSTAT_DURATIONS);

        //设置心跳超时时间
        serverGroupContext.setHeartbeatTimeout(ShowcaseServerConfig.HEARTBEAT_TIMEOUT);

//        if (P.getInt("ws.use.ssl", 1) == 1) {
//            //如果你希望通过wss来访问，就加上下面的代码吧，不过首先你得有SSL证书（证书必须和域名相匹配，否则可能访问不了ssl）
////			String keyStoreFile = "classpath:config/ssl/keystore.jks";
////			String trustStoreFile = "classpath:config/ssl/keystore.jks";
////			String keyStorePwd = "214323428310224";
//
//
//            String keyStoreFile = P.get("ssl.keystore", null);
//            String trustStoreFile = P.get("ssl.truststore", null);
//            String keyStorePwd = P.get("ssl.pwd", null);
//            serverGroupContext.useSsl(keyStoreFile, trustStoreFile, keyStorePwd);
//        }


        //增加redis集群配置
        RedissonTioClusterTopic redissonTioClusterTopic = new RedissonTioClusterTopic(ShowcaseServerConfig.CHANNEL, RedissonConfig.newInstance().getRedissonClient());
        TioClusterConfig tioClusterConfig = new TioClusterConfig(redissonTioClusterTopic);
        tioClusterConfig.setCluster4group(true);
        serverGroupContext.setTioClusterConfig(tioClusterConfig);
    }

    /**
     * @author lyk 2019/6/6 11:13
     * 初始化ShowcaseWebsocketStarter  并启动websocket server
     */
    public static void start() throws Exception {
        ShowcaseWebsocketStarter appStarter = new ShowcaseWebsocketStarter(ShowcaseServerConfig.SERVER_PORT, ShowcaseWsMsgHandler.me);
        appStarter.wsServerStarter.start();
    }

    /**
     * @author lyk 2019/6/6 11:14
     * 获取 serverGroupContext
     */
    public ServerGroupContext getServerGroupContext() {
        return serverGroupContext;
    }

    /**
     * @author lyk 2019/6/6 11:14
     * 获取 wsServerStarter
     */
    public WsServerStarter getWsServerStarter() {
        return wsServerStarter;
    }

}
