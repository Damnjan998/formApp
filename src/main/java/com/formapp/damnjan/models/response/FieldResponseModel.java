package com.formapp.damnjan.models.response;

import com.formapp.damnjan.entities.FormEntity;
import com.formapp.damnjan.enumes.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FieldResponseModel {

    private String name;
    private Integer orderDisplay;
    private Type type;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private FormResponseModel formResponseModel;
}
