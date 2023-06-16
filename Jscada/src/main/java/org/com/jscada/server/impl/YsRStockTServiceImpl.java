package org.com.jscada.server.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.com.jscada.entity.YsRStockT;
import org.com.jscada.mapper.YsRStockTMapper;
import org.com.jscada.server.YsRStockTService;
import org.com.utils.Conn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;




/**
 * @类描述: ys_r_stock_t
 * @项目名称:成品庫
 * @包名: org.jeecg.modules.warehouse
 * @类名称: YsRStockTServiceImpl
 * @创建人: 刘凯
 * @创建时间: 2021-04-30 11:32:03
 * @修改人:
 * @修改时间:
 * @修改备注:
 * @Version: V1.0
 */
@Service
public class YsRStockTServiceImpl extends ServiceImpl<YsRStockTMapper, YsRStockT> implements YsRStockTService {
	@Autowired
	private	YsRStockTMapper ysRStockTMapper;

	public  void connQueryRStockT() {
		List<YsRStockT>list=new ArrayList<YsRStockT>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {// 1、获取连接对象
			conn = Conn.getconn();
			//2、创建statement类对象，用来执行SQL语句！！
			st = conn.createStatement();
			//3、创建sql查询语句
			String sql = "SELECT MATERIAL_ID AS materialid,SN AS sn, BOXES AS boxes, STATUS AS status FROM R_STOCK_T";
			//4、执行sql语句并且换回一个查询的结果集
			rs = st.executeQuery(sql);

			while (rs.next()) {// 循环遍历结果集
				BigDecimal materialid = rs.getBigDecimal("materialid");
				String sn = rs.getString("sn");
				BigDecimal boxes = rs.getBigDecimal("boxes");
				String status = rs.getString("status");
				YsRStockT ysRStockT= new YsRStockT();
				ysRStockT.setMaterialId(materialid);
				ysRStockT.setSn(sn);
				ysRStockT.setBoxes(boxes);
				ysRStockT.setStatus(status);
				ysRStockT.setCreateBy("admin");
				list.add(ysRStockT);
			}
			if (list.size()>0&&list!=null) {
				ysRStockTMapper.delAllRStockT();
				saveBatch(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
