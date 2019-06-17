package com.yingke.tiostarter.websocket.server;

import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.intf.Packet;
import org.tio.core.stat.IpStat;
import org.tio.core.stat.IpStatListener;

/**
 * @author lyk 2019/6/6 10:34
 * IP监听类   因为只在初始化配置时使用一次。，所以单例
 */
public class ShowcaseIpStatListener implements IpStatListener {

    //单例
    public static final ShowcaseIpStatListener me = new ShowcaseIpStatListener();

    private ShowcaseIpStatListener() {
    }

    @Override
    public void onExpired(GroupContext groupContext, IpStat ipStat) {

    }

    @Override
    public void onAfterConnected(ChannelContext channelContext, boolean b, boolean b1, IpStat ipStat) throws Exception {

    }

    @Override
    public void onDecodeError(ChannelContext channelContext, IpStat ipStat) {

    }

    @Override
    public void onAfterSent(ChannelContext channelContext, Packet packet, boolean b, IpStat ipStat) throws Exception {

    }

    @Override
    public void onAfterDecoded(ChannelContext channelContext, Packet packet, int i, IpStat ipStat) throws Exception {

    }

    @Override
    public void onAfterReceivedBytes(ChannelContext channelContext, int i, IpStat ipStat) throws Exception {

    }

    @Override
    public void onAfterHandled(ChannelContext channelContext, Packet packet, IpStat ipStat, long l) throws Exception {

    }
}
