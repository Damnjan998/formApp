package com.formapp.damnjan.services;

import com.formapp.damnjan.entities.FormEntity;
import com.formapp.damnjan.exceptions.ExceptionSupplier;
import com.formapp.damnjan.exceptions.FormException;
import com.formapp.damnjan.mappers.FormMapper;
import com.formapp.damnjan.models.request.FormRequestDto;
import com.formapp.damnjan.models.response.FormPageResponseModel;
import com.formapp.damnjan.models.response.FormResponseModel;
import com.formapp.damnjan.repositories.FormRepository;
import com.formapp.damnjan.repositories.FormUserRepository;
import com.formapp.damnjan.repositories.UserRepository;
import com.formapp.damnjan.utils.ErrorMessages;
import com.formapp.damnjan.utils.PrincipalHelper;
import com.formapp.damnjan.validators.PropertyValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FormServiceTest {

    @Mock
    FormRepository formRepository;
    @Mock
    PropertyValidator propertyValidator;
    @Mock
    FormUserRepository formUserRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    PrincipalHelper principalHelper;
    private final FormMapper formMapper = mock(FormMapper.class);

    FormEntity formEntity;

    @BeforeEach
    void setUp() {
        formEntity = new FormEntity();
    }

    @InjectMocks
    FormService formService;

    @Test
    void countNumberOfPopulatedFormsForDayBefore() {

        LocalDate currentDate = LocalDate.now();
        LocalDate dayBefore = currentDate.minusDays(1);

        Timestamp startOfDayTimestamp = Timestamp.valueOf(dayBefore.atTime(LocalTime.MIN));
        Timestamp endOfDayTimestamp = Timestamp.valueOf(dayBefore.atTime(LocalTime.MAX));

        int expectedCount = 1;
        when(formUserRepository.countByCreatedAtAfterAndCreatedAtBefore(
                eq(startOfDayTimestamp),
                eq(endOfDayTimestamp)
        )).thenReturn(expectedCount);

        long actualCount = formService.countNumberOfPopulatedFormsForDayBefore();

        assertEquals(expectedCount, actualCount);

        verify(formUserRepository, times(1)).countByCreatedAtAfterAndCreatedAtBefore(any(), any());
    }

    @Test
    void createForm() {

        FormRequestDto formRequestDto = new FormRequestDto("ExampleName");
        formEntity.setName(formRequestDto.name());

        try (MockedStatic<PrincipalHelper> mocked = mockStatic(PrincipalHelper.class)) {

            mocked.when(PrincipalHelper::checkPermission).then(invocation -> null);

            doNothing().when(propertyValidator).isNameValid(eq("ExampleName"));
            when(formMapper.createFormRequestDtoToFormEntity(eq(formRequestDto)))
                    .thenReturn(formEntity);

            formService.createForm(formRequestDto);

            verify(propertyValidator, times(1)).isNameValid(eq("ExampleName"));
            verify(formRepository, times(1)).save(any(FormEntity.class));
        }
    }

    @Test
    void deleteForm() {

        int id = 1;

        try (MockedStatic<PrincipalHelper> mocked = mockStatic(PrincipalHelper.class)) {

            mocked.when(PrincipalHelper::checkPermission).then(invocation -> null);

            when(formRepository.findById(eq(id))).thenReturn(Optional.of(formEntity));

            formService.deleteForm(id);

            verify(formRepository, times(1)).findById(eq(id));
            verify(formRepository, times(1)).delete(eq(formEntity));
        }
    }

    @Test
    void getForms() {

        int page = 1;
        int size = 1;

        ArrayList<FormEntity> formEntities = new ArrayList<>();
        formEntities.add(formEntity);

        when(formRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(formEntities));

        FormPageResponseModel<FormResponseModel> forms = formService.getForms(page, size);

        verify(formRepository, times(1)).findAll(any(Pageable.class));
        assertEquals(formEntities.size(), forms.getCount());
    }

    @Test
    void getFormById() {

        int id = 1;
        formEntity.setId(id);
        formEntity.setName("Test");
        FormResponseModel expectedResponseModel = new FormResponseModel();
        expectedResponseModel.setName("Test");

        when(formRepository.findById(eq(id))).thenReturn(Optional.of(formEntity));

        FormResponseModel actualResponseModel = formService.getFormById(id);

        verify(formRepository, times(1)).findById(eq(id));
        assertEquals(expectedResponseModel.getName(), actualResponseModel.getName());
    }

    @Test
    void should_throw_user_not_found_exception() {
        Integer id = 1;

        try (MockedStatic<PrincipalHelper> mocked = mockStatic(PrincipalHelper.class)) {

            mocked.when(PrincipalHelper::checkPermission).then(invocation -> null);

            when(formRepository.findById(eq(id))).thenReturn(Optional.empty());

            FormException exception = Assertions.assertThrows(FormException.class, () -> {
                formService.deleteForm(id);
            });

            assertEquals(ErrorMessages.FORM_NOT_FOUND.getErrorMessage(), exception.getMessage());

            verify(formRepository, times(1)).findById(eq(id));
            verify(formRepository, never()).delete(any(FormEntity.class));
        }
    }
}