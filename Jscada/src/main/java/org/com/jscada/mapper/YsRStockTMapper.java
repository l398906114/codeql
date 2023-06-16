package org.com.jscada.mapper;


import org.apache.ibatis.annotations.Delete;
import org.com.jscada.entity.YsRStockT;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @类描述: ys_r_stock_t
 * @项目名称:
 * @包名: org.jeecg.modules.warehouse
 * @类名称: YsRStockTMapper
 * @创建人: 刘凯
 * @创建时间: 2021-04-30 11:32:03
 * @修改人:
 * @修改时间:
 * @修改备注:
 * @Version: V1.0
 */
@Repository
public interface YsRStockTMapper extends BaseMapper<YsRStockT> {

	 @Delete("DELETE FROM ys_r_stock_t")
	 public void delAllRStockT() ;

}
