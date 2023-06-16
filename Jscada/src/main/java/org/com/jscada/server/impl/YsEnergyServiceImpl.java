package org.com.jscada.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.com.jscada.entity.YsEnergy;
import org.com.jscada.mapper.YsEnergyMapper;
import org.com.jscada.server.YsEnergyService;
import org.com.socket.WebSocket;
import org.com.utils.JsonUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @类描述: 能耗
 * @项目名称:
 * @包名: org.jeecg.modules.ysTouch
 * @类名称: YsEnergyServiceImpl
 * @创建人: 刘凯
 * @创建时间: 2021-04-01 12:50:13
 * @修改人:
 * @修改时间:
 * @修改备注:
 * @Version: V1.0
 */
@Service
public class YsEnergyServiceImpl extends ServiceImpl<YsEnergyMapper, YsEnergy> implements YsEnergyService {

private  static  final  int port=8001;
	/**
	 * 能耗
	 */

	public void  getElectricEnergy() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		new Thread(new Runnable() {
			@Override
			public void run() {
				String  nh_192= getEnergyColle("01 03 00 3F 00 02 F4 07","172.16.10.192") ;
				if(!"".equals(nh_192)){
					BigDecimal  NH_192=	numDecimal(nh_192.substring(9,20),"1000");
					map.put("energy",NH_192);
					map.put("ipadd", "172.16.10.192");
					map.put("cjData",new Date());
					list.add(map);
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				String  nh_190= getEnergyColle("01 03 00 3F 00 02 F4 07","172.16.10.190") ;
				if(!"".equals(nh_190)){
					BigDecimal  NH_190=	numDecimal(nh_190.substring(9,20),"1000");
					map.put("energy",NH_190);
					map.put("ipadd", "172.16.10.190");
					map.put("cjData",new Date());
					list.add(map);
				}
			}
		}).start();
		for (Map<String, Object> maps : list) {
			YsEnergy ysEnergy = JsonUtil.toEntity(JsonUtil.toJson(maps), YsEnergy.class);
			save(ysEnergy);
		}
	}

	public BigDecimal numDecimal(String bignum1 ,String bignum2) {
		BigDecimal num1 = new BigDecimal(Integer.parseInt(bignum1, 16)*120); 
		BigDecimal num2 = new BigDecimal(bignum2); 
		return num1.divide(num2);
		
	}

	/**
	 * 调用采集
	 * @param str
	 * @param ip
	 * @return
	 */
	public static synchronized String getEnergyColle(String str, String ip) {
		WebSocket ws = new WebSocket(str, ip, port, 11);
		Map map = ws.call();
		return map.get(ip).toString().replaceAll(" ","").trim();
	}

}
