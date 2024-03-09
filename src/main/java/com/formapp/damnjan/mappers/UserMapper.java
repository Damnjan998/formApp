package com.formapp.damnjan.mappers;

import com.formapp.damnjan.entities.UserEntity;
import com.formapp.damnjan.models.request.SingUpRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity signUpRequestDtoToUserEntity(SingUpRequestDto signUpRequestDto);
}
