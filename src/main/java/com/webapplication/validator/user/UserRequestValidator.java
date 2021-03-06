package com.webapplication.validator.user;


import com.webapplication.dto.user.ChangePasswordRequestDto;
import com.webapplication.dto.user.MessageRequestDto;
import com.webapplication.dto.user.MessageResponseDto;
import com.webapplication.dto.user.UserLogInRequestDto;
import com.webapplication.dto.user.UserRegisterRequestDto;
import com.webapplication.dto.user.UserUpdateRequestDto;
import com.webapplication.dto.user.VoteLinkDto;
import com.webapplication.exception.ValidationException;
import com.webapplication.validator.ValidatorWrapper.UserRequestValidatorWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRequestValidator implements UserRequestValidatorWrapper {

    @Autowired
    private UserLogInValidator userLogInValidator;

    @Autowired
    private UserRegisterValidator userRegisterValidator;

    @Autowired
    private UserUpdateRequestValidator userUpdateRequestValidator;

    @Autowired
    private ChangePasswordValidator changePasswordValidator;

    @Autowired
    private MessageValidator messageValidator;

    @Autowired
    private VoteLinkValidator voteLinkValidator;

    @Override
    public void validate(UserLogInRequestDto userLogInRequestDto) throws ValidationException {
        userLogInValidator.validate(userLogInRequestDto);
    }

    @Override
    public void validate(UserRegisterRequestDto userRegisterRequestDto) throws ValidationException {
        userRegisterValidator.validate(userRegisterRequestDto);
    }

    @Override
    public void validate(ChangePasswordRequestDto changePasswordRequestDto) throws ValidationException {
        changePasswordValidator.validate(changePasswordRequestDto);
    }

    @Override
    public void validate(UserUpdateRequestDto userUpdateRequestDto) throws ValidationException {
        userUpdateRequestValidator.validate(userUpdateRequestDto);
    }

    @Override
    public void validate(MessageRequestDto messageRequestDto) throws ValidationException {
        messageValidator.validate(messageRequestDto);
    }

    @Override
    public void validate(VoteLinkDto voteLinkDto) throws ValidationException {
        voteLinkValidator.validate(voteLinkDto);
    }

}
