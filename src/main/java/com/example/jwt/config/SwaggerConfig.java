package com.example.jwt.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
	
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant("/api/**"))
				//.paths(PathSelectors.any())
				.build()
	            .pathMapping("/")
	            .securitySchemes(apiKey())
	            .securityContexts(securityContext())
	            .apiInfo(apiInfo());
	}
	  
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Jwt Spring Security with Swagger")
				.description("Jwt Spring Security Template with Swagger")
				.version("2.0")
				.build();
	}
	
	private List<ApiKey> apiKey() {
		List<ApiKey> apiList = new ArrayList<>();
		apiList.add(new ApiKey("apiKey", "Authorization", "header"));
        return apiList;
    }
	  
    private List<SecurityContext> securityContext() {
    		List<SecurityContext> securityList = new ArrayList<>();
    		securityList.add( SecurityContext.builder()
                    .securityReferences(defaultAuth())
                    .forPaths(PathSelectors.regex("/api.*"))
                    .build());
        return securityList;
    }
    
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        
        List<SecurityReference> securityList = new ArrayList<>();
        securityList.add(new SecurityReference("apiKey", authorizationScopes));
        return securityList;
    }    
	
    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
//                .clientId(clientId)
//                .clientSecret(clientSecret)
                .realm("realm")
                .appName("jwt-spring-security-template")
                .scopeSeparator(",")
                .additionalQueryStringParams(null)
                .useBasicAuthenticationWithAccessCodeGrant(false)
                .build();
    }	
	
}