package org.com.jscada.server.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.com.jscada.entity.YsOpcRealTime;
import org.com.jscada.mapper.YsOpcRealTimeMapper;
import org.com.jscada.server.YsAtService;
import org.com.jscada.server.YsOpcRealTimeService;
import org.com.socket.WebSocket;
import org.com.utils.ForEachUtils;
import org.com.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;


/**
 * @author dell
 */
@Service
public class YsAtServiceImpl extends ServiceImpl<YsOpcRealTimeMapper, YsOpcRealTime> implements YsAtService {

    @Autowired
    private YsOpcRealTimeService ysOpcRealTimeService;
    /**
     * 端口
     */
    public static final int PORT = 502;
    /**
     * ip
     */
    private static final LinkedList<String> IPLIST = new LinkedList<String>() {
        {
            this.add("172.16.10.168");//1-65
            this.add("172.16.10.197");//1-14
            this.add("172.16.10.198");//1-15
            this.add("172.16.10.200");//1-23
            this.add("172.16.10.201");//1-22
            this.add("172.16.10.209");//1-20
            this.add("172.16.10.229");//1-11
            this.add("172.16.10.230");//1-12
            this.add("172.16.10.231");//1-21
        }
    };
    /**
     * 发送的报文
     */
    private static final LinkedList<String> SENDLIST = new LinkedList<String>() {
        {
            this.add("00 00 00 00 00 06 09 03 52 32 00 01");//保压一段时间 /100   0
            this.add("00 00 00 00 00 06 09 03 52 0A 00 01");//保压一段压力   1
            this.add("00 00 00 00 00 06 09 03 53 10 00 01");//转保压速度    2
            this.add("00 00 00 00 00 06 09 03 52 50 00 03");//熔胶一段终止位置、熔胶二段终止位置、熔胶三段终止位置 /100  3
            this.add("00 00 00 00 00 06 09 03 52 46 00 03"); //熔胶一段背压、熔胶二段背压、 熔胶三段背压  4
            this.add("00 00 00 00 00 06 09 03 52 4B 00 03");//熔胶一段速度、熔胶二段速度、熔胶三段速度  5
            this.add("00 00 00 00 00 06 09 03 33 03 00 05");//成型温度一~五段  6
            this.add("00 00 00 00 00 06 09 03 53 02 00 01");//射出时间 /1000 7
            this.add("00 00 00 00 00 06 09 03 52 15 00 01");// 射出速度 5215  /10
            this.add("00 00 00 00 00 06 09 03 52 01 00 01");// 射出压力 9
            this.add("00 00 00 00 00 06 09 03 2B 2C 00 01");//全程计时 10  /10
            this.add("00 00 00 00 00 06 09 03 55 81 00 01");// 冷却时间低位  11
            this.add("00 00 00 00 00 06 09 03 55 83 00 01");//  冷却时间高位  12
            this.add("00 00 00 00 00 06 09 03 01 01 00 03");//警报  13

            this.add("00 00 00 00 00 06 09 03 63 02 00 02");//开模次数  14
        }
    };
    /**
     * 字段
     */
    private static final LinkedList<String> KEYLIST = new LinkedList<String>() {
        {
            this.add("tmHoldTime1");
            this.add("tmHoldPress1");
            this.add("tmHoldSpeed1");
            this.add("tmChargePosi1");
            this.add("tmChargePosi2");
            this.add("tmChargePosi3");
            this.add("tmChargePress1");
            this.add("tmChargePress2");
            this.add("tmChargePress3");
            this.add("tmChargeSpeed1");
            this.add("tmChargeSpeed2");
            this.add("tmChargeSpeed3");
            this.add("tmTemp1Current");
            this.add("tmTemp2Current");
            this.add("tmTemp3Current");
            this.add("tmTemp4Current");
            this.add("tmTemp5Current");
            this.add("tmInjTime");
            this.add("tmInjSpeed1");
            this.add("tmInjPress1");
            this.add("tmCycleTime");
            this.add("tmCoolingTime");
            this.add("tmAlarmDateTime");
            this.add("tmShotCount");

        }
    };


