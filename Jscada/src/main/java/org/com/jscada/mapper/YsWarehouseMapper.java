package org.com.jscada.mapper;

import org.apache.ibatis.annotations.Delete;
import org.com.jscada.entity.YsWarehouse;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;


@Repository
public interface YsWarehouseMapper extends BaseMapper<YsWarehouse> {
	/**
     * 删除所有数据
     * @方法名: delAllWarehouse
     * @方法描述:
     * @作者: 刘凯
     * @时间: 2021年4月30日 上午10:48:38
     * @return:void
     * @version
     */
    @Delete("DELETE FROM ys_warehouse")
    public void delAllWarehouse() ;
}
