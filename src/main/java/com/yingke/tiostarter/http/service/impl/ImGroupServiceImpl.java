package com.yingke.tiostarter.http.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yingke.tiostarter.http.entity.ImGroup;
import com.yingke.tiostarter.http.service.ImGroupService;
import com.yingke.tiostarter.http.service.mapper.ImGroupMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
@Qualifier("imGroupService")
public class ImGroupServiceImpl extends ServiceImpl<ImGroupMapper, ImGroup> implements ImGroupService {

}
