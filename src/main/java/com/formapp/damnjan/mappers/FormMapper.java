package com.formapp.damnjan.mappers;

import com.formapp.damnjan.entities.FormEntity;
import com.formapp.damnjan.models.request.FormRequestDto;
import com.formapp.damnjan.models.response.FormResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FormMapper {

    FormMapper INSTANCE = Mappers.getMapper(FormMapper.class);

    FormEntity createFormRequestDtoToFormEntity(FormRequestDto formRequestDto);

    List<FormResponseModel> formEntityToFormResponseModels(List<FormEntity> formEntities);

    FormResponseModel formEntityToFormResponseModel(FormEntity formEntity);
}
