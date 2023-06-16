package org.com.jscada.server.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.com.jscada.entity.YsOeeAlarm;
import org.com.jscada.mapper.YsOeeAlarmMapper;
import org.com.jscada.server.YsOeeAlarmService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @类描述: oee 的 报警时间
 * @项目名称:
 * @包名: org.jeecg.modules.ysHtopc
 * @类名称: YsOeeAlarmServiceImpl
 * @创建人: 刘凯
 * @创建时间: 2021-04-10 10:31:03
 * @修改人:
 * @修改时间:
 * @修改备注:
 * @Version: V1.0
 */
@Service
public class YsOeeAlarmServiceImpl extends ServiceImpl<YsOeeAlarmMapper, YsOeeAlarm> implements YsOeeAlarmService {
	@Resource
	private YsOeeAlarmMapper ysOeeAlarmMapper;

	public List<Map<String, Object>> findOeeList() {

		return ysOeeAlarmMapper.findOeeList();
	}
	
	public List<Map<String, Object>> alarmInformationRanking() {

		return ysOeeAlarmMapper.alarmInformationRanking();
	}
}
