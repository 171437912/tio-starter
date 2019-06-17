package com.yingke.tiostarter.http.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yingke.tiostarter.http.entity.ImChatGroup;
import com.yingke.tiostarter.http.service.ImChatGroupService;
import com.yingke.tiostarter.http.service.mapper.ImChatGroupMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("imChatGroupServiceImpl")
public class ImChatGroupServiceImpl extends ServiceImpl<ImChatGroupMapper,ImChatGroup> implements ImChatGroupService {

}
