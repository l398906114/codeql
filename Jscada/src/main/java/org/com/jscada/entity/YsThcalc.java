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
 * @类描述： 温湿度
 * @项目名称：jeecg-boot-module-system
 * @包名： org.jeecg.modules.ysTouch.entity
 * @类名称：YsThcalc	
 * @创建人： 刘凯
 * @创建时间：2020/10/10 15:53
 * @修改人：刘凯
 * @修改时间：2020/10/10 15:53
 * @修改备注：
 * @version v1.0
 */
@Data
@TableName("ys_thcalc")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class YsThcalc  implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private String id;//主键

    private String temperature;//温度

    private String humidity;//湿度

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date collectionTime;//采集时间

    private String    address;//采集地址
	
    
    private String    flag;// 
    
    /***创建人*/
	private String createBy;// 创建人

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	/***创建日期*/
	private Date createTime;// 创建日期

	/***更新人*/
	private String updateBy;// 更新人

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	/***更新日期*/
	private Date updateTime;// 更新日期

	/***所属部门*/
	private String sysOrgCode;// 所属部门

}
