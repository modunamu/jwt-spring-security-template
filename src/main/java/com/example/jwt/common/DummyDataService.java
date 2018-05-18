package com.example.jwt.common;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.jwt.controller.dto.SignupDto;
import com.example.jwt.model.Authority;
import com.example.jwt.model.AuthorityName;
import com.example.jwt.repository.AuthorityRepository;
import com.example.jwt.repository.UserRepository;
import com.example.jwt.service.UserSginupService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Profile({"dev", "local"})
@Component
public class DummyDataService {
	
	@Autowired private UserSginupService userSginupService;
	@Autowired private AuthorityRepository authorityRepository;
	@Autowired private UserRepository userRepository;	
	
	
	@PostConstruct
	public void init() {
		
		addRoles();
		addUser();
	}


	private void addRoles() {
		authorityRepository.save(new Authority(AuthorityName.ROLE_ADMIN));
		authorityRepository.save(new Authority(AuthorityName.ROLE_USER));
		authorityRepository.save(new Authority(AuthorityName.ROLE_ANONYMOUS));
	}	

	private void addUser() {

		if (userRepository.count() == 0) {
			SignupDto adminDto = new SignupDto();
			adminDto.setEmail("admin@admin.com");
			adminDto.setUsername("admin");
			adminDto.setPassword("admin");
			adminDto.setFirstname("KIM");
			adminDto.setLastname("Jaba");
			userSginupService.signup(adminDto);
			
			log.debug("===> admin");

			SignupDto userDto = new SignupDto();
			userDto.setEmail("user@user.com");
			userDto.setUsername("user");
			userDto.setPassword("user");
			userDto.setFirstname("Jo");
			userDto.setLastname("Kaja");
			userSginupService.signup(userDto);
			
			log.debug("===> user");

		}
	}
	
	
	private List<Authority> getAdminRoles() {
		List<Authority> authoritys = new ArrayList<Authority>();
		
		Authority adminRole = authorityRepository.findByAuthorityName(AuthorityName.ROLE_ADMIN);
		Authority userRole = authorityRepository.findByAuthorityName(AuthorityName.ROLE_USER);
		
		authoritys.add(adminRole);
		authoritys.add(userRole);
		
		
		return authoritys;
	}

	private List<Authority> getUserRoles() {
		Authority userRole = authorityRepository.findByAuthorityName(AuthorityName.ROLE_USER);
		return Collections.singletonList(userRole);
	}
	
}
