package org.com.jscada.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *
 * @类描述：注塑机历史数据表
 * @项目名称：Spring-Collection @包名： org.com.jscada.entity
 * @类名称：HtOpcData @创建人： 刘凯
 * @创建时间：2021年4月8日下午12:37:11
 * @修改人：刘凯
 * @修改时间：2021年4月8日下午12:37:11 @修改备注：
 * @version v1.0
 */
@Data
@TableName("ys_opc_data")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class HtOpcData implements Serializable {
	private static final long serialVersionUID = 1L;

	/** id */
	@TableId(type = IdType.ASSIGN_ID)
	private String id;

	/**
	 * 设备编号
	 */
	private String tmMachId;

	/**
	 * 设备品牌
	 */
	private String tmMachType;

	/**
	 * 成型温度1
	 */
	private Float tmTemp1Current;

	/**
	 * 成型温度 2
	 */
	private Float tmTemp2Current;

	/**
	 * 成型温度 3
	 */
	private Float tmTemp3Current;

	/**
	 * 成型温度 4
	 */
	private Float tmTemp4Current;

	/**
	 * 成型温度 5
	 */
	private Float tmTemp5Current;

	/**
	 * 溶胶一压力(储料)
	 */
	private Float tmChargePress1;

	/**
	 * 溶胶速度1
	 */
	private Float tmChargeSpeed1;

	/**
	 * 溶胶一位置(储料)
	 */
	private Float tmChargePosi1;
	/**
	 * 溶胶二压力(储料)
	 */
	private Float tmChargePress2;

	/**
	 * 溶胶速度 2
	 */
	private Float tmChargeSpeed2;

	/**
	 * 溶胶二位置(储料)
	 */
	private Float tmChargePosi2;

	/**
	 * 射出一段压力
	 */
	private Float tmInjPress1;

	/**
	 * 射出一段速度
	 */
	private Float tmInjSpeed1;

	/**
	 * 射出时间
	 */
	private Float tmInjTime;

	/**
	 * 保压一段压力
	 */
	private Float tmHoldPress1;
	/**
	 * 保压一段速度
	 */
	private Float tmHoldSpeed1;

	/**
	 * 保压一段时间
	 */
	private Float tmHoldTime1;

	/**
	 * 冷却时间
	 */
	private Float tmCoolingTime;

	/**
	 * 模穴数
	 */
	private Integer tmMoldCavity;

	/**
	 * 计划数
	 */
	private Integer tmPlanCount;

	/**
	 * 完成数
	 */
	private Integer tmFinishCount;

	/**
	 * 警报状态
	 */
	private Integer tmAlarmState;

	/**
	 * 警报 ID
	 */
	private Integer tmAlarmId;

	/**
	 * 警报时间
	 */
	private String tmAlarmDateTime;

	/**
	 * 成型时间
	 */
	private Float tmCycleTime;

	/**
	 * 开模数
	 */
	private Integer tmShotCount;

	/**
	 * 次品数
	 */
	private Integer tmInferior;
	/**
	 * 采集时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date cjDate;
	/***创建人*/
	private String createBy;
	/***创建日期*/
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/***更新人*/
	private String updateBy;
	/***更新日期*/
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	/***所属部门*/
	private String sysOrgCode;
	/**
	 * 溶胶4压力(储料)
	 */
	private Float tmChargePress3;

	/**
	 * 溶胶4速度(储料)
	 */
	private Float tmChargeSpeed3;

	/**
	 * 溶胶4位置(储料)
	 */
	private Float tmChargePosi3;

	/**
	 * 溶胶4压力(储料)
	 */
	private Float tmChargePress4;

	/**
	 * 溶胶4速度(储料)
	 */
	private Float tmChargeSpeed4;

	/**
	 * 溶胶4位置(储料)
	 */
	private Float tmChargePosi4;
	/**
	 * 溶胶5压力(储料)
	 */
	private Float tmChargePress5;

	/**
	 * 溶胶5速度(储料)
	 */
	private Float tmChargeSpeed5;

	/**
	 * 溶胶5位置(储料)
	 */
	private Float tmChargePosi5;
	/**
	 * 模具编号
	 */
	private String touchCode;
	/**
	 * 指令单编号
	 */
	private String instructId;
}
