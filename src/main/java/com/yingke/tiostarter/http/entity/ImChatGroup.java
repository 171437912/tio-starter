package com.yingke.tiostarter.http.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lyk 2019/6/11 15:21
 * 群组表
 */
@Data
@TableName("im_chat_group")
public class ImChatGroup implements Serializable {

    @TableId(value = "id",type = IdType.UUID)
    private String id;

    /**
     * 群名称
     */
    private String name;

    /**
     * 群头像
     */
    private String avatar;

    /**
     * 群主
     */
    private String master;
}
