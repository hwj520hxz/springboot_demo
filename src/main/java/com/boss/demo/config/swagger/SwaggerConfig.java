package com.boss.demo.config.swagger;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author ：hwj
 * @version 版本号：V1.0
 * @Description ：
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.boss.demo.controller"))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Spring Boot Swagger2 构建restful API")
                .description("Spring Boot Swagger2接口文档")
                .contact(new Contact("XXXX","",""))
                .version("1.0")
                .build();
    }
}
