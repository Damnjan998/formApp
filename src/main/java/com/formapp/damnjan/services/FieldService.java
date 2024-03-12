package com.formapp.damnjan.services;

import com.formapp.damnjan.entities.FieldEntity;
import com.formapp.damnjan.entities.FieldUserEntity;
import com.formapp.damnjan.entities.FormEntity;
import com.formapp.damnjan.entities.FormUserEntity;
import com.formapp.damnjan.exceptions.ExceptionSupplier;
import com.formapp.damnjan.mappers.FieldMapper;
import com.formapp.damnjan.models.request.FieldRequestDto;
import com.formapp.damnjan.models.request.FillFieldRequestDto;
import com.formapp.damnjan.models.response.FieldPageResponseModel;
import com.formapp.damnjan.models.response.FieldResponseModel;
import com.formapp.damnjan.repositories.*;
import com.formapp.damnjan.validators.PropertyValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.formapp.damnjan.utils.PrincipalHelper.checkPermission;
import static com.formapp.damnjan.utils.PrincipalHelper.getLoggedInUserId;

@Service
public class FieldService {

    private final FieldRepository fieldRepository;
    private final FormRepository formRepository;
    private final PropertyValidator propertyValidator;
    private final UserRepository userRepository;
    private final FormUserRepository formUserRepository;
    private final FieldUserRepository fieldUserRepository;
    private final FieldMapper fieldMapper = FieldMapper.INSTANCE;

    public FieldService(FieldRepository fieldRepository,
                        FormRepository formRepository,
                        PropertyValidator propertyValidator,
                        UserRepository userRepository,
                        FormUserRepository formUserRepository,
                        FieldUserRepository fieldUserRepository) {
        this.fieldRepository = fieldRepository;
        this.formRepository = formRepository;
        this.propertyValidator = propertyValidator;
        this.userRepository = userRepository;
        this.formUserRepository = formUserRepository;
        this.fieldUserRepository = fieldUserRepository;
    }

    public void createField(FieldRequestDto fieldRequestDto) {

        checkPermission();
        propertyValidator.fieldPropertiesValidation(fieldRequestDto);

        FormEntity formEntity = formRepository.findById(fieldRequestDto.formId())
                .orElseThrow(ExceptionSupplier.formNotFound);

        FieldEntity fieldEntity = fieldMapper.fieldRequestDtoToFieldEntity(fieldRequestDto, formEntity);
        fieldEntity.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        fieldRepository.save(fieldEntity);

    }

    public void deleteField(Integer id) {
        checkPermission();
        FieldEntity fieldEntity = fieldRepository.findById(id).orElseThrow(ExceptionSupplier.fieldNotFound);
        fieldRepository.delete(fieldEntity);
    }

    public FieldResponseModel getFieldById(Integer id) {

        FieldEntity fieldEntity = fieldRepository.findById(id).orElseThrow(ExceptionSupplier.fieldNotFound);
        return fieldMapper.fieldEntityToFieldResponseModel(fieldEntity);

    }

    public FieldPageResponseModel<FieldResponseModel> getFields(Integer page, Integer size) {

        propertyValidator.pageSizeValidation(page, size);

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<FieldEntity> fieldEntities = fieldRepository.findAll(pageable);

        List<FieldResponseModel> allFields = FieldMapper.INSTANCE
                .fieldEntityToFieldResponseModels(fieldEntities.getContent());

        return new FieldPageResponseModel<>(allFields, fieldEntities.getTotalPages(),
                fieldEntities.getNumber() + 1, allFields.size());
    }

