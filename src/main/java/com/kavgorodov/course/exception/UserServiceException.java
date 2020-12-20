package com.kavgorodov.course.exception;

import java.io.Serializable;

/**
 * @author Vladimir Vishnevskiy
 */


public class UserServiceException extends RuntimeException {

    private static final long serialVersionUID = 8574934302309371833L;

    public UserServiceException(String message) {
        super(message);
    }
}
