package com.formapp.damnjan.validators;

import com.formapp.damnjan.exceptions.FormException;
import com.formapp.damnjan.utils.ErrorMessages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PropertyValidatorTest {

    @InjectMocks
    PropertyValidator propertyValidator;

    @Test
    void isNameValid() {
        String formName = "";

        FormException formException = assertThrows(FormException.class,
                () -> propertyValidator.isNameValid(formName));

        Assertions.assertEquals(ErrorMessages.FORM_NAME_NOT_VALID.getErrorMessage(), formException.getMessage());
    }
}