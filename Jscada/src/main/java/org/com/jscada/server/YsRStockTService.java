package org.com.jscada.server;

import org.com.jscada.entity.YsRStockT;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @类描述: ys_r_stock_t
 * @项目名称:
 * @包名: org.jeecg.modules.warehouse
 * @类名称: YsRStockTService 
 * @创建人: 刘凯
 * @创建时间: 2021-04-30 11:32:03
 * @修改人:
 * @修改时间:
 * @修改备注:
 * @Version: V1.0
 */
public interface YsRStockTService extends IService<YsRStockT> {

	
	public void	connQueryRStockT();
}
