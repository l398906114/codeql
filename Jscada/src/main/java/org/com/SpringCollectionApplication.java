package org.com;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.com.utils.oConvertUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication 
@MapperScan(basePackages = "org.com.*.mapper") // 扫描的是mapper.xml中namespace指向值的包位置
@Slf4j
@EnableScheduling
public class SpringCollectionApplication {
	public static void main(String[] args) throws UnknownHostException {
		  ConfigurableApplicationContext application = SpringApplication.run(SpringCollectionApplication.class, args);
	        Environment env = application.getEnvironment();
	        String ip = InetAddress.getLocalHost().getHostAddress();
	        String port = env.getProperty("server.port");
	        String path = oConvertUtils.getString(env.getProperty("server.servlet.context-path"));
	        log.info("\n----------------------------------------------------------\n\t" +
	                "Application Spring-Boot is running! Access URLs:\n\t" +
	                "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
	                "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
	                "Swagger-ui: \thttp://" + ip + ":" + port + path + "/swagger-ui.html\n\t" +
	                "Doc文档: \thttp://" + ip + ":" + port + path + "/doc.html\n" +
	                "----------------------------------------------------------");
	}

}
