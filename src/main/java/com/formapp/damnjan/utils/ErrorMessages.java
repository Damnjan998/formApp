package com.formapp.damnjan.utils;

public enum ErrorMessages {
    NOT_FOUND("Not found."),
    FORM_NAME_NOT_VALID("You must provide name for form."),
    USERNAME_ALREADY_EXISTS("Username already exists."),
    USERNAME_NOT_FOUND("Username not found."),
    INTERNAL_SERVER_ERROR("Internal server error."),
    PAGE_NUMBER_WRONG("Page index must not be less than one."),
    SIZE_NUMBER_WRONG("Page size must not be less than one."),
    USER_DOES_NOT_HAVE_PERMISSION("User does not have permission."),
    FORBIDDEN("FORBIDDEN"),
    BAD_REQUEST("Bad request."),
    FORM_NOT_FOUND("There is no form with provided id.");


    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
