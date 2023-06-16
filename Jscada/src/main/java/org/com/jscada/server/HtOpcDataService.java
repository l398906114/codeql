package org.com.jscada.server;

import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.com.jscada.entity.HtOpcData;
import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.AddFailedException;
import org.openscada.opc.lib.da.DuplicateGroupException;

import com.baomidou.mybatisplus.extension.service.IService;


public interface HtOpcDataService extends IService<HtOpcData> {

	/**
	 *
	 * @方法名: yldOpcService
	 * @方法描述: 亿利达采集
	 * @方法创建时间: 2020年11月30日 上午10:07:29
	 * @throws IllegalArgumentException
	 * @throws UnknownHostException
	 * @throws NotConnectedException
	 * @throws DuplicateGroupException
	 * @throws AddFailedException
	 * @throws AlreadyConnectedException:void
	 *
	 */
	public void yldOpcService() throws IllegalArgumentException, UnknownHostException, NotConnectedException,
            DuplicateGroupException, AddFailedException, AlreadyConnectedException, JIException;

	/**
	 * 采集数据同步
	 * @return
	 */
	public void Realsyn();


}
