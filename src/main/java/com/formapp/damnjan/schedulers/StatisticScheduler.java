package com.formapp.damnjan.schedulers;

import com.formapp.damnjan.services.StatisticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class StatisticScheduler {

    private final StatisticService statisticService;
    Logger logger = LoggerFactory.getLogger(StatisticScheduler.class);

    public StatisticScheduler(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @Scheduled(cron = "0 0 0 * * ?", zone = "Europe/Paris")
    public void logStatistic() {
        logger.info("Logging in statistic table");
        statisticService.logStatisticForCurrentDay();
    }
}
