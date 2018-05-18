package com.example.jwt.controller.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponseDto implements Serializable {

	private static final long serialVersionUID = 659209349112237433L;
	
	private final String token;

}
