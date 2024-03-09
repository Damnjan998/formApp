package com.formapp.damnjan.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormException extends RuntimeException {

    private String message;
    private Integer status;
    private String timestamp;
    private String errorMessage;
}
