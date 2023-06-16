package org.com.jscada.server;

import com.baomidou.mybatisplus.extension.service.IService;
import org.com.jscada.entity.YsOpcRealTime;

/**
 * 安藤采集
 * @author dell
 */
public interface YsAtService  extends IService<YsOpcRealTime> {


	/**
	 * 安腾注塑机
	 */
	public void atOpcService();


}
