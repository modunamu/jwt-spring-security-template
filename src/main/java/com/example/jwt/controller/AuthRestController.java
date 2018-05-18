package com.example.jwt.controller;

import java.net.URI;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.jwt.common.ApiError;
import com.example.jwt.common.JwtUser;
import com.example.jwt.controller.dto.AuthResponseDto;
import com.example.jwt.controller.dto.UserDto;
import com.example.jwt.controller.dto.SignupDto;
import com.example.jwt.exception.AuthenticationException;
import com.example.jwt.exception.UserDuplicateException;
import com.example.jwt.service.JwtUserDetailsService;
import com.example.jwt.service.UserSginupService;
import com.example.jwt.util.TokenUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private AuthenticationManager authenticationManager;
    private TokenUtil jwtTokenUtil;
    private JwtUserDetailsService jwtUserDetailsService;
    private UserSginupService userSginupService;
    
    @Value("${jwt.header}")
    private String tokenHeader;
    
    public AuthRestController(AuthenticationManager authenticationManager, 
    								TokenUtil jwtTokenUtil, 
    								JwtUserDetailsService jwtUserDetailsService,
    								UserSginupService userSginupService) {
    		this.authenticationManager = authenticationManager;
    		this.jwtTokenUtil = jwtTokenUtil;
    		this.jwtUserDetailsService = jwtUserDetailsService;
    		this.userSginupService = userSginupService;
    }


    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDto userDto) throws AuthenticationException {

        authenticate(userDto.getUsername(), userDto.getPassword());

        // Reload password post-security so we can generate the token
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(userDto.getUsername());
        
        final String token = jwtTokenUtil.generateToken(userDetails);

        // Return the token
        return ResponseEntity.ok(new AuthResponseDto(token));
    }


    @GetMapping(value = "/refresh")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String authToken = request.getHeader(tokenHeader);
        final String token = authToken.substring(7);
        
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) jwtUserDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new AuthResponseDto(refreshedToken));
        } 
        
        return ResponseEntity.badRequest().body(null);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<SignupDto> signup(@RequestBody @Valid final SignupDto userDto) {
    	
    		log.debug("\nSignup=>"+userDto.toString());
    	
    		JwtUser savedUser = userSginupService.signup(userDto);
    		SignupDto savedSignupDto = SignupDto.builder()
    					.id(savedUser.getId())
    					.username(savedUser.getUsername())
    					.email(savedUser.getEmail())
    					.build();
    	
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedUser.getId()).toUri();

		return ResponseEntity.created(location).body(savedSignupDto);
    }    

    /**
     * Authenticates the user. If something is wrong, an {@link AuthenticationException} will be thrown
     */
    private void authenticate(String username, String password) {
        Objects.requireNonNull(username, () -> "Username is required.");
        Objects.requireNonNull(password, () -> "Password is required.");

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationException("User is disabled!", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Bad credentials!", e);
        }
    }
    
    
    @ExceptionHandler({ UserDuplicateException.class })
    public ResponseEntity<Object> handleUserDuplicateException(final UserDuplicateException ex, final WebRequest request) {
    		log.info(ex.getClass().getName());
    		log.error("\nUserDuplicateException in controller \n", ex);
        //
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), "Duplicate User Exception!!! in controller");
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }
    
}
