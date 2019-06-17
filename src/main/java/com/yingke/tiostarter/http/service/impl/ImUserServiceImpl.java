package com.yingke.tiostarter.http.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yingke.tiostarter.http.entity.ImUser;
import com.yingke.tiostarter.http.service.ImUserService;
import com.yingke.tiostarter.http.service.mapper.ImUserMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
@Qualifier(value = "imUserService")
public class ImUserServiceImpl extends ServiceImpl<ImUserMapper, ImUser> implements ImUserService {

}
