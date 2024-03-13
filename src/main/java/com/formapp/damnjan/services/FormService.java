package com.formapp.damnjan.services;

import com.formapp.damnjan.entities.FormEntity;
import com.formapp.damnjan.entities.FormUserEntity;
import com.formapp.damnjan.exceptions.ExceptionSupplier;
import com.formapp.damnjan.mappers.FormMapper;
import com.formapp.damnjan.models.request.FillFormRequestDto;
import com.formapp.damnjan.models.request.FormRequestDto;
import com.formapp.damnjan.models.response.FilledFormResponseModel;
import com.formapp.damnjan.models.response.FormPageResponseModel;
import com.formapp.damnjan.models.response.FormResponseModel;
import com.formapp.damnjan.repositories.FormRepository;
import com.formapp.damnjan.repositories.FormUserRepository;
import com.formapp.damnjan.repositories.UserRepository;
import com.formapp.damnjan.validators.PropertyValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.formapp.damnjan.utils.PrincipalHelper.*;

@Service
public class FormService {

    private final FormRepository formRepository;
    private final PropertyValidator propertyValidator;
    private final FormMapper formMapper = FormMapper.INSTANCE;
    private final FormUserRepository formUserRepository;
    private final UserRepository userRepository;


    public FormService(FormRepository formRepository,
                       PropertyValidator formValidator,
                       FormUserRepository formUserRepository,
                       UserRepository userRepository) {
        this.formRepository = formRepository;
        this.propertyValidator = formValidator;
        this.formUserRepository = formUserRepository;
        this.userRepository = userRepository;
    }

    public int countNumberOfPopulatedFormsForDayBefore() {

        LocalDate currentDate = LocalDate.now();
        LocalDate dayBefore = currentDate.minusDays(1);

        Timestamp startOfDayTimestamp = Timestamp.valueOf(dayBefore.atTime(LocalTime.MIN));
        Timestamp endOfDayTimestamp = Timestamp.valueOf(dayBefore.atTime(LocalTime.MAX));
        return formUserRepository.countByCreatedAtAfterAndCreatedAtBefore(startOfDayTimestamp, endOfDayTimestamp);
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
        checkPermission();

        propertyValidator.isNameValid(formRequestDto.name());

        Integer loggedInUserId = getLoggedInUserId();
        Integer userId = userRepository.findById(loggedInUserId).orElseThrow(ExceptionSupplier.userNotFound).getId();

        FormEntity formEntity = formRepository.findById(id).orElseThrow(ExceptionSupplier.formNotFound);
        formEntity.setName(formRequestDto.name());
        formEntity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        formEntity.setLastUserToModify(userId);

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

    public void fillForm(FillFormRequestDto fillFormRequestDto) {

        FormEntity formEntity = formRepository.findById(fillFormRequestDto.formId())
                .orElseThrow(ExceptionSupplier.formNotFound);

        FormUserEntity formUserEntity = FormUserEntity.builder()
                .formEntity(formEntity)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        formUserRepository.save(formUserEntity);
    }

    public void deleteFilledForm(Integer id) {
        checkPermission();

        FormUserEntity formUserEntity = formUserRepository.findById(id)
                .orElseThrow(ExceptionSupplier.filledFormNotFound);

        formUserRepository.delete(formUserEntity);
    }

    public FilledFormResponseModel getFilledFormById(Integer id) {

        FormUserEntity formUserEntity = formUserRepository.findById(id).orElseThrow(ExceptionSupplier.filledFormNotFound);

        return FormMapper.INSTANCE.formUserEntityToFilledFormResponseModel(formUserEntity);

    }

    public FormPageResponseModel<FilledFormResponseModel> getFilledForms(Integer page, Integer size) {

        propertyValidator.pageSizeValidation(page, size);

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<FormUserEntity> formUserEntities = formUserRepository.findAll(pageable);

        List<FilledFormResponseModel> allForms = FormMapper.INSTANCE
                .formUserEntityToFilledFormResponseModels(formUserEntities.getContent());

        return new FormPageResponseModel<>(allForms, formUserEntities.getTotalPages(),
                formUserEntities.getNumber() + 1, allForms.size());
    }
}
