package org.com.config;

import lombok.extern.slf4j.Slf4j;
import org.com.jscada.server.*;
import org.com.utils.DoDataSycn;
import org.com.utils.JDBCUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static java.lang.Thread.sleep;

/**
 * 定时采集类
 * @author dell
 * @version v1.0
 */
@EnableAsync
@Configuration // 1.主要用于标记配置类，兼备Component的效果。
@Slf4j
public class SaticScheduleTask {
  @Autowired
  private HtOpcDataService htOpcDataService;
  @Autowired
  private DoDataSycn doDataSycn;
  @Autowired
  private YsThcalcService ysThcalcService;
  @Autowired
  private YsEnergyService ysEnergyService;
  @Autowired
  private YsVoltageService ysVoltageService;
  @Autowired
  private YsProPrintService ysProPrintService;
  @Autowired
  private YsRStockTService ysStocktService;
  @Autowired
  private    YsElectricityService ysElectricityService;
  @Autowired
  private     YsAtService ysAtService;


  @Bean
  public TaskScheduler taskScheduler() {
    ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
    taskScheduler.setPoolSize(100);
    return taskScheduler;
  }

  /**
   * 实时采集OPC 数据
   * 每分钟采集一次
   */
  @Scheduled(cron = "0 0/1 * * * ? ")
  public void collOpc() {
    Long startTime = System.currentTimeMillis();
    try {
        htOpcDataService.yldOpcService();
    } catch (Exception e) {
      e.printStackTrace();
    }
      Long endTime = System.currentTimeMillis();
    log.info("采集完毕,用时：" + (endTime - startTime)/1000);
  }
  /**
   * 定时同步OPC实时数据到历史数据表中
   * 每2小时同步一次
   */
   @Scheduled(cron = "0 0 0/2 * * ?")
  public void realsyn() {
      Long startTime = System.currentTimeMillis();
    try {
        htOpcDataService.Realsyn();
    } catch (Exception e) {
      e.printStackTrace();
    }
      Long endTime = System.currentTimeMillis();
    log.info("同步完毕,用时：" + (endTime - startTime)/1000);

  }
  /**
   * 定时同步半成品仓库
   *  每天上班前， 下班后各同步一次
   */
   @Scheduled(cron = "0 0 07,20 * * ?")
  public void updateCategory() {
  Long startTime = System.currentTimeMillis();
    try {
        doDataSycn.updateCategory();
    } catch (Exception e) {
      e.printStackTrace();
    }
      Long endTime = System.currentTimeMillis();
    log.info("同步完毕,用时：" + (endTime - startTime)/1000);
  }
  /**
   *  定时采集温湿度
   *  每2分钟采集一次
   */
  @Scheduled(cron = "0 0/2 * * * ?")
  public void getCalcColle()   {
      Long startTime = System.currentTimeMillis();
    try {
        ysThcalcService.GetCalcColle();
    } catch (Exception e) {
      e.printStackTrace();
    }
      Long endTime = System.currentTimeMillis();
    log.info("采集完毕,用时：" + (endTime - startTime)/1000);
  }

  /**
   *  能耗
   * 每小时采集一次
   */
  @Scheduled(cron = "0 0 0/1 * * ?")
  public void getElectricEnergy()  {
      Long startTime = System.currentTimeMillis();
    try {
        ysEnergyService.getElectricEnergy();
    } catch (Exception e) {
      e.printStackTrace();
    }
      Long endTime = System.currentTimeMillis();
    log.info("采集完毕,用时：" + (endTime - startTime)/1000);
  }

  /**
   *  电压
   * 每小时采集一次
   */
   @Scheduled(cron = "0 0 0/1 * * ?")
  public void addEnergyService()  {
      Long startTime = System.currentTimeMillis();
    try {
        ysVoltageService.getVoltageService();
    } catch (Exception e) {
      e.printStackTrace();
    }
      Long endTime = System.currentTimeMillis();
    log.info("采集完毕,用时：" + (endTime - startTime)/1000+"秒");
  }
  /**
   * 定时采集印刷
   * 每小时采集一次
   */
   @Scheduled(cron = "0 0 0/1 * * ?")
  public void getYsProPrint() {
      Long startTime = System.currentTimeMillis();
    try {
        ysProPrintService.getYsProPrint();
    } catch (Exception e) {
      e.printStackTrace();
    }
      Long endTime = System.currentTimeMillis();
    log.info("采集完毕,用时：" + (endTime - startTime)/1000);
  }

