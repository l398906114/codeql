package org.com.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn {
	// oracle的驱动
	static String driverClass = "oracle.jdbc.driver.OracleDriver"; 
	// 连接oracle路径方式 "XE"是要建立连接的数据库名 1521端口
	static String url = "jdbc:oracle:thin:@172.16.10.224:1521:XE";
	// user是数据库的用户名
	static String user = "rles";
	// 用户登录密码
	static String password = "rles";
	// 为了方便下面的讲解，这里专门建立了一个用于数据库连接的一个方法
	public static Connection getconn() {
		Connection conn = null;
		try {// 首先建立驱动
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 驱动成功后进行连接
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("连接成功");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 返回一个连接
		return conn;
	}
}
