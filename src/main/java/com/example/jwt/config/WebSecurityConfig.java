package com.example.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.jwt.exception.JwtAuthenticationExceptionHandler;
import com.example.jwt.filter.JwtAuthorizationTokenFilter;
import com.example.jwt.service.JwtUserDetailsService;
import com.example.jwt.util.TokenUtil;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtAuthenticationExceptionHandler exceptionHandler;
    private TokenUtil jwtTokenUtil;
    private JwtUserDetailsService jwtUserDetailsService;
    
    public WebSecurityConfig(JwtAuthenticationExceptionHandler exceptionHandler, TokenUtil jwtTokenUtil, JwtUserDetailsService jwtUserDetailsService) {
    		this.exceptionHandler = exceptionHandler;
    		this.jwtTokenUtil = jwtTokenUtil;
    		this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.route.authentication.path}")
    private String authenticationPath;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(jwtUserDetailsService)
            .passwordEncoder(passwordEncoderBean());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf().disable()
            .exceptionHandling().authenticationEntryPoint(exceptionHandler).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
            
            // Un-secure Resource
            .antMatchers(
                    HttpMethod.GET,
                    "/v2/api-docs",					// Swagger 
                    "/configuration/ui/**", 			// Swagger
                    "/swagger-resources/**", 			// Swagger
                    "/configuration/security/**", 	// Swagger
                    "/swagger-ui.html", 				// Swagger
                    "/webjars/**"					// Swagger
            ).permitAll()
            
            // H2 Database 
            .antMatchers("/h2-console/**/**").permitAll()
            .antMatchers("/api/auth/**").permitAll()
            .anyRequest().authenticated();

        // Custom JWT based security filter
        JwtAuthorizationTokenFilter authenticationTokenFilter = new JwtAuthorizationTokenFilter(userDetailsService(), jwtTokenUtil, tokenHeader);
        httpSecurity
            .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // disable page caching
        httpSecurity
            .headers()
            .frameOptions().sameOrigin()
            .cacheControl();
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        // AuthenticationTokenFilter will ignore the below paths
//        web
//            .ignoring()
//            .antMatchers(HttpMethod.POST, authenticationPath)
//            
//            // allow anonymous resource requests
//            .and()
//	            .ignoring()
//	            .antMatchers(
//	                HttpMethod.GET,
//	                "/",
//	                "/*.html",
//	                "/favicon.ico",
//	                "/**/*.html",
//	                "/**/*.css",
//	                "/**/*.js"
//	            )
//
//            // Un-secure H2 Database (for testing purposes, H2 console shouldn't be unprotected in production)
//            .and()
//            		.ignoring()
//            		.antMatchers("/h2-console/**/**")
//            ;
//    }
    
    
}
