package org.com.jscada.server;

import java.util.concurrent.ExecutionException;

import org.com.jscada.entity.YsEnergy;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 能耗
 */
public interface YsEnergyService extends IService<YsEnergy> {


	/**
	 * 能耗
	 */
  	public void getElectricEnergy();
}
