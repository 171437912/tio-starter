package com.yingke.tiostarter.http.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lyk 2019/6/11 15:21
 * 用户-好友
 */
@Data
@TableName("im_user_friend")
public class ImUserFriend implements Serializable {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 好友ID
     */
    private String friendId;

    /**
     * 用户分组
     */
    private String userGroupId;

    /**
     * 好友分组
     */
    private String friendGroupId;

}
