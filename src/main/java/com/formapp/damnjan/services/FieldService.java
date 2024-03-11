package com.formapp.damnjan.services;

import com.formapp.damnjan.entities.FieldEntity;
import com.formapp.damnjan.entities.FormEntity;
import com.formapp.damnjan.exceptions.ExceptionSupplier;
import com.formapp.damnjan.mappers.FieldMapper;
import com.formapp.damnjan.models.request.FieldRequestDto;
import com.formapp.damnjan.models.response.FieldPageResponseModel;
import com.formapp.damnjan.models.response.FieldResponseModel;
import com.formapp.damnjan.repositories.FieldRepository;
import com.formapp.damnjan.repositories.FormRepository;
import com.formapp.damnjan.validators.PropertyValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static com.formapp.damnjan.utils.PrincipalHelper.checkPermission;

@Service
public class FieldService {

    private final FieldRepository fieldRepository;
    private final FormRepository formRepository;
    private final PropertyValidator propertyValidator;
    private final FieldMapper fieldMapper = FieldMapper.INSTANCE;

    public FieldService(FieldRepository fieldRepository,
                        FormRepository formRepository,
                        PropertyValidator propertyValidator) {
        this.fieldRepository = fieldRepository;
        this.formRepository = formRepository;
        this.propertyValidator = propertyValidator;
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

        propertyValidator.fieldPropertiesValidation(fieldRequestDto);
        checkPermission();

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
            fieldEntity.setOrderDisplay(fieldRequestDto.orderDisplay());
        } else {
            fieldEntity.setOrderDisplay(fieldEntity.getOrderDisplay());
        }

        if (fieldRequestDto.type() != null) {
            fieldEntity.setType(fieldRequestDto.type());
        } else {
            fieldEntity.setType(fieldEntity.getType());
        }

        fieldEntity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        fieldRepository.save(fieldEntity);
    }
}
