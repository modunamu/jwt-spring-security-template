package com.example.jwt.service;

import java.util.Collections;

import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jwt.common.JwtUser;
import com.example.jwt.controller.dto.SignupDto;
import com.example.jwt.exception.UserDuplicateException;
import com.example.jwt.model.Authority;
import com.example.jwt.model.AuthorityName;
import com.example.jwt.model.User;
import com.example.jwt.repository.AuthorityRepository;
import com.example.jwt.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserSginupService  {

    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Transactional
    public JwtUser signup(SignupDto userDto) {
    	
    		verifyDuplicateUsername(userDto.getUsername());
    		
    		Authority userRole = authorityRepository.findByAuthorityName(AuthorityName.ROLE_USER);
    	
    		userDto.setEnabled(true);
    		userDto.setAuthorities(Collections.singletonList(userRole));
    		userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
    		
    		User user = userRepository.save(userDto.toEntity());
    		
    		return JwtUser.builder()
					.authorities(user.getAuthorities())
					.username(user.getUsername())
					.email(user.getEmail())
					.enabled(user.getEnabled())
					.firstname(user.getFirstname())
					.lastname(user.getLastname())
					.id(user.getId())
					.lastPasswordResetDate(user.getLastPasswordResetDate())
				.build();
    	}
    
    private void verifyDuplicateUsername(String username){
        if( userRepository.findByUsername(username).isPresent() ){
            throw new UserDuplicateException("이미 사용중인 username 입니다.(" + username + ")");
        }
    }
    
    
    
    
}
