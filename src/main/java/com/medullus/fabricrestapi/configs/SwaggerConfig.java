package com.medullus.fabricrestapi.configs;

import com.medullus.fabricrestapi.FabricRestApiApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiDocket(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .directModelSubstitute(LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(LocalDateTime.class, java.util.Date.class)
                .useDefaultResponseMessages(true)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(FabricRestApiApplication.class.getPackage().getName()))
                .paths(PathSelectors.any())
                .build();
        return docket;

    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("FabricInterface")
                .description("Rest api service for fabric")
                .contact(new Contact("marek","marek.com","marek@com.com"))
                .build();
    }
}
