package com.example.jwt.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.common.JwtUser;
import com.example.jwt.service.JwtUserDetailsService;
import com.example.jwt.util.TokenUtil;

@RestController
@RequestMapping("/api/v1/user")
public class UserRestController {

    @Value("${jwt.header}")
    private String tokenHeader;

    private TokenUtil jwtTokenUtil;
    
    private JwtUserDetailsService jwtUserDetailsService;
    
    
    public UserRestController(TokenUtil jwtTokenUtil, JwtUserDetailsService jwtUserDetailsService) {
    		this.jwtTokenUtil = jwtTokenUtil;
    		this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @GetMapping
    public JwtUser getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) jwtUserDetailsService.loadUserByUsername(username);
        return user;
    }
    


}
