package com.formapp.damnjan.models.response;

public record ErrorMessage(String message, Integer status, String timestamp, String errorMessage) {
}
