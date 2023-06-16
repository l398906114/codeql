package org.com.jscada.server;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.com.jscada.entity.YsVoltage;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 电压
 */
public interface YsVoltageService extends IService<YsVoltage> {

    /**
     * 电压
     */
    public void getVoltageService();



}
