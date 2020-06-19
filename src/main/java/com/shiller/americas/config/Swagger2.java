package com.shiller.americas.config;

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



@Configuration
@EnableSwagger2
public class Swagger2 {

  /**
   * Create a Docket bean to generate the swagger documentation.
   * 
   * @return a Docket bean that the generates the api info.
   */
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.basePackage("com.shiller.americas"))
        .paths(PathSelectors.regex("/.*")).build().apiInfo(apiEndPointsInfo());
  }

  /**
   * Create the general information.
   * 
   * @return the ApiInfo that contains the general information.
   */
  private ApiInfo apiEndPointsInfo() {
    return new ApiInfoBuilder().title("Spring Boot Person REST API").description("REST API")
        .contact(new Contact("Oscar Vazquez", "www.shilleramericas.net", "oscarvr1213@gmail.com"))
        .license("Apache 2.0").licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
        .version("1.0").build();
  }
}
