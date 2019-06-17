package com.yingke.tiostarter.http.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lyk 2019/6/11 15:20
 * 消息表
 */
@Data
@TableName("im_message")
public class ImMessage implements Serializable {

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 接收人
     */
    private String toId;

    /**
     * 发送人id
     */
    private String fromId;

    private Long sendTime;

    private String content;

    /**
     * 类型 0单聊 1 群聊
     */
    private String type;

    /**
     * 1 已读 0 未读
     */
    private String readStatus;
}
