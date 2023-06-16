package org.com.jscada.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.com.config.ReadFile;
import org.com.jscada.entity.HtOpcData;
import org.com.jscada.entity.YsOeeAlarm;
import org.com.jscada.entity.YsOpcRealTime;
import org.com.jscada.mapper.HtOpcDataMapper;
import org.com.jscada.server.HtOpcDataService;
import org.com.jscada.server.YsOeeAlarmService;
import org.com.jscada.server.YsOpcRealTimeService;
import org.com.utils.JsonUtil;
import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @Description: 海天注塑机数据采集
 * @Author: jeecg-boot
 * @Date: 2020-09-12
 * @Version: V1.0
 */
@Configuration
@Service
@Slf4j
public class HtOpcDataServiceImpl extends ServiceImpl<HtOpcDataMapper, HtOpcData> implements HtOpcDataService {
	@Autowired
	private YsOpcRealTimeService ysOpcRealTimeService;
	@Autowired
	private YsOeeAlarmService ysOeeAlarmService;
	@Autowired
	private HtOpcDataMapper htOpcDataMapper;
	@Autowired
	private HtOpcDataService htOpcDataService;
	/**
	 * opc 服务的地址
	 *
	 */
	private static final List<String>svsKey=new LinkedList<String>(){{
		this.add(".SVs.HeatingNozzle1.sv_Zone1.prActTemp^");                                // 温度1-5
		this.add(".SVs.HeatingNozzle1.sv_Zone2.prActTemp^");                                // 温度1-5
		this.add(".SVs.HeatingNozzle1.sv_Zone3.prActTemp^");                                // 温度1-5
		this.add(".SVs.HeatingNozzle1.sv_Zone4.prActTemp^");                                // 温度1-5
		this.add(".SVs.HeatingNozzle1.sv_Zone5.prActTemp^");                                // 温度1-5
		this.add(".SVs.Injection1.sv_PlastProfVis.Profile.Points[1].rBackPressure");        // 溶胶压力1
		this.add(".SVs.Injection1.sv_PlastProfVis.Profile.Points[1].rRotation");            // 溶胶速度1
		this.add(".SVs.Injection1.sv_PlastProfVis.Profile.Points[2].rStartPos");            // 溶胶位置1
		this.add(".SVs.Injection1.sv_PlastProfVis.Profile.Points[2].rBackPressure");        // 溶胶压力2
		this.add(".SVs.Injection1.sv_PlastProfVis.Profile.Points[2].rRotation");            // 溶胶速度2
		this.add(".SVs.Injection1.sv_PlastProfVis.Profile.Points[3].rStartPos");            // 溶胶位置2
		this.add(".SVs.Injection1.sv_InjectProfVis.Profile.Points[1].rVelocity");            // 射出压力
		this.add(".SVs.Injection1.sv_InjectProfVis.Profile.Points[1].rPressure");            // 射出速度
		this.add(".SVs.Injection1.sv_InjectTimesAct.dActMoveTime");                            // 射出时间  13
		this.add(".SVs.Injection1.sv_HoldProfVis.Profile.Points[1].rPressure");                // 保压压力
		this.add(".SVs.Injection1.sv_HoldProfVis.Profile.Points[1].rVelocity");                // 保压速度
		this.add(".SVs.Injection1.sv_HoldProfVis.Profile.Points[2].rStartPos");                // 保压时间
		this.add(".SVs.CoolingTime1.sv_dActCoolingTime");                                    // 冷却时间   17
		this.add(".SVs.system.sv_iCavities");                                                // 模穴数
		this.add(".SVs.system.sv_iProdCounterSet");                                            // 计划数
		this.add(".SVs.system.sv_iProdCounterAct");                                            // 完成数
		this.add(".SVs.system.sv_dCycleTime");                                                // 成型时间 21
		this.add(".SVs.system.sv_iShotCounterAct");                                            //开模次数
		this.add(".SVs.Injection1.sv_PlastProfVis.Profile.Points[3].rBackPressure");        // 溶胶压力3
		this.add(".SVs.Injection1.sv_PlastProfVis.Profile.Points[3].rRotation");            // 溶胶速度3
		this.add(".SVs.Injection1.sv_PlastProfVis.Profile.Points[4].rStartPos");            // 溶胶位置3
		this.add(".SVs.Injection1.sv_PlastProfVis.Profile.Points[4].rBackPressure");        // 溶胶压力4
		this.add(".SVs.Injection1.sv_PlastProfVis.Profile.Points[4].rRotation");            // 溶胶速度4
		this.add(".SVs.Injection1.sv_PlastProfVis.Profile.Points[5].rStartPos");            // 溶胶位置4
		this.add(".SVs.Injection1.sv_PlastProfVis.Profile.Points[5].rBackPressure");        // 溶胶压力5
		this.add(".SVs.Injection1.sv_PlastProfVis.Profile.Points[5].rRotation");            // 溶胶速度5
		this.add(".SVs.Injection1.sv_PlastProfVis.Profile.Points[6].rStartPos");            // 溶胶位置5
	}};



