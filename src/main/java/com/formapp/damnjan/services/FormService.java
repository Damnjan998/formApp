package com.formapp.damnjan.services;

import com.formapp.damnjan.repositories.FormRepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class FormService {

    private final FormRepository formRepository;

    public FormService(FormRepository formRepository) {
        this.formRepository = formRepository;
    }

    public int countNumberOfPopulatedFormsForDayBefore() {

        LocalDate currentDate = LocalDate.now();
        LocalDate dayBefore = currentDate.minusDays(1);

        return formRepository.countByCreatedAt(Timestamp.valueOf(dayBefore.atStartOfDay()));
    }
}
