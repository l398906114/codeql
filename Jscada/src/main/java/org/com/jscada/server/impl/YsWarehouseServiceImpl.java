package org.com.jscada.server.impl;

import org.com.jscada.entity.YsWarehouse;
import org.com.jscada.mapper.YsWarehouseMapper;
import org.com.jscada.server.YsWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @类描述: 仓库同步信息
 * @项目名称:
 * @包名: org.jeecg.modules.warehouse
 * @类名称: YsWarehouseServiceImpl 
 * @创建人: 王凯祥
 * @创建时间: 2020-12-30 17:26:24
 * @修改人:
 * @修改时间:
 * @修改备注:
 * @Version: V1.0
 */
@Service
public class YsWarehouseServiceImpl extends ServiceImpl<YsWarehouseMapper, YsWarehouse> implements YsWarehouseService {

	 @Autowired
	 private YsWarehouseMapper ysWarehouseMapper;
	 /**
     * 删除所有数据
     * @方法名: delAllWarehouse
     * @方法描述:
     * @作者: 刘凯
     * @时间: 2021年4月30日 上午10:48:38
     * @return:void
     * @version
     */
    public void delAllWarehouse()  {
		  ysWarehouseMapper.delAllWarehouse();
		
	}
}
