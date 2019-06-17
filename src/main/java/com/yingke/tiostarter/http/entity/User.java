package com.yingke.tiostarter.http.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @author lyk 2019/6/11 15:14
 * 用户缓存保存用户信息
 */
@Data
public class User  implements Serializable {

    private String username;
    private Set<String> group;

    /**
     * @author lyk 2019/6/11 9:13
     * 服务所在节点
     */
    private String serverNode;

    /**
     * @author lyk 2019/6/11 9:13
     * 客户端连接节点
     */
    private String clientNode;
}
