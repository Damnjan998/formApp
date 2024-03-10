package com.formapp.damnjan.schedulers;

import com.formapp.damnjan.services.StatisticService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StatisticScheduler {

    private final StatisticService statisticService;

    public StatisticScheduler(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void logStatistic() {
        statisticService.logStatisticForCurrentDay();
    }
}
