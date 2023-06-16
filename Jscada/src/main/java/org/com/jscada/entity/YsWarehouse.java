package org.com.jscada.entity;

import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @类描述: 仓库同步信息
 * @项目名称:
 * @包名: org.jeecg.modules.warehouse
 * @类名称: YsWarehouse
 * @创建人: 刘凯
 * @创建时间: 2020-12-30 17:26:24
 * @修改人:
 * @修改时间:
 * @修改备注:
 * @Version: V1.0
 */
@Data
@TableName("ys_warehouse")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class YsWarehouse implements Serializable{
    private static final long serialVersionUID = 1L;
	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    private java.lang.String id;
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
	/**所属部门*/
    private java.lang.String sysOrgCode;
	/**cnSOwner*/
    private java.lang.String cnSOwner;
	/**物料编码*/
    private java.lang.String cnSItemCode;
	/**物料名称*/
    private java.lang.String cnSItemName;
	/**物料状态*/
    private java.lang.String cnSItemState;
	/**指令批号*/
    private java.lang.String cnSLotNo;
	/**仓库*/
    private java.lang.String cnSStockCode;
	/**库区*/
    private java.lang.String cnSStockArea;
	/**库存量*/
    private java.math.BigDecimal cnFQuantity;
	/**cnFPlannedQty*/
    private java.math.BigDecimal cnFPlannedQty;
	/**分配量*/
    private java.math.BigDecimal cnFAllocQty;
	/**cnSNote*/
    private java.lang.String cnSNote;
	/**cnSTimestamp*/
    private java.lang.String cnSTimestamp;
	/**cnSProductionBatch*/
    private java.lang.String cnSProductionBatch;
	/**cnSFigureNo*/
    private java.lang.String cnSFigureNo;
	/**规格*/
    private java.lang.String cnSModel;
	/**cnSMeasureUnit*/
    private java.lang.String cnSMeasureUnit;
    
}
