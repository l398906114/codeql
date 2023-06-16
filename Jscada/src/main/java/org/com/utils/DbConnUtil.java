package org.com.utils;

 
import java.sql.Connection;
import java.sql.DriverManager;
 
@SuppressWarnings("deprecation")
public class DbConnUtil {
 
    private static String sqlserverDriverClassName = "net.sourceforge.jtds.jdbc.Driver";
    private static String sqlserverDbUrl = "jdbc:jtds:sqlserver://192.168.155.101:1433;DatabaseName=WXYS_WMS";
    private static String sqlserverDbUser ="mes_user"; 
    private static String sqlserverDbPwd = "123456";
    
    /**
     * 从这里取数据
     *
     * @return
     */
	public static Connection createConnSrc() {
        Connection conn = null;
        try {
            Class.forName(sqlserverDriverClassName).newInstance();
            conn = DriverManager.getConnection(sqlserverDbUrl, sqlserverDbUser, sqlserverDbPwd);
        } catch (Exception e) {
            e.printStackTrace();
 
        }
        return conn;
    }
 
 
    /**
     * 存入的目标数据库
     *
     * @return
     */

	public static Connection createConnDist() {
        Connection conn = null;
        try {
            String driverClassName = "com.mysql.cj.jdbc.Driver";
            String dbUrl = "jdbc:mysql://172.16.10.251:3306/ys_dev?characterEncoding=UTF-8&useUnicode=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai";
            String dbUser ="root";
            String dbPwd = "123456";
            Class.forName(driverClassName).newInstance();
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
        } catch (Exception e) {
            e.printStackTrace();
 
        }
 
        return conn;
    }
 
 
    public static void close(Connection conn) {
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
 
        }
    }
 
}
 
 