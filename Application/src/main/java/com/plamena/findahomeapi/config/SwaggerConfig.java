package com.plamena.findahomeapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
//@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket fhApi1() {
    return new Docket(DocumentationType.OAS_30)
            .apiInfo(apiInfo())
            .groupName("Version 1")
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.regex(".*/api/v1/.*"))
            .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("Find A Home REST API")
            .description("REST API for Find A Home application")
            .license("Apache 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .version("1.0.0")
            .build();
  }
}

