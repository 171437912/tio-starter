package com.yingke.tiostarter.websocket.server;

import com.yingke.tiostarter.http.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.util.CollectionUtils;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.core.intf.Packet;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.server.WsServerAioListener;

/**
 * @author lyk 2019/6/6 10:33
 * 监听类，根据需求实现  因为只在初始化配置时使用一次。，所以单例
 */
@Slf4j
public class ShowcaseServerAioListener extends WsServerAioListener {

    //单例模式
    public static final ShowcaseServerAioListener me = new ShowcaseServerAioListener();

    private ShowcaseServerAioListener() {

    }

    @Override
    public void onBeforeClose(ChannelContext channelContext, Throwable throwable, String remark, boolean isRemove) throws Exception {
        super.onBeforeClose(channelContext, throwable, remark, isRemove);

        RedissonClient client = RedissonConfig.newInstance().getRedissonClient();

        String username = channelContext.userid;
        //删除用户数据

//        client.getKeys().delete(Const.WS_USER_PREFIX + username);

        //同一用户通道列表
        RSet<User> clientSet = client.getSet(Const.WS_USER_PREFIX + username);
        clientSet.forEach(user->{
            if (channelContext.getClientNode().toString().equals(user.getClientNode())){
                clientSet.remove(user);
            }
        });
        RSet<Object> allOnlineUser = client.getSet(Const.ALL_ONLINE_USER);
        if (CollectionUtils.isEmpty(clientSet)){
            //从在线总用户中清除用户
            allOnlineUser.remove(username);

            //绑定到群组（用户发送在线人数消息的组），后面会有群发
            Tio.bindGroup(channelContext, Const.GROUP_ID);
            String msg = "{name:'admin',message:'" + channelContext.userid + " 离开了，共【" + allOnlineUser.size() + "】人在线" + "'}";
            //用tio-websocket，服务器发送到客户端的Packet都是WsResponse
            WsResponse wsResponse = WsResponse.fromText(msg, ShowcaseServerConfig.CHARSET);
            //群发
            Tio.sendToGroup(channelContext.groupContext, Const.GROUP_ID, wsResponse);
        }



    }

    @Override
    public void onAfterConnected(ChannelContext channelContext, boolean isConnected, boolean isReconnect) throws Exception {
        super.onAfterConnected(channelContext, isConnected, isReconnect);
        if (log.isInfoEnabled()) {
            log.debug("onAfterConnected\r\n{}", channelContext);
        }

    }

    @Override
    public void onAfterSent(ChannelContext channelContext, Packet packet, boolean isSentSuccess) throws Exception {
        super.onAfterSent(channelContext, packet, isSentSuccess);
        if (log.isInfoEnabled()) {
            log.debug("onAfterSent\r\n{}\r\n{}", packet.logstr(), channelContext);
        }
    }

    @Override
    public void onAfterDecoded(ChannelContext channelContext, Packet packet, int packetSize) throws Exception {
        super.onAfterDecoded(channelContext, packet, packetSize);
        if (log.isInfoEnabled()) {
            log.debug("onAfterDecoded\r\n{}\r\n{}", packet.logstr(), channelContext);
        }
    }

    @Override
    public void onAfterReceivedBytes(ChannelContext channelContext, int receivedBytes) throws Exception {
        super.onAfterReceivedBytes(channelContext, receivedBytes);
        if (log.isInfoEnabled()) {
            log.debug("onAfterReceivedBytes\r\n{}", channelContext);
        }
    }

    @Override
    public void onAfterHandled(ChannelContext channelContext, Packet packet, long cost) throws Exception {
        super.onAfterHandled(channelContext, packet, cost);
    }
}
