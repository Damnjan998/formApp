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

    public static final Supplier<FormException> formNameIsNotValid = () -> new FormException(
            ErrorMessages.FORM_NAME_NOT_VALID.getErrorMessage(),
            HttpStatus.BAD_REQUEST.value(),
            LocalDateTime.now().format(formatter),
            ErrorMessages.BAD_REQUEST.getErrorMessage()
    );

    public static final Supplier<FormException> fieldNameIsNotValid = () -> new FormException(
            ErrorMessages.FIELD_NAME_NOT_VALID.getErrorMessage(),
            HttpStatus.BAD_REQUEST.value(),
            LocalDateTime.now().format(formatter),
            ErrorMessages.BAD_REQUEST.getErrorMessage()
    );

    public static final Supplier<FormException> userDoesNotHavePermission = () -> new FormException(
            ErrorMessages.USER_DOES_NOT_HAVE_PERMISSION.getErrorMessage(),
            HttpStatus.FORBIDDEN.value(),
            LocalDateTime.now().format(formatter),
            ErrorMessages.FORBIDDEN.getErrorMessage()
    );

    public static final Supplier<FormException> formNotFound = () -> new FormException(
            ErrorMessages.FORM_NOT_FOUND.getErrorMessage(),
            HttpStatus.NOT_FOUND.value(),
            LocalDateTime.now().format(formatter),
            ErrorMessages.NOT_FOUND.getErrorMessage()
    );

    public static final Supplier<FormException> pageWrong = () -> new FormException(
            ErrorMessages.PAGE_NUMBER_WRONG.getErrorMessage(),
            HttpStatus.BAD_REQUEST.value(),
            LocalDateTime.now().format(formatter),
            ErrorMessages.BAD_REQUEST.getErrorMessage()
    );

    public static final Supplier<FormException> sizeWrong = () -> new FormException(
            ErrorMessages.SIZE_NUMBER_WRONG.getErrorMessage(),
            HttpStatus.BAD_REQUEST.value(),
            LocalDateTime.now().format(formatter),
            ErrorMessages.BAD_REQUEST.getErrorMessage()
    );

    public static final Supplier<FormException> orderDisplayMustBeGreaterThanZero = () -> new FormException(
            ErrorMessages.ORDER_DISPLAY_MUST_BE_GREATER_THAN_ZERO.getErrorMessage(),
            HttpStatus.BAD_REQUEST.value(),
            LocalDateTime.now().format(formatter),
            ErrorMessages.BAD_REQUEST.getErrorMessage()
    );

    public static final Supplier<FormException> formIdMustBeProvided = () -> new FormException(
            ErrorMessages.FORM_ID_MUST_BE_PROVIDED.getErrorMessage(),
            HttpStatus.BAD_REQUEST.value(),
            LocalDateTime.now().format(formatter),
            ErrorMessages.BAD_REQUEST.getErrorMessage()
    );

    public static final Supplier<FormException> typeIsNotValid = () -> new FormException(
            ErrorMessages.TYPE_IS_NOT_VALID.getErrorMessage(),
            HttpStatus.BAD_REQUEST.value(),
            LocalDateTime.now().format(formatter),
            ErrorMessages.BAD_REQUEST.getErrorMessage()
    );

    public static final Supplier<FormException> fieldNotFound = () -> new FormException(
            ErrorMessages.FIELD_NOT_FOUND.getErrorMessage(),
            HttpStatus.NOT_FOUND.value(),
            LocalDateTime.now().format(formatter),
            ErrorMessages.NOT_FOUND.getErrorMessage()
    );
}
