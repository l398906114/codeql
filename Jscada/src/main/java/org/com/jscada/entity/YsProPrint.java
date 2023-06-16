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
 * @类描述: 印刷
 * @项目名称:
 * @包名: org.jeecg.modules.ysProPrint
 * @类名称: YsProPrint
 * @创建人: 王凯祥
 * @创建时间: 2021-04-24 15:19:37
 * @修改人:
 * @修改时间:
 * @修改备注:
 * @Version: V1.0
 */
@Data
@TableName("ys_pro_print")
@Accessors(chain = true)
public class YsProPrint  implements Serializable {
    private static final long serialVersionUID = 1L;
	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    private java.lang.String id;
	/**第一温度*/
    private java.lang.Double temperature1;
	/**第二温度*/
    private java.lang.Double temperature2;
	/**电击频率*/
    private java.lang.Double shockFrequency;
	/**当前频率*/
    private java.lang.Double frequency;
	/**产量*/
    private java.lang.Integer yield;
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
}
