package org.com.utils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.com.jscada.entity.YsRStockT;

public class Query {
	
	public static void connQueryRStockT() {
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
				System.err.println(ysRStockT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
