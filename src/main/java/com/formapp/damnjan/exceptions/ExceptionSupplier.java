package com.formapp.damnjan.exceptions;

import com.formapp.damnjan.utils.ErrorMessages;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

public class ExceptionSupplier {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final Supplier<FormException> usernameAlreadyExists = () -> new FormException(
            ErrorMessages.USERNAME_ALREADY_EXISTS.getErrorMessage(),
            HttpStatus.BAD_REQUEST.value(),
            LocalDateTime.now().format(formatter),
            ErrorMessages.BAD_REQUEST.getErrorMessage()
    );

    public static final Supplier<FormException> usernameNotFound = () -> new FormException(
            ErrorMessages.USERNAME_NOT_FOUND.getErrorMessage(),
            HttpStatus.NOT_FOUND.value(),
            LocalDateTime.now().format(formatter),
            ErrorMessages.NOT_FOUND.getErrorMessage()
    );

    public static final Supplier<FormException> internalServerError = () -> new FormException(
            ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            LocalDateTime.now().format(formatter),
            ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage()
    );

}
