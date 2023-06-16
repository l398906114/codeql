package org.com.jscada.server;

import java.util.List;
import java.util.Map;

import org.com.jscada.entity.YsOeeAlarm;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @类描述: oee 的 报警时间
 * @项目名称:
 * @包名: org.jeecg.modules.ysHtopc
 * @类名称: YsOeeAlarmService 
 * @创建人: 刘凯
 * @创建时间: 2021-04-10 10:31:03
 * @修改人:
 * @修改时间:
 * @修改备注:
 * @Version: V1.0
 */
public interface YsOeeAlarmService extends IService<YsOeeAlarm> {

	
	public List<Map<String, Object>>findOeeList();
	
	public List<Map<String, Object>>alarmInformationRanking();
}
