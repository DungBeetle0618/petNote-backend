package com.petnote;

import com.petnote.global.config.AuthVerifyProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AuthVerifyProperties.class)
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@MapperScan("com.petnote.api.**.mapper")
public class PetnoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetnoteApplication.class, args);
	}

}
