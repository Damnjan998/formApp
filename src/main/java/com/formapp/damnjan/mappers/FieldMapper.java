package com.formapp.damnjan.mappers;

import com.formapp.damnjan.entities.FieldEntity;
import com.formapp.damnjan.entities.FormEntity;
import com.formapp.damnjan.models.request.FieldRequestDto;
import com.formapp.damnjan.models.response.FieldResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FieldMapper {

    FieldMapper INSTANCE = Mappers.getMapper(FieldMapper.class);

    @Mapping(target = "formEntity.id", source = "formEntity.id")
    @Mapping(target = "formEntity.name", source = "formEntity.name")
    @Mapping(target = "formEntity.createdAt", source = "formEntity.createdAt")
    @Mapping(target = "formEntity.updatedAt", source = "formEntity.updatedAt")
    @Mapping(target = "name", source = "fieldRequestDto.name")
    @Mapping(target = "orderDisplay", source = "fieldRequestDto.orderDisplay")
    @Mapping(target = "type", source = "fieldRequestDto.type")
    FieldEntity fieldRequestDtoToFieldEntity(FieldRequestDto fieldRequestDto, FormEntity formEntity);

    @Mapping(target = "formResponseModel", source = "fieldEntity.formEntity")
    FieldResponseModel fieldEntityToFieldResponseModel(FieldEntity fieldEntity);

    List<FieldResponseModel> fieldEntityToFieldResponseModels(List<FieldEntity> content);
}
