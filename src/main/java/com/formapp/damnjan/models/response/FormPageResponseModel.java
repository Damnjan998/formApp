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
public class FormPageResponseModel<T> {
    private List<T> forms;
    private Integer totalPages;
    private Integer currentPage;
    private Integer formCount;
}
