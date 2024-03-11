package com.formapp.damnjan.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormPageResponseModel<T> extends PageClass {
    private List<T> forms;

    public FormPageResponseModel(List<T> forms, Integer totalPages, Integer currentPage, Integer formCount) {
        super(totalPages, currentPage, formCount);
        this.forms = forms;
    }
}
