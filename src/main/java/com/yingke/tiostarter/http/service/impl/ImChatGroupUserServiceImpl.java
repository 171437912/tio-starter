package com.yingke.tiostarter.http.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yingke.tiostarter.http.entity.ImChatGroupUser;
import com.yingke.tiostarter.http.service.ImChatGroupUserService;
import com.yingke.tiostarter.http.service.mapper.ImChatGroupUserMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
@Qualifier("imChatGroupUserService")
public class ImChatGroupUserServiceImpl extends ServiceImpl<ImChatGroupUserMapper, ImChatGroupUser> implements ImChatGroupUserService {

}
