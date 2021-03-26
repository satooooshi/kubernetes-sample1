package com.creationline.k8sthinkit.sample1.rank.scheduled;

import java.time.LocalDate;

import com.creationline.k8sthinkit.sample1.rank.repository.entity.DailyTotalEntry;
import com.creationline.k8sthinkit.sample1.rank.repository.entity.DailyUniqueEntry;
import com.creationline.k8sthinkit.sample1.rank.service.ranking.RankCalculationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;

@Component
public class RankUpdateSchedulerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RankUpdateSchedulerService.class);

    private static final String DAILY_UPDATE_SCHEDULE_CRON = "0 0 0 */1 * *";

    @NonNull
    private final RankCalculationService rankCalculationService;

    @Autowired
    public RankUpdateSchedulerService(

        @NonNull //
        final RankCalculationService rankCalculationService //

    ) {
        
        this.rankCalculationService = rankCalculationService;
    }

    @Scheduled(cron = DAILY_UPDATE_SCHEDULE_CRON)
    public void dailyUpdate() {

        final LocalDate today = LocalDate.now();

        LOGGER.debug("start scheduled daily rank update for {}", today);

        final Flux<DailyTotalEntry> updatedTotalRanks = this.rankCalculationService.updateDailyTotalAccessRank(today);
        final Flux<DailyUniqueEntry> updatedUniqueRanks = this.rankCalculationService.updateDailyUniqueAccessRank(today);

        Flux.zip(updatedTotalRanks, updatedUniqueRanks).blockLast();

        LOGGER.debug("complete scheduled daily rank update for {}", today);

    }

}
