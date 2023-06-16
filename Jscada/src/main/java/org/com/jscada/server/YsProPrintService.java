package org.com.jscada.server;

import org.com.jscada.entity.YsProPrint;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @类描述: 印刷
 * @项目名称:
 * @包名: org.jeecg.modules.ysProPrint
 * @类名称: YsProPrintService
 * @创建人:
 * @创建时间: 2021-04-24 15:19:37
 * @修改人:
 * @修改时间:
 * @修改备注:
 * @Version: V1.0
 */
public interface YsProPrintService extends IService<YsProPrint> {

	/**
	 *
	 * @方法名: getYsProPrint
	 * @方法描述:印刷采集
	 * @作者: 刘凯
	 * @时间: 2021年4月24日 下午3:37:15
	 * @return:void
	 * @version
	 */
	  public  void getYsProPrint() ;
}
