package com.yingke.tiostarter.http.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lyk 2019/6/11 15:20
 * 用户表
 */
@Data
@TableName(value = "im_user")
public class ImUser implements Serializable {

    @TableId(value = "id",type = IdType.UUID)
    private String id;

    private String avatar;

    private String name;

    private String sign;

    private String mobile;

    private String email;

    private String password;

    @TableField("login_name")
    private String loginName;

    @TableField("default_group_id")
    private String defaultGroupId;
}
