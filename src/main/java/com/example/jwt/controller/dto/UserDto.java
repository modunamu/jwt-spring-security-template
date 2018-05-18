package com.example.jwt.controller.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.jwt.model.Authority;
import com.example.jwt.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    
    private String password;
    
    private String email;
//    private Collection<? extends GrantedAuthority> authorities;
    private List<Authority> authorities;
    
    private boolean enabled;
    
    @JsonIgnore
    private Date lastPasswordResetDate;

    @Builder
    public UserDto(
          Long id,
          String username,
          String firstname,
          String lastname,
          String email,
          String password, 
          //Collection<? extends GrantedAuthority> authorities,
          List<Authority> authorities,
          boolean enabled,
          Date lastPasswordResetDate
    ) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;        
        this.authorities = authorities;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
    }
    
    public User toEntity() {
    		return User.builder()
    				.id(id)
    				.username(username)
    				.firstname(firstname)
    				.lastname(lastname)
    				.email(email)
    				.password(password)
    				//.authorities(mapToGrantedAuthorities(authorities))
    				.authorities(authorities)
    				.enabled(enabled)
    				.lastPasswordResetDate(lastPasswordResetDate)
    				.build();
    }
    
    private List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName().name()))
                .collect(Collectors.toList());
    }

}