    public void updateField(Integer id, FieldRequestDto fieldRequestDto) {

        checkPermission();

        Integer loggedInUserId = getLoggedInUserId();
        Integer userId = userRepository.findById(loggedInUserId).orElseThrow(ExceptionSupplier.userNotFound).getId();

        FieldEntity fieldEntity = fieldRepository.findById(id).orElseThrow(ExceptionSupplier.fieldNotFound);

        if (fieldRequestDto.formId() != null) {
            FormEntity formEntity = formRepository.findById(fieldRequestDto.formId()).orElseThrow(ExceptionSupplier.formNotFound);
            fieldEntity.setFormEntity(formEntity);
        } else {
            fieldEntity.setFormEntity(fieldEntity.getFormEntity());
        }

        if (fieldRequestDto.name() != null) {
            fieldEntity.setName(fieldRequestDto.name());
        } else {
            fieldEntity.setName(fieldEntity.getName());
        }

        if (fieldRequestDto.orderDisplay() != null) {
            if (fieldRequestDto.orderDisplay() <= 0) {
                throw ExceptionSupplier.orderDisplayMustBeGreaterThanZero.get();
            }
            fieldEntity.setOrderDisplay(fieldRequestDto.orderDisplay());
        } else {
            fieldEntity.setOrderDisplay(fieldEntity.getOrderDisplay());
        }

        if (fieldRequestDto.type() != null) {
            if (ObjectUtils.isEmpty(fieldRequestDto.type().name()) ||
                    !fieldRequestDto.type().name().equals("TEXT") &&
                            !fieldRequestDto.type().name().equals("NUMBER")) {
                throw ExceptionSupplier.typeIsNotValid.get();
            }
            fieldEntity.setType(fieldRequestDto.type());
        } else {
            fieldEntity.setType(fieldEntity.getType());
        }

        fieldEntity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        fieldEntity.setLastUserToModify(userId);

        fieldEntity.getFormEntity().setLastUserToModify(userId);
        fieldEntity.getFormEntity().setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        fieldRepository.save(fieldEntity);
    }

    public void fillField(FillFieldRequestDto fillFieldRequestDto) {

        Optional<FieldUserEntity> byFormUserEntityIdAndFieldEntityId = fieldUserRepository
                .findByFormUserEntityIdAndFieldEntityId(fillFieldRequestDto.filledFormId(),
                        fillFieldRequestDto.fieldId());

        if (byFormUserEntityIdAndFieldEntityId.isPresent()) {
            throw ExceptionSupplier.fieldAlreadyFilled.get();
        }

        if (ObjectUtils.isEmpty(fillFieldRequestDto.textValue())) {
            throw ExceptionSupplier.textValueIsNotValid.get();
        }

        if (fillFieldRequestDto.numberValue() != null && fillFieldRequestDto.numberValue() < 0) {
            throw ExceptionSupplier.numberValueIsNotValid.get();
        }

        FormUserEntity filledFormEntity = formUserRepository.findById(fillFieldRequestDto.filledFormId())
                .orElseThrow(ExceptionSupplier.filledFormNotFound);

        FieldEntity fieldEntity = fieldRepository.findById(fillFieldRequestDto.fieldId())
                .orElseThrow(ExceptionSupplier.fieldNotFound);

        FieldUserEntity fieldUserEntity = FieldUserEntity.builder()
                .formUserEntity(filledFormEntity)
                .numberValue(fillFieldRequestDto.numberValue())
                .textValue(fillFieldRequestDto.textValue())
                .fieldEntity(fieldEntity)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        filledFormEntity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        filledFormEntity.setLastUserToModify(getLoggedInUserId());

        fieldUserRepository.save(fieldUserEntity);
    }

    public FieldPageResponseModel<FieldResponseModel> getFilledFields(Integer page, Integer size) {

        propertyValidator.pageSizeValidation(page, size);

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<FieldUserEntity> fieldUserEntities = fieldUserRepository.findAll(pageable);

        List<FieldResponseModel> allFields = FieldMapper.INSTANCE
                .filledFieldEntityToFieldResponseModels(fieldUserEntities.getContent());

        return new FieldPageResponseModel<>(allFields, fieldUserEntities.getTotalPages(),
                fieldUserEntities.getNumber() + 1, allFields.size());

    }

    public void deleteFilledField(Integer id) {
        checkPermission();

        FieldUserEntity fieldUserEntity = fieldUserRepository.findById(id)
                .orElseThrow(ExceptionSupplier.filledFieldNotFound);

        fieldUserRepository.delete(fieldUserEntity);
    }
}
