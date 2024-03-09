package com.formapp.damnjan.exceptions;

import com.formapp.damnjan.models.response.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FormExceptionHandler {

    @ExceptionHandler(value = {FormException.class})
    public ResponseEntity<Object> handleBadRequestException(FormException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(new ErrorMessage(
                        ex.getMessage(), ex.getStatus(), ex.getTimestamp(), ex.getErrorMessage()
                ));
    }
}
