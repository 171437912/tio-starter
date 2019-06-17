package com.yingke.tiostarter.http.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lyk 2019/6/11 15:21
 * 群组-用户
 */
@Data
@TableName("im_chat_group_user")
public class ImChatGroupUser implements Serializable {

    /**
     * 群id
     */
    private String chatGroupId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 入群时间
     */
    private Date createDate;

}
