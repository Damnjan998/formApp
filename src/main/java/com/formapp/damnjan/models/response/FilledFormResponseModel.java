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
public class FilledFormResponseModel {

    private FormResponseModel formResponseModel;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
