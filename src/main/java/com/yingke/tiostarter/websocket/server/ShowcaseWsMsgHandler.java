package com.yingke.tiostarter.websocket.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yingke.tiostarter.http.entity.ChatUtils;
import com.yingke.tiostarter.http.entity.Message;
import com.yingke.tiostarter.http.entity.SendInfo;
import com.yingke.tiostarter.http.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.server.handler.IWsMsgHandler;

import java.io.IOException;

/**
 * @author lyk 2019/6/6 10:20
 * 一些处理类  因为只在初始化配置时使用一次。，所以单例
 */
@Slf4j
public class ShowcaseWsMsgHandler implements IWsMsgHandler {

    public static final ShowcaseWsMsgHandler me = new ShowcaseWsMsgHandler();

    private ShowcaseWsMsgHandler() {

    }
    /**
     * @author lyk 2019/6/6 10:21
     * 握手时执行
     */
    @Override
    public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        String clientip = httpRequest.getClientIp();
        String myname = httpRequest.getParam("name");

        Tio.bindUser(channelContext, myname);

//		channelContext.setUserid(myname);

        log.info("收到来自{}的ws握手包\r\n{}", clientip, httpResponse.toString());

        return httpResponse;
    }

    /**
     * @author lyk 2019/6/6 10:21
     * 握手后执行
     */
    @Override
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        RedissonClient client = RedissonConfig.newInstance().getRedissonClient();
        //将握手信息保存到redis中
        String username= channelContext.userid;
        //TODO 如查询当前用户所在组的功能
        //Set<String> groups = userService.getUserGroups(username);
        // for 循环 ：Aio.bindGroup(channelContext, group);

        //不管之前是否已经登录，直接覆盖，实际业务时会有具体处理
        User user = new User();
        // user.setGroup(groups);
        user.setUsername(username);
        user.setServerNode(channelContext.getServerNode().toString());
        user.setClientNode(channelContext.getClientNode().toString());

        RSet<User> userRSet =client.getSet(Const.WS_USER_PREFIX + username);
        userRSet.add(user);

        log.info("用户{}加入", username);
        //保存在线总用户
        RSet<String> allOnlineUser = client.getSet(Const.ALL_ONLINE_USER);
        allOnlineUser.add(username);

        //绑定到群组（用户发送在线人数消息的组），后面会有群发
        Tio.bindGroup(channelContext, Const.GROUP_ID);
        String msg = "{name:'admin',message:'" + channelContext.userid + " 进来了，共【" + allOnlineUser.size() + "】人在线" + "'}";
        //用tio-websocket，服务器发送到客户端的Packet都是WsResponse
        WsResponse wsResponse = WsResponse.fromText(msg, ShowcaseServerConfig.CHARSET);
        //群发
        Tio.sendToGroup(channelContext.groupContext, Const.GROUP_ID, wsResponse);
    }

    /**
     * @author lyk 2019/6/6 10:29
     * 字节消息
     */
    @Override
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        return null;
    }

    /**
     * @author lyk 2019/6/6 10:29
     * 断开连接
     */
    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        Tio.remove(channelContext, "receive close flag");
        return null;
    }

    /**
     * @author lyk 2019/6/6 10:30
     * 字符消息（binaryType = blob）过来后会走这个方法
     */
    @Override
    public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SendInfo sendInfo = objectMapper.readValue(text, SendInfo.class);
            //心跳检测包
            if (ChatUtils.MSG_PING.equals(sendInfo.getCode())) {
//                WsResponse wsResponse = WsResponse.fromText(text, ShowcaseServerConfig.CHARSET);
//                Tio.send(channelContext, wsResponse);
            }
            //真正的消息
            else if (ChatUtils.MSG_MESSAGE.equals(sendInfo.getCode())) {
                Message message=sendInfo.getMessage();

                //1.判断接收方是否在线
                RedissonClient client = RedissonConfig.newInstance().getRedissonClient();
                RSet<Object> onlineUsers = client.getSet(Const.ALL_ONLINE_USER);
                if (onlineUsers.contains(message.getId())){
                    //在线
                    log.info(message.getId()+"在线状态");
                }else{
                    //离线
                    log.info(message.getId()+"离线状态");
                }

//                Message message = sendInfo.getMessage();
//                message.setMine(false);
//                WsResponse wsResponse = WsResponse.fromText(objectMapper.writeValueAsString(sendInfo), ShowcaseServerConfig.CHARSET);
//                //单聊
//                if (ChatUtils.FRIEND.equals(message.getType())) {
//                    SetWithLock<ChannelContext> channelContextSetWithLock = Tio.getChannelContextsByUserid(channelContext.groupContext, message.getId());
//                    //用户没有登录，存储到离线文件
//                    if (channelContextSetWithLock == null || channelContextSetWithLock.size() == 0) {
//                        saveMessage(message, ChatUtils.UNREAD);
//                    } else {
//                        Tio.sendToUser(channelContext.groupContext, message.getId(), wsResponse);
//                        //入库操作
//                        saveMessage(message, ChatUtils.READED);
//                    }
//                } else {
//                    Tio.sendToGroup(channelContext.groupContext, message.getId(), wsResponse);
//                    //入库操作
//                    saveMessage(message, ChatUtils.READED);
//                }
            }
            //准备就绪，需要发送离线消息
            else if (ChatUtils.MSG_READY.equals(sendInfo.getCode())) {
//                //未读消息
//                sendOffLineMessage(channelContext, objectMapper);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回值是要发送给客户端的内容，一般都是返回null
        return null;



//        log.info("收到ws消息:{}", text);
//
//        if (Objects.equals("心跳内容", text)) {
//            return null;
//        }
//        //channelContext.getToken()
//        //String msg = channelContext.getClientNode().toString() + " 说：" + text;
//        String msg = "{name:'" + channelContext.userid + "',message:'" + text + "'}";
//        //用tio-websocket，服务器发送到客户端的Packet都是WsResponse
//        WsResponse wsResponse = WsResponse.fromText(msg, ShowcaseServerConfig.CHARSET);
//        //群发
//        Tio.sendToGroup(channelContext.groupContext, Const.GROUP_ID, wsResponse);
//
//        //返回值是要发送给客户端的内容，一般都是返回null
//        return null;
    }
}
