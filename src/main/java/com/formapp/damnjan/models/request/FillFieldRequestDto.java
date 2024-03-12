package com.formapp.damnjan.models.request;

public record FillFieldRequestDto(Integer filledFormId, Integer fieldId, String textValue, Double numberValue) {
}