    /**
     * 安腾注塑机
     */
    @Override
    public void atOpcService() {
        Map<String, LinkedList<Double>> map = new HashMap<>(8);
        List<String> key = new ArrayList<>();
        for (String value : IPLIST) {
            LinkedList<Double> list = new LinkedList<>();
            for (int j = 0; j < SENDLIST.size(); j++) {
                String s = addAt(SENDLIST.get(j), value);
                if ("".equals(s)) {
                    key.add(value);
                    break;
                }
                double d;
                LinkedList<Integer> integerList = subString(s);
                switch (j) {
                    case 0:
                    case 3:
                        for (Integer k : integerList) {
                            d = div(k, 100, 1);
                            list.add(d);
                        }
                        break;
                    case 1:
                    case 2:
                    case 4:
                    case 5:
                    case 6:
                    case 9:
                    case 11:
                    case 12:
                    case 13:
                        for (Integer k : integerList) {
                            d = k;
                            list.add(d);
                        }
                        break;

                    case 14:
                        d = (integerList.getFirst() << 16 | integerList.getLast());
                        list.add(d);
                        break;
                    case 7:
                        for (Integer k : integerList) {
                            d = div(k, 1000, 3);
                            list.add(d);
                        }
                        break;
                    case 8:
                    case 10:
                        for (Integer k : integerList) {
                            d = div(k, 10, 1);
                            list.add(d);
                        }
                        break;
                    default:
                        break;
                }
            }
            map.put(value, list);
        }
        key.forEach(map::remove);
        dataHandle(map);

    }


