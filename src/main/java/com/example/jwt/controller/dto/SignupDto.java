package com.example.jwt.controller.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.example.jwt.model.Authority;
import com.example.jwt.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class SignupDto {

    private Long id;
    
    @NotBlank(message = "ID를 작성해주세요.")
    private String username;
    
    @NotBlank(message = "이름을 작성해주세요.")
    private String firstname;
    
    @NotBlank(message = "성을 작성해주세요.")
    private String lastname;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "비밀번호를 입력 해 주세요.")
    private String password;
    
    @NotBlank(message = "메일을 작성해주세요.")
    @Email(message = "메일 양식을 지켜주세요.")
    private String email;
    private List<Authority> authorities;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean enabled;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date lastPasswordResetDate;

    @Builder
    public SignupDto(
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
