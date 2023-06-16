package org.com.jscada.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.com.jscada.entity.YsOeeAlarm;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;


/**
 * @类描述: oee 的 报警时间
 * @项目名称:
 * @包名: org.jeecg.modules.ysHtopc
 * @类名称: YsOeeAlarmMapper
 * @创建人: 刘凯
 * @创建时间: 2021-04-10 10:31:03
 * @修改人:
 * @修改时间:
 * @修改备注:
 * @Version: V1.0
 */
@Repository
public interface YsOeeAlarmMapper extends BaseMapper<YsOeeAlarm> {
	
	
	@Select("SELECT * FROM ys_oee_alarm WHERE tm_mach_id = #{tmMachId} AND statistics_type='1'")
	public   YsOeeAlarm getYsOeeAlarmBytmMachId(String tmMachId);
	
	
	/**
	 * 
	 * @方法名: findOeeList
	 * @方法描述: oee
	 * @作者: 刘凯
	 * @时间: 2021年4月10日 下午3:16:08
	 * @return
	 * @return:List<Map<String,Object>>
	 * @version
	 */
	@Select("SELECT tm_mach_id,SUM(count_time) AS duration FROM ys_oee_alarm WHERE statistics_type=0 AND TO_DAYS(cj_date_end) = TO_DAYS(NOW()) GROUP BY tm_mach_id ")
	public List<Map<String, Object>>findOeeList();

	
	public List<Map<String, Object>>alarmInformationRanking();
}
