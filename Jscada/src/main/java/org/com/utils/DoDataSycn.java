package org.com.utils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.com.jscada.entity.YsWarehouse;
import org.com.jscada.server.YsWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@Slf4j
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling 
@SuppressWarnings("unused")
public class DoDataSycn {

	@Autowired
	private YsWarehouseService  ysWarehouseService;
	
	
	/**
	 * 半成品
	 */
	public  void updateCategory() {
		// 打开链接
		Connection connSqlserver = DbConnUtil.createConnSrc();
		DbBuilder sqlserver = new DbBuilder(connSqlserver);
		try {
			// 1、遍历mysql数据。添加map id为key
			// 2、遍历sqlserver数据获取新增数据添加插入map，获取修改过的数据添加update map
			// 3、实现批量更新功能
			// 由于是查询全表的数据做更新和插入操作 建议定时的频率不需要太高
			String sql = " SELECT  CN_GUID id,CN_S_OWNER cnSOwner,CN_S_ITEM_CODE cnSItemCode,CN_S_ITEM_NAME cnSItemName,"
					   + " CN_S_ITEM_STATE cnSItemState,CN_S_LOT_NO cnSLotNo,CN_S_STOCK_CODE cnSStockCode,CN_S_STOCK_AREA cnSStockArea,"
					   + " CN_F_QUANTITY cnFQuantity,CN_F_PLANNED_QTY cnFPlannedQty,CN_F_ALLOC_QTY cnFAllocQty,CN_S_NOTE cnSNote,"
					   + " CN_S_TIMESTAMP cnSTimestamp,CN_S_PRODUCTION_BATCH cnSProductionBatch,CN_S_FIGURE_NO cnSFigureNo,"
					   + " CN_S_MODEL cnSModel,CN_S_MEASURE_UNIT cnSMeasureUnit  FROM tn_wm_b_area_qty ";
			
			// 获取需要同步的数据
			List<Map<String, Object>> remoteList = sqlserver.getListMap(sql, null);
			if(remoteList.size()>0){
				ysWarehouseService.delAllWarehouse();
				List<YsWarehouse> insertList = new ArrayList<>();
				// 初始化全量插入
				for (Map<String, Object> item : remoteList) {
					insertList.add(JsonUtil.toEntity(JsonUtil.toJson(item), YsWarehouse.class));
				}
				ysWarehouseService.saveBatch(insertList);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭
			DbConnUtil.close(connSqlserver);
		}

	}

}