package com.formapp.damnjan.mappers;

import com.formapp.damnjan.entities.FormEntity;
import com.formapp.damnjan.entities.FormUserEntity;
import com.formapp.damnjan.entities.UserEntity;
import com.formapp.damnjan.models.request.FormRequestDto;
import com.formapp.damnjan.models.response.FilledFormResponseModel;
import com.formapp.damnjan.models.response.FormResponseModel;
import com.formapp.damnjan.models.response.UserResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FormMapper {

    FormMapper INSTANCE = Mappers.getMapper(FormMapper.class);

    FormEntity createFormRequestDtoToFormEntity(FormRequestDto formRequestDto);

    List<FormResponseModel> formEntityToFormResponseModels(List<FormEntity> formEntities);

    @Mapping(target = "user", source = "createdByUser")
    FormResponseModel formEntityToFormResponseModel(FormEntity formEntity);

    UserResponseModel userEntityToUserResponseModel(UserEntity userEntity);

    @Mapping(target = "formResponseModel", source = "formEntity")
    FilledFormResponseModel formUserEntityToFilledFormResponseModel(FormUserEntity formUserEntity);

    List<FilledFormResponseModel> formUserEntityToFilledFormResponseModels(List<FormUserEntity> content);
}
