package com.formapp.damnjan.mappers;

import com.formapp.damnjan.entities.FieldEntity;
import com.formapp.damnjan.entities.FieldUserEntity;
import com.formapp.damnjan.entities.FormEntity;
import com.formapp.damnjan.entities.FormUserEntity;
import com.formapp.damnjan.models.request.FieldRequestDto;
import com.formapp.damnjan.models.response.FieldResponseModel;
import com.formapp.damnjan.models.response.FormResponseModel;
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

    List<FieldResponseModel> filledFieldEntityToFieldResponseModels(List<FieldUserEntity> content);

    @Mapping(target = "formResponseModel", source = "fieldUserEntity.formUserEntity")
    @Mapping(target = "name", source = "fieldUserEntity.fieldEntity.name")
    @Mapping(target = "orderDisplay", source = "fieldUserEntity.fieldEntity.orderDisplay")
    @Mapping(target = "type", source = "fieldUserEntity.fieldEntity.type")
    FieldResponseModel fieldEntityToFieldResponseModel(FieldUserEntity fieldUserEntity);

    @Mapping(target = "user", source = "formUserEntity.userEntity")
    FormResponseModel fieldEntityToFieldResponseModel(FormUserEntity formUserEntity);
}
