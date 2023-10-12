package com.jdk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdk.bean.Furn;
import com.jdk.mapper.FurnMapper;
import com.jdk.service.FurnService;
import org.springframework.stereotype.Service;

@Service
public class FurnServiceImpl extends ServiceImpl<FurnMapper, Furn> implements FurnService {
}
