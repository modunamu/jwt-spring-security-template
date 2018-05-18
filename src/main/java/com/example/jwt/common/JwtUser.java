package com.example.jwt.common;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.jwt.model.Authority;
import com.example.jwt.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.ToString;


@ToString
public class JwtUser implements UserDetails {

	private static final long serialVersionUID = 8151278159429871254L;
	
    private final Long id;
    private final String username;
    private final String firstname;
    private final String lastname;
    private final String password;
    private final String email;
    private final List<Authority> authorities;
    private final boolean enabled;
    private final Date lastPasswordResetDate;

    @Builder
    public JwtUser(
          Long id,
          String username,
          String firstname,
          String lastname,
          String email,
          String password, 
          List<Authority> authorities,
          boolean enabled,
          Date lastPasswordResetDate
    ) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName().name()))
                .collect(Collectors.toList());    	
    }

    
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @JsonIgnore
    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }
    
    public User toEntity() {
    		return User.builder()
    			.authorities(authorities)
    			.email(email)
    			.enabled(enabled)
    			.firstname(firstname)
    			.id(id)
    			.lastname(lastname)
    			.lastPasswordResetDate(lastPasswordResetDate)
    			.password(password)
    			.username(username)
    			.build();
    }
}
