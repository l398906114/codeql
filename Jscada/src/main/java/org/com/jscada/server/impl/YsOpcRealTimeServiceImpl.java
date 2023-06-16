package org.com.jscada.server.impl;

import org.com.jscada.entity.YsOpcRealTime;
import org.com.jscada.mapper.YsOpcRealTimeMapper;
import org.com.jscada.server.YsOpcRealTimeService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class YsOpcRealTimeServiceImpl extends ServiceImpl<YsOpcRealTimeMapper, YsOpcRealTime> implements YsOpcRealTimeService {

}
