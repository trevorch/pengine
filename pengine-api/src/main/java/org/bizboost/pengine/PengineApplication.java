package org.bizboost.pengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
/**
 * @author ：cdm
 * @date ：Created in 2019/6/28 22:55
 * @description：
 * @modified By：
 * @version: 0.1.0$
 */
@EnableScheduling
@SpringBootApplication
public class PengineApplication {

	public static void main(String[] args) {
		SpringApplication.run(PengineApplication.class, args);
	}

}
