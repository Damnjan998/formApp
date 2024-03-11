package com.formapp.damnjan.services;

import com.formapp.damnjan.entities.FormEntity;
import com.formapp.damnjan.exceptions.ExceptionSupplier;
import com.formapp.damnjan.mappers.FormMapper;
import com.formapp.damnjan.models.request.FormRequestDto;
import com.formapp.damnjan.models.response.FormPageResponseModel;
import com.formapp.damnjan.models.response.FormResponseModel;
import com.formapp.damnjan.repositories.FormRepository;
import com.formapp.damnjan.validators.PropertyValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.formapp.damnjan.utils.PrincipalHelper.checkPermission;

@Service
public class FormService {

    private final FormRepository formRepository;
    private final PropertyValidator propertyValidator;
    private final FormMapper formMapper = FormMapper.INSTANCE;


    public FormService(FormRepository formRepository, PropertyValidator formValidator) {
        this.formRepository = formRepository;
        this.propertyValidator = formValidator;
    }

    public int countNumberOfPopulatedFormsForDayBefore() {

        LocalDate currentDate = LocalDate.now();
        LocalDate dayBefore = currentDate.minusDays(1);

        return formRepository.countByCreatedAt(Timestamp.valueOf(dayBefore.atStartOfDay()));
    }

    public void createForm(FormRequestDto formRequestDto) {

        checkPermission();
        propertyValidator.isNameValid(formRequestDto.name());

        FormEntity formEntity = formMapper.createFormRequestDtoToFormEntity(formRequestDto);
        formEntity.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        formRepository.save(formEntity);

    }

    public void deleteForm(Integer id) {
        checkPermission();

        FormEntity formEntity = formRepository.findById(id).orElseThrow(ExceptionSupplier.formNotFound);

        formRepository.delete(formEntity);
    }

    public void updateForm(Integer id, FormRequestDto formRequestDto) {
        propertyValidator.isNameValid(formRequestDto.name());

        checkPermission();

        FormEntity formEntity = formRepository.findById(id).orElseThrow(ExceptionSupplier.formNotFound);
        formEntity.setName(formRequestDto.name());
        formEntity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        formRepository.save(formEntity);

    }

    public FormPageResponseModel<FormResponseModel> getForms(Integer page, Integer size) {

        propertyValidator.pageSizeValidation(page, size);

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<FormEntity> formEntities = formRepository.findAll(pageable);

        List<FormResponseModel> allForms = FormMapper.INSTANCE
                .formEntityToFormResponseModels(formEntities.getContent());

        return new FormPageResponseModel<>(allForms, formEntities.getTotalPages(),
                formEntities.getNumber() + 1, allForms.size());
    }

    public FormResponseModel getFormById(Integer id) {

        FormEntity formEntity = formRepository.findById(id).orElseThrow(ExceptionSupplier.formNotFound);

        return FormMapper.INSTANCE.formEntityToFormResponseModel(formEntity);
    }
}
