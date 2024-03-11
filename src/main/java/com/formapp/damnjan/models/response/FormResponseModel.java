package com.formapp.damnjan.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormResponseModel {

    private String name;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
