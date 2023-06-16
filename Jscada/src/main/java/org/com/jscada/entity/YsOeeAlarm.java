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
 * @类描述: oee 的 报警时间
 * @项目名称:
 * @包名: org.jeecg.modules.ysHtopc
 * @类名称: YsOeeAlarm
 * @创建人: 刘凯
 * @创建时间: 2021-04-10 10:31:03
 * @修改人:
 * @修改时间:
 * @修改备注:
 * @Version: V1.0
 */
@Data
@TableName("ys_oee_alarm")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class YsOeeAlarm implements Serializable {
    private static final long serialVersionUID = 1L;
	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    private java.lang.String id;
	/**创建人*/
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;
	/**更新人*/
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date updateTime;
	/**所属部门*/
    private java.lang.String sysOrgCode;
	/**设备编码*/
    private java.lang.String tmMachId;
	/**采集时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date cjDate;
	/**统计状态*/
    private java.lang.Integer statisticsType;//0已统计 1未统计
	/**报警时长*/
    private java.lang.Long countTime;
	/**报警结束时间*/
	private java.util.Date cjDateEnd;
	/**警报id*/
	private java.lang.Integer alarmId;
	
	
	
}
