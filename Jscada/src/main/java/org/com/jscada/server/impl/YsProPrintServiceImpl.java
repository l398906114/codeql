package org.com.jscada.server.impl;

import java.text.DecimalFormat;

import org.com.jscada.entity.YsProPrint;
import org.com.jscada.mapper.YsProPrintMapper;
import org.com.jscada.server.YsProPrintService;
import org.com.utils.Modbus4jUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.serotonin.modbus4j.code.DataType;

/**
 * @类描述: 印刷
 * @项目名称:
 * @包名: org.jeecg.modules.ysProPrint
 * @类名称: YsProPrintServiceImpl
 * @创建人:
 * @创建时间: 2021-04-24 15:19:37
 * @修改人:
 * @修改时间:
 * @修改备注:
 * @Version: V1.0
 */
@Service
public class YsProPrintServiceImpl extends ServiceImpl<YsProPrintMapper, YsProPrint> implements YsProPrintService {

	/**
	 * 印刷采集
	 *	getYsProPrint
	 * 2021年4月24日 下午3:37:39
	 */
	@Override
	public void getYsProPrint() {
		  try {

	        	//第一温度
	           Number temperature1 = Modbus4jUtils.readHoldingRegister(1,0,DataType.TWO_BYTE_INT_SIGNED);// 注意,float
	           //第二温度
	           Number temperature2 = Modbus4jUtils.readHoldingRegister(1,2,DataType.TWO_BYTE_INT_SIGNED);// 注意,float
	           //电击频率
	           Number shockFrequency = Modbus4jUtils.readHoldingRegister(1,100,DataType.TWO_BYTE_INT_SIGNED);// 注意,float
	           //频率设置
	           Number frequency = Modbus4jUtils.readHoldingRegister(1,4500,DataType.TWO_BYTE_INT_SIGNED);// 注意,float
	           //产量低位
	           Number highYield = Modbus4jUtils.readHoldingRegister(1,4200,DataType.TWO_BYTE_INT_SIGNED);// 注意,float
	           //产量高位
	           Number lowYield = Modbus4jUtils.readHoldingRegister(1,4201,DataType.TWO_BYTE_INT_SIGNED);// 注意,float
	           DecimalFormat df=new DecimalFormat("0.0");
	   		   Integer yield= lowYield.intValue()*65536+highYield.intValue()+65535;
	   		   YsProPrint ysProPrint= new YsProPrint();
		   		ysProPrint.setTemperature1(Double.parseDouble(df.format(temperature1.doubleValue()/10)));
		   		ysProPrint.setTemperature2(Double.parseDouble(df.format(temperature2.doubleValue()/10)));
		   		ysProPrint.setShockFrequency(Double.parseDouble(df.format(shockFrequency.doubleValue()/100)));
		   		ysProPrint.setFrequency(Double.parseDouble(df.format(frequency.doubleValue()/100)));
		   		ysProPrint.setYield(yield);
		   		save(ysProPrint);
	        } catch (Exception e) {
	            e.printStackTrace();


	        }
	}
}
