package org.com.jscada.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.com.jscada.entity.YsElectricity;
import org.com.jscada.mapper.YsElectricityMapper;
import org.com.jscada.server.YsElectricityService;
import org.com.socket.WebSocket;
import org.com.utils.ForEachUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

/**
 * @Description: 电表一期
 * @Author: jeecg-boot
 * @Date:   2022-01-10
 * @Version: V1.0
 */
@Service
public class YsElectricityServiceImpl extends ServiceImpl<YsElectricityMapper, YsElectricity> implements YsElectricityService {
    public static final int port = 502;

    /**
     * 一期
     */
    private  static final    LinkedList<String> nameListI = new LinkedList<String>(){
        {
            this.add("422E宿舍楼用电");
            this.add("421F11AP1一期一层空调");
            this.add("422F1AP2一期一层空调");
            this.add("421E1AP3一期一层新风");
            this.add("411C2AP1一期二层新风");
            this.add("411E3AP1一期三层新风");
            this.add("3PPB1一期三层吸塑");
            this.add("2PPB1期二层针尖");
            this.add("2P1V1一期二层冰水机");
            this.add("422C模具车间动力");
            this.add("421B小花园");
            this.add("1APPB4一期注塑机");
            this.add("1APPB3一期注塑机");
            this.add("413E1APPB2一期注塑");
            this.add("1APPB5一期注塑机");
            this.add("411F1APPB1一期注塑机");
            this.add("413D1APK2空压机");
            this.add("1APK1一层空压机");
            this.add("一期新空压左");
            this.add("一期新空压右");
            this.add("412F灭菌动力");
            this.add("412D灭菌动力");
            this.add("412C4AP1一期四层化验室");
            this.add("413B成品库");
            this.add("411D一期照明");
        }
    };
    /**
     * 二期
     */
    private  static final    LinkedList<String> nameListII = new LinkedList<String>(){
        {
            this.add("1AP8印刷主电");
            this.add("1AP7粉碎电");
            this.add("1AP9冰水机主电");
            this.add("办公室空调");
            this.add("1-4冷却水泵");
            this.add("5#4#水泵");
            this.add("1AP6集中供料");
            this.add("3#4#冷却机组");
            this.add("11AP1注塑东");
            this.add("41APB4注塑东");
            this.add("1AP5注塑西");
            this.add("31AP3注塑东");
            this.add("1AP2注塑东二冷却机组");
            this.add("2AA1二楼配电主线");
            this.add("3AA1三楼配电主线");
            this.add("二期照明二楼配电");
            this.add("1-3备用");
            this.add("1-4备用");
            this.add("1-5备用");
            this.add("1-2备用");
            this.add("2-3备用");
        }
    };

    /**
     * 电柜采集
     */
    @Override
    public void colleElectricity() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LinkedList<BigDecimal>bigList = new LinkedList<BigDecimal>();
                String  rbw= getColleElectricity("00 00 00 00 00 06 01 03 00 00 00 32","172.16.10.227").replaceAll(" ","").trim();
                if(!"".equals(rbw)){
                Integer h= Integer.parseInt(rbw.substring(16,18),16);
                String r= rbw.substring(18,18+h*2);
                for (int j=0; j<r.length()/8;j ++) {
                    String t = r.substring(j*r.length()/(r.length()/8), (j+1)*r.length()/(r.length()/8));
                    bigList.add(hexFloat2BigDecimal(t));
                    }
                    addElectricity(bigList,nameListI);
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                LinkedList<BigDecimal>bigList1 = new LinkedList<BigDecimal>();
                String  rbw=    getColleElectricity("00 00 00 00 00 06 02 03 00 00 00 2a","172.16.10.226");
                if(!"".equals(rbw)){
                    Integer h= Integer.parseInt(rbw.substring(16,18),16);
                    String r= rbw.substring(18,18+h*2);
                    for (int j=0; j<r.length()/8;j ++) {
                        String t = r.substring(j*r.length()/(r.length()/8), (j+1)*r.length()/(r.length()/8));
                        bigList1.add(hexFloat2BigDecimal(t));
                    }
                    addElectricity(bigList1,nameListII);
                }
            }
        }).start();

    }

    public static BigDecimal hexFloat2BigDecimal(String hex) {
        float value = Float.intBitsToFloat((int) Long.parseLong(hex, 16));
        BigDecimal bd = new BigDecimal(Float.toString(value));
        return bd.setScale(2, BigDecimal.ROUND_DOWN);
    }

    /**
     *  调用采集方法
     */

    public synchronized String getColleElectricity(String str,String ip){
        WebSocket ws=new WebSocket(str,ip,port,109);
        Map map=ws.call();
        return map.get(ip).toString();
    }

    /**
     * 添加保存
     * @param intList
     * @param nameList
     */
    public void  addElectricity(LinkedList<BigDecimal> intList , LinkedList<String> nameList){
        LinkedList<YsElectricity>listed =new LinkedList<YsElectricity>();
        ForEachUtils.forEach(0, nameList, (i, send) -> {
            YsElectricity electricity = new YsElectricity();
            if ( nameList.size()==25){
                electricity.setId(i+1);
                electricity.setCjDate(new Date());
                electricity.setIp("172.16.10.227");
                electricity.setEpi(intList.get(i));
                String code1="";
                String electricName="";
                String code2="";
                switch(i){
                    case 0:
                    case 9:
                    case 10:
                    case 20:
                    case 21:
                    case 23:
                    case 24:
                        code1=send.substring(0,4);
                        electricName=send.substring(4);
                        electricity.setCode1(code1);
                        electricity.setElectricName(electricName);
                        break;
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 13:
                    case 15:
                    case 16:
                    case 22:
                        code1=send.substring(0,4);
                        code2=send.substring(4).replaceAll("[^a-zA-Z0-9]","");
                        electricName =send.substring(4).replaceAll(code2,"");
                        electricity.setCode1(code1);
                        electricity.setCode2(code2);
                        electricity.setElectricName(electricName);
                        break;
                    case 6:
                    case 7:
                    case 8:
                    case 11:
                    case 12:
                    case 14:
                    case 17:
                        code2=send.replaceAll("[^a-zA-Z0-9#-]","");
                        electricName =send.replaceAll(code2,"");
                        electricity.setCode2(code2);
                        electricity.setElectricName(electricName);
                        break;
                    case 18:
                    case 19:
                        electricity.setElectricName(send);
                        break;
                }
            }
            if (nameList.size()==21) {
                electricity.setId(i + 1 + 25);
                electricity.setCjDate(new Date());
                electricity.setIp("172.16.10.226");
                electricity.setEpi(intList.get(i));
                String code1 = "";
                String electricName = "";
                String code2 = "";
                switch (i) {
                    case 25:
                    case 26:
                    case 27:
                    case 29:
                    case 30:
                    case 31:
                    case 32:
                    case 33:
                    case 34:
                    case 35:
                    case 36:
                    case 37:
                    case 38:
                    case 39:
                        code2=send.replaceAll("[^a-zA-Z0-9#-]","");
                        electricName =send.replaceAll(code2,"");
                        electricity.setCode2(code2);
                        electricity.setElectricName(electricName);
                        break;
                    case 28:
                    case 40:
                    case 41:
                    case 42:
                    case 43:
                    case 44:
                    case 45:
                        electricity.setElectricName(send);
                        break;
                }
            }
            listed.add(electricity);

        });
        this.saveOrUpdateBatch(listed);
    }


}
