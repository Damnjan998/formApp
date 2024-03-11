package com.formapp.damnjan.validators;

import com.formapp.damnjan.exceptions.ExceptionSupplier;
import com.formapp.damnjan.models.request.FieldRequestDto;
import org.springframework.util.ObjectUtils;

public class PropertyValidator {

    public void isNameValid(String name) {

        if (ObjectUtils.isEmpty(name)) {
            throw ExceptionSupplier.formNameIsNotValid.get();
        }
    }

    public void fieldPropertiesValidation(FieldRequestDto fieldRequestDto) {

        if (ObjectUtils.isEmpty(fieldRequestDto.name())) {
            throw ExceptionSupplier.fieldNameIsNotValid.get();
        }

        if (fieldRequestDto.orderDisplay() <= 0) {
            throw ExceptionSupplier.orderDisplayMustBeGreaterThanZero.get();
        }

        if (fieldRequestDto.formId() == null || fieldRequestDto.formId() == 0) {
            throw ExceptionSupplier.formIdMustBeProvided.get();
        }

        if (ObjectUtils.isEmpty(fieldRequestDto.type().name()) ||
                !fieldRequestDto.type().name().equals("TEXT") &&
                !fieldRequestDto.type().name().equals("NUMBER")) {
            throw ExceptionSupplier.typeIsNotValid.get();
        }
    }

    public void pageSizeValidation(Integer page, Integer size) {
        if (page <= 0) {
            throw ExceptionSupplier.pageWrong.get();
        }

        if (size < 1) {
            throw ExceptionSupplier.sizeWrong.get();
        }
    }
}
