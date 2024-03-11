package com.formapp.damnjan.validators;

import com.formapp.damnjan.exceptions.ExceptionSupplier;
import org.springframework.util.ObjectUtils;

public class FormValidator {

    public void isFormNameValid(String name) {

        if (ObjectUtils.isEmpty(name)) {
            throw ExceptionSupplier.formNameIsNotValid.get();
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
