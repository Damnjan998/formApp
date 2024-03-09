package com.formapp.damnjan.utils;

public enum ErrorMessages {
    NOT_FOUND("Not found."),
    USERNAME_ALREADY_EXISTS("Username already exists."),
    USERNAME_NOT_FOUND("Username not found."),
    INTERNAL_SERVER_ERROR("Internal server error."),
    BAD_REQUEST("Bad request.");


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
