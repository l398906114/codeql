package org.com.jscada.server;

import com.baomidou.mybatisplus.extension.service.IService;
import org.com.jscada.entity.YsThcalc;

/**
 * 温湿度采集
 */
public interface YsThcalcService extends IService<YsThcalc> {


	/**
	 * 温湿度采集
	 */
	public void GetCalcColle();


}
