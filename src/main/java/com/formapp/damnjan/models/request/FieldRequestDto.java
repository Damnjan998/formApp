package com.formapp.damnjan.models.request;

import com.formapp.damnjan.enumes.Type;

public record FieldRequestDto(Integer formId, String name, Integer orderDisplay, Type type) {
}