	@Override
	public void yldOpcService() throws IllegalArgumentException, UnknownHostException, NotConnectedException, JIException, DuplicateGroupException, AlreadyConnectedException {
		// 连接信息
		ConnectionInformation ci = new ConnectionInformation();
		ci.setHost("172.16.10.233"); // 服务器IP
		ci.setUser("opcs"); // 服务器电脑的账号
		ci.setPassword("123456"); // 服务器电脑的密码
		ci.setClsid("76B3D81D-9E9D-4DA2-913B-E369D3354CCF");
 		List<String> ipList = ReadFile.getContent();
		List<YsOpcRealTime> opcRealTimeList = new ArrayList<YsOpcRealTime>();
		YsOpcRealTime opc = new YsOpcRealTime();
		opc.setTmMachType("YLD");
		Map<String, Object> map = JsonUtil.toMap(JsonUtil.toJson(opc));
		map.remove("cjDate");
		map.remove("createBy");
		map.remove("sysOrgCode");
		map.remove("updateTime");
		map.remove("updateBy");
		map.remove("createTime");
		map.remove("id");
		map.remove("tmMachId");
		map.remove("tmMachType");
		map.remove("tmAlarmState");
		map.remove("tmAlarmId");
		map.remove("tmAlarmDateTime");
		map.remove("tmInferior");
		List<String> key = map.keySet().stream().collect(Collectors.toList());
		// 启动服务
		final String y="Meachine_";
		final Server server = new Server(ci, Executors.newSingleThreadScheduledExecutor());
		server.connect();
		Group group = server.addGroup();
		group.setActive(true);
		ipList.stream().forEach(ip->{
			LinkedHashMap<String, Object>  lm=new LinkedHashMap<String, Object>();
			for (int index = 0; index <svsKey.size() ; index++) {
				try {
					Item item = group.addItem(y + ip + svsKey.get(index));
					/**
					 * 此处读取的是时间
					 * 这三个地址的读取会出现下标越界
					 * 直接跳出本次循环
					 */
					if (index==13||index==17||index==21) {

						lm.put(key.get(index),0.0);
						continue;
					}
					lm.put(key.get(index),item.read(true).getValue().getObject());
				} catch ( JIException | AddFailedException ex) {
					break;
				}
			}
			lm.put("cjDate",new Date());
			lm.put("createBy","admin");
			lm.put("sysOrgCode","");
			lm.put("updateTime",new Date());
			lm.put("updateBy","");
			lm.put("createTime",new Date());
			lm.put("id","YLD1_"+ip);
			lm.put("tmMachId","YLD1_"+ip);
			lm.put("tmMachType","YLD");
			lm.put("tmAlarmState","");
			lm.put("tmAlarmId",0);
			lm.put("tmAlarmDateTime","");
			lm.put("tmInferior","");
			YsOpcRealTime ort =JsonUtil.toEntity(JsonUtil.toJson(lm),YsOpcRealTime.class);
			opcRealTimeList.add(ort);
		});
		group.clear();
		group.remove();
		server.disconnect();
		ysOpcRealTimeService.updateBatchById(opcRealTimeList);
		OeeAlarm(opcRealTimeList);
	}

	/**
	 * 定时同步信息
	 */
	@Override
	public void Realsyn() {
		List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
		List<YsOpcRealTime> list = ysOpcRealTimeService.list();
		list.forEach(item -> resList.add(JsonUtil.toMap(JsonUtil.toJson(item))));
		resList.forEach(item -> {
			item.put("id", UUID.randomUUID().toString().replaceAll("-", ""));
			item.put("createBy", "admin");
			item.put("createTime", new Date());
			item.put("updateBy", "admin");
			item.put("updateTime", new Date());
			HtOpcData htOpcData = JsonUtil.toEntity(JsonUtil.toJson(item), HtOpcData.class);
			Map<String ,String>htMap= htOpcDataMapper.getTouchCodeByOpcCode(htOpcData.getTmMachId());
			if( htMap!=null && htMap.size()>0){
				htOpcData.setTouchCode(htMap.get("touchCode"));
				htOpcData.setInstructId(htMap.get("instructCode"));
			}
			htOpcDataService.save(htOpcData);
		});

	}

	/**
	 * OEE
	 */
	public  void OeeAlarm(List<YsOpcRealTime>list){
		List<YsOeeAlarm> oeeList=new ArrayList<>();
		list.stream().forEach(item -> {
			if(item.getTmAlarmId()!=null&& item.getTmAlarmId() != 0){
				YsOeeAlarm oee=new YsOeeAlarm();
				long t=60;
				oee.setAlarmId(item.getTmAlarmId()).setCjDate(item.getCjDate()).setCreateBy("admin").setCountTime(t);
				oeeList.add(oee);
			}
		});
		ysOeeAlarmService.saveBatch(oeeList);
	}


}
