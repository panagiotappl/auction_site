package com.webapplication.service.user;

import com.webapplication.dto.user.*;
import com.webapplication.validator.user.ChangePasswordValidator;

import java.util.List;
import java.util.UUID;


public interface UserServiceApi {

    UserLogInResponseDto login(UserLogInRequestDto userLogInRequestDto) throws Exception;

    UserRegisterResponseDto register(UserRegisterRequestDto userRegisterRequestDto) throws Exception;

    UserResponseDto getUser(UUID authToken, String userId) throws Exception;
    
    SellerResponseDto getSeller(String sellerId) throws Exception;

    void verifyUser(UUID authToken, String userId) throws Exception;

    UserResponseDto updateUser(UUID authToken, String userId, UserUpdateRequestDto userUpdateRequestDto) throws Exception;

    void changePassword(UUID authToken, String userId, ChangePasswordRequestDto changePasswordRequestDto) throws Exception;

    List<UserResponseDto> getUnverifiedUsers(UUID authToken, Integer from, Integer to) throws Exception;

}
