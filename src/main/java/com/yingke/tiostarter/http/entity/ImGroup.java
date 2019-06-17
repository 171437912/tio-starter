package com.yingke.tiostarter.http.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *@author lyk 2019/6/11 15:21
 * 分组表
 */
@Data
@TableName("im_group")
public class ImGroup implements Serializable {

    @TableId(value = "id",type = IdType.UUID)
    private String id;

    private String userId;

    private String name;

    @TableField(exist = false)
    private List<ImUser> userList;

    @TableField(exist = false)
    private boolean expansion = false;
}
