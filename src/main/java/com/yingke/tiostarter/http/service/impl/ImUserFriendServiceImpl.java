package com.yingke.tiostarter.http.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yingke.tiostarter.http.entity.ImGroup;
import com.yingke.tiostarter.http.entity.ImUserFriend;
import com.yingke.tiostarter.http.service.ImUserFriendService;
import com.yingke.tiostarter.http.service.mapper.ImUserFriendMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  用户关系表
 * </p>
 *
 * @author jobob
 * @since 2018-12-31
 */
@Service
@Qualifier(value = "imUserFriendService")
public class ImUserFriendServiceImpl extends ServiceImpl<ImUserFriendMapper, ImUserFriend> implements ImUserFriendService {


}