    /**
     * 数据处理
     */
    public void dataHandle(Map<String, LinkedList<Double>> map) {
        LinkedHashMap<String, Object> opcMap = new LinkedHashMap<>();
        IPLIST.forEach(ip -> {
            LinkedList<Double> list = map.get(ip);
            if (list != null && list.size() > 0) {
                ForEachUtils.forEach(0, KEYLIST, (k, key) -> {
                    if ("172.16.10.168".equals(ip)) {
                        opcMap.put("id", "AT1_65");
                        opcMap.put("tmMachId", "AT1_65");
                        opcMap.put("tmMachType", "AT");
                    }
                    if ("172.16.10.197".equals(ip)) {
                        opcMap.put("id", "AT1_14");
                        opcMap.put("tmMachId", "AT1_14");
                        opcMap.put("tmMachType", "AT");
                    }
                    if ("172.16.10.198".equals(ip)) {
                        opcMap.put("id", "AT1_15");
                        opcMap.put("tmMachId", "AT1_15");
                        opcMap.put("tmMachType", "AT");
                    }
                    if ("172.16.10.200".equals(ip)) {
                        opcMap.put("id", "AT1_23");
                        opcMap.put("tmMachId", "AT1_23");
                        opcMap.put("tmMachType", "AT");
                    }
                    if ("172.16.10.201".equals(ip)) {
                        opcMap.put("id", "AT1_22");
                        opcMap.put("tmMachId", "AT1_22");
                        opcMap.put("tmMachType", "AT");
                    }
                    if ("172.16.10.209".equals(ip)) {
                        opcMap.put("id", "AT1_20");
                        opcMap.put("tmMachId", "AT1_20");
                        opcMap.put("tmMachType", "AT");
                    }
                    if ("172.16.10.229".equals(ip)) {
                        opcMap.put("id", "AT1_11");
                        opcMap.put("tmMachId", "AT1_11");
                        opcMap.put("tmMachType", "AT");
                    }
                    if ("172.16.10.230".equals(ip)) {
                        opcMap.put("id", "AT1_12");
                        opcMap.put("tmMachId", "AT1_12");
                        opcMap.put("tmMachType", "AT");
                    }
                    if ("172.16.10.231".equals(ip)) {
                        opcMap.put("id", "AT1_21");
                        opcMap.put("tmMachId", "AT1_21");
                        opcMap.put("tmMachType", "AT");
                    }
                    opcMap.put(key, list.get(k));
                    if (k == 21) {
                        String dw = String.format("%04x", list.get(21).intValue());
                        String gw = String.format("%04x", list.get(22).intValue());
                        int dg = Integer.parseInt(dw, 16) == 0 ? Integer.parseInt(gw, 16) : Integer.parseInt(dw, 16);
                        double b = div(dg, 100, 2);
                        opcMap.put(key, b);
                    }
                    if (k == 22) {
                        String json = "";
                        try {
                            Map<String, String> jsonMap = jsonMap();
                            int jb1 = list.get(23).intValue();
                            int jb2 = list.get(24).intValue();
                            int jb3 = list.get(25).intValue();
                            if (jb1 != 65535) {
                                String jb1Json = String.format("%04x", jb1);
                                json += jsonMap.get("0x" + jb1Json) + ",";
                            }
                            if (jb2 != 65535) {
                                String jb2Json = String.format("%04x", jb2);
                                json += jsonMap.get("0x" + jb2Json) + ",";
                            }
                            if (jb3 != 65535) {
                                String jb3Json = String.format("%04x", jb3);
                                json += jsonMap.get("0x" + jb3Json);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        opcMap.put(key, json);
                    }
                    if (k == 23) {
                        opcMap.put(key, list.get(26).intValue());
                    }
                });
                mapToEntity(opcMap);
            }
        });

    }

    /**
     * 转换成实体
     *
     * @param: map
     */
    public void mapToEntity(Map<String, Object> map) {
        YsOpcRealTime opcRealTime = JsonUtil.toEntity(JsonUtil.toJson(map), YsOpcRealTime.class);
        assert opcRealTime != null;
        opcRealTime.setCjDate(new Date());
        opcRealTime.setCreateBy("admin");
        opcRealTime.setCreateTime(new Date());
        opcRealTime.setUpdateBy("admin");
        opcRealTime.setUpdateTime(new Date());
        opcRealTime.setSysOrgCode("");
        ysOpcRealTimeService.saveOrUpdate(opcRealTime);
    }


    /**
     * 精度转换
     *
     * @param v1    除数
     * @param v2    被除数
     * @param scale 保留 的小数位数
     */
    public static double div(int v1, int v2, int scale) {
        if (scale < 0) {
            System.err.println("除法精度必须大于0!");
            return 0;
        }
        BigDecimal b1 = new BigDecimal(Integer.toString(v1));
        BigDecimal b2 = new BigDecimal(Integer.toString(v2));
        return (b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP)).doubleValue();
    }

    /**
     * 读取报警信息的字符串
     *
     * @return:
     * @throws: IOException
     */
    public static Map<String, String> jsonMap() throws IOException {
        InputStream is = YsAtServiceImpl.class.getClassLoader().getResourceAsStream("static/dic.json");
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int len;
        byte[] b = new byte[1024];
        while (true) {
            assert is != null;
            if ((len = is.read(b)) == -1) {
                break;
            }
            os.write(b, 0, len);
        }
        String str = os.toString("UTF-8");
        is.close();
        os.close();
        return JSON.parseObject(str, new TypeReference<Map<String, String>>() {
        });
    }


    /**
     * 调用采集接口
     *
     * @param str 发送的字符串 </br>
     * @param ip  发送的IP地址 </br>
     * @return String  返回的数据  </br>
     */
    public static synchronized String addAt(String str, String ip) {
        WebSocket ws = new WebSocket(str, ip, PORT, 32);
        Map<String, String> map = ws.call();
        return map.get(ip);
    }

    /**
     * 采集的数据拆分成需要的集合
     *
     * @param str ：需要拆分的数据
     * @return list 返回的集合
     */
    public static LinkedList<Integer> subString(String str) {
        LinkedList<Integer> list = new LinkedList<>();
        int h = Integer.parseInt(str.substring(16, 18), 16);
        String r = str.substring(18, 18 + h * 2);
        for (int i = 0; i < r.length() / 4; i++) {
            String t = r.substring(i * r.length() / (r.length() / 4), (i + 1) * r.length() / (r.length() / 4));
            Integer x = Integer.parseInt(t, 16);
            list.add(x);
        }
        return list;
    }

    public static void main(String[] args) {
        String ip="172.16.10.231";
        String str="00 00 00 00 00 06 09 03 63 02 00 02";
        String ret=  addAt(str,ip);
        System.out.println( subString(ret).getFirst()<<16|subString(ret).getLast());

    }
}
