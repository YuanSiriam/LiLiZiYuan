package com.ziyuan.serve.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * class description goes here
 *
 * @ClassName: SwaggerConfig
 * @Description:
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public ApiInfo apiInfo(){
        return new ApiInfo(
                "community API 文档",
                "API 文档",
                "v0.1",
                "",
                "石子源",
                "",
                ""
        );

    }

    @Bean
    public Docket docket(ApiInfo apiInfo){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                //通过.select()方法配置扫描接口

                .select()
                /*
                    RequestHandlerSelectors 配置如何扫描接口
                    any()：全部扫描（项目中的所有接口）
                    none()：不扫描
                    basePackage：扫描指定包下的接口
                    withClassAnnotation：扫描带有指定注解的类
                    withMethodAnnotation：扫描带有指定注解的方法
                */
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))//扫描RestController修饰的所有类
                .build();
    }

}