  /**
   * 定时同步成品库
   * 每天上班前 下班后 各同步一次
   */
   @Scheduled(cron = "0 0 07,20 * * ?")
  public void connQueryRStockT() {
      Long startTime = System.currentTimeMillis();
    try {
        ysStocktService.connQueryRStockT();
    } catch (Exception e) {
      e.printStackTrace();
    }
      Long endTime = System.currentTimeMillis();
    log.info("采集完毕,用时：" + (endTime - startTime)/1000);
  }

    /**
     * 动态分表（按年月）<br>
     * 每个月的最后一天的23：58分开始做动态分表操作<br>
     * 根据现在修改后的操作计算数据量<br>
     * 实际上是可以做季度分表<br>
     *  200台机器 2小时同步一次历史数据 一个月 200*12*30=72000 条数据<br>
     *  比起以往一个月400W数据 减少太多   按照季度分表也就是每季度216000条数据<br>
     *  而且查历史数据 会更快一些<br>
     *  如果查询历史在一年之内  月份跨度大  最多要连12张表查询<br>
     *  按照季度分表  一年之内   最多连表4张<br>
     */
     @Scheduled(cron = "0 58 23 L * ? ")
    public void dynamicSubTable() {
        Long startTime = System.currentTimeMillis();
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        String s= LocalDateTime.now(ZoneOffset.of("+8")).format(sdf);
        s="ys_opc_data_"+s;
        String newTableName="ys_opc_data_copy";
        /**
         * 此语句调用存储过程建表
         */
        String sql = "{call test_create_table(?)}";
        Connection conn = null;
        CallableStatement call = null;
        PreparedStatement ps =null;
        try {
            //得到一个数据库连接
            conn = JDBCUtils.getConnection();
            //通过连接创建出statement
            call = conn.prepareCall(sql);
            call.setString(1, newTableName);
            call.execute();
            /**
             * 此语句给原表改名字 我们根据年月给原表来命名
             */
            String sql1 = " rename table ys_opc_data  to "+ s;
            ps=  conn.prepareStatement(sql1);
            ps.execute();
            /**
             * 此语句将复制的表改成原表的名字
             */
            sleep(1000);
            String sql2 ="rename table "+newTableName +" to ys_opc_data";

            ps=  conn.prepareStatement(sql2);
            ps.execute();
            Long endTime = System.currentTimeMillis();
          log.info("复制完毕,用时：" + (endTime - startTime)+"秒");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            //关闭连接，释放资源
            JDBCUtils.release(conn, ps, null);
        }

    }

    /**
     * 安藤采集
     */
    @Scheduled(cron = "0  0/2  * * * ? ")
    public void atColle() {
        Long startTime = System.currentTimeMillis();
       ysAtService.atOpcService();
        Long endTime = System.currentTimeMillis();
        log.info("安藤采集完毕：" + (endTime - startTime)/1000+"秒");
    }

    /**
     * 电柜采集
     * 已经修改完毕
     */
    @Scheduled(cron = "0 0 0/1 * * ? ")
   public void colleElectricity()  {
       Long startTime = System.currentTimeMillis();
       ysElectricityService.colleElectricity();
       Long endTime = System.currentTimeMillis();
       log.info("电柜采集完毕：" + (endTime - startTime)/1000+"秒");
     }

    /**
     * 电柜采集的数据进行历史数据的保存
     *  未实现
     */
//    @Scheduled(cron = "0 0 0/1 * * ? ")
    public void colleElectricityHistory ()  {
        Long startTime = System.currentTimeMillis();
//        ysElectricityService.colleElectricity();
        Long endTime = System.currentTimeMillis();
        log.info("电柜采集完毕：" + (endTime - startTime)/1000+"秒");
    }
}
