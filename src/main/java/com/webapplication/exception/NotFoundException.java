package com.webapplication.exception;

import com.webapplication.error.UserError;

public class NotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public NotFoundException(UserError error) {
        super(error.getDescription());
    }

}
