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
public class FieldPageResponseModel<T> extends PageClass {

    private List<T> fields;

    public FieldPageResponseModel(List<T> fields, Integer totalPages, Integer currentPage, Integer fieldCount) {
        super(totalPages, currentPage, fieldCount);
        this.fields = fields;
    }
}
