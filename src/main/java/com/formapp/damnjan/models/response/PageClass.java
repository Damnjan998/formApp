package com.formapp.damnjan.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageClass {

    private Integer totalPages;
    private Integer currentPage;
    private Integer count;
}
