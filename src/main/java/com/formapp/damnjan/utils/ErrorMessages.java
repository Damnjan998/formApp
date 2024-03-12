package com.formapp.damnjan.utils;

public enum ErrorMessages {
    NOT_FOUND("Not found."),
    FORM_NAME_NOT_VALID("You must provide name for form."),
    FIELD_NAME_NOT_VALID("You must provide name for field."),
    USERNAME_ALREADY_EXISTS("Username already exists."),
    USERNAME_NOT_FOUND("Username not found."),
    INTERNAL_SERVER_ERROR("Internal server error."),
    PAGE_NUMBER_WRONG("Page index must not be less than one."),
    SIZE_NUMBER_WRONG("Page size must not be less than one."),
    ORDER_DISPLAY_MUST_BE_GREATER_THAN_ZERO("Order display must be greater than 0."),
    FORM_ID_MUST_BE_PROVIDED("Form must be provided."),
    TYPE_IS_NOT_VALID("Type is not valid."),
    USER_DOES_NOT_HAVE_PERMISSION("User does not have permission."),
    FORBIDDEN("FORBIDDEN"),
    BAD_REQUEST("Bad request."),
    FORM_NOT_FOUND("There is no form with provided id."),
    FILLED_FORM_NOT_FOUND("There is no filled form with provided id."),
    FIELD_NOT_FOUND("There is no field with provided id."),
    USER_NOT_FOUND("User not found."),
    TEXT_NOT_VALID("Text value is not valid."),
    NUMBER_NOT_VALID("Number value is not valid."),
    FIELD_ALREADY_FILLED("Field is already filled."),
    FILLED_FIELD_NOT_FOUND("There is no filled field with provided id.");

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
