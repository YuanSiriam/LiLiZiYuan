package com.ziyuan.serve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@ComponentScan(basePackages = {"com.ziyuan.serve"})
public class ServeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServeApplication.class, args);
	}

}
