package com.webapplication.service;

import com.webapplication.dto.UserLogInRequestDto;
import com.webapplication.dto.UserLogInResponseDto;
import com.webapplication.dto.UserRegisterRequestDto;
import com.webapplication.dto.UserRegisterResponseDto;

public interface UserServiceApi {

	UserLogInResponseDto login(UserLogInRequestDto userLogInRequestDto) throws Exception;
	
	UserRegisterResponseDto register(UserRegisterRequestDto userRegisterRequestDto) throws Exception;
}
