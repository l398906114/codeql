package org.com.jscada.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.com.jscada.entity.YsThcalc;
import org.com.jscada.mapper.YsThcalcMapper;
import org.com.jscada.server.YsThcalcService;
import org.com.socket.WebSocket;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 温湿度采集
 */
@Service
public class YsThcalcServiceImpl extends ServiceImpl<YsThcalcMapper, YsThcalc> implements YsThcalcService {


	/**
	 * 温湿度采集
	 */
	public void GetCalcColle(){
		List<String>list=new ArrayList<String>(){
			{
				this.add("172.16.10.193");
				this.add("172.16.10.194");
			}
		};
		list.stream().forEach(ip->{
			String ret=	getCalcColle(ip);
			DecimalFormat df = new DecimalFormat("0.00");
			String  humidity = ret.substring(6, 10).toString().replaceAll(" ", "").trim();// 湿度
			String  temperature = ret.substring(10,14).toString().replaceAll(" ", "").trim();// 温度
			humidity = df.format((float) Integer.parseInt(humidity, 16) / 100);
			temperature = df.format(((float) Integer.parseInt(temperature, 16) - 27315) / 100);
			YsThcalc thCalc =new YsThcalc();
			thCalc.setAddress(ip);
			thCalc.setCollectionTime(new Date());
			thCalc.setHumidity(humidity);
			thCalc.setTemperature(temperature);
			save(thCalc);
		});
	}

	public static synchronized String getCalcColle(String ip){
		String str = "01 03 00 00 00 03 05 CB"; // 发送的16进制字符串
		int port=8001;
		WebSocket ws=new WebSocket(str,ip,port,11);
		Map map=ws.call();
	 	return map.get(ip).toString();
	}



}
