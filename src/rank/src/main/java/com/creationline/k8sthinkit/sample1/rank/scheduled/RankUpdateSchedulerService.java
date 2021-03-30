package com.creationline.k8sthinkit.sample1.rank.scheduled;

import java.time.Clock;
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

/**
 * 順位の定期更新サービス
 */
@Component
public class RankUpdateSchedulerService {

    /** ログ出力 */
    private static final Logger LOGGER = LoggerFactory.getLogger(RankUpdateSchedulerService.class);

    /** アクセスランキング更新サービス */
    private final RankCalculationService rankCalculationService;

    /** システム時計 */
    private final Clock systemClock;

    /**
     * コンストラクタインジェクションのためのコンストラクタ
     * 
     * @param rankCalculationService アクセスランキング更新サービス
     */
    @Autowired
    public RankUpdateSchedulerService( //

        @NonNull //
        final RankCalculationService rankCalculationService, //

        @NonNull //
        final Clock systemClock //

    ) {
        
        this.rankCalculationService = rankCalculationService;
        this.systemClock = systemClock;

    }

    /**
     * 日次更新
     */
    @Scheduled( //
        cron = "${rank.schedule.daily}", //
        zone = "${timezone:}" //
    )
    public void dailyUpdate() {

        final LocalDate today = LocalDate.now(this.systemClock);

        LOGGER.debug("start scheduled daily rank update for {}", today);

        final Flux<DailyTotalEntry> updatedTotalRanks = this.rankCalculationService.updateDailyTotalAccessRank(today);
        final Flux<DailyUniqueEntry> updatedUniqueRanks = this.rankCalculationService.updateDailyUniqueAccessRank(today);

        Flux.zip(updatedTotalRanks, updatedUniqueRanks).blockLast();

        LOGGER.debug("complete scheduled daily rank update for {}", today);

    }

}
