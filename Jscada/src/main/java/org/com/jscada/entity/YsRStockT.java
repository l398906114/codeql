package org.com.jscada.entity;

import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @类描述: ys_r_stock_t
 * @项目名称:
 * @包名: org.jeecg.modules.warehouse
 * @类名称: YsRStockT
 * @创建人: 刘凯
 * @创建时间: 2021-04-30 11:32:03
 * @修改人:
 * @修改时间:
 * @修改备注:
 * @Version: V1.0
 */
@Data
@TableName("ys_r_stock_t")
@Accessors(chain = true)
public class YsRStockT implements Serializable{
    private static final long serialVersionUID = 1L;
	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    private java.lang.String id;
	/**物料ID*/
    private java.math.BigDecimal materialId;
	/**托盘条码*/
    private java.lang.String sn;
	/**箱数*/
    private java.math.BigDecimal boxes;
	/**出库状态 0-初始, 1-待出库*/
    private java.lang.String status;
	/**分配数量*/
    private java.lang.Integer distributionNum;
	/**创建人*/
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date createTime;
	/**更新人*/
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date updateTime;
}
