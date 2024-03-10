package com.formapp.damnjan.services;

import com.formapp.damnjan.entities.StatisticEntity;
import com.formapp.damnjan.repositories.StatisticRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StatisticService {

    private final FormService formService;
    private final StatisticRepository statisticRepository;

    public StatisticService(FormService formService, StatisticRepository statisticRepository) {
        this.formService = formService;
        this.statisticRepository = statisticRepository;
    }

    public void logStatisticForCurrentDay() {

        int count = formService.countNumberOfPopulatedFormsForDayBefore();
        StatisticEntity statisticEntity = StatisticEntity.builder()
                .date(new Date())
                .numberOfPopulatedForms(count)
                .build();
        statisticRepository.save(statisticEntity);
    }
}
