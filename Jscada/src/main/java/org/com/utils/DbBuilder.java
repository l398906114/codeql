package org.com.utils;



import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
 
public class DbBuilder {
    Connection connection = null;
 
    public DbBuilder(Connection conn) {
        this.connection = conn;
    }
 
 
    public QueryRunner getQueryRunner() {
        return new QueryRunner();
    }
 
    /**
     * 根据传入的sql，查询记录，以Map形式返回第一行记录 注意：如果有多行记录，只会返回第一行，所以使用场景需要注意，可以使用根据主键来查询的场景
     *
     * @param sql
     * @param params
     * @return
     */
    public Map<String, Object> getFirstRowMap(String sql, Object... params) {
        try {
            QueryRunner runner = new QueryRunner();
            return runner.query(connection, sql, new MapHandler(), params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
 
 
    /**
     * 根据传入的sql查询数据，以List Map形式返回
     *
     * @param sql
     * @param params
     * @return
     */
    public List<Map<String, Object>> getListMap(String sql, Object... params) {
        try {
            QueryRunner runner = new QueryRunner();
            return runner.query(connection, sql, new MapListHandler(), params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
 
    /**
     * 根据sql和对象，查询结果并以对象形式返回
     *
     * @param sql
     * @param type
     * @return
     */
    public <T> T getBean(String sql, Class<T> type) {
        try {
            QueryRunner runner = getQueryRunner();
            return runner.query(sql, new BeanHandler<T>(type));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
 
    /**
     * 根据sql和对象，查询结果并以对象形式返回
     *
     * @param sql
     * @param type
     * @return
     */
    public <T> T getBean(Connection connection, String sql, Class<T> type) {
        try {
            QueryRunner runner = new QueryRunner();
            return runner.query(connection, sql, new BeanHandler<T>(type));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
 
 
    /**
     * 根据sql和对象，查询结果并以对象形式返回
     *
     * @param sql
     * @param type
     * @param params
     * @return
     */
    public <T> T getBean(Connection connection, String sql, Class<T> type, Object... params) {
        try {
            QueryRunner runner = new QueryRunner();
            return runner.query(connection, sql, new BeanHandler<T>(type), params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
 
 
    /**
     * 根据sql查询list对象
     *
     * @param sql
     * @param type
     * @return
     */
    public <T> List<T> getListBean(Connection connection, String sql, Class<T> type) {
        try {
            QueryRunner runner = new QueryRunner();
            return runner.query(connection, sql, new BeanListHandler<T>(type));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
 
    /**
     * 根据sql查询list对象
     *
     * @param sql
     * @param type
     * @param params
     * @return
     */
    public <T> List<T> getListBean(String sql, Class<T> type, Object... params) {
        try {
            QueryRunner runner = getQueryRunner();
            return runner.query(sql, new BeanListHandler<T>(type), params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
 
    /**
     * 根据sql查询list对象
     *
     * @param sql
     * @param type
     * @param params
     * @return
     */
    public <T> List<T> getListBean(Connection connection, String sql, Class<T> type, Object... params) {
        try {
            QueryRunner runner = new QueryRunner();
            return runner.query(connection, sql, new BeanListHandler<T>(type), params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
 
    /**
     * 保存操作
     *
     * @param sql
     * @param params
     * @return
     */
    public int save(String sql, Object... params) {
        try {
            QueryRunner runner = getQueryRunner();
            return runner.update(connection, sql, params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
 
    /**
     * 保存操作
     *
     * @param sql
     * @param params
     * @return
     */
    public int save(Connection connection, String sql, Object... params) {
        try {
            QueryRunner runner = new QueryRunner();
            return runner.update(connection, sql, params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
 
    /**
     * 更新操作
     *
     * @param sql
     * @param params
     * @return
     */
    public int update(String sql, Object... params) {
        try {
            QueryRunner runner = getQueryRunner();
            return runner.update(sql, params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
 
    /**
     * 更新操作
     *
     * @param sql
     * @param params
     * @return
     */
    public int update(Connection connection, String sql, Object... params) {
        try {
            QueryRunner runner = new QueryRunner();
            return runner.update(connection, sql, params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
 
    /**
     * 删除操作
     *
     * @param sql
     * @param params
     * @return
     */
    public int delete(String sql, Object... params) {
        try {
            QueryRunner runner = getQueryRunner();
            return runner.update(sql, params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
 
    /**
     * 删除操作
     *
     * @param sql
     * @param params
     * @return
     */
    public int delete(Connection connection, String sql, Object... params) {
        try {
            QueryRunner runner = new QueryRunner();
            return runner.update(connection, sql, params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
 
    /**
     * 批量操作，包括批量保存、修改、删、	 *
     *
     * @param sql
     * @param params
     * @return
     */
    public int[] batch(String sql, Object[][] params) {
        try {
            QueryRunner runner = getQueryRunner();
            return runner.batch(sql, params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
 
    /**
     * 批量操作，包括批量保存、修改、删处	 *
     *
     * @param sql
     * @param params
     * @return
     */
    public int[] batch(Connection connection, String sql, Object[][] params) {
        try {
            QueryRunner runner = new QueryRunner();
            return runner.batch(connection, sql, params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
 
    /**
     * �?��事务
     */
    public void beginTransaction(Connection conn) {
        try {
            conn.setAutoCommit(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
 
    /**
     * 回滚事务
     */
    public void rollback(Connection conn) {
        try {
            conn.rollback();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
 
    /**
     * 提交事务
     */
    public void commit(Connection conn) {
        try {
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
 
	/*
	 * public static void main(String[] args) { try {
	 * 
	 * Connection conn = DbConnUtil.createConnDist(); DbBuilder b = new
	 * DbBuilder(conn); //
	 * System.out.println(b.save("insert into demo_test(id,kitchen) values(?,?)",
	 * "11", "11")); DbConnUtil.close(conn); } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */
}
