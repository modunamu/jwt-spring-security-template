package com.example.jwt.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.jwt.common.JwtUser;
import com.example.jwt.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
    		return userRepository.findByUsername(username)
    						.map(user->JwtUser.builder()
							.authorities(user.getAuthorities())
							.username(user.getUsername())
							.email(user.getEmail())
							.enabled(user.getEnabled())
							.firstname(user.getFirstname())
							.lastname(user.getLastname())
							.id(user.getId())
							.lastPasswordResetDate(user.getLastPasswordResetDate())
							.password(user.getPassword())
							.build())
    						.orElseThrow(()->new UsernameNotFoundException(String.format("User not found by username '%s'.", username)));
    }
    
    
}
