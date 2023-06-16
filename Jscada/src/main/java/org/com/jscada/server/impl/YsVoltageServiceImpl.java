package org.com.jscada.server.impl;

import java.util.*;

import org.com.jscada.entity.YsVoltage;
import org.com.jscada.mapper.YsVoltageMapper;
import org.com.jscada.server.YsVoltageService;
import org.com.socket.WebSocket;
import org.com.utils.JsonUtil;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;




/**
 * @类描述: 电压
 * @项目名称:
 * @包名: org.jeecg.modules.ysTouch
 * @类名称: YsVoltageServiceImpl
 * @创建人: 刘凯
 * @创建时间: 2021-04-01 12:50:04
 * @修改人:
 * @修改时间:
 * @修改备注:
 * @Version: V1.0
 */
@Service
public class YsVoltageServiceImpl extends ServiceImpl<YsVoltageMapper, YsVoltage> implements YsVoltageService {

	private  static  final  int port=8001;

	/**
	 * 电压
	 */
	@Override
	public void getVoltageService()   {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		new Thread(new Runnable() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				String  dy= getVoltageColle("01 03 00 28 00 03 85 C3","172.16.10.190") ;
				String  dcp= getVoltageColle("01 03 00 23 00 01 75 C0","172.16.10.190") ;
				if(!"".equals(dy)&&!"".equals(dcp)){
					int Dy1 = Integer.parseInt(dy.substring(9, 14), 16);// 电压1
					int Dy2 = Integer.parseInt(dy.substring(15, 20), 16);// 电压2
					int Dy3 = Integer.parseInt(dy.substring(21, 26), 16);// 电压3
					int Dcp = Integer.parseInt(dcp.substring(9, 11), 16);
					double d1 = Dy1 * (Math.pow(10, Dcp - 4));
					double d2 = Dy2 * (Math.pow(10, Dcp - 4));
					double d3 = Dy3 * (Math.pow(10, Dcp - 4));
					map.put("dy1", d1);
					map.put("dy2", d2);
					map.put("dy3", d3);
					map.put("ipadd", "172.16.10.190");
					map.put("cjData", new Date());
					list.add(map);
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				String  dy= getVoltageColle("01 03 00 28 00 03 85 C3","172.16.10.192") ;
				String  dcp= getVoltageColle("01 03 00 23 00 01 75 C0","172.16.10.192") ;
				if(!"".equals(dy)&&!"".equals(dcp)){
					int Dy1 = Integer.parseInt(dy.substring(9, 14), 16);// 电压1
					int Dy2 = Integer.parseInt(dy.substring(15, 20), 16);// 电压2
					int Dy3 = Integer.parseInt(dy.substring(21, 26), 16);// 电压3
					int Dcp = Integer.parseInt(dcp.substring(9, 11), 16);
					double d1 = Dy1 * (Math.pow(10, Dcp - 4));
					double d2 = Dy2 * (Math.pow(10, Dcp - 4));
					double d3 = Dy3 * (Math.pow(10, Dcp - 4));
					map.put("dy1", d1);
					map.put("dy2", d2);
					map.put("dy3", d3);
					map.put("ipadd", "172.16.10.190");
					map.put("cjData", new Date());
					list.add(map);
				}
			}
		}).start();

		if(list.size()>0){
			list.stream().forEach(itme->{
				YsVoltage ysVoltage = JsonUtil.toEntity(JsonUtil.toJson(itme), YsVoltage.class);
				System.out.println(ysVoltage);
			});
		}

	}

	/**
	 * 调用采集
	 * @param str
	 * @param ip
	 * @return
	 */
	public static  String getVoltageColle(String str, String ip) {
			WebSocket ws = new WebSocket(str, ip, port, 11);
			Map map = ws.call();
			return map.get(ip).toString().replaceAll(" ","").trim();
	}

}
