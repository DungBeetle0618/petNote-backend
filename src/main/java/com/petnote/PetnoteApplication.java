package com.petnote;

import com.petnote.global.config.AuthVerifyProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@EnableConfigurationProperties({AuthVerifyProperties.class})
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@MapperScan("com.petnote.api.**.mapper")
public class PetnoteApplication extends SpringBootServletInitializer {

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PetnoteApplication.class);
    }

	public static void main(String[] args) {
		SpringApplication.run(PetnoteApplication.class, args);
	}

}
