package com.example.jwt.controller.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class  AuthRequestDto implements Serializable {

	private static final long serialVersionUID = -8096917879072502338L;
	
	private String username;
    private String password;

}
