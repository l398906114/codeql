package org.com.jscada.server;

import com.baomidou.mybatisplus.extension.service.IService;
import org.com.jscada.entity.YsElectricity;

import java.io.IOException;

/**
 * @Description: 电表一期
 * @Author: jeecg-boot
 * @Date:   2022-01-10
 * @Version: V1.0
 */
public interface YsElectricityService extends IService<YsElectricity> {


    /**
     * 电柜采集
     */
   public void colleElectricity();
}